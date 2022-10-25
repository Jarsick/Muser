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
import jarsick.muser.structure.SongInfo;

public class VoiceLinePatternGenerator extends PatternGenerator<Integer> {

	final static private float NOISE_INCREMENT = 1f;

	public VoiceLinePatternGenerator(SongInfo songInfo) {
		super(songInfo);
	}

	@Override
	public List<Integer> generatePattern(int measures) {
		var result = new ArrayList<Integer>();
		float noiseX = Random.random(1000);
		for(int i = 0 ; i < measures * getSongInfo().getDivisionPerMeasure(); i++) {
			final int INTERVAL_SIZE = 8;
			var noise = (2f * (Random.noise(noiseX, 0, 0) - 0.5f)); // -1 < noise < 1
			var note = (int) (INTERVAL_SIZE * noise); 
			result.add(note);
			noiseX += NOISE_INCREMENT;
		}
		return result;
	}


}
