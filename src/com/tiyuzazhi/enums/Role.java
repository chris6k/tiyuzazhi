package com.tiyuzazhi.enums;

/**
 * @author chris.xue
 */
public enum Role {
    UNLOGIN(0, "未登录"), AUTHOR(1, "作者"), EDITOR(2, "编辑"), CHIEF_EDITOR(4, "主编"), MASTER(8, "专家");

    private final int code;
    private final String name;

    Role(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
