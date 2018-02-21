package com.group3.recipe;

import java.util.ArrayList;

/**
 * Presentation<p>
 * As defined in the PWS, presentations must contain a minimum of 1 {@linkplain Slide}. 
 * Any other metadata, or items contained within the slide is optional, and therefore
 * are not necessarily NEEDED to satisfy the PWS. <p>
 * The {@linkplain Recipe} class will comply with the PWS by extending this 
 * abstract class.<p>
 * Slides are contained within the attribute:<p>
 * {@linkplain ArrayList}<{@linkplain Slide}> {@linkplain #slides}<p>
 * Any further attributes can be optionally implemented within the object extending this class.
 * @author Marco
 *
 */
public abstract class Presentation {
	ArrayList<Slide> slides;
}
