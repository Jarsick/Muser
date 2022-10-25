/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.notation;

import java.util.ArrayList;
import java.util.List;

public class Chord implements MusicalNotation{

	public enum Type{
		MAJOR(0, 4, 7),
		MINOR(0, 3, 7),
		DIM(0, 3, 6),
		TONIC(0),
		POWER(0, 7);

		private int[] distances;

		private Type(int... distances) {
			this.distances = distances;
		}

		/**Return an array with chords grade relative to the tonic, lower-hight*/
		public int[] getDistances() {
			return this.distances.clone();
		}
	}

	final static public Chord create(Note firstNote, Chord.Type type) {
		var notes = new ArrayList<Note>();
		for(int dist : type.getDistances()) {
			notes.add(new Note(firstNote.getValue() + dist, firstNote.getDuration(), firstNote.getValue()));
		}
		return new Chord(type, notes);
	}	



	private List<Note> notes;
	private Type type;
	private int duration = -1;

	public Chord() {
		this(Type.TONIC, new ArrayList<>());
	}

	public Chord(Type type, List<Note> notes) {
		this.notes = new ArrayList<Note>();
		for(Note note : notes) {
			this.notes.add(note);
		}
		this.type = type;
	}

	public Chord addNote(Note note) {
		this.notes.add(note);
		return this;
	}

	public List<Note> getNotes(){
		return this.notes;
	}


	public Type getType() {
		return this.type;
	}


	public Chord setType(Type type) {
		this.type = type;
		return this;
	}

	final public Note getLowerNote() {
		Note lower = null;
		for(var note : notes) {
			if(lower == null || note.getValue() < lower.getValue()) {
				lower = note;
			}
		}
		return lower;
	}


	final public Chord transpose(int value) {
		for(var note : notes) {
			note.transpose(value);
		}
		return this;
	}

	final public Note getHigherNote() {
		Note higer = null;
		for(Note note : notes) {
			if(higer == null || note.getValue() > higer.getValue()) {
				higer = note;
			}
		}
		return higer;
	}

	public Note getMiddleNote() {
		var middleNote = getLowerNote();
		for(var note : notes) {
			if(note != getLowerNote() && note != getHigherNote()) {
				middleNote = note;
			}
		}
		return middleNote;
	}

	/**Invert this chord making it higher*/
	public Chord invertUp() {
		var lower = getLowerNote();
		lower.setValue(lower.getValue() + 12);
		return this;
	}


	/**Invert this chord making it lower*/
	public Chord invertDown() {
		var higer = getHigherNote();
		higer.setValue(higer.getValue() - 12);
		return this;
	}


	public boolean isSilence() {
		var allNoteSilence = true;
		for(var note : notes) {
			allNoteSilence &= note.isSilence();
		}
		return allNoteSilence || notes.size() == 0;
	}


	public Chord setDuration(int duration) {
		for(var note : notes) {
			note.setDuration(duration);
		}
		this.duration = duration;
		return this;
	}


	public int getDuration() {
		if(this.duration > 0) 
			return this.duration;
		int maxDuration = 1;
		for(var note : notes) {
			if(note.getDuration() > maxDuration) {
				maxDuration = note.getDuration();
			}
		}
		return maxDuration;
	}


	public Chord setVelocity(float velocity) {
		for(var note : notes) {
			note.setVelocity(velocity);
		}
		return this;
	}


	public float getVelocity() {
		float maxVelocity = 0;
		for(var note : notes) {
			if(note.getVelocity() > maxVelocity) {
				maxVelocity = note.getVelocity();
			}
		}
		return maxVelocity;
	}


	public void clear() {
		this.notes.clear();
	}


	public Chord copy() {
		List<Note> newList = new ArrayList<Note>();
		for(Note note : this.getNotes()) {
			newList.add(note.copy());
		}
		return new Chord(this.getType(), newList);
	}


	@Override
	public Chord makeSilence() {
		for(var note : this.notes)
			note.makeSilence();
		return this;
	}


	@Override
	public String toString() {
		return this.getNotes().size() > 0 ? this.getNotes().get(0).toString() + " " + this.getType() : "Silence";
	}
}
