package jarsick.muser.jfigure;

import java.util.List;

import org.jfugue.pattern.Pattern;
import org.jfugue.rhythm.Rhythm;
import org.staccato.StaccatoUtil;

import jarsick.muser.notation.Chord;
import jarsick.muser.notation.Drum;
import jarsick.muser.notation.Note;

public class PatternFactory {

	private static int voiceIndex = 0;

	final public static int resetVoiceIndex() {
		return voiceIndex = 0;
	}

	final public static int getNextVoiceIndexAndIncrement() {
		return voiceIndex++;
	}

	final private static String DURATION_UNIT = "i";

	final static public Pattern fromNotes(List<Note> notes, int tempo, int instrument) {
		var result = new StringBuilder();
		for(var note : notes) {	
			if(!note.isSilence()) {
				result.append(note.getName());
			}else { 
				result.append("R");
			}
			for(int i = 0; i < note.getDuration(); i ++) {
				result.append(DURATION_UNIT);
			}
			result.append(" ");

		}
		var pattern = new Pattern(result.toString());
		pattern.setVoice(voiceIndex++);
		pattern.setTempo(tempo);
		pattern.setInstrument(instrument);
		return pattern;
	}

	final static public Pattern fromChords(List<Chord> chords, int tempo, int instrument) {
		var pattern = new Pattern();
		int voiceCount = getVoiceCount(chords);

		for(int i = 0; i < voiceCount; i++) {
			var voiceString = new StringBuilder();
			voiceString.append("L");
			voiceString.append(i);
			voiceString.append(" ");
			for(var chord : chords) {			
				if(i < chord.getNotes().size() && !chord.getNotes().get(i).isSilence()) { // quindi se l'accordo ha effettivamente quella voce
					voiceString.append(chord.getNotes().get(i).getName());
				}else {
					voiceString.append("R");
				}
				for(int j = 0; j < chord.getDuration(); j ++) {
					voiceString.append(DURATION_UNIT);
				}
				voiceString.append(" ");
			}
			var voicePattern = new Pattern(voiceString.toString());
			voicePattern.setVoice(voiceIndex);
			voicePattern.setTempo(tempo);
			voicePattern.setInstrument(instrument);
			pattern.add(voicePattern);
		}		
		pattern.setTempo(tempo);
		voiceIndex++;
		return pattern;
	}


	final static private int getVoiceCount(List<Chord> chords) {
		int voiceCount = 0;
		for(var chord : chords) {
			if(chord.getNotes().size() > voiceCount) {
				voiceCount = chord.getNotes().size();
			}
		}
		return voiceCount;
	}


	final static public String drumsToRyhthm(List<Drum> drums) {
		var result = new StringBuilder();
		for(var drum : drums) {
			result.append(
					switch(drum) {
					case HAT -> "`";
					case KICK -> "O";
					case SNARE -> "S";
					case CRASH -> "+";
					default -> ".";
					}
					);
		}

		return result.toString();
	}

	@SafeVarargs
	public static Pattern fromDrums(int channel, int tempo, List<Drum>... layers) {
		var rhythm = new Rhythm();
		for(var layer : layers) {
			rhythm.addLayer(drumsToRyhthm(layer));
		}
		var pattern = fromRhythm(
				rhythm,
				channel
				);
		pattern.setTempo(tempo);
		return pattern;
	}

	public static Pattern fromRhythm(Rhythm rhythm, int voiceIndex) {
		var fullPattern = new Pattern();
		for (int segment=0; segment < rhythm.getLength(); segment++) {
			fullPattern.add(
					PatternFactory.getPatternAt(rhythm, segment, voiceIndex)
					);
		}
		return fullPattern; 
	}

	private static Pattern getPatternAt(Rhythm rhythm, int segment, int voiceIndex) {
		var pattern = new Pattern();
		pattern.setVoice(voiceIndex);
		pattern.setInstrument("synth_drum"); // TODO: need a workaround, how to set a real drumset?
		byte layerCounter = 0;
		for (var layer : rhythm.getLayersAt(segment)) {
			pattern.add(StaccatoUtil.createLayerElement(layerCounter));
			layerCounter++;
			pattern.add(rhythm.getStaccatoStringForRhythm(layer));
		}
		return pattern;
	}
}
