package jarsick.muser.generator.utils;

import java.util.ArrayList;
import java.util.List;

import jarsick.muser.notation.MusicalNotation;
import jarsick.muser.structure.SongInfo;

public class SongGeneratorUtil {
	
	public static <T extends MusicalNotation> void adjustDuration(List<T> notes, SongInfo songInfo){
		MusicalNotation lastNote = null;
		int lastMeasure = 0;
		var toBeRemoved = new ArrayList<MusicalNotation>();
		for(int i = 0; i < notes.size(); i++) {
			int currentMeasure  = i / songInfo.getDivisionPerMeasure();
			var currentNote = notes.get(i);
			if(lastNote != null && currentNote.isSilence() && currentMeasure == lastMeasure) { // se ho trovato un silezio, aggiusto la lunghezza della nota precedente
				lastNote.setDuration(lastNote.getDuration() + currentNote.getDuration());
				toBeRemoved.add(currentNote);
			}
			
			if(lastNote == null || (!currentNote.isSilence())) {
				lastNote = currentNote;
				lastMeasure = currentMeasure;
			}
		}
		
		notes.removeAll(toBeRemoved);
	}
	
	
	final public static <T> List<T> copyList(List<T> source){
		var clone = new ArrayList<T>();
		clone.addAll(source);
		return clone;
	}
	
	
	
}
