/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator.drum;

import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Drum;
import jarsick.muser.structure.SongInfo;

public class HatDrumPatternGenerator extends DrumPatternGenerator{

	public HatDrumPatternGenerator(SongInfo songInfo, float density) {
		super(Drum.HAT, songInfo, density);
	}

	@Override
	public float getProbability(int timeInUnit, int measures) {
		if(getSongInfo().isBeat(timeInUnit)) { 
			return Random.STANDARD_PROBABILITY * getDensity() * 1.5f;
		}else if (getSongInfo().isUpbeat(timeInUnit)) {
			return Random.HIGH_PROBABILITY * getDensity() * 1.5f;
		}else {
			return Random.STANDARD_PROBABILITY * getDensity() * 1.5f;
		}
	}


}
