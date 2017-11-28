package com.model;

public enum UserType {
		BASIC (0),
		PREMIUM (1),
		ARTIST (2),
		ADMIN (3)
		;
	private final int levelCode;

	UserType(int levelCode) {
        this.levelCode = levelCode;
        //
    }
    
    public int getTypeCode() {
        return this.levelCode;
    }
}
