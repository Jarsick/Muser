/******************************************************************************
 * Copyright (c) 2022, Daniele Aurigemma
 * All rights reserved.
 * 
 * Part of the Muser project github: https://github.com/Jarsick/Muser
 */

package jarsick.muser.structure;
import static jarsick.muser.structure.Subsection.*;

public enum SectionStructure {
	AABA(A,A,B,A),
	ABAB(A,B,A,B),
	AABB(A,A,B,B);

	private Subsection[] subsections;

	private SectionStructure(Subsection... subsections) {
		this.subsections = subsections;
	}


	public Subsection[] getSubsections() {
		return this.subsections;
	}

}
