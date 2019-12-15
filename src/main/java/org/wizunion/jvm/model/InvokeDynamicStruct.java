package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

public class InvokeDynamicStruct extends BaseStruct{
    //u4
    private short boostrapMethodAttrIndex;
    private short nameAndTypeIndex;
    public InvokeDynamicStruct(byte[] payload, int begin){
        super(payload,begin);
        boostrapMethodAttrIndex = Shorts.fromBytes(payload[begin+1],payload[begin+2]);
        nameAndTypeIndex =Shorts.fromBytes(payload[begin+3],payload[begin+4]);
        setPayloadLen(1+2+2);
    }
}
