/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.structure;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Note;

public class Song {

	private SongInfo songInfo;

	private List<Drum> kick = new ArrayList<>();
	private List<Drum> snare = new ArrayList<>();
	private List<Drum> hat = new ArrayList<>();
	private List<Drum> crash = new ArrayList<>();

	private List<Chord> chords = new ArrayList<>();
	private List<Note> bass = new ArrayList<>();
	private List<Note> melody = new ArrayList<>();

	public Song(SongInfo songInfo) {
		super();
		this.songInfo = songInfo;
	}

	public SongInfo getSongInfo() {
		return this.songInfo;
	}

	public List<Drum> getKick() {
		return kick;
	}

	public List<Drum> getSnare() {
		return snare;
	}

	public List<Drum> getHat() {
		return hat;
	}

	public List<Drum> getCrash() {
		return crash;
	}


	public List<Chord> getChords() {
		return chords;
	}

	public List<Note> getBass() {
		return bass;
	}

	public List<Note> getMelody() {
		return melody;
	}


	public int getDivisionCount() {
		return (int) getMelody().stream().mapToLong(note -> note.getDuration()).sum();
	}

}
