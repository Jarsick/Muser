/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator;

import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Key;
import jarsick.muser.notation.Note;
import jarsick.muser.notation.Scale;
import jarsick.muser.notation.TimeDivision;
import jarsick.muser.notation.TimeSignature;
import jarsick.muser.structure.SongInfo;

public class SongGeneratorSettings {

	public static int DEFAULT_MIN_OCTAVE = 5;
	public static int DEFAULT_MAX_OCTAVE = 6;

	private static int MAX_TEMPO = 250;
	private static int MIN_TEMPO = 90;
	private static int DEFAULT_SUBSECTION_MEASURES = 4;

	 private boolean singableMelody;
	 public boolean isSingableMelody() {
		return singableMelody;
	}
	
	private int bassInstrument, melodyInstrument, chordsInstrument, drumKit;
	 private float bassVolume, drumVolume, melodyVolume, chordsVolume;
	 private TimeSignature timeSignature;
	 private TimeDivision timeDivision;
	 private Density density;
	 private int tempo;
	 private Note tonic;
	 private Scale scale;
	 private int loops;
	 private boolean doubleDrumExport;
	 private int subsectionMeasures;
	 private int minOctave;
	 private int maxOctave;
	 
	 public SongGeneratorSettings() {
		 bassInstrument = 0;
		 melodyInstrument = 0;
		 chordsInstrument = 0;
		 drumKit = 0;
		 bassVolume = drumVolume = melodyVolume = chordsVolume = 1;
		 timeSignature = TimeSignature.FOUR_FOUR;
		 timeDivision = TimeDivision.TD_1_8;
		 tonic = Note.C;
		 scale = Scale.MAJOR;
		 tempo = 120;
		 loops = 1;
		 density = new Density();
		 doubleDrumExport = true;
		 subsectionMeasures = DEFAULT_SUBSECTION_MEASURES;
		 minOctave = DEFAULT_MIN_OCTAVE;
		 maxOctave = DEFAULT_MAX_OCTAVE;
	 }

	public int getBassInstrument() {
		return bassInstrument;
	}

	public void setBassInstrument(int bassInstrument) {
		this.bassInstrument = bassInstrument;
	}

	public int getMelodyInstrument() {
		return melodyInstrument;
	}

	public void setMelodyInstrument(int melodyInstrument) {
		this.melodyInstrument = melodyInstrument;
	}

	public int getChordsInstrument() {
		return chordsInstrument;
	}

	public void setChordsInstrument(int chordsInstrument) {
		this.chordsInstrument = chordsInstrument;
	}

	public int getDrumKit() {
		return drumKit;
	}

	public void setDrumKit(int drumKit) {
		this.drumKit = drumKit;
	}

	public float getBassVolume() {
		return bassVolume;
	}

	public void setBassVolume(float bassVolume) {
		this.bassVolume = bassVolume;
	}

	public float getDrumVolume() {
		return drumVolume;
	}

	public void setDrumVolume(float drumVolume) {
		this.drumVolume = drumVolume;
	}

	public float getMelodyVolume() {
		return melodyVolume;
	}

	public void setMelodyVolume(float melodyVolume) {
		this.melodyVolume = melodyVolume;
	}

	public float getChordsVolume() {
		return chordsVolume;
	}

	public void setChordsVolume(float chordsVolume) {
		this.chordsVolume = chordsVolume;
	}

	public TimeSignature getTimeSignature() {
		return timeSignature;
	}

	public void setTimeSignature(TimeSignature timeSignature) {
		this.timeSignature = timeSignature;
	}

	public TimeDivision getTimeDivision() {
		return timeDivision;
	}

	public void setTimeDivision(TimeDivision timeDivision) {
		this.timeDivision = timeDivision;
	}

	public Density getDensity() {
		return density;
	}

	public void setDensity(Density value) {
		this.density = value;
	}

	

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public Note getTonic() {
		return tonic;
	}

	public void setTonic(Note tonic) {
		this.tonic = tonic;
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		this.scale = scale;
	}
	
	public int getLoops() {
		return loops;
	}

	public void setLoops(int loops) {
		this.loops = loops;
	}
	
	
	public Key getKey() {
		return new Key(tonic, scale);
	}
	public SongInfo getSongInfo() {
		return new SongInfo(getKey(), getTimeSignature(), getTempo(), getTimeDivision());
	}
	
	
	public int getSubsectionMeasures() {
		return subsectionMeasures;
	}

	public void setSubSectionMeasures(int measures) {
		this.subsectionMeasures = measures;
	}
	
	

	public SongGeneratorSettings randomize() {
		setDensity(Density.random());
		setTonic(Note.randomTonic());
		setScale(Scale.values()[(int)Random.random(Scale.values().length)]);
		setTimeSignature(TimeSignature.values()[(int)Random.random(TimeSignature.values().length)]);
		setTempo((int)Random.random(MIN_TEMPO, MAX_TEMPO));
		return this;
	}

	public void setMinOctave(int minOctave) {
		this.minOctave = minOctave;
	}

	public int getMinOctave() {
		return this.minOctave;
	}

	public void setMaxOctave(int maxOctave) {
		this.maxOctave = maxOctave;
	}

	public int getMaxOctave() {
		return this.maxOctave;
	}

	
	public void setDoubleDrumExport(boolean value) {
		this.doubleDrumExport = value;
	}
	
	public boolean doubleDrumExportEnabled() {
		return this.doubleDrumExport;
	}

	public SongGeneratorSettings copy() {
		var clone = new SongGeneratorSettings();
		clone.setMinOctave(this.getMinOctave());
		clone.setMaxOctave(this.getMaxOctave());
		clone.setBassInstrument(this.getBassInstrument());
		clone.setBassVolume(this.getBassVolume());
		clone.setChordsInstrument(this.getChordsInstrument());
		clone.setChordsVolume(this.getChordsVolume());
		clone.setDrumKit(this.getDrumKit());
		clone.setDrumVolume(this.getDrumVolume());
		clone.setLoops(this.getLoops());
		clone.setMelodyInstrument(this.getMelodyInstrument());
		clone.setMelodyVolume(clone.getMelodyVolume());
		clone.setScale(this.getScale());
		clone.setSubSectionMeasures(this.getSubsectionMeasures());
		clone.setTempo(this.getTempo());
		clone.setTimeDivision(this.getTimeDivision());
		clone.setTimeSignature(this.getTimeSignature());
		clone.setTonic(this.getTonic());
		clone.setDoubleDrumExport(this.doubleDrumExportEnabled());
		clone.setDensity(this.getDensity().copy());
		return clone;
	}
}
