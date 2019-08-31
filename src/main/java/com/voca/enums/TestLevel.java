package com.voca.enums;

public enum TestLevel {
	
	LEVEL_0(0, 0),LEVEL_1(3, 3), LEVEL_2(6, 7), LEVEL_3(9, 30), LEVEL_4(12, 90), LEVEL_5(15, 365);
	
	private int totalPoint;
	private int totalDay;
	
	TestLevel(int totalPoint, int totalDay) {
		this.totalDay = totalDay;
		this.totalPoint = totalPoint;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public int getTotalDay() {
		return totalDay;
	}

}
