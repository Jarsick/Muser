/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator.melody;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.generator.PatternGenerator;
import jarsick.muser.generator.random.Random;
import jarsick.muser.generator.utils.SongGeneratorUtil;
import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Key;
import jarsick.muser.notation.Note;
import jarsick.muser.structure.SongInfo;

public class MelodyPatternGenerator extends PatternGenerator<Note>{

	private Key key;
	private List<Chord> progression;
	private List<Drum> rhythm;

	private List<Integer> voiceLine; 

	 private int minOctave = 5;
	 private int maxOctave = 6;

	public MelodyPatternGenerator(Key key, List<Chord> progression, List<Drum> rhythm, List<Integer> voiceLine, SongInfo songInfo, int minOctave, int maxOctave) {
		super(songInfo);
		this.progression = progression;
		this.key = key;
		this.rhythm = rhythm;
		this.voiceLine = voiceLine;
		this.minOctave = minOctave;
		this.maxOctave = maxOctave;
	}

	@Override
	public List<Note> generatePattern(int measures) {

		var result = new ArrayList<Note>();

		Note lastNote = null;

		for(int i = 0; i < measures; i++) { // each measure has its own chord
			Chord chord = progression.get(i%progression.size());

			Note baseNote = lastNote != null? getNearestChordNote(lastNote, chord) : chord.getNotes().get((int)Random.random(chord.getNotes().size())).copy();//getNearestChordNote(lastNote, chord);



			// Adjusting voiceline on rhythm and chords
			for(int drumIndex = 0; drumIndex < rhythm.size() ; drumIndex++) {
				var drum = rhythm.get(drumIndex);
				Note note = null;
				switch(drum) {
				case HAT:
					if(Random.random() < Random.STANDARD_PROBABILITY) {
						note = getMelodyNote(baseNote, voiceLine, drumIndex);
					}else {
						note = Note.createSilence(1);
					}
					break;
				case KICK:
				case SNARE:
					if(Random.random() < Random.HIGH_PROBABILITY) {
						note = getMelodyNote(baseNote, voiceLine, drumIndex);
					}else {
						note = Note.createSilence(1);
					}
					break;
				case SILENCE:
				default:
					note = Note.createSilence(1);
					break;
				}

				if(!note.isSilence() && (getSongInfo().isBeat(drumIndex) || getSongInfo().isUpbeat(drumIndex))) {
					note = getNearestChordNote(note, chord); // forcing note to be on the chord
				}


				 // limit the note octave
				if(!note.isSilence()) {
					int oldOctave = note.getOctaveIndex();
					while (note.getOctaveIndex() > maxOctave || note.getOctaveIndex() < minOctave) {
						int octave = note.getOctaveIndex();
						if (octave > maxOctave) {
							note.transpose(-12);
						} else if (octave < minOctave) {
							note.transpose(12);
						}
					}
				}

				if(note != null && !note.isSilence()) {
					lastNote = note.copy();
				}

				note.setDuration(1);
				result.add(note);
			}
		}

		SongGeneratorUtil.adjustDuration(result, this.getSongInfo());
		return result;
	}


	private Note getNearestChordNote(Note note, Chord chord) {
		if(note == null || note.isSilence()) {
			return chord.getNotes().get((int) Random.random(chord.getNotes().size()));
		}else {
			Note nearestNote = null;
			for(int transpose = -48; transpose <= 48; transpose+= 12) {  //searching the nearest note looping octaves
				for(var chordNote : chord.getNotes()) {
					int dist = Math.abs((chordNote.getValue() + transpose) - note.getValue());
					if(nearestNote == null || dist < Math.abs(nearestNote.getValue() - note.getValue())) {
						nearestNote = chordNote.copy();
						nearestNote.transpose(transpose);
					}
				}
			}
			return nearestNote;
		}
	}


	private Note getMelodyNote(Note baseNote, List<Integer> voiceLine, int melodyIndex) {
		var note = baseNote.copy();
		key.transpose(note, voiceLine.get(melodyIndex));
		return note;
	}

}
