package org.wizunion.jvm.model;

import com.google.common.primitives.Shorts;

import java.util.ArrayList;
import java.util.List;

public class AccessFlag {
    private short flag;
    private List<String> accessNameList = new ArrayList<>();
    private String accessName;

    public AccessFlag(byte[] payload, int begin) {
        flag = Shorts.fromBytes(payload[begin], payload[begin + 1]);

        if ((flag & 0x0001) == 0x0001) {
            accessNameList.add("ACC_PUBLIC");
        }
        if ((flag & 0x0010) == 0x0010) {
            accessNameList.add("ACC_FINAL");
        }
        if ((flag & 0x0020) == 0x0020) {
            accessNameList.add("ACC_SUPER");
        }
        if ((flag & 0x0200) == 0x0200) {
            accessNameList.add("ACC_INTERFACE");
        }
        if ((flag & 0x0400) == 0x0400) {
            accessNameList.add("ACC_ABSTRACT");
        }
        if ((flag & 0x1000) == 0x1000) {
            accessNameList.add("ACC_SYNTHETIC");
        }
        if ((flag & 0x2000) == 0x2000) {
            accessNameList.add("ACC_ANNOTATION");
        }
        if ((flag & 0x4000) == 0x4000) {
            accessNameList.add("ACC_ENUM");
        }

    }

    public short getFlag() {
        return flag;
    }

    public String getAccessName() {
        return String.join(",", accessNameList);
    }

    @Override
    public String toString() {
        return String.format("access flag:%s",getAccessName());
    }
}
