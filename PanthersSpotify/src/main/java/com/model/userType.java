package com.model;

public enum userType {
		BASIC (0),
		PREMIUM (1),
		ARTIST (2),
		ADMIN (3)
		;
	private final int levelCode;

	userType(int levelCode) {
        this.levelCode = levelCode;
    }
    
    public int getTypeCode() {
        return this.levelCode;
    }
}
