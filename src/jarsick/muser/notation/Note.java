package jarsick.muser.notation;


public class Note implements MusicalNotation{
	final static private int SILENCE_VALUE = 0;
	
	// central OCTAVE definition
	final static public Note C = new UnmutableNote(60, 1, 1, "C");
	final static public Note C_SHARP = new UnmutableNote(61, 1, 1, "C#"),  D_FLAT = C_SHARP;
	final static public Note D = new UnmutableNote(62, 1, 1, "D");
	final static public Note D_SHARP = new UnmutableNote(63, 1, 1, "D#"),  E_FLAT = D_SHARP;
	final static public Note E = new UnmutableNote(64, 1, 1, "E");
	final static public Note F = new UnmutableNote(65, 1, 1, "F");
	final static public Note F_SHARP = new UnmutableNote(66, 1, 1, "F#"),  G_FLAT = F_SHARP;
	final static public Note G = new UnmutableNote(67, 1, 1, "G");
	final static public Note G_SHARP = new UnmutableNote(68, 1, 1, "G#"),  A_FLAT = G_SHARP;
	final static public Note A = new UnmutableNote(69, 1, 1, "A");
	final static public Note A_SHARP = new UnmutableNote(70, 1, 1, "A#"),  B_FLAT = A_SHARP;
	final static public Note B = new UnmutableNote(71, 1, 1, "B");

	final public static Note[] TONICS = {A, A_SHARP, B, C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP};

	private static class UnmutableNote extends Note {
		private String name;
		
		public UnmutableNote(int value, int duration, float velocity, String name) {
			super(value, duration, velocity);
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public void setValue(int value) {
			throw new RuntimeException("Cannot modify an unmutable note!");
		}

		@Override
		public Note setDuration(int value) {
			throw new RuntimeException("Cannot modify an unmutable note!");
		}

		@Override
		public Note setVelocity(float value) {
			throw new RuntimeException("Cannot modify an unmutable note!");
		}

	}


	final public static Note createSilence(int duration) {
		return new Note(SILENCE_VALUE, duration, 0);
	}

	public static Note randomTonic() {
		return TONICS[(int)Math.random() * TONICS.length];
	}

	
	private int value;
	private int duration;
	private float velocity;

	/**@param value the MIDI value for the note
	 * @param duration the duration of the note expressed in time divisions
	 * @velocity the note velocity
	 * */
	public Note(int value,  int duration, float velocity) {
		super();
		this.value = value;
		this.velocity = velocity;
		this.duration = duration;
	}

	public int getMIDI() {
		return this.value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public float getVelocity() {
		return velocity;
	}

	public Note setVelocity(float velocity) {
		this.velocity = velocity;
		return this;
	}

	public int getDuration() {
		return duration;
	}

	public Note setDuration(int duration) {
		this.duration = duration;
		return this;
	}


	public boolean isSilence() {
		return this.getValue() == SILENCE_VALUE;
	}

	public int getOctaveIndex() {
		return ((this.getValue() - 24) / 12) + 1; // 24 is C1 note
	}

	/**Returns the index of the note outside of the octave context starting from C (C = 0, C# = 1, D = 2, ...)*/
	public int getBaseNoteIndex() {
		int cDist = (this.getValue() - 60) % 12;
		if(cDist < 0) {
			cDist += 12;
		}
		return cDist;
	}


	final public Note transpose(int value) {
		setValue(getValue() + value);
		return this;
	}

	public String getName() {
		int baseNoteIndex = getBaseNoteIndex();
		int octave = getOctaveIndex();

		String name = "";
		switch(baseNoteIndex) {
		case 0:
			name += "C";
			break;
		case 1:
			name += "C#";
			break;
		case 2:
			name += "D";
			break;
		case 3:
			name += "D#";
			break;
		case 4:
			name += "E";
			break;
		case 5:
			name += "F";
			break;
		case 6:
			name += "F#";
			break;
		case 7:
			name += "G";
			break;
		case 8:
			name += "G#";
			break;
		case 9:
			name += "A";
			break;
		case 10:
			name += "A#";
			break;
		case 11:
			name += "B";
			break;
		default:
			name += "C";
			break;
		}
		if(octave >= 0 )
			return name + octave;
		else return "Rest";
	}


	public String toString() {
		return getName() + "(" + this.getDuration() + ")";
	}


	public Note copy() {
		return new Note(this.getValue(), this.getDuration(), this.getVelocity());
	}


	@Override
	public Note makeSilence() {
		this.setValue(SILENCE_VALUE);
		return this;
	}

}
