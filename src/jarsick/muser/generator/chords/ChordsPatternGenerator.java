package jarsick.muser.generator.chords;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.generator.PatternGenerator;
import jarsick.muser.generator.random.Random;
import jarsick.muser.generator.utils.SongGeneratorUtil;
import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Note;
import jarsick.muser.structure.SongInfo;


public class ChordsPatternGenerator extends PatternGenerator<Chord>{

	public static enum ChordsPatternType{
		PAD,
		ARPEGGIATED,
		RHYTHMIC
	}

	private List<Chord> chordProgression;
	private List<Drum> rhythm;
	private ChordsPatternType type;

	public ChordsPatternGenerator(List<Chord> chordProgression, List<Drum> rhythm, ChordsPatternType type, SongInfo songInfo) {
		super(songInfo);
		this.chordProgression = chordProgression;
		this.rhythm = rhythm;
		this.type = type;
	}


	/**Returns the chord for each beat (even id the chord is not changed!)*/
	private List<Chord> getFullChordOnTimeDivision(int measures){
		ArrayList<Chord> result = new ArrayList<>();
		for(int i = 0; i < measures; i++) {
			for(int j = 0; j < getSongInfo().getDivisionPerMeasure(); j++) {
				var chord = this.chordProgression.get(i % measures).copy();
				chord.setDuration(1);
				result.add(chord);
			}
		}
		return result;
	}


	private void invertChords(List<Chord> chords){
		for(var chord : chords) {
			this.invertChord(chord);
		}
	}

	private void invertChord(Chord chord){
		int inversion = (int) Random.random(3);
		switch(inversion) {
		case 1:
			chord.invertUp();
			break;
		case 2:
			chord.invertDown();
			break;
		default:
			break;
		}
	}


	private void makeBassNote(Chord chord){
		Note bassNote = chord.getLowerNote().copy();
		Note originalLowerNote = chord.getLowerNote().copy();
		chord.clear();
		bassNote.setValue(bassNote.getValue() - 12); // an octave down
		bassNote.setDuration(1);
		originalLowerNote.setDuration(1);
		chord.addNote(bassNote);
		chord.addNote(originalLowerNote);
	}



	private void applyRhythm(List<Drum> rhythm, List<Chord> chords) {
		for(int i = 0; i < chords.size(); i ++) {
			var chord = chords.get(i);
			var drumInstrument = rhythm.get(i % rhythm.size());
			switch(drumInstrument) {
			case RIDE:
			case HAT:
				chord.setVelocity(0.6f); 
				break;
			case CRASH:
			case KICK:
				this.makeBassNote(chord);
				break;
			case SILENCE:
				chord.clear();
				break;
			case SNARE: 
			default:
				break;
			}
		}
	}


	@Override
	public List<Chord> generatePattern(int measures) {
		List<Chord> result = switch(this.type) {
			case RHYTHMIC-> generateRhythmicAccompaniment(rhythm, measures);
			case ARPEGGIATED -> generateArpeggiatedAccompaniment(rhythm, measures);
			case PAD -> generatePadAccompaniment(rhythm, measures);
		};
		SongGeneratorUtil.adjustDuration(result, getSongInfo());
		return result;
	}




	List<Chord> generateRhythmicAccompaniment(List<Drum> rhythm, int measures){
		List<Chord> result = this.getFullChordOnTimeDivision(measures);
		this.invertChords(result);
		this.applyRhythm(rhythm, result);
		return result;
	}


	List<Chord> generatePadAccompaniment(List<Drum> rhythm, int measures){
		var result = this.getFullChordOnTimeDivision(measures);
		this.invertChords(result);
		for(int i = 0; i < result.size(); i++) {
			if(!getSongInfo().isBeat(i)) {
				result.get(i).clear();
			}
		}
		return result;
	}

	List<Chord> generateArpeggiatedAccompaniment(List<Drum> rhythm, int measures){
		List<Chord> result = new ArrayList<>();
		List<Chord> progression = new ArrayList<>();

		for(var chord : this.chordProgression) {
			var newChord = chord.copy();
			if(newChord.getNotes().get(0).getValue() > 66)
				newChord.transpose(-12); 
			newChord.setDuration(1);
			progression.add(newChord);
		}

		this.invertChords(progression); 
		var arpeggiator = new Arpeggiator(progression, rhythm);
		result = arpeggiator.generate();
		return result;
	}

}
