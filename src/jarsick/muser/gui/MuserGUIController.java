/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.gui;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jarsick.muser.audio.Audio;
import jarsick.muser.generator.SongGeneratorSettings;
import jarsick.muser.midi.MIDI;
import jarsick.muser.midi.SequenceExporter;

public class MuserGUIController {
	private Sequence currentSequence;
	private JFrame frame;
	private SongGeneratorSettings settings;
	private Sequencer sequencer;


	public MuserGUIController(JFrame frame) {
		this.frame = frame;
		this.settings = new SongGeneratorSettings().randomize();
		try {
			this.sequencer = MidiSystem.getSequencer();
			sequencer.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.frame, "MIDI player not available!");
		}
	}

	public SongGeneratorSettings getSettings() {
		return this.settings;
	}

	public void generateSong() {
		this.currentSequence = MIDI.createSequence(settings);
	}


	public void playGenertedSong() {
		if(sequencer == null) return;
		try {
			sequencer.stop();
			sequencer.setSequence(this.currentSequence);
			sequencer.start();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.frame, "Cannot Play the MIDI file");
		}
	}

	public void saveMIDI() {
		if(currentSequence == null) {
			JOptionPane.showMessageDialog(this.frame, "You must generate a song before saving");
			return;
		}
		var fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this.frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.saveMIDI(fc.getSelectedFile());
		}
	}

	private String getOutputPath(File file, String extension) {
		var absolutePath = file.getAbsolutePath();
		if(!absolutePath.endsWith(extension)){
			absolutePath += extension;
		}
		return absolutePath;
	}
	
	

	private void saveMIDI(File file) {
		exportFile(file, Audio.MIDI_EXTENSION, MIDI::exportMIDI);
	}
	
	private void exportFile(File file, String extension, SequenceExporter exporter) {
		String filePath = this.getOutputPath(file, Audio.MIDI_EXTENSION);
		try {
			exporter.export(currentSequence, new File(filePath));
			JOptionPane.showMessageDialog(this.frame,"File exported");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.frame,"Cannot export file");
		}
	}

	public void stopSong() {
		if(this.sequencer != null && this.sequencer.isOpen()) {
			this.sequencer.stop();
		}
	}
}
