package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

public class ClassStruct extends BaseStruct {
    private short nameIndex;
    private UTF8Struct className;

    public ClassStruct(byte[] payload,int begin){
        super(payload,begin);
        nameIndex =Shorts.fromBytes(payload[begin+1],payload[begin+2]);
        setPayloadLen(1+2);
    }

    public ClassStruct(byte[] payload,int begin,int flags){
        nameIndex =Shorts.fromBytes(payload[begin],payload[begin+1]);
        setPayloadLen(2);
    }
    public short getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(short nameIndex) {
        this.nameIndex = nameIndex;
    }

    public UTF8Struct getClassName() {
        return className;
    }

    public void setClassName(UTF8Struct className) {
        this.className = className;
    }

    public String getS(){
        if(className==null){
            return "java/lang/Object";
        }
        return className.getS();
    }
    public String toString(){
        return String.format(POOL_ITEM_FMT,"Class","#"+nameIndex,"// "+getS());
    }
}
