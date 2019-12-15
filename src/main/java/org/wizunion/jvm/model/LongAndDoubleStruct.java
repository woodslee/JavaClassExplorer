package org.wizunion.jvm.model;

public class LongAndDoubleStruct extends BaseStruct{
    //u4
    private byte[] HighBytes;
    private byte[] lowBytes;
    public LongAndDoubleStruct(byte[] payload, int begin){
        super(payload,begin);
        System.arraycopy(payload,begin+1,HighBytes,0,4);
        System.arraycopy(payload,begin+5,lowBytes,0,4);
        setPayloadLen(1+4+4);
    }
}
