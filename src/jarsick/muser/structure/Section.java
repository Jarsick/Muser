package jarsick.muser.structure;

import java.util.ArrayList;
import java.util.List;
import jarsick.muser.generator.chords.ChordsPatternGenerator;

import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Note;

public record Section (
		int measures,
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
		List<Note> melody
		) {
	
	private Section() {
		this(
				0,
				new ArrayList<Chord>(), 
				new ArrayList<Integer>(), 
				new ArrayList<Drum>(),  
				new ArrayList<Drum>(), 
				new ArrayList<Drum>(),
				ChordsPatternGenerator.ChordsPatternType.PAD,
				new ArrayList<Drum>(),
				new ArrayList<Drum>(), 
				new ArrayList<Drum>(),
				new ArrayList<Drum>(),
				new ArrayList<Chord>(),
				new ArrayList<Note>(),
				new ArrayList<Note>()
				);
	}
	
	public static Song mergeInSong(SongInfo songInfo, List<Section > sections) {
		var song = new Song(songInfo);
		for(var section : sections) {
			song.getKick().addAll(section.kick());
			song.getSnare().addAll(section.snare());
			song.getHat().addAll(section.hat());
			song.getCrash().addAll(section.crash());
			song.getChords().addAll(section.chords());
			song.getBass().addAll(section.bass());
			song.getMelody().addAll(section.melody());

		}
		return song;
	}


	public static Section merge(List<Section > sections) {
		var result  = new Section();
		for(var section : sections) {
			result.kick().addAll(section.kick());
			result.snare().addAll(section.snare());
			result.hat().addAll(section.hat());
			result.crash().addAll(section.crash());
			result.chords().addAll(section.chords());
			result.bass().addAll(section.bass());
			result.melody().addAll(section.melody());

		}
		return result;
	}


}
