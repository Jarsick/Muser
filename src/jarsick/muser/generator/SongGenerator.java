/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator;

import static jarsick.muser.generator.PatternModification.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jarsick.muser.generator.chords.ChordsPatternGenerator;
import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Note;
import jarsick.muser.structure.Section;
import jarsick.muser.structure.SectionStructure;
import jarsick.muser.structure.Song;
import jarsick.muser.structure.Subsection;

public class SongGenerator {

	final private static int TONIC_HIT_DURATION = 16;

	static public Song generate(SongGeneratorSettings settings){
		var songInfo = settings.getSongInfo();
		var A_MAIN_SUBSECTION = SectionGenerator.generate(settings);
		var A_ALTERED_MAIN_SUBSECTION = modifySection(settings, A_MAIN_SUBSECTION, false, SectionModification.ALTERED_SECTION.getRandomModification());
		var B_MAIN_SUBSECTION = modifySection(settings, A_MAIN_SUBSECTION, false, SectionModification.NEW_SECTION.getRandomModification());
		var B_ALTERED_MAIN_SUBSECTION = modifySection(settings, B_MAIN_SUBSECTION, false, SectionModification.ALTERED_SECTION.getRandomModification());
		var C_MAIN_SUBSECTION = modifySection(settings, A_MAIN_SUBSECTION, false, SectionModification.NEW_SECTION.getRandomModification());;

		var A = generateSection(settings, A_MAIN_SUBSECTION, false);
		var A_ENDING = generateSection(settings, A_MAIN_SUBSECTION, true);
		var A_ALTERED = generateSection(settings, A_ALTERED_MAIN_SUBSECTION, true);
		var B = generateSection(settings, B_MAIN_SUBSECTION, true);
		var B_ALTERED = generateSection(settings, B_ALTERED_MAIN_SUBSECTION, true);
		var C = generateSection(settings, C_MAIN_SUBSECTION, true);
		var introModifications = SectionModification.INTRO.getRandomModification();
		var INTRO =  modifySection(settings, B_MAIN_SUBSECTION, false, introModifications);
		var INTRO_2 =  modifySection(settings, B_MAIN_SUBSECTION, false, introModifications);
		var INTRO_FILL =  modifySection(settings, INTRO_2, true);
		var ENDING =  modifySection(settings, A_MAIN_SUBSECTION, false, SectionModification.ENDING.getRandomModification());

		var timeSignatureFraction = settings.getTimeSignature().getTop() / (float)settings.getTimeSignature().getBottom();
		if(timeSignatureFraction < 1) {
			INTRO = Section.merge(Arrays.asList(INTRO, INTRO_FILL)); // Double the intro
		}else {
			INTRO = Section.merge(Arrays.asList(INTRO_FILL)); // Just 1 intro loop
		}

		var core = Arrays.asList(B, A, B_ALTERED, B, A_ALTERED, A, C, A, A_ENDING);
		var coreLoop = new ArrayList<Section>();
		for(int i = 0; i < settings.getLoops(); i++) {
			coreLoop.addAll(core);
		}
		var song = new ArrayList<Section>();
		song.add(INTRO);
		song.addAll(coreLoop);
		song.add(ENDING);
		return Section.mergeInSong(songInfo, song); // TODO: unica struttura possibile?
	}


	private static Section generateSection(SongGeneratorSettings settings, Section mainSubsection, boolean fill){
		Section secondary = modifySection(settings, mainSubsection, false, SectionModification.SUBSECTION.getRandomModification());

		List<Section> sections = new ArrayList<>();
		for(Subsection subsection : SectionStructure.values()[(int)(Random.random(SectionStructure.values().length))].getSubsections()) {
			switch(subsection) {
			case A:
				sections.add(mainSubsection);
				break;
			case B:
			default:
				sections.add(secondary);
				break;
			}
		}
		if(fill) {
			int lastIndex = sections.size() - 1;
			Section lastSection = sections.get(lastIndex);
			sections.set(lastIndex, createFillSection(settings, lastSection));
		}
		return Section.merge(sections);
	}

	private static Section createFillSection(SongGeneratorSettings settings, Section section) {
		return modifySection(settings, section, true, PatternModification.CHANGE_SNARE);
	}


