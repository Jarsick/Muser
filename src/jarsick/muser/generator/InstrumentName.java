package jarsick.muser.generator;

public record InstrumentName(String name, int midi) {
	public String toString() {
		return name;
	}
	
	@Override
	public int midi() {
		return this.midi - 1;
	}
};