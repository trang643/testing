package com.voca.enums;

public enum TestSettingType {
	LAST_WEEK("Last week"), LAST_MONTH("Last month"), LAST_DAY("Last day"), TODAY("Today"), LAST_BULK("Last bulk"), LAST_WORDS("Last words");
	
	private String label;
	private TestSettingType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
