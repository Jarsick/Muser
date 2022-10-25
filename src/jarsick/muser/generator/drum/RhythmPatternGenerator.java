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
import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Drum;
import jarsick.muser.structure.SongInfo;

/**Generate patterns representing a Rhythm that can be applied to every instrument,
 *  where KICK is beat, SNARE is upbeat, and hat is a passing note
 */
public class RhythmPatternGenerator extends PatternGenerator<Drum>{
	private float density;

	public RhythmPatternGenerator(SongInfo songInfo, float density) {
		super(songInfo);
		this.density = density;
	}


	@Override
	public List<Drum> generatePattern(int measures) {
		ArrayList<Drum> result = new ArrayList<>();

		final int KICK = Drum.KICK.ordinal();
		final int SNARE = Drum.SNARE.ordinal();
		final int HAT = Drum.HAT.ordinal();
		final int SILENCE = Drum.SILENCE.ordinal();

		float[] probabilities = new float[Drum.values().length]; // Kick, Snare, Hat, silence, che poi verranno tradotti
		boolean allSilence = true;

		for(int i = 0; i < measures * (getSongInfo().getDivisionPerMeasure()); i++) {
			if(getSongInfo().isBeat(i)) {
				probabilities[KICK] = Random.HIGH_PROBABILITY * density;
				probabilities[SNARE] = Random.LOW_PROBABILITY * density;
				probabilities[HAT] = Random.LOW_PROBABILITY * density;
				probabilities[SILENCE] = Random.LOW_PROBABILITY;
			}else if(getSongInfo().isUpbeat(i)) {
				probabilities[KICK] = Random.LOW_PROBABILITY * density;
				probabilities[SNARE] = Random.HIGH_PROBABILITY * density;
				probabilities[HAT] = Random.LOW_PROBABILITY * density;
				probabilities[SILENCE] = Random.LOW_PROBABILITY;
			}else {
				probabilities[KICK] = Random.LOW_PROBABILITY * density;
				probabilities[SNARE] = Random.LOW_PROBABILITY * density;
				probabilities[HAT] = Random.HIGH_PROBABILITY * density; 
				probabilities[SILENCE] = Random.HIGH_PROBABILITY; 
			}

			int instrumentIndex = Random.montecarlo(probabilities);

			var instrument = Drum.values()[instrumentIndex];
			result.add(instrument);			
			if(instrument != Drum.SILENCE) {
				allSilence = false;
			}
		}


		if(allSilence) {
			result.set(0, Drum.KICK);
		}
		return result;
	}

}
