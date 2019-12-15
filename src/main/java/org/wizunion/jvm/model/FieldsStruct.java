package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

import java.util.Arrays;

public class FieldsStruct extends BaseStruct{
    private short accessFlag;
    private short nameIndex;
    private UTF8Struct name;
    private short descriptorIndex;
    private UTF8Struct descriptor;
    private short attributeCnt;
    private AttributeStruct[] attributes;

    public FieldsStruct(byte[] payload,int begin){
        accessFlag= Shorts.fromBytes(payload[begin],payload[begin+1]);
        nameIndex=Shorts.fromBytes(payload[begin+2],payload[begin+3]);
        descriptorIndex=Shorts.fromBytes(payload[begin+4],payload[begin+5]);
        attributeCnt=Shorts.fromBytes(payload[begin+6],payload[begin+7]);

        begin=begin+8;

        int attriLen=0;
        attributes=new AttributeStruct[attributeCnt];
        for(int i=0;i<attributeCnt;i++){
            attributes[i]=new AttributeStruct(payload,begin);
            begin=begin+attributes[i].getPayloadLen();
            attriLen=attriLen+attributes[i].getPayloadLen();
        }

        setPayloadLen(2+2+2+2+attriLen);
    }

    public short getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(short accessFlag) {
        this.accessFlag = accessFlag;
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(short nameIndex) {
        this.nameIndex = nameIndex;
    }

    public UTF8Struct getName() {
        return name;
    }

    public void setName(UTF8Struct name) {
        this.name = name;
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(short descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public UTF8Struct getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(UTF8Struct descriptor) {
        this.descriptor = descriptor;
    }

    public short getAttributeCnt() {
        return attributeCnt;
    }

    public void setAttributeCnt(short attributeCnt) {
        this.attributeCnt = attributeCnt;
    }

    public AttributeStruct[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeStruct[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
       StringBuilder sb=new StringBuilder();
       sb.append(String.format("%-20s:%-40s",name.getS(),descriptor.getS()));
       sb.append("\n").append("atttributes:total"+attributeCnt).append("\n");
       for(AttributeStruct as:attributes){
           sb.append(as).append("\n");
       }
       return sb.toString();
    }
}
