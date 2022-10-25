package jarsick.muser.structure;

import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Key;
import jarsick.muser.notation.Note;
import jarsick.muser.notation.Scale;
import jarsick.muser.notation.TimeDivision;
import jarsick.muser.notation.TimeSignature;

public class SongInfo {
	//	Key key = new Key(Note.C, Scale.NATURAL_MINOR);
	//	TimeSignature timeSignature= TimeSignature.FOUR_FOUR;
	//	int tempo = 220;
	//	TimeDivision division = TimeDivision.TD_8;

	public static SongInfo randomize() {
		return new SongInfo(
				new Key(Note.randomTonic(), Scale.values()[(int)Random.random(Scale.values().length)]),
				TimeSignature.values()[(int)Random.random(TimeSignature.values().length)],
				(int)Random.random(90, 230),
				TimeDivision.TD_1_8
				);
	}



	private Key key;
	private TimeSignature timeSignature;
	private int tempo;
	private TimeDivision timeDivision;


	public SongInfo(Key key, TimeSignature timeSignature, int tempo, TimeDivision timeDivision) {
		super();
		this.key = key;
		this.timeSignature = timeSignature;
		this.tempo = tempo;
		this.timeDivision = timeDivision;
	}



	public Key getKey() {
		return key;
	}

	public TimeSignature getTimeSignature() {
		return timeSignature;
	}

	public int getTempo() {
		return tempo;
	}

	public TimeDivision getTimeDivision() {
		return timeDivision;
	}


	public int getDivisionPerMeasure() {
		return getTimeSignature().getTop() * (getTimeDivision().getValue() / getTimeSignature().getBottom());
	}
	
	final public boolean isBeat(int timeInDivisions) {
		return measureRelativeTimeDivisionIndex(timeInDivisions) == 0;
	}
	
	final public boolean isUpbeat(int timeInDivisions) {
		float currentBeatInMeasure = measureRelativeTimeDivisionIndex(timeInDivisions);
		return  currentBeatInMeasure != 0 && currentBeatInMeasure % 1 == 0 /**only int results are accepted*/;
	}
	
	final private float measureRelativeTimeDivisionIndex(int timeInDivisions) {
		return ((timeInDivisions * this.getTimeSignature().getBottom()) / (float)getTimeDivision().getValue()) % getTimeSignature().getTop();
	}
	

}
