package com.jdroid.android.repository.sqlite;

import com.jdroid.android.domain.Entity;

/**
 * Generic entity to store {@link String} objects.
 */
public class StringEntity extends Entity {
	
	private static final long serialVersionUID = -2598725684392359256L;
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
