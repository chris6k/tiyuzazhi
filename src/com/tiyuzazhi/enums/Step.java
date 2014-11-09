package com.tiyuzazhi.enums;

/**
 * @author chris.xue
 *         审核状态
 */
public enum Step {
    NEW(6, "收稿", "作者: 收稿"), FIRST_MANU(7, "初审", "编辑审稿: 初审"), SECOND_MANU(8, "复审", "编辑审稿: 复审"),
    EXTERNAL_MANU(8, "外审", "专家审稿: 外审"), REDO(10, "退修", "审稿: 退修"), REJECT(11, "退稿", "退稿"),
    EDITOR_MODIFY(12, "编辑加工", "编辑加工"), PUBLISH(13, "发稿", "发稿"), FINAL(24, "终审", "终审");
    private final int code;
    private final String text;
    private final String system;

    Step(int code, String text, String system) {
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

    public static Step findStatusByCode(int step) {
        for (Step status : values()) {
            if (status.getCode() == step) {
                return status;
            }
        }
        return NEW;
    }
}
