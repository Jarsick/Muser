/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator;

public record InstrumentName(String name, int midi) {
	public String toString() {
		return name;
	}
	
	@Override
	public int midi() {
		return this.midi - 1;
	}
};