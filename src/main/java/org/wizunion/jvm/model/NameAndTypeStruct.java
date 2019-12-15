package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

public class NameAndTypeStruct extends BaseStruct{
    //u4
    private short nameIndex;
    private short descriptorIndex;
    private UTF8Struct name;
    private UTF8Struct descriptor;
    public NameAndTypeStruct(byte[] payload, int begin){
        super(payload,begin);
        nameIndex= Shorts.fromBytes(payload[begin+1],payload[begin+2]);
        descriptorIndex=Shorts.fromBytes(payload[begin+3],payload[begin+4]);
        setPayloadLen(1+2+2);
    }

    public UTF8Struct getName() {
        return name;
    }

    public void setName(UTF8Struct name) {
        this.name = name;
    }

    public UTF8Struct getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(UTF8Struct descriptor) {
        this.descriptor = descriptor;
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(short nameIndex) {
        this.nameIndex = nameIndex;
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(short descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }
    public String getS(){
       return name.getS()+":"+descriptor.getS();
    }
    @Override
    public String toString() {
       return String.format(POOL_ITEM_FMT,"NameAndType","#"+nameIndex+".#"+descriptorIndex,"// " +getS());
    }
}
