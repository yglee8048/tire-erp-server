package com.minsoo.co.tireerpserver.model.code;

public enum TireOption {
    RUN_FLAT("런플렛"),
    SPONGE("스펀지"),
    SEALING("실링")
    ;

    private String name;

    TireOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
