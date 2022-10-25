package jarsick.muser.generator.drum;

import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Drum;
import jarsick.muser.structure.SongInfo;

public class SnareDrumPatternGenerator extends DrumPatternGenerator{

	private boolean fill;

	public SnareDrumPatternGenerator(SongInfo songInfo, float density, boolean fill) {
		super(Drum.SNARE, songInfo, density);
		this.fill = fill;
	}

	@Override
	public float getProbability(int timeInUnit, int measures) {
		if(!fill) {
			if (getSongInfo().isUpbeat(timeInUnit)) {
				return Random.STANDARD_PROBABILITY * getDensity();
			}else {
				return Random.LOW_PROBABILITY * getDensity();
			}
		}else {
			float base = 0.5f;
			float percent = base + (timeInUnit/(float)(measures * getSongInfo().getDivisionPerMeasure() - 1)) * (0.9f/*we want a little probability of silence*/ - base);
			return percent;
		}
	}

	
	public boolean isFill() {
		return this.fill;
	}

}