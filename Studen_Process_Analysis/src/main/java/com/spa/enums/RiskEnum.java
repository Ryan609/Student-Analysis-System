package com.spa.enums;


public enum RiskEnum {
    NORMAL("Normal"),
    RISK1("Risk1"),
    RISK2("Risk2"),
    UNSATISFACTORY("Unsatisfactory");

    private String risk;


    RiskEnum(String risk) {
        this.risk = risk;
    }

    public String getRisk() {
        return risk;
    }

}
