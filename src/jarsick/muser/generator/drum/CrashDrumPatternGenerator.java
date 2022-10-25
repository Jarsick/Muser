package jarsick.muser.generator.drum;

import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Drum;
import jarsick.muser.structure.SongInfo;

public class CrashDrumPatternGenerator extends DrumPatternGenerator{

	public CrashDrumPatternGenerator(SongInfo songInfo, float density) {
		super(Drum.CRASH, songInfo, density);
	}

	@Override
	public float getProbability(int timeInUnit, int measures) {
			if (getSongInfo().isBeat(timeInUnit)) 
				return 2 * Random.LOW_PROBABILITY * getDensity();
			else return 0;
	}

}




