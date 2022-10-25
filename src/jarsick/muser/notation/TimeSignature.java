/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.notation;

public enum TimeSignature {
	TWO_FOUR(2, 4),
	THREE_FOUR(3, 4),
	FOUR_FOUR(4, 4),
	FIVE_FOUR(5, 4),
	SEVEN_FOUR(7, 4),
	THREE_EIGHT(6, 8);

	private int top;
	private int bottom;

	private TimeSignature(int top, int bottom) {
		this.top = top;
		this.bottom = bottom;
	}

	public int getTop() {
		return this.top;
	}

	public int getBottom() {
		return this.bottom;
	}

	public String toString() {
		return getTop() + "/" + getBottom();
	}
}
