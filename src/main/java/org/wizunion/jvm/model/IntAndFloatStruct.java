package org.wizunion.jvm.model;

public class IntAndFloatStruct extends BaseStruct{
    //u4
    private byte[] bytes;
    public IntAndFloatStruct(byte[] payload,int begin){
        super(payload,begin);
        System.arraycopy(payload,begin+1,bytes,0,4);
        setPayloadLen(1+4);
    }
}
