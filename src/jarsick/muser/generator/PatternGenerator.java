/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator;

import java.util.List;

import jarsick.muser.structure.SongInfo;

public abstract class PatternGenerator<T> {
	private SongInfo songInfo;

	public PatternGenerator(SongInfo songInfo) {
		this.songInfo = songInfo;
	}

	public SongInfo getSongInfo() {
		return this.songInfo;
	}

	abstract public List<T> generatePattern(int measures);
}
