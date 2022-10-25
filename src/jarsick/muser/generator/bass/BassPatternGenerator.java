package jarsick.muser.generator.bass;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.generator.PatternGenerator;
import jarsick.muser.generator.random.Random;
import jarsick.muser.generator.utils.SongGeneratorUtil;
import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Note;
import jarsick.muser.structure.SongInfo;

public class BassPatternGenerator extends PatternGenerator<Note>{

	private List<Chord> progression;
	private List<Drum> rhythm;

	public BassPatternGenerator(List<Chord> progression, List<Drum> rhythm, SongInfo songInfo) {
		super(songInfo);
		this.progression = progression;
		this.rhythm = rhythm;
	}

	@Override
	public List<Note> generatePattern(int measures) {
		var result = new ArrayList<Note>();
		for(int i = 0; i < measures; i++) {
			var chord = progression.get(i % progression.size());
			for(int j = 0; j < rhythm.size(); j++) {
				var drum = rhythm.get(j);
				Note note = null;
				switch(drum) {
				case HAT:
					if(Random.random() < Random.LOW_PROBABILITY) {
						var copiedNote = chord.getLowerNote().copy();
						var transposeValue = (int)(7 * Random.randomGaussian());
						getSongInfo().getKey().transpose(copiedNote, transposeValue);
						note = copiedNote;
					}else {
						note = Note.createSilence(1);
					}
					break;
				case SILENCE:
					note = Note.createSilence(1);
					break;
				case KICK:
				case CRASH:
				case RIDE:
				case SNARE:
					if(Random.random() < Random.HIGH_PROBABILITY) {
						note = chord.getLowerNote();
					}else {
						note = Note.createSilence(1);
					}
					break;	
				}

				if(!note.isSilence() && (getSongInfo().isBeat(j) || getSongInfo().isUpbeat(j))) {
					note = chord.getLowerNote().copy(); // force chord note in beat and upbeat
				}


				if(!note.isSilence()) {
					note.transpose(-12);
				}

				note.setDuration(1);
				result.add(note.copy());
			}
		}

		SongGeneratorUtil.adjustDuration(result, getSongInfo());

		return result;
	}

}
