package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

public class StringStruct extends BaseStruct {
    private short stringIndex;
    private UTF8Struct className;

    public StringStruct(byte[] payload, int begin){
        super(payload,begin);
        stringIndex =Shorts.fromBytes(payload[begin+1],payload[begin+2]);
        setPayloadLen(1+2);
    }
    public short getStringIndex() {
        return stringIndex;
    }

    public void setStringIndex(short stringIndex) {
        this.stringIndex = stringIndex;
    }

    public UTF8Struct getClassName() {
        return className;
    }

    public void setClassName(UTF8Struct className) {
        this.className = className;
    }

    public String getS(){
        return className.getS();
    }
    @Override
    public String toString() {
        return String.format(POOL_ITEM_FMT,"String","#"+stringIndex,"// "+getS());
    }
}
