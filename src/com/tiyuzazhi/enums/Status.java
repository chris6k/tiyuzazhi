package com.tiyuzazhi.enums;

/**
 * @author chris.xue
 *         审核状态
 */
public enum Status {
    WAIT(0, "待审核", "专家正在审稿，请耐心等待，感谢你的支持"), DONE(1, "已审核", "已审核");
    private final int code;
    private final String text;
    private final String system;

    Status(int code, String text, String system) {
        this.code = code;
        this.text = text;
        this.system = system;
    }

    public String getSystem() {
        return system;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static Status findStatusByCode(int code) {
        for (Status status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return WAIT;
    }
}
