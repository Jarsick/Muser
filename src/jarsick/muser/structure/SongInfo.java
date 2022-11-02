/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.structure;

import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Key;
import jarsick.muser.notation.Note;
import jarsick.muser.notation.Scale;
import jarsick.muser.notation.TimeDivision;
import jarsick.muser.notation.TimeSignature;

public record SongInfo(Key key, TimeSignature timeSignature, int tempo, TimeDivision timeDivision){

	public static SongInfo randomize() {
		return new SongInfo(
				new Key(Note.randomTonic(), Scale.values()[(int)Random.random(Scale.values().length)]),
				TimeSignature.values()[(int)Random.random(TimeSignature.values().length)],
				(int)Random.random(90, 230),
				TimeDivision.TD_1_8
				);
	}

	public int getDivisionPerMeasure() {
		return timeSignature().getTop() * (timeDivision().getValue() / timeSignature().getBottom());
	}

	final public boolean isBeat(int timeInDivisions) {
		return measureRelativeTimeDivisionIndex(timeInDivisions) == 0;
	}

	final public boolean isUpbeat(int timeInDivisions) {
		float currentBeatInMeasure = measureRelativeTimeDivisionIndex(timeInDivisions);
		return  currentBeatInMeasure != 0 && currentBeatInMeasure % 1 == 0 /**only int results are accepted*/;
	}

	final private float measureRelativeTimeDivisionIndex(int timeInDivisions) {
		return ((timeInDivisions * this.timeSignature().getBottom()) / (float)timeDivision().getValue()) % timeSignature().getTop();
	}

}
