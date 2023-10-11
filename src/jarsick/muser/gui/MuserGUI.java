/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import jarsick.muser.generator.InstrumentName;
import jarsick.muser.generator.InstrumentNames;
import jarsick.muser.generator.random.Random;
import jarsick.muser.notation.Note;
import jarsick.muser.notation.Scale;
import jarsick.muser.notation.TimeSignature;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class MuserGUI {

	private JFrame frmMuser;
	private MuserGUIController controller;
	final private static String VERSION = "1.1.0_alpha";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MuserGUI window = new MuserGUI();
					window.frmMuser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MuserGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMuser = new JFrame();
		frmMuser.setResizable(false);
		frmMuser.setTitle("Muser " + VERSION);
		frmMuser.setIconImage(Toolkit.getDefaultToolkit().getImage(MuserGUI.class.getResource("/logo.png")));
		controller = new MuserGUIController(frmMuser);
		frmMuser.getContentPane().setForeground(Color.WHITE);
		frmMuser.getContentPane().setBackground(new Color(63, 63, 63));
		frmMuser.setBounds(100, 100, 425, 727);
		frmMuser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FormLayout formLayout = new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.MIN_COLSPEC,
				ColumnSpec.decode("min:grow"),
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(0dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,});
		frmMuser.getContentPane().setLayout(formLayout);

		var lblGeneralSettings = new JLabel("General");
		lblGeneralSettings.setIcon(new ImageIcon(MuserGUI.class.getResource("/settings_icon.png")));
		lblGeneralSettings.setForeground(new Color(255, 128, 128));
		lblGeneralSettings.setFont(new Font("Teko SemiBold", Font.BOLD, 16));
		frmMuser.getContentPane().add(lblGeneralSettings, "2, 2");
		
		var btnHelp = new JButton("");
		btnHelp.setToolTipText("Help");
		btnHelp.setIcon(new ImageIcon(MuserGUI.class.getResource("/help_icon.png")));
		btnHelp.setContentAreaFilled(false);
		btnHelp.addActionListener(evt ->{
			var desktop =  java.awt.Desktop.getDesktop();
			URI oURL;
			try {
				oURL = new URI("https://jarsick.itch.io/muser");
				desktop.browse(oURL);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		frmMuser.getContentPane().add(btnHelp, "7, 2, right, default");

		var lblTempo = new JLabel("Tempo(BPM)");
		lblTempo.setToolTipText("The song speed");
		lblTempo.setForeground(new Color(255, 255, 255));
		lblTempo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTempo.setHorizontalAlignment(SwingConstants.RIGHT);
		frmMuser.getContentPane().add(lblTempo, "2, 4, right, default");

		var lblTempoValue = new JLabel("*value");
		lblTempoValue.setForeground(new Color(255, 255, 255));
		frmMuser.getContentPane().add(lblTempoValue, "5, 4");

		var sliderTempo = new JSlider();
		sliderTempo.setPaintTicks(true);
		sliderTempo.setBackground(SystemColor.controlDkShadow);
		sliderTempo.setValue(160);
		sliderTempo.setMaximum(250);
		sliderTempo.setMinimum(90);
		frmMuser.getContentPane().add(sliderTempo, "4, 4, left, default");

		var lblTimeSignature = new JLabel("Time Signature");
		lblTimeSignature.setToolTipText("This will influence the rhythms of the whole song");
		lblTimeSignature.setForeground(new Color(255, 255, 255));
		lblTimeSignature.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTimeSignature.setHorizontalAlignment(SwingConstants.RIGHT);
		frmMuser.getContentPane().add(lblTimeSignature, "2, 6, right, default");

		var comboBoxTimeSignature = new JComboBox<TimeSignature>();
		comboBoxTimeSignature.setFont(new Font("Tahoma", Font.BOLD, 12));
		for(var value : TimeSignature.values())
			comboBoxTimeSignature.addItem(value);
		frmMuser.getContentPane().add(comboBoxTimeSignature, "4, 6, left, default");

		var lblTonic = new JLabel("Tonic");
		lblTonic.setToolTipText("The Key for  the song");
		lblTonic.setForeground(new Color(255, 255, 255));
		lblTonic.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTonic.setHorizontalAlignment(SwingConstants.RIGHT);
		frmMuser.getContentPane().add(lblTonic, "2, 8, right, default");

		var comboBoxTonic = new JComboBox<Note>();
		comboBoxTonic.setFont(new Font("Tahoma", Font.BOLD, 12));
		for(var value : Note.TONICS)
			comboBoxTonic.addItem(value);
		frmMuser.getContentPane().add(comboBoxTonic, "4, 8, left, default");

		var lblScale = new JLabel("Scale");
		lblScale.setToolTipText("The Scale for the song");
		lblScale.setForeground(new Color(255, 255, 255));
		lblScale.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblScale.setHorizontalAlignment(SwingConstants.RIGHT);
		frmMuser.getContentPane().add(lblScale, "2, 10, right, default");

		var comboBoxScale = new JComboBox<Scale>();
		comboBoxScale.setFont(new Font("Tahoma", Font.BOLD, 12));
		for(var value : Scale.values())
			comboBoxScale.addItem(value);
		frmMuser.getContentPane().add(comboBoxScale, "4, 10, left, default");

		var separator = new JSeparator();
		frmMuser.getContentPane().add(separator, "1, 12, 15, 1");

		var lblMelodySettings = new JLabel("Melody");
		lblMelodySettings.setIcon(new ImageIcon(MuserGUI.class.getResource("/melody_icon.png")));
		lblMelodySettings.setForeground(new Color(255, 128, 128));
		lblMelodySettings.setFont(new Font("Teko SemiBold", Font.BOLD, 16));
		frmMuser.getContentPane().add(lblMelodySettings, "2, 14");
		
		JLabel lblChannel = new JLabel("(MIDI Channel 1)");
		lblChannel.setForeground(new Color(255, 255, 255));
		frmMuser.getContentPane().add(lblChannel, "4, 14");

		JLabel lblBassInstrument_1 = new JLabel("Instrument: ");
		lblBassInstrument_1.setToolTipText("Instrument for the melody");
		lblBassInstrument_1.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblBassInstrument_1, "2, 16, right, default");

		var comboBoxMelodyInstrument = new JComboBox<InstrumentName>();
		comboBoxMelodyInstrument.setFont(new Font("Tahoma", Font.BOLD, 12));
		frmMuser.getContentPane().add(comboBoxMelodyInstrument, "4, 16, fill, default");
		
		JCheckBox chckbxSingableMelody = new JCheckBox("Singable Melody");
		chckbxSingableMelody.setToolTipText("Limits the range of the melody o be more singable");
		chckbxSingableMelody.setForeground(Color.WHITE);
		chckbxSingableMelody.setBackground(SystemColor.controlDkShadow);
		frmMuser.getContentPane().add(chckbxSingableMelody, "4, 18");

		var lblMelodyDensity = new JLabel("Density");
		lblMelodyDensity.setToolTipText("The value of the 'presence' of this instrument (0 means deleting the instrument)");
		lblMelodyDensity.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblMelodyDensity, "2, 20, right, default");

		var sliderMelodyDensity = new JSlider();
		sliderMelodyDensity.setPaintTicks(true);
		sliderMelodyDensity.setValue(7);
		sliderMelodyDensity.setMinimum(0);
		sliderMelodyDensity.setMaximum(10);
		sliderMelodyDensity.setBackground(SystemColor.controlDkShadow);
		frmMuser.getContentPane().add(sliderMelodyDensity, "4, 20, left, default");

		var lblMelodyDensityValue = new JLabel("*density");
		lblMelodyDensityValue.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblMelodyDensityValue, "5, 20");
		
		JSeparator separator_1 = new JSeparator();
		frmMuser.getContentPane().add(separator_1, "1, 22, 15, 1");

		var lblBassSettings = new JLabel("Bass");
		lblBassSettings.setIcon(new ImageIcon(MuserGUI.class.getResource("/bass_icon.png")));
		lblBassSettings.setForeground(new Color(255, 128, 128));
		lblBassSettings.setFont(new Font("Teko SemiBold", Font.BOLD, 16));
		frmMuser.getContentPane().add(lblBassSettings, "2, 24");
		
		JLabel lblmidiChannel_1 = new JLabel("(MIDI Channel 2)");
		lblmidiChannel_1.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblmidiChannel_1, "4, 24");


		var lblBassInstrument = new JLabel("Instrument: ");
		lblBassInstrument.setToolTipText("Instrument for the bass");
		lblBassInstrument.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblBassInstrument, "2, 26, right, default");

		var comboBoxBassInstrument = new JComboBox<InstrumentName>();
		comboBoxBassInstrument.setFont(new Font("Tahoma", Font.BOLD, 12));
		frmMuser.getContentPane().add(comboBoxBassInstrument, "4, 26, fill, default");

		var lblBassDensity = new JLabel("Density");
		lblBassDensity.setToolTipText("The value of the 'presence' of this instrument (0 means deleting the instrument)");
		lblBassDensity.setForeground(new Color(255, 255, 255));
		frmMuser.getContentPane().add(lblBassDensity, "2, 28, right, default");

		var sliderBassDensity = new JSlider();
		sliderBassDensity.setPaintTicks(true);
		sliderBassDensity.setBackground(SystemColor.controlDkShadow);
		sliderBassDensity.setValue(7);
		sliderBassDensity.setMinimum(0);
		sliderBassDensity.setMaximum(10);
		frmMuser.getContentPane().add(sliderBassDensity, "4, 28, left, default");
		
		
				var lblBassDensityValue = new JLabel("*density");
				lblBassDensityValue.setForeground(Color.WHITE);
				frmMuser.getContentPane().add(lblBassDensityValue, "5, 28");
				bindLabelToSlider(lblBassDensityValue, sliderBassDensity);
		
		JSeparator separator_3 = new JSeparator();
		frmMuser.getContentPane().add(separator_3, "1, 30, 15, 1");

		var lblChordsSettings = new JLabel("Chords");
		lblChordsSettings.setToolTipText("Instrument for the chords");
		lblChordsSettings.setIcon(new ImageIcon(MuserGUI.class.getResource("/chords_icon.png")));
		lblChordsSettings.setForeground(new Color(255, 128, 128));
		lblChordsSettings.setFont(new Font("Teko SemiBold", Font.BOLD, 16));
		frmMuser.getContentPane().add(lblChordsSettings, "2, 32");
		
		JLabel lblmidiChannel_2 = new JLabel("(MIDI Channel 3)");
		lblmidiChannel_2.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblmidiChannel_2, "4, 32");

		JLabel lblBassInstrument_2 = new JLabel("Instrument: ");
		lblBassInstrument_2.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblBassInstrument_2, "2, 34, right, default");

		var comboBoxChordsInstrument = new JComboBox<InstrumentName>();
		comboBoxChordsInstrument.setFont(new Font("Tahoma", Font.BOLD, 12));
		frmMuser.getContentPane().add(comboBoxChordsInstrument, "4, 34, fill, default");

		var lblChordsDensity = new JLabel("Density");
		lblChordsDensity.setToolTipText("The value of the 'presence' of this instrument (0 means deleting the instrument)");
		lblChordsDensity.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblChordsDensity, "2, 36, right, default");

		var sliderChordsDensity = new JSlider();
		sliderChordsDensity.setPaintTicks(true);
		sliderChordsDensity.setValue(7);
		sliderChordsDensity.setMinimum(0);
		sliderChordsDensity.setMaximum(10);
		sliderChordsDensity.setBackground(SystemColor.controlDkShadow);
		frmMuser.getContentPane().add(sliderChordsDensity, "4, 36, left, default");
		
				var lblChordsDensityValue = new JLabel("*density");
				lblChordsDensityValue.setForeground(Color.WHITE);
				frmMuser.getContentPane().add(lblChordsDensityValue, "5, 36");
				bindLabelToSlider(lblChordsDensityValue, sliderChordsDensity);
		
		JSeparator separator_2 = new JSeparator();
		frmMuser.getContentPane().add(separator_2, "1, 38, 15, 1");

		var lblDrumSettings = new JLabel("Drum");
		lblDrumSettings.setIcon(new ImageIcon(MuserGUI.class.getResource("/drum_icon.png")));
		lblDrumSettings.setForeground(new Color(255, 128, 128));
		lblDrumSettings.setFont(new Font("Teko SemiBold", Font.BOLD, 16));
		frmMuser.getContentPane().add(lblDrumSettings, "2, 40");
		
		JLabel lblmidiChannel = new JLabel("(MIDI Channel 10)");
		lblmidiChannel.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblmidiChannel, "4, 40");

		var lblDrumDensity = new JLabel("Density");
		lblDrumDensity.setToolTipText("The value of the 'presence' of this instrument (0 means deleting the instrument)");
		lblDrumDensity.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblDrumDensity, "2, 42, right, default");	

		var sliderDrumDensity = new JSlider();
		sliderDrumDensity.setPaintTicks(true);
		sliderDrumDensity.setValue(10);
		sliderDrumDensity.setMinimum(0);
		sliderDrumDensity.setMaximum(10);
		sliderDrumDensity.setBackground(SystemColor.controlDkShadow);
		frmMuser.getContentPane().add(sliderDrumDensity, "4, 42, left, default");

		var lblDrumDensityValue = new JLabel("*density");
		lblDrumDensityValue.setForeground(Color.WHITE);
		frmMuser.getContentPane().add(lblDrumDensityValue, "5, 42, left, default");

		var menuBar = new JMenuBar();
		menuBar.setForeground(Color.BLACK);
		menuBar.setBackground(Color.BLACK);
		frmMuser.setJMenuBar(menuBar);

		// fill combo boxes
		for(var value : InstrumentNames.MELODY)
			comboBoxMelodyInstrument.addItem(value);

		for(var value : InstrumentNames.BASS)
			comboBoxBassInstrument.addItem(value);

		for(var value : InstrumentNames.CHORDS)
			comboBoxChordsInstrument.addItem(value);
		
		
		var chckbxDoubleDrumExport = new JCheckBox("Generate Drum also on channel 4");
		chckbxDoubleDrumExport.setBackground(SystemColor.controlDkShadow);
		chckbxDoubleDrumExport.setForeground(new Color(255, 255, 255));
		chckbxDoubleDrumExport.setToolTipText("The default drum channel is 10,  Some music editor couldn't import it");
		frmMuser.getContentPane().add(chckbxDoubleDrumExport, "4, 44, center, default");
		
		var btnRandom = new JButton("Random");
		btnRandom.setToolTipText("Randomize the song generation settings");
		btnRandom.setForeground(Color.WHITE);
		btnRandom.setBackground(Color.BLACK);
		btnRandom.setIcon(new ImageIcon(MuserGUI.class.getClassLoader().getResource("dice_icon.png")));
		menuBar.add(btnRandom);
		btnRandom.addActionListener(evt -> {
			randomSlider(sliderTempo, 120, 200);

			randomCheckBox(chckbxSingableMelody);

			randomComboBox(comboBoxTimeSignature);
			randomComboBox(comboBoxTonic);
			randomComboBox(comboBoxScale);
			randomComboBox(comboBoxMelodyInstrument);
			randomComboBox(comboBoxBassInstrument);
			randomComboBox(comboBoxChordsInstrument);

			randomSlider(sliderMelodyDensity, 2, 10);
			randomSlider(sliderBassDensity, 2, 10);
			randomSlider(sliderChordsDensity, 2, 10);
			randomSlider(sliderDrumDensity, 2, 10);


		});
		
		

		var btnGenerate = new JButton("Generate");
		btnGenerate.setToolTipText("Generate a song from settings and play it");
		btnGenerate.setBackground(Color.BLACK);
		btnGenerate.setForeground(new Color(255, 255, 255));
		btnGenerate.setIcon(new ImageIcon(MuserGUI.class.getClassLoader().getResource("play_icon.png")));
		menuBar.add(btnGenerate);

		var btnStop = new JButton("Stop");
		btnStop.setToolTipText("Stop the current playing song");
		btnStop.setBackground(Color.BLACK);
		btnStop.setForeground(new Color(255, 255, 255));
		btnStop.setIcon(new ImageIcon(MuserGUI.class.getClassLoader().getResource("stop_icon.png")));
		menuBar.add(btnStop);

		var btnSave = new JButton("Save MIDI");
		btnSave.setToolTipText("Export the last generated song into a MIDI file");
		btnSave.setBackground(Color.BLACK);
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setIcon(new ImageIcon(MuserGUI.class.getClassLoader().getResource("save_icon.png")));
		menuBar.add(btnSave);
		btnSave.addActionListener(evt -> {
			controller.saveMIDI();
		});
		btnStop.addActionListener(evt -> {controller.stopSong();});
		btnGenerate.addActionListener(evt -> {
			var settings = controller.getSettings();
			settings.setTonic((Note) comboBoxTonic.getSelectedItem());
			settings.setScale((Scale) comboBoxScale.getSelectedItem());
			settings.setTimeSignature((TimeSignature) comboBoxTimeSignature.getSelectedItem());
			settings.setTempo(sliderTempo.getValue());
			
			settings.setDoubleDrumExport(chckbxDoubleDrumExport.isSelected());

			/** Limit to one Octave*/ // TODO: maybe we can make the octave range selectable?
			if(chckbxSingableMelody.isSelected()){
				int octave = (int)Random.random(settings.DEFAULT_MIN_OCTAVE, settings.DEFAULT_MAX_OCTAVE + 1);
				settings.setMinOctave(octave);
				settings.setMaxOctave(octave);
			}
			
			settings.setMelodyInstrument(((InstrumentName)comboBoxMelodyInstrument.getSelectedItem()).midi());
			settings.setBassInstrument(((InstrumentName)comboBoxBassInstrument.getSelectedItem()).midi());
			settings.setChordsInstrument(((InstrumentName)comboBoxChordsInstrument.getSelectedItem()).midi());

			settings.getDensity().setMelody(sliderMelodyDensity.getValue()/10f);
			settings.getDensity().setBass(sliderBassDensity.getValue()/10f);
			settings.getDensity().setChords(sliderChordsDensity.getValue()/10f);
			settings.getDensity().setDrum(sliderDrumDensity.getValue()/10f);

			controller.generateSong();
			controller.playGenertedSong();
		});
		
		//bind labels
		bindLabelToSlider(lblTempoValue, sliderTempo);
		bindLabelToSlider(lblMelodyDensityValue, sliderMelodyDensity);
		bindLabelToSlider(lblDrumDensityValue, sliderDrumDensity);
		
	}

	static private void randomSlider(JSlider slider, int min, int max) {
		slider.setValue((int) Random.random(min, max));
	}

	static private void randomComboBox(JComboBox<?> combobox) {
		combobox.setSelectedIndex((int) Random.random(combobox.getItemCount()));
	}

	static private void randomCheckBox(JCheckBox checkbox) {
		checkbox.setSelected((int)Random.random(2) == 0);
	}

	static private void bindLabelToSlider(JLabel label, JSlider slider) {
		label.setText("" + slider.getValue());
		slider.addChangeListener(evt ->{
			label.setText("" + ((JSlider)evt.getSource()).getValue());
		});
	}

}
