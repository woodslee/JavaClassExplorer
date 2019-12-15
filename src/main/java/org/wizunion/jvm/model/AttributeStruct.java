package org.wizunion.jvm.model;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class AttributeStruct extends BaseStruct{
    private short nameIndex;
    private UTF8Struct name;
    private int attributeLen;
    private byte[] info;

    public AttributeStruct(byte[] payload,int begin){
        nameIndex= Shorts.fromBytes(payload[begin],payload[begin+1]);
        byte[] bytes=new byte[4];
        System.arraycopy(payload,begin+2,bytes,0,4);
        attributeLen= Ints.fromByteArray(bytes);

        info=new byte[attributeLen];
        for(int i=0;i<attributeLen;i++){
            info[i]=payload[begin+6+i];
        }

        setPayloadLen(2+4+attributeLen);
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

    public int getAttributeLen() {
        return attributeLen;
    }

    public void setAttributeLen(int attributeLen) {
        this.attributeLen = attributeLen;
    }

    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
            sb.append(String.format("%-20s:%-10s%-30d",name.getS(),nameIndex,Shorts.fromBytes(info[0],info[1])));
        return sb.toString();
    }
}
