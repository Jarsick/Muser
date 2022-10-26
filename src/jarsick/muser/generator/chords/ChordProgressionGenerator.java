/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator.chords;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.generator.PatternGenerator;
import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Chord;
import jarsick.muser.structure.SongInfo;

public class ChordProgressionGenerator extends PatternGenerator<Chord>{

	final static private float[] DEGREES_DISTRIBUTION = {
			Random.LOW_PROBABILITY,
			Random.LOW_PROBABILITY,
			Random.LOW_PROBABILITY,
			Random.HIGH_PROBABILITY,
			Random.HIGH_PROBABILITY,
			Random.STANDARD_PROBABILITY,
			Random.LOW_PROBABILITY
	};

	
	public ChordProgressionGenerator(SongInfo songInfo) {
		super(songInfo);
	}


	final private float[] getLowProbabilityDistribution() {
		float[] result = new float[DEGREES_DISTRIBUTION.length];
		for(int i = 0; i < DEGREES_DISTRIBUTION.length; i++) {
			result[i] = Random.LOW_PROBABILITY;
		}
		return result;
	}

	private int[] generateDegreesProgression(int measures) {
		int[] result = new int[measures];
		int lastDegree = 0;
		for(int i = 0; i < measures; i ++) {
			float[] distribution = null;
			if(i == 0) { // first measure (resolve)
				distribution = getLowProbabilityDistribution();
				distribution[0] = Random.HIGH_PROBABILITY; // 5TH
				distribution[3] = Random.STANDARD_PROBABILITY; // 4TH
				distribution[5] = Random.STANDARD_PROBABILITY; // 6TH
			}else if(i == measures - 1) { // last measure (tension)
				distribution = getLowProbabilityDistribution();
				int toFirstDegree = (0/*1 - 1*/ - lastDegree + 7) % 7; // +7 to be always positive
				int toFifthDegree = (4/*5 - 1*/ - lastDegree + 7) % 7; // +7 to be always positive
				int toSeventhDegree =  (6/*7 - 1*/ - lastDegree + 7) % 7; // +7 to be always positive
				distribution[toFirstDegree] = Random.STANDARD_PROBABILITY; // high probability of resolution
				distribution[toFifthDegree] = Random.HIGH_PROBABILITY; // high probability of a chords that lead to resolution
				distribution[toSeventhDegree] = Random.HIGH_PROBABILITY; // high probability of a chords that lead to resolution
			}else {
				distribution = DEGREES_DISTRIBUTION.clone();
				distribution[0] = Random.STANDARD_PROBABILITY/i; // limiting the possibilities to remain on the same note
			}
			
			int currentDegree = Random.montecarlo(distribution);
			int resultDegree = (lastDegree + currentDegree)%distribution.length; //convert the result relative to the tonic!
			lastDegree = currentDegree;
			result[i] = resultDegree + 1; // 1 is the tonic! Here we don't want to start from 0
		}
		return result;
	}

	@Override
	public List<Chord> generatePattern(int measures) {
		int[] degrees = generateDegreesProgression(measures);
		var result = new ArrayList<Chord>();
		for(int degree : degrees) {
			var firstNote = getSongInfo().getKey().getNote(degree);
			var chord = Chord.create(firstNote, getSongInfo().getKey().getScale().getDegree(degree).type());
			result.add(chord);
		}
		return result;
	}

}
