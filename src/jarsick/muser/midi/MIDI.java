/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.midi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import org.jfugue.pattern.PatternProducer;
import org.jfugue.player.Player;

import jarsick.muser.generator.SongGenerator;
import jarsick.muser.generator.SongGeneratorSettings;
import jarsick.muser.jfigure.PatternFactory;

public class MIDI {

	static public Sequence createSequence(SongGeneratorSettings settings) {

		var player = new Player();
		player.getManagedPlayer().reset();
		var song = SongGenerator.generate(settings);


		var tempo = settings.getTempo();

		var melodyPattern = PatternFactory.fromNotes(song.getMelody(), tempo, settings.getMelodyInstrument());
		var bassPattern = PatternFactory.fromNotes(song.getBass(), tempo, settings.getBassInstrument());
		var chordsPattern = PatternFactory.fromChords(song.getChords(), tempo, settings.getChordsInstrument());

		var drumPattern = PatternFactory.fromDrums(
				9,
				tempo,
				song.getKick(),
				song.getSnare(),
				song.getHat(),
				song.getCrash()
				);
		var drumPattern2 = PatternFactory.fromDrums(
				PatternFactory.getNextVoiceIndexAndIncrement(),
				tempo,
				song.getKick(),
				song.getSnare(),
				song.getHat(),
				song.getCrash()
				);

		var patterns = new ArrayList<PatternProducer>();
		if(settings.getDensity().getDrum() > 0) {
			patterns.add(drumPattern.setTempo(settings.getTempo()));
			if(settings.doubleDrumExportEnabled()) {
				patterns.add(drumPattern2.setTempo(settings.getTempo()));
			}
		}

		if(settings.getDensity().getMelody() > 0) {
			patterns.add(melodyPattern);
		}

		if(settings.getDensity().getBass() > 0) {
			patterns.add(bassPattern);
		}

		if(settings.getDensity().getChords() > 0) {
			patterns.add(chordsPattern);
		}

		PatternFactory.resetVoiceIndex();
		return player.getSequence(patterns.toArray(PatternProducer[]::new));
	}


	static public void exportMIDI(Sequence sequence, File file) throws IOException {
		MidiSystem.write(sequence, 1, file);
	}



}
