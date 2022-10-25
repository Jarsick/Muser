package jarsick.muser.generator.random;

/*Adapted from the processing.org project*/

/**Random helper class*/
public class Random {
	final public static float LOW_PROBABILITY = 0.1f;
	final public static float STANDARD_PROBABILITY = 0.5f;
	final public static float HIGH_PROBABILITY = 0.9f;
	
	static private java.util.Random internalRandom;
	
	final static public float random() {
		return random(1);
	}
	
	final static public float random(float max) {
		return random(0, max);
	}

	final static public float random(float min, float max) {
		return (float) ((Math.random() * (max - min)) + min);
	}


	/**Return a random index from the array of distribution*/
	final static public int montecarlo(float[] distribution) {
		do {
			int index = (int) random(distribution.length);
			float randomValue = random(1);
			if(randomValue <= distribution[index]) {
				return index;
			}
		}while(true);
	}
	
	
	//---------------------------PERLIN NOISE---------------------------------
	
	  static final int PERLIN_YWRAPB = 4;
	  static final int PERLIN_YWRAP = 1<<PERLIN_YWRAPB;
	  static final int PERLIN_ZWRAPB = 8;
	  static final int PERLIN_ZWRAP = 1<<PERLIN_ZWRAPB;
	  static final int PERLIN_SIZE = 4095;

	  private static int perlin_octaves = 4; // default to medium smooth
	  private static float perlin_amp_falloff = 0.5f; // 50% reduction/octave

	  // [toxi 031112]
	  // new vars needed due to recent change of cos table in PGraphics
	  private static int perlin_TWOPI, perlin_PI;
	  private static float[] perlin_cosTable;
	  private static float[] perlin;

	  private static java.util.Random perlinRandom;
	
	 /**
	   * ( begin auto-generated from noise.xml )
	   *
	   * Returns the Perlin noise value at specified coordinates. Perlin noise is
	   * a random sequence generator producing a more natural ordered, harmonic
	   * succession of numbers compared to the standard <b>random()</b> function.
	   * It was invented by Ken Perlin in the 1980s and been used since in
	   * graphical applications to produce procedural textures, natural motion,
	   * shapes, terrains etc.<br /><br /> The main difference to the
	   * <b>random()</b> function is that Perlin noise is defined in an infinite
	   * n-dimensional space where each pair of coordinates corresponds to a
	   * fixed semi-random value (fixed only for the lifespan of the program).
	   * The resulting value will always be between 0.0 and 1.0. Processing can
	   * compute 1D, 2D and 3D noise, depending on the number of coordinates
	   * given. The noise value can be animated by moving through the noise space
	   * as demonstrated in the example above. The 2nd and 3rd dimension can also
	   * be interpreted as time.<br /><br />The actual noise is structured
	   * similar to an audio signal, in respect to the function's use of
	   * frequencies. Similar to the concept of harmonics in physics, perlin
	   * noise is computed over several octaves which are added together for the
	   * final result. <br /><br />Another way to adjust the character of the
	   * resulting sequence is the scale of the input coordinates. As the
	   * function works within an infinite space the value of the coordinates
	   * doesn't matter as such, only the distance between successive coordinates
	   * does (eg. when using <b>noise()</b> within a loop). As a general rule
	   * the smaller the difference between coordinates, the smoother the
	   * resulting noise sequence will be. Steps of 0.005-0.03 work best for most
	   * applications, but this will differ depending on use.
	   *
	   * ( end auto-generated )
	   *
	   * @webref math:random
	   * @param x x-coordinate in noise space
	   * @param y y-coordinate in noise space
	   * @param z z-coordinate in noise space
	   * @see PApplet#noiseSeed(long)
	   * @see PApplet#noiseDetail(int, float)
	   * @see PApplet#random(float,float)
	   */
	  public static float noise(float x, float y, float z) {
	    if (perlin == null) {
	      if (perlinRandom == null) {
	        perlinRandom = new java.util.Random();
	      }
	      perlin = new float[PERLIN_SIZE + 1];
	      for (int i = 0; i < PERLIN_SIZE + 1; i++) {
	        perlin[i] = perlinRandom.nextFloat(); //(float)Math.random();
	      }
	      // [toxi 031112]
	      // noise broke due to recent change of cos table in PGraphics
	      // this will take care of it
	      perlin_cosTable = SinCosLookupTable.cosLUT;
	      perlin_TWOPI = perlin_PI = SinCosLookupTable.SINCOS_LENGTH;
	      perlin_PI >>= 1;
	    }

	    if (x<0) x=-x;
	    if (y<0) y=-y;
	    if (z<0) z=-z;

	    int xi=(int)x, yi=(int)y, zi=(int)z;
	    float xf = x - xi;
	    float yf = y - yi;
	    float zf = z - zi;
	    float rxf, ryf;

	    float r=0;
	    float ampl=0.5f;

	    float n1,n2,n3;

	    for (int i=0; i<perlin_octaves; i++) {
	      int of=xi+(yi<<PERLIN_YWRAPB)+(zi<<PERLIN_ZWRAPB);

	      rxf=noise_fsc(xf);
	      ryf=noise_fsc(yf);

	      n1  = perlin[of&PERLIN_SIZE];
	      n1 += rxf*(perlin[(of+1)&PERLIN_SIZE]-n1);
	      n2  = perlin[(of+PERLIN_YWRAP)&PERLIN_SIZE];
	      n2 += rxf*(perlin[(of+PERLIN_YWRAP+1)&PERLIN_SIZE]-n2);
	      n1 += ryf*(n2-n1);

	      of += PERLIN_ZWRAP;
	      n2  = perlin[of&PERLIN_SIZE];
	      n2 += rxf*(perlin[(of+1)&PERLIN_SIZE]-n2);
	      n3  = perlin[(of+PERLIN_YWRAP)&PERLIN_SIZE];
	      n3 += rxf*(perlin[(of+PERLIN_YWRAP+1)&PERLIN_SIZE]-n3);
	      n2 += ryf*(n3-n2);

	      n1 += noise_fsc(zf)*(n2-n1);

	      r += n1*ampl;
	      ampl *= perlin_amp_falloff;
	      xi<<=1; xf*=2;
	      yi<<=1; yf*=2;
	      zi<<=1; zf*=2;

	      if (xf>=1.0f) { xi++; xf--; }
	      if (yf>=1.0f) { yi++; yf--; }
	      if (zf>=1.0f) { zi++; zf--; }
	    }
	    return r;
	  }

