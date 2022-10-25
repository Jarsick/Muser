package jarsick.muser.generator;

import jarsick.muser.generator.random.Random;

public class Density {
	private static float MAX = 1;
	private static float MIN = 0.2f;

	private float melody, bass, chords, drum;
	
	public static Density random() {
		return new Density(
				Random.random(MIN, MAX),
				Random.random(MIN, MAX),
				Random.random(MIN, MAX),
				Random.random(MIN, MAX)
				);
	}
	
	public Density() {
		this(1, 1, 1, 1);
	}
	
	public Density(float melody, float bass, float chords, float drum) {
		super();
		this.melody = melody;
		this.bass = bass;
		this.chords = chords;
		this.drum = drum;
	}

	public float getMelody() {
		return melody;
	}

	public void setMelody(float melody) {
		this.melody = melody;
	}

	public float getBass() {
		return bass;
	}

	public void setBass(float bass) {
		this.bass = bass;
	}

	public float getChords() {
		return chords;
	}

	public void setChords(float chords) {
		this.chords = chords;
	}

	public float getDrum() {
		return drum;
	}

	public void setDrum(float drum) {
		this.drum = drum;
	}
	
	
	public Density copy() {
		return new Density(this.melody, this.bass, this.chords, this.drum);
	}
	
}
