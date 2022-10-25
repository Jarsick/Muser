package jarsick.muser.generator;

import static jarsick.muser.generator.PatternModification.*;

import jarsick.muser.generator.random.Random;

public enum SectionModification {
	INTRO(
			array(REMOVE_CHORDS, HALF_DRUM, CHANGE_VOICELINE),
			array(REMOVE_BASS, REMOVE_DRUM, HALF_MELODY),
			array(REMOVE_MELODY, REMOVE_KICK, REMOVE_SNARE),
			array(REMOVE_MELODY, CHANGE_CHORDS_TYPE, REMOVE_SNARE, REMOVE_HAT, REMOVE_CRASH), 
			array(REMOVE_MELODY, REMOVE_SNARE, REMOVE_CRASH)
			),
	ENDING(
			array(TONIC_HIT)
		),
	SUBSECTION(
			array(HALF_MELODY, CHANGE_CHORDS),
			array(HALF_DRUM, CHANGE_MELODY, CHANGE_BASS),
			array(CHANGE_CHORDS, CHANGE_BASS, CHANGE_MELODY),
			array(CHANGE_DRUM, CHANGE_CHORDS_TYPE, CHANGE_MELODY, CHANGE_VOICELINE),
			array(CHANGE_MELODY, CHANGE_VOICELINE, CHANGE_BASS_RHYTHM, CHANGE_CHORDS_TYPE),
			array(HALF_MELODY, CHANGE_CHORDS_TYPE, HALF_DRUM),
			array(HALF_BASS, HALF_CHORDS, CHANGE_CHORDS, CHANGE_MELODY),
			array(CHANGE_MELODY, CHANGE_VOICELINE, CHANGE_CHORDS, CHANGE_BASS)
			),
	NEW_SECTION(
			array(CHANGE_MELODY, CHANGE_VOICELINE, CHANGE_CHORDS, CHANGE_CHORDS_TYPE, CHANGE_CHORDS_RHYTHM, CHANGE_BASS, CHANGE_DRUM, REMOVE_HAT),
			array(CHANGE_MELODY_RHYTHM, CHANGE_CHORDS, CHANGE_CHORDS_TYPE, CHANGE_BASS, CHANGE_DRUM, REMOVE_SNARE, REMOVE_CRASH),
			array(CHANGE_MELODY, CHANGE_VOICELINE, CHANGE_CHORDS, CHANGE_CHORDS_TYPE, CHANGE_CHORDS_RHYTHM, CHANGE_BASS, HALF_DRUM, REMOVE_SNARE),
			array(CHANGE_MELODY_RHYTHM, CHANGE_CHORDS, CHANGE_CHORDS_TYPE, CHANGE_BASS, CHANGE_DRUM, REMOVE_HAT)
			),
	ALTERED_SECTION(
			array(REMOVE_HAT, HALF_DRUM, CHANGE_CHORDS_TYPE, HALF_BASS),
			array(REMOVE_HAT, REMOVE_SNARE, CHANGE_CHORDS_TYPE, REMOVE_CRASH),
			array(HALF_DRUM, CHANGE_CHORDS_TYPE, CHANGE_BASS)
			),
	;

	private PatternModification[][] modifications;
	
	private SectionModification(PatternModification[]... modifications) {
		this.modifications = modifications;
	}

	public PatternModification[][] getModifications(){
		return this.modifications;
	}


	public PatternModification[] getRandomModification() {
		return this.modifications[(int)(Random.random(this.modifications.length))];
	}

	@SafeVarargs
	private static <T> T[] array(T... array) {
		return array;
	}
}
