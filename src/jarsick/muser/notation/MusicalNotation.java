package jarsick.muser.notation;

public interface MusicalNotation {
	public int getDuration();
	public MusicalNotation setDuration(int duration);

	public float getVelocity();
	public MusicalNotation setVelocity(float velocity);

	public boolean isSilence();
	public MusicalNotation makeSilence();

	MusicalNotation copy();
}
