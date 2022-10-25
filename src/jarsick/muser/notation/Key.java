/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.notation;

public class Key {

	private Note tonic;
	private Scale scale;

	public Key(Note tonic, Scale scale) {
		super();
		this.tonic = tonic;
		this.scale = scale;
	}

	public Note getNote(int degree) {
		return new Note(tonic.getValue() + scale.getDegree(degree).tonicDistance(), tonic.getDuration(), tonic.getVelocity());
	}


	/**@param note the note to transpose
	 * @param dist the distance in degrees(not semitones!) for the transposing process
	 * */
	public void transpose(Note note, int dist) {
		int noteDegree = this.getNoteDegree(note);
		noteDegree = ((noteDegree - 1) % this.getScale().getDegreeCount()) + 1;
		if(noteDegree < 1) {
			noteDegree += scale.getDegreeCount();
		}
		int resultDegree = (noteDegree + dist); 
		resultDegree = ((resultDegree - 1) % this.getScale().getDegreeCount()) + 1;
		if(resultDegree < 1) {
			resultDegree += scale.getDegreeCount();
		}

		int noteTonicDistance =  this.getScale().getDegree(noteDegree).tonicDistance();
		int resultTonicDistance = this.getScale().getDegree(resultDegree).tonicDistance();
		//calculating the shift in semitones
		int shift =  resultTonicDistance - noteTonicDistance;

		// limiting the shift
		if(shift > 6) {
			shift -= 12;
		}else if(shift < - 6) {
			shift += 12;
		}
		note.setValue(note.getValue() + shift);
	}


	public int getNoteDegree(Note note) {
		int tonicBaseNoteIndex = this.getTonic().getBaseNoteIndex();
		int noteBaseNoteIndex = note.getBaseNoteIndex();
		int tonicDistance = noteBaseNoteIndex - tonicBaseNoteIndex;
		if(tonicDistance < 0) {
			tonicDistance += 12;
		}
		int degreeIndex = -1;
		for(int i = 1 ; i <= this.getScale().getDegreeCount(); i++) { // ciclo sui gradi, quindi parto da 1
			var degree = this.getScale().getDegree(i);
			if(degree.tonicDistance() == tonicDistance) {
				return i;
			}
		}
		return degreeIndex;
	}

	public Note getTonic() {
		return tonic;
	}

	public Scale getScale() {
		return scale;
	}
}
