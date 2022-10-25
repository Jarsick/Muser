package jarsick.muser.generator.chords;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;

public class Arpeggiator {

	public enum Type{
		ASCENDING ,
		DESCENDING,
		ASCENDING_DESCENDING,
		DESCENDING_ASCENDING,


	}

	private List<Chord> progression;
	private List<Drum> rhythm;

	public Arpeggiator(List<Chord> progression, List<Drum> rhythm) {
		this.progression = progression;
		this.rhythm = rhythm;
	}


	public List<Chord> generate(){
		var result = new ArrayList<Chord>();
		for(var chord : this.progression) {
			for(var drum : this.rhythm) {
				result.add(
						switch(drum) {
						case HAT,RIDE -> new Chord().addNote(chord.getMiddleNote().copy().setDuration(1));
						case KICK,CRASH -> new Chord().addNote(chord.getLowerNote().copy().setDuration(1));
						case SNARE ->new Chord().addNote(chord.getHigherNote().copy().setDuration(1));
						default -> new Chord().setDuration(1);
						}
						);
			}
		}
		return result;
	}



}
