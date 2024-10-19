package com.meysam.common.customsecurity.model.enums;

public enum UserTypeEnum {

    SUPERVISOR("01"),
    EXTERNAL_USER("02"),
    INTERNAL_USER("03");

    UserTypeEnum(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
