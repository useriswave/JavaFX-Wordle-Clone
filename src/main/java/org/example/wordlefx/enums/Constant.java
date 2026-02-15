package org.example.wordlefx.enums;

public enum Constant {

    TITLE("WORDLE");
    private final String identifier;

    Constant(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
