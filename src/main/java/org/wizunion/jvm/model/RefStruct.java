package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

public class RefStruct extends BaseStruct {
    private short classIndex;
    private short nameAndTypeIndex;
    private ClassStruct clazz;
    private NameAndTypeStruct nameAndType;

    public RefStruct(byte[] payload, int begin){
        super(payload,begin);
        classIndex= Shorts.fromBytes(payload[begin+1],payload[begin+2]);
        nameAndTypeIndex=Shorts.fromBytes(payload[begin+3],payload[begin+4]);
        setPayloadLen(1+2+2);
    }

    public short getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(short classIndex) {
        this.classIndex = classIndex;
    }

    public short getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(short nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public ClassStruct getClazz() {
        return clazz;
    }

    public void setClazz(ClassStruct clazz) {
        this.clazz = clazz;
    }

    public NameAndTypeStruct getNameAndType() {
        return nameAndType;
    }

    public void setNameAndType(NameAndTypeStruct nameAndType) {
        this.nameAndType = nameAndType;
    }

    public String getName(){
        switch (getTag()){
            case 9: return "FieldRef";
            case 10:return "MethodRef";
            case 11:return "InterfaceMethodRef";
            default: throw new RuntimeException("Unsupported tag:"+getTag());
        }
    }
    public String getS(){
        return clazz.getS()+"."+nameAndType.getS();
    }
    @Override
    public String toString() {
       return String.format(POOL_ITEM_FMT,getName(),"#"+classIndex+".#"+nameAndTypeIndex,"// "+getS());
    }
}
