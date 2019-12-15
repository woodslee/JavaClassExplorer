package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

import java.io.UnsupportedEncodingException;

public class UTF8Struct extends BaseStruct{
    private short length;
    private byte[] bytes;
    private String s;

    public UTF8Struct(byte[] payload, int begin){
        super(payload,begin);
        length= Shorts.fromBytes(payload[begin+1],payload[begin+2]);
        bytes=new byte[length];
        System.arraycopy(payload,begin+3,bytes,0,length);

        try {
            s=new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setPayloadLen(1+2+length);
    }

    public String toString(){
        return String.format(POOL_ITEM_FMT,"UTF8",s,"");
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

}
