package com.caju.desafio.shared;

public record Error(String code, String description) {
    public static final Error None = new Error("00", null);
}
