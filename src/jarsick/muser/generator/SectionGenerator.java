/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator;

import java.util.List;

import jarsick.muser.generator.bass.BassPatternGenerator;
import jarsick.muser.generator.chords.ChordProgressionGenerator;
import jarsick.muser.generator.chords.ChordsPatternGenerator;
import jarsick.muser.generator.drum.CrashDrumPatternGenerator;
import jarsick.muser.generator.drum.HatDrumPatternGenerator;
import jarsick.muser.generator.drum.KickDrumPatternGenerator;
import jarsick.muser.generator.drum.RhythmPatternGenerator;
import jarsick.muser.generator.drum.SnareDrumPatternGenerator;
import jarsick.muser.generator.melody.MelodyPatternGenerator;
import jarsick.muser.generator.melody.VoiceLinePatternGenerator;
import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Note;
import jarsick.muser.structure.Section;

import static jarsick.muser.generator.utils.SongGeneratorUtil.copyList;

public class SectionGenerator {

	public static Section generate(SongGeneratorSettings settings) {
		return generate(
				settings,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				false
				);
	}



	public static Section generate(
			SongGeneratorSettings settings,
			List<Chord> progression,
			List<Integer> voiceLine,
			List<Drum> melodyRhythm,
			List<Drum> bassRhythm,
			List<Drum> chordsRhythm,
			ChordsPatternGenerator.ChordsPatternType chordsPatternType,
			List<Drum> kick,
			List<Drum> snare,
			List<Drum> hat,
			List<Drum> crash,
			List<Chord> chords,
			List<Note> bass,
			List<Note> melody,
			boolean fill
			) {

		var songInfo = settings.getSongInfo();
		var density = settings.getDensity();
		var measures = settings.getSubsectionMeasures();

		if(progression == null) {
			progression = new ChordProgressionGenerator(songInfo).generatePattern(measures);
		}else {
			progression = copyList(progression);
		}


		if(melodyRhythm == null) {
			melodyRhythm = new RhythmPatternGenerator(songInfo, density.getMelody()).generatePattern(1);
		}else {
			melodyRhythm = copyList(melodyRhythm);
		}

		if(bassRhythm == null) {
			bassRhythm = new RhythmPatternGenerator(songInfo, density.getBass()).generatePattern(1);
		}else {
			bassRhythm = copyList(bassRhythm);
		}

		if(chordsRhythm == null) {
			chordsRhythm = new RhythmPatternGenerator(songInfo, density.getChords()).generatePattern(1);
		}else {
			chordsRhythm = copyList(chordsRhythm);
		}


		if(chordsPatternType == null) {	
			chordsPatternType = ChordsPatternGenerator.ChordsPatternType.values()[(int)Random.random(ChordsPatternGenerator.ChordsPatternType.values().length)];
		}




		if(kick == null) {	
			kick = new KickDrumPatternGenerator(songInfo, density.getDrum()).generatePattern(measures/2);
			kick.addAll(kick); // double
		}else {
			kick = copyList(kick);
		}

		if(snare == null) {	
			snare = new SnareDrumPatternGenerator(songInfo, density.getDrum(), false).generatePattern(measures/2);
			var snarePatternFill = snare;
			if(fill) {
				snarePatternFill = new SnareDrumPatternGenerator(songInfo, density.getDrum(), true).generatePattern(measures/2);
				snare.addAll(snarePatternFill); // adding fill
			}else {
				snare.addAll(snare); // double
			}
		}else {
			snare = copyList(snare);
		}

		if(hat == null) {	
			hat = new HatDrumPatternGenerator(songInfo, density.getDrum()).generatePattern(measures/2);
			hat.addAll(hat); // double
		}else {
			hat = copyList(hat);
		}


		if(crash == null) {	
			crash = new CrashDrumPatternGenerator(songInfo, density.getDrum()).generatePattern(measures/2);
			crash.addAll(crash); // double
		}else {
			crash = copyList(crash);
		}


		if(chords == null) {	
			chords = new ChordsPatternGenerator(
					progression,
					chordsRhythm,
					chordsPatternType,
					songInfo
					).generatePattern(measures);
		}else {
			chords = copyList(chords);
		}

		if(bass == null) {	
			bass = new BassPatternGenerator(
					progression,
					bassRhythm,
					songInfo
					).generatePattern(measures);
		}else {
			bass = copyList(bass);
		}

		if(voiceLine == null) {
			voiceLine = new VoiceLinePatternGenerator(songInfo).generatePattern(1); 
		}else {
			voiceLine = copyList(voiceLine);
		}

		if(melody == null) {	
			melody = new MelodyPatternGenerator(
					songInfo.getKey(),
					progression,
					melodyRhythm,
					voiceLine,
					songInfo
					).generatePattern(measures);
		}else {
			melody = copyList(melody);
		}

		return new Section(
				measures,
				progression,
				voiceLine,
				melodyRhythm,
				bassRhythm,
				chordsRhythm,
				chordsPatternType,
				kick,
				snare,
				hat,
				crash,
				chords,
				bass,
				melody
				);

	}


}
