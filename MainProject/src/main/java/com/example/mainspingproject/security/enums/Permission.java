package com.example.mainspingproject.security.enums;

public enum Permission {
    MEETUPS_READ("meetups:read"),
    MEETUPS_WRITE("meetups:write"),

    REQUEST_READ ("requests:read"),
    REQUEST_WRITE("requests:write"),
    REQUEST_SOLVE("requests:solve"),

    REPORT_READ("reports:read"),
    REPORT_WRITE("reports:write"),

    ADMIN_PAGE("admin_page"),
    USER_PAGE("user_page");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
