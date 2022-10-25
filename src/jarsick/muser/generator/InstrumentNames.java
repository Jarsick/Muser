/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.generator;

public interface InstrumentNames {
	
	InstrumentName[] MELODY = {
			new InstrumentName("Piano", 1),
			new InstrumentName("Harpsichord", 7),
			new InstrumentName("Electric Piano", 5),
			new InstrumentName("Rock Organ", 18),
			new InstrumentName("Church Organ", 19),
			new InstrumentName("Accordion", 21),
			new InstrumentName("Clarinet", 72),
			new InstrumentName("Flute", 74),
			new InstrumentName("Trumpet", 57),
			new InstrumentName("Violin", 41),
			new InstrumentName("Alto Sax", 66),
			new InstrumentName("Tenor Sax", 67),
			new InstrumentName("Acoustic Guitar", 25),
			new InstrumentName("Electric Guitar", 30),
			new InstrumentName("Celesta", 9),
			new InstrumentName("Lead", 81),
	};
	
	InstrumentName[] BASS = {
			new InstrumentName("Acoustic Bass", 33),
			new InstrumentName("Electric Bass", 34),
			new InstrumentName("Slap Bass", 36),
			new InstrumentName("Synth Bass", 39),
			new InstrumentName("Cello", 43),
			new InstrumentName("Contrbass", 44),
			new InstrumentName("Trombone", 58),
	};
	
	InstrumentName[] CHORDS = {
			new InstrumentName("Choir", 53),
			new InstrumentName("Brass", 62),
			new InstrumentName("Strings", 49),
			new InstrumentName("Pad", 89),
			new InstrumentName("Piano", 1),
			new InstrumentName("Harpsichord", 7),
			new InstrumentName("Electric Piano", 5),
			new InstrumentName("Rock Organ", 18),
			new InstrumentName("Church Organ", 19),
			new InstrumentName("Acoustic Guitar", 25),
			new InstrumentName("Electric Guitar", 30),
	};

	default String toDisplayName(String instrumentName) {
		var displayName = instrumentName.substring(0, 1).toUpperCase() + instrumentName.substring(1);
		displayName = instrumentName.replace('_', ' ');	
		return displayName;
	}
}
