package jarsick.muser.notation;

public enum Drum{
	KICK(36),
	SNARE(28),
	HAT(42),
	CRASH(49),
	RIDE(51),
	SILENCE(0);
	
	
	private int midiValue;
	
	Drum(int midiValue) {
		this.midiValue = midiValue;
	}
	
	public int getMIDI(){
		return midiValue;
	}
}
