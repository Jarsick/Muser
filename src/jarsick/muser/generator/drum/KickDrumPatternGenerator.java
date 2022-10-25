package jarsick.muser.generator.drum;

import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Drum;
import jarsick.muser.structure.SongInfo;


public class KickDrumPatternGenerator extends DrumPatternGenerator{
	public KickDrumPatternGenerator(SongInfo songInfo, float density) {
		super(Drum.KICK, songInfo, density);
	}

	public float getProbability(int timeInUnit, int measures) {
		if(getSongInfo().isBeat(timeInUnit)) { 
			return Random.HIGH_PROBABILITY * 1.2f; // Really high probability of kick on beat
		}else if (getSongInfo().isUpbeat(timeInUnit)) {
			return Random.STANDARD_PROBABILITY;
		}else {
			return Random.LOW_PROBABILITY;
		}
	}

}
