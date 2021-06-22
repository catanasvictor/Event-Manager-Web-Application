package org.launchcode.codingevents.models;

public enum UserCategory {

    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE");

    private final String name;

    UserCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
