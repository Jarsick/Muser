package jarsick.muser.generator.drum;

import java.util.List;

import jarsick.muser.notation.Drum;
import jarsick.muser.structure.SongInfo;

public class RandomDrumPattern {

	private List<Drum> kickPattern;
	private List<Drum> snarePattern; 
	private List<Drum> hatPattern;
	private List<Drum> crashPattern;

	public RandomDrumPattern(SongInfo songInfo, int patternMeasures, float density, boolean fill) {
		kickPattern = new KickDrumPatternGenerator(songInfo, density).generatePattern(patternMeasures);
		snarePattern = new SnareDrumPatternGenerator(songInfo, density, fill).generatePattern(patternMeasures);
		hatPattern = new HatDrumPatternGenerator(songInfo, density).generatePattern(patternMeasures);
		crashPattern = new HatDrumPatternGenerator(songInfo, density).generatePattern(patternMeasures);
	}

	public List<Drum> getKickPattern() {
		return kickPattern;
	}

	public List<Drum> getSnarePattern() {
		return snarePattern;
	}

	public List<Drum> getHatPattern() {
		return hatPattern;
	}
	
	public List<Drum> getCrashPattern() {
		return crashPattern;
	}
	
	
	
}
