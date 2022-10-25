/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator.drum;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.generator.PatternGenerator;
import jarsick.muser.notation.Drum;
import jarsick.muser.structure.SongInfo;

public abstract class DrumPatternGenerator extends PatternGenerator<Drum> {

	private Drum instrument;
	private float density;

	public DrumPatternGenerator(Drum instrument, SongInfo songInfo, float density) {
		super(songInfo);
		this.instrument = instrument;
		this.density = density;
	}


	public float getDensity() {
		return this.density;
	}

	/**Returns the probability to find a note in a certain position of the measure*/
	public abstract float getProbability(int timeInUnit, int measures);

	@Override
	public List<Drum> generatePattern(int measures) {
		int patternSize = measures * getSongInfo().getDivisionPerMeasure();
		ArrayList<Drum> result = new ArrayList<>();
		for(int i = 0; i < patternSize; i++) {
			Drum drum = this.instrument;
			if(Math.random() > getProbability(i, measures)) {
				drum = Drum.SILENCE;
			}
			result.add(drum);
		}
		return result;
	}

}