	static Section modifySection(SongGeneratorSettings settings, Section section, boolean fill, PatternModification... modifications){
		var originalSettings = settings;
		settings = settings.copy();
		var songInfo = settings.getSongInfo();
		var progression = !contains(modifications, CHANGE_CHORDS)? section.progression() : null;
		var voiceLine = !contains(modifications, CHANGE_VOICELINE)? section.voiceLine() : null;
		var melodyRhythm = !contains(modifications, CHANGE_MELODY_RHYTHM)? section.melodyRhythm() : null;
		var bassRhythm = !contains(modifications, CHANGE_BASS_RHYTHM)? section.bassRhythm() : null;
		var chordsRhythm = !contains(modifications, CHANGE_CHORDS_RHYTHM)? section.chordsRhythm() : null;
		var chordsPatternType = !contains(modifications, CHANGE_CHORDS_TYPE)? section.chordsPatternType() : newRandomChordsType(section.chordsPatternType());
		var kickPattern = !contains(modifications, CHANGE_DRUM, CHANGE_KICK, HALF_DRUM)? section.kick() : null;
		var snarePattern = (!contains(modifications, CHANGE_DRUM, CHANGE_SNARE, HALF_DRUM) && !fill)?section.snare() : null;
		var hatPattern = !contains(modifications, CHANGE_DRUM, CHANGE_HAT, HALF_DRUM)?section.hat() : null;
		var crashPattern = !contains(modifications, CHANGE_DRUM, CHANGE_CRASH, HALF_DRUM)?section.crash() : null;
		var chordsPattern = !contains(modifications, CHANGE_CHORDS_TYPE, CHANGE_CHORDS,CHANGE_CHORDS_RHYTHM, HALF_CHORDS)? section.chords() : null;
		var bassNotes = !contains(modifications, CHANGE_CHORDS, CHANGE_BASS, CHANGE_BASS_RHYTHM, HALF_BASS)?section.bass() : null;
		var melodyNotes = !contains(modifications, CHANGE_CHORDS, CHANGE_VOICELINE, CHANGE_MELODY, CHANGE_MELODY_RHYTHM, HALF_MELODY)?section.melody() : null;

		if(contains(modifications, HALF_MELODY)) {
			settings.getDensity().setMelody(originalSettings.getDensity().getMelody()/2);
		}

		if(contains(modifications, HALF_BASS)) {
			settings.getDensity().setBass(originalSettings.getDensity().getBass()/2);
		}

		if(contains(modifications, HALF_CHORDS)) {
			settings.getDensity().setChords(originalSettings.getDensity().getChords()/2);
		}

		if(contains(modifications, HALF_DRUM)) {
			settings.getDensity().setDrum(originalSettings.getDensity().getDrum()/2);
		}

		if(contains(modifications, TONIC_HIT)) {
			var type = songInfo.key().getScale().getType();
			kickPattern = Arrays.asList(Drum.KICK);
			snarePattern = Arrays.asList(Drum.SNARE);
			hatPattern = Arrays.asList(Drum.HAT);
			crashPattern = Arrays.asList(Drum.CRASH);
			chordsPattern = Arrays.asList(Chord.create(songInfo.key().getTonic(), type).setDuration(TONIC_HIT_DURATION));
			bassNotes =  Arrays.asList(songInfo.key().getTonic().copy().transpose(-12).setDuration(TONIC_HIT_DURATION));
			melodyNotes =  Arrays.asList(songInfo.key().getTonic().copy().transpose(12).setDuration(TONIC_HIT_DURATION));

		}

		var newSection = SectionGenerator.generate(
				settings,
				progression,
				voiceLine,
				melodyRhythm,
				bassRhythm,
				chordsRhythm,
				chordsPatternType,
				kickPattern,
				snarePattern,
				hatPattern,
				crashPattern,
				chordsPattern,
				bassNotes,
				melodyNotes,
				fill
				);

		if(contains(modifications, REMOVE_KICK, REMOVE_DRUM))
			silenceDrums(newSection.kick());
		if(contains(modifications, REMOVE_SNARE, REMOVE_DRUM) && !fill)
			silenceDrums(newSection.snare());
		if(contains(modifications, REMOVE_HAT, REMOVE_DRUM))
			silenceDrums(newSection.hat());
		if(contains(modifications, REMOVE_BASS))
			silenceNotes(newSection.bass());
		if(contains(modifications, REMOVE_MELODY))
			silenceNotes(newSection.melody());
		if(contains(modifications, REMOVE_CHORDS))
			silenceChords(newSection.chords());

		return newSection;
	}


	private static void silenceDrums(List<Drum> drums) {
		for(int i = 0; i < drums.size(); i ++) {
			drums.set(i, Drum.SILENCE);
		}
	}

	private static void silenceChords(List<Chord> notes) {
		for(int i = 0; i < notes.size(); i ++) {
			notes.set(i, notes.get(i).copy().makeSilence());
		}
	}

	private static void silenceNotes(List<Note> notes) {
		for(int i = 0; i < notes.size(); i ++) {
			notes.set(i, notes.get(i).copy().makeSilence());
		}
	}


	private static ChordsPatternGenerator.ChordsPatternType newRandomChordsType(ChordsPatternGenerator.ChordsPatternType oldType){
		ChordsPatternGenerator.ChordsPatternType result = null;
		do {
			result = ChordsPatternGenerator.ChordsPatternType.values()[(int)(Random.random(ChordsPatternGenerator.ChordsPatternType.values().length))];
		}while(result == oldType);
		return result;
	}


	/**Return true if contains at least 1 of this modifications*/
	static private boolean contains(PatternModification[] modifications, PatternModification... toBeChecked) {
		for(var check : toBeChecked){
			for(var modification : modifications) {
				if(modification.equals(check))
					return true;
			}
		}
		return false;
	}

}
