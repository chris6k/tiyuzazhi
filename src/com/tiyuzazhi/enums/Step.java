package com.tiyuzazhi.enums;

/**
 * @author chris.xue
 *         审核状态
 */
public enum Step {
    NEW(0, "投稿", "作者:投稿"), EDITOR_OK(1, "通过", "编辑审稿:通过"), EDITOR_REJECT(2, "退稿", "编辑审稿:退稿"),
    MASTER_OK(3, "通过", "专家审稿:通过"), MASTER_REJECT(4, "退稿", "专家审稿:退稿"), CHIEF_OK(5, "通过", "主编审稿:通过"),
    CHIEF_REJECT(6, "退稿", "主编审稿:退稿"), RETRY(7, "退修", "作者:重投稿");
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
