package com.minsoo.co.tireerpserver.model.code;

public enum PatternOption {

    QUIETNESS("정숙성"),
    RIDE_QUALITY("승차감"),
    MILEAGE("마일리지"),
    HANDLING("핸들링"),
    BREAKING_POWER("제동력"),
    SPORTS("스포츠"),
    WET_SURFACE("젖은노면"),
    ;

    private final String name;

    PatternOption(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
