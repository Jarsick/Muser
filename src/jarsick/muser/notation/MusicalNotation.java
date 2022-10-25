/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.notation;

public interface MusicalNotation {
	public int getDuration();
	public MusicalNotation setDuration(int duration);

	public float getVelocity();
	public MusicalNotation setVelocity(float velocity);

	public boolean isSilence();
	public MusicalNotation makeSilence();

	MusicalNotation copy();
}
