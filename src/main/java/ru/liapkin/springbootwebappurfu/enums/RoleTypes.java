package ru.liapkin.springbootwebappurfu.enums;

public enum RoleTypes {
    ADMIN("ROLE_ADMIN"),
    READER("ROLE_READER"),
    USER("ROLE_USER");

    private final String type;

    RoleTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
