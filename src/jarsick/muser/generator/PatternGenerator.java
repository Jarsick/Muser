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
