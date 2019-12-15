package org.wizunion.jvm.model;

public class BaseStruct {
    public static final String POOL_ITEM_FMT="%-25s%-20s%-20s";
    //u1
    private byte tag;
    private int payloadLen;

    public int getPayloadLen() {
        return payloadLen;
    }

    public void setPayloadLen(int payloadLen) {
        this.payloadLen = payloadLen;
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public BaseStruct(byte[] payload,int begin){
        tag = payload[begin];
    }

    public BaseStruct(){}
    public String getTagName(){
        return StructConstant.CONST_POOL_NAMES[tag];
    }
}