	  // [toxi 031112]
	  // now adjusts to the size of the cosLUT used via
	  // the new variables, defined above
	  private static float noise_fsc(float i) {
	    // using bagel's cosine table instead
	    return 0.5f*(1.0f-perlin_cosTable[(int)(i*perlin_PI)%perlin_TWOPI]);
	  }
	  
	  /**
	   * ( begin auto-generated from noiseDetail.xml )
	   *
	   * Adjusts the character and level of detail produced by the Perlin noise
	   * function. Similar to harmonics in physics, noise is computed over
	   * several octaves. Lower octaves contribute more to the output signal and
	   * as such define the overal intensity of the noise, whereas higher octaves
	   * create finer grained details in the noise sequence. By default, noise is
	   * computed over 4 octaves with each octave contributing exactly half than
	   * its predecessor, starting at 50% strength for the 1st octave. This
	   * falloff amount can be changed by adding an additional function
	   * parameter. Eg. a falloff factor of 0.75 means each octave will now have
	   * 75% impact (25% less) of the previous lower octave. Any value between
	   * 0.0 and 1.0 is valid, however note that values greater than 0.5 might
	   * result in greater than 1.0 values returned by <b>noise()</b>.<br /><br
	   * />By changing these parameters, the signal created by the <b>noise()</b>
	   * function can be adapted to fit very specific needs and characteristics.
	   *
	   * ( end auto-generated )
	   * @webref math:random
	   * @param lod number of octaves to be used by the noise
	   * @see PApplet#noise(float, float, float)
	   */
	  static public void noiseDetail(int lod) {
	    if (lod>0) perlin_octaves=lod;
	  }

	  /**
	   * @see #noiseDetail(int)
	   * @param falloff falloff factor for each octave
	   */
	  static public void noiseDetail(int lod, float falloff) {
	    if (lod>0) perlin_octaves=lod;
	    if (falloff>0) perlin_amp_falloff=falloff;
	  }

	  /**
	   * ( begin auto-generated from noiseSeed.xml )
	   *
	   * Sets the seed value for <b>noise()</b>. By default, <b>noise()</b>
	   * produces different results each time the program is run. Set the
	   * <b>value</b> parameter to a constant to return the same pseudo-random
	   * numbers each time the software is run.
	   *
	   * ( end auto-generated )
	   * @webref math:random
	   * @param seed seed value
	   * @see PApplet#noise(float, float, float)
	   * @see PApplet#noiseDetail(int, float)
	   * @see PApplet#random(float,float)
	   * @see PApplet#randomSeed(long)
	   */
	  static public void noiseSeed(long seed) {
	    if (perlinRandom == null) perlinRandom = new java.util.Random();
	    perlinRandom.setSeed(seed);
	    // force table reset after changing the random number seed [0122]
	    perlin = null;
	  }
	  
	  
	  
	  //---------------------GAUSSIAN--------------------------
	  
	  
	  /**
	   * ( begin auto-generated from randomGaussian.xml )
	   *
	   * Returns a float from a random series of numbers having a mean of 0
	   * and standard deviation of 1. Each time the <b>randomGaussian()</b>
	   * function is called, it returns a number fitting a Gaussian, or
	   * normal, distribution. There is theoretically no minimum or maximum
	   * value that <b>randomGaussian()</b> might return. Rather, there is
	   * just a very low probability that values far from the mean will be
	   * returned; and a higher probability that numbers near the mean will
	   * be returned.
	   *
	   * ( end auto-generated )
	   * @webref math:random
	   * @see PApplet#random(float,float)
	   * @see PApplet#noise(float, float, float)
	   */
	  static public final float randomGaussian() {
	    if (internalRandom == null) {
	      internalRandom = new java.util.Random();
	    }
	    return (float) internalRandom.nextGaussian();
	  }
}
