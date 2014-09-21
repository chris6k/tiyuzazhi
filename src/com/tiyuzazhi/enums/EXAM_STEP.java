package com.tiyuzazhi.enums;

/**
 * @author chris.xue
 */
public enum EXAM_STEP {
    ZHONGSHEN(4, "终审"), WAISHEN(3, "外审"), TUIXIU(2, "退修"), SHOUGAO(1, "收稿");
    private final int code;
    private final String name;

    private EXAM_STEP(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static EXAM_STEP findByCode(int step) {
        for (EXAM_STEP examStep : values()) {
            if (examStep.getCode() == step) {
                return examStep;
            }
        }
        return EXAM_STEP.SHOUGAO;
    }
}
