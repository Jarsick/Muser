/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.Sequence;

public interface SequenceExporter{
	void export(Sequence sequence, File file) throws IOException;
}	