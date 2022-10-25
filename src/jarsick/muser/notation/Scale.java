/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.notation;

public enum Scale{
	MAJOR(
			Chord.Type.MAJOR,
			degree(0, Chord.Type.MAJOR),
			degree(2, Chord.Type.MINOR),
			degree(4, Chord.Type.MINOR),
			degree(5, Chord.Type.MAJOR),
			degree(7, Chord.Type.MAJOR),
			degree(9, Chord.Type.MINOR),
			degree(11, Chord.Type.DIM)
			),
	NATURAL_MINOR(
			Chord.Type.MINOR,
			degree(0, Chord.Type.MINOR),
			degree(2, Chord.Type.DIM),
			degree(3, Chord.Type.MAJOR),
			degree(5, Chord.Type.MINOR),
			degree(7, Chord.Type.MINOR),
			degree(8, Chord.Type.MAJOR),
			degree(10, Chord.Type.MAJOR)
			),

	HARMONIC_MINOR(
			Chord.Type.MINOR,
			degree(0, Chord.Type.MINOR),
			degree(2, Chord.Type.DIM),
			degree(3, Chord.Type.MAJOR),
			degree(5, Chord.Type.MINOR),
			degree(7, Chord.Type.MAJOR),
			degree(8, Chord.Type.MAJOR),
			degree(11, Chord.Type.DIM)
			);

	private Degree[] degrees;
	private Chord.Type type;

	private Scale(Chord.Type type, Degree... degrees) {
		this.type = type;
		this.degrees = degrees;
	}


	private static Degree degree(int tonicDistance, Chord.Type chordType) {
		return new Degree(tonicDistance, chordType);
	}

	final public Chord.Type getType(){
		return this.type;
	}

	final public Degree getDegree(int degree){
		return this.degrees[degree - 1];
	}


	final public int getDegreeCount() {
		return this.degrees.length;
	}

	static public record Degree(int tonicDistance, Chord.Type type){
	}

	@Override
	public String toString() {
		return super.toString().replace('_', ' ');
	}

}
