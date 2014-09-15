package com.tiyuzazhi.enums;

/**
 * @author chris.xue
 */
public enum ExaminerType {
    RECOMM(1, "快速推荐"), PSY(2, "体育心理学"), EDU(3, "体育教育");
    private final int code;
    private final String name;

    private ExaminerType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ExaminerType findByCode(int step) {
        for (ExaminerType type : values()) {
            if (type.getCode() == step) {
                return type;
            }
        }
        return ExaminerType.RECOMM;
    }
}
