package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

public class MethodHandleStruct extends BaseStruct{
    //u4
    private byte referenceKind;
    private short refrenceIndex;
    public MethodHandleStruct(byte[] payload, int begin){
        super(payload,begin);
        referenceKind=payload[begin+1];
        refrenceIndex=Shorts.fromBytes(payload[begin+2],payload[begin+3]);
        setPayloadLen(1+1+2);
    }
}
