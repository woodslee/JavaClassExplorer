package org.wizunion.jvm;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;
import com.sun.tools.classfile.ClassTranslator;
import org.wizunion.jvm.model.*;
import sun.jvm.hotspot.jdi.ArrayReferenceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassFile {
 *     u4             magic;
 *     u2             minor_version;
 *     u2             major_version;
 *     u2             constant_pool_count;
 *     cp_info        constant_pool[constant_pool_count-1];
 *     u2             access_flags;
 *     u2             this_class;
 *     u2             super_class;
 *     u2             interfaces_count;
 *     u2             interfaces[interfaces_count];
 *     u2             fields_count;
 *     field_info     fields[fields_count];
 *     u2             methods_count;
 *     method_info    methods[methods_count];
 *     u2             attributes_count;
 *     attribute_info attributes[attributes_count];
 * }
 */
public class ClassExplorer implements TestInterfaceA,TestInterfaceB{
    private byte[] payload;
    private int magic;
    private int majorVer;
    private int minorVer;
    private List<BaseStruct> poolList=new ArrayList();

    private int contantPoolCnt;
    private AccessFlag accessFlag;

    private ClassStruct thisClass;

    private ClassStruct superClass;

    private short interfaceCnt;
    private List<ClassStruct> interfaceList=new ArrayList<>();
    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getMajorVer() {
        return majorVer;
    }

    public void setMajorVer(int majorVer) {
        this.majorVer = majorVer;
    }

    public int getMinorVer() {
        return minorVer;
    }

    public void setMinorVer(int minorVer) {
        this.minorVer = minorVer;
    }

    public ClassExplorer(String file) throws IOException {
        payload = Files.readAllBytes(Paths.get(file));
        init();
    }

    public void init() {
        magic= Ints.fromByteArray(getBytes(0,4));
        minorVer=Shorts.fromByteArray(getBytes(4,2));
        majorVer=Shorts.fromByteArray(getBytes(6,2));
        contantPoolCnt= Shorts.fromByteArray(getBytes(8,2));

        int begin=10;
        for(int i=1;i<contantPoolCnt;i++){
            BaseStruct obj=getStruct(begin);
            poolList.add(obj);
            begin=begin+obj.getPayloadLen();
        }

        for(BaseStruct bs:poolList){
            if(bs instanceof ClassStruct){
                ClassStruct obj=(ClassStruct)bs;
                BaseStruct s=poolList.get(obj.getNameIndex()-1);
                obj.setClassName((UTF8Struct)s);
            }
            if(bs instanceof NameAndTypeStruct){
                NameAndTypeStruct obj=(NameAndTypeStruct)bs;
                obj.setName((UTF8Struct)poolList.get(obj.getNameIndex()-1));
                obj.setDescriptor((UTF8Struct)poolList.get(obj.getDescriptorIndex()-1));
            }

            if(bs instanceof RefStruct){
                RefStruct obj=(RefStruct)bs;
                obj.setClazz((ClassStruct)poolList.get(obj.getClassIndex()-1));
                obj.setNameAndType((NameAndTypeStruct)poolList.get(obj.getNameAndTypeIndex()-1));
            }

            if(bs instanceof StringStruct){
                StringStruct obj=(StringStruct)bs;
                obj.setClassName((UTF8Struct)poolList.get(obj.getStringIndex()-1));
            }

        }
        //u2
        accessFlag = new AccessFlag(payload,begin);

        begin=begin+2;

        thisClass=new ClassStruct(payload,begin,0);
        thisClass=(ClassStruct)poolList.get(thisClass.getNameIndex()-1);

        begin=begin+2;

        superClass=new ClassStruct(payload,begin,0);
        if(superClass.getNameIndex()!=0){
            superClass=(ClassStruct)poolList.get(superClass.getNameIndex()-1);
        }

        begin=begin+2;
        interfaceCnt=Shorts.fromBytes(payload[begin],payload[begin+1]);


        begin=begin+2;
        if(interfaceCnt>0){
           for(int i=0;i<interfaceCnt;i++){
               short idx=Shorts.fromBytes(payload[begin],payload[begin+1]);
               interfaceList.add((ClassStruct)poolList.get(idx-1));
               begin=begin+2;
           }
        }

    }

    /**
     * CONSTANT_Utf8	            1
     * CONSTANT_Integer	            3
     * CONSTANT_Float	            4
     * CONSTANT_Long	            5
     * CONSTANT_Double	            6
     * CONSTANT_Class	            7
     * CONSTANT_String	            8
     * CONSTANT_Fieldref	        9
     * CONSTANT_Methodref	        10
     * CONSTANT_InterfaceMethodref	11
     * CONSTANT_NameAndType	        12
     * CONSTANT_MethodHandle	    15
     * CONSTANT_MethodType	        16
     * CONSTANT_InvokeDynamic	    18
     * @param begin
     * @return
     */
    public BaseStruct getStruct(int begin){
        BaseStruct bs=new BaseStruct(payload,begin);
        switch (bs.getTag()){
            case 1:{
                return new UTF8Struct(payload,begin);
            }
            case 3:
            case 4:{
                return new IntAndFloatStruct(payload,begin);
            }
            case 5:
            case 6:{
                return new LongAndDoubleStruct(payload,begin);
            }
            case 7:{
                return new ClassStruct(payload,begin);
            }
            case 8:{
                return new StringStruct(payload,begin);
            }
            case 9:
            case 10:
            case 11:{
                return new RefStruct(payload,begin);
            }
            case 12:{
                return new NameAndTypeStruct(payload,begin);
            }
            case 15:{
                return new MethodHandleStruct(payload,begin);
            }
            case 16:{
                return new MethodTypeStruct(payload,begin);
            }
            case 18:{
                return new InvokeDynamicStruct(payload,begin);
            }
            default: {
                throw new RuntimeException("Unsupported tag:"+bs.getTag());
            }
        }
    }
    public byte[] getBytes(int posi,int len){
        byte[] bytes=new byte[len];
        System.arraycopy(payload,posi,bytes,0,len);
        return bytes;
    }

    public String toString(){
        String strFmt="%35s:  %s";

        StringBuilder sb=new StringBuilder();
        sb.append("[version info]").append("\n");
        sb.append(String.format(strFmt,"Magic",Integer.toHexString(magic)).toUpperCase()).append("\n");
        sb.append(String.format(strFmt,"Major version",majorVer)).append("\n");
        sb.append(String.format(strFmt,"Minor version",minorVer)).append("\n");

        sb.append("[Constant pool],total count:"+(contantPoolCnt-1)).append("\n");
        for(int i=0;i<contantPoolCnt-1;i++){
            BaseStruct bs=poolList.get(i);
            String curFmt="%5s = %s\n";
            sb.append(String.format(curFmt,"#"+(i+1),bs.toString()));
        }

        sb.append("[Access flag]:").append(accessFlag.getAccessName()).append("\n");
        sb.append("[this_class]").append("\n").append(thisClass).append("\n");
        sb.append("[super_class]").append("\n").append(superClass);
        sb.append("\n[interface].count:"+interfaceCnt+"\n");
        for(ClassStruct cs:interfaceList){
            sb.append(cs).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void ta1() {

    }

    @Override
    public void tb1() {

    }
}
