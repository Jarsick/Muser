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


	public MelodyPatternGenerator(Key key, List<Chord> progression, List<Drum> rhythm, List<Integer> voiceLine, SongInfo songInfo) {
		super(songInfo);
		this.progression = progression;
		this.key = key;
		this.rhythm = rhythm;
		this.voiceLine = voiceLine;
	}

	@Override
	public List<Note> generatePattern(int measures) {
		final int MAX_OCTAVE = 6;
		final int MIN_OCTAVE = 5;
		var result = new ArrayList<Note>();

		Note lastNote = null;

		for(int i = 0; i < measures; i++) { // each measure has its own chord
			Chord chord = progression.get(i%progression.size());

			Note baseNote = lastNote != null? getNearestChordNote(lastNote, chord) : chord.getNotes().get((int)Random.random(chord.getNotes().size())).copy();//getNearestChordNote(lastNote, chord);
			
			// Limiting the octaves
			if(baseNote.getOctaveIndex() > MAX_OCTAVE) {
				baseNote.transpose(-12); 
			}
			
			if(baseNote.getOctaveIndex() < MIN_OCTAVE) {
				baseNote.transpose(12);
			}
			
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
					note = getMelodyNote(baseNote, voiceLine, drumIndex);
					break;
				case SILENCE:
				default:
					note = Note.createSilence(1);
					break;
				}

				if(!note.isSilence() && (getSongInfo().isBeat(drumIndex) || getSongInfo().isUpbeat(drumIndex))) {
					note = getNearestChordNote(note, chord); // forcing note to be on the chord
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
