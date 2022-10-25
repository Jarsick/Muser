/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.notation;

/**Represents possibles Time Divisions as [FIRST_NUMBER]/[SECOND_NUMBER] (e.g. TD_1_8 = 1/8)*/
public enum TimeDivision {

	TD_1_4(4),
	TD_1_8(8),
	TD_1_16(16),
	TD_1_32(32);

	private int value;

	private TimeDivision(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
