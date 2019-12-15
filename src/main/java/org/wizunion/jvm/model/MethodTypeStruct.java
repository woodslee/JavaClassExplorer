package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

public class MethodTypeStruct extends BaseStruct {
    private short descriptorIndex;

    public MethodTypeStruct(byte[] payload, int begin){
        super(payload,begin);
        descriptorIndex =Shorts.fromBytes(payload[begin+1],payload[begin+2]);
        setPayloadLen(1+2);
    }
    public short getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(short descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

}
