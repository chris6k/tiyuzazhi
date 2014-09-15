package com.tiyuzazhi.enums;

/**
 * @author chris.xue
 */
public enum Category {
    ZHONGSHEN(4, "终审"), WAISHEN(3, "外审"), TUIXIU(2, "退修"), SHOUGAO(1, "收稿");
    private final int code;
    private final String name;

    private Category(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Category findByCode(int step) {
        for (Category category : values()) {
            if (category.getCode() == step) {
                return category;
            }
        }
        return Category.SHOUGAO;
    }
}
