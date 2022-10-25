package jarsick.muser.generator.random;

/**Code from the processing.org project*/
class SinCosLookupTable {
	
	final static private float DEG_TO_RAD = 0.017453292f;

	static final protected float[] sinLUT;
	static final protected float[] cosLUT;
	static final protected float SINCOS_PRECISION = 0.5f;
	static final protected int SINCOS_LENGTH = (int) (360f / SINCOS_PRECISION);
	static {
		sinLUT = new float[SINCOS_LENGTH];
		cosLUT = new float[SINCOS_LENGTH];
		for (int i = 0; i < SINCOS_LENGTH; i++) {
			sinLUT[i] = (float) Math.sin(i * DEG_TO_RAD * SINCOS_PRECISION);
			cosLUT[i] = (float) Math.cos(i * DEG_TO_RAD * SINCOS_PRECISION);
		}
	}

}
