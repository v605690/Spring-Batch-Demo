package com.crus.Batch.Demo.model;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;

// enum class for the designation of the employees
public enum Designation {
    DEVELOPER("dev", "Developer"),
    DESIGNER("des", "Designer"),
    TESTER("test", "Tester");

    private final String code;
    private final String title;

    Designation(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public static Designation getByCode(final String code) {
        return Arrays.stream(Designation.values())
                .filter(designation ->
                        designation.getCode().equals(code))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }
}

