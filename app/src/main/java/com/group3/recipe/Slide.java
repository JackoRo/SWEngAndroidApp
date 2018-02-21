package com.group3.recipe;

import java.util.ArrayList;

/**
 * Slide<p>
 * Slides contain the following attributes:<p>
 * {@linkplain ArrayList}<{@linkplain Object}> {@link Slide#items}
 * @see Recipe
 * @author Marco
 *
 */
public class Slide {
	/**
	 * {@linkplain ArrayList}<{@linkplain Object}> items<p>
	 * LITERALLY anything. This is an arrayList (consider it as just an array) of Objects
	 * (all classes extend Object, so anything can be put into this array!).<p>
	 * Ideally, objects such as Text, Image, Video, Shape etc would be stored here
	 * and can be retrieved elsewhere in code.<p>
	 * NOTE: When it comes to handling an object from this array, it must be type-casted
	 * to the relevant class before you have access to it's methods. This could be done by
	 * the following:<p>
	 * {@code ((<Class>)<ObjectName>).<MethodName>(<Args>);}<p> 
	 * such as<p>
	 * {@code int x = ((Image)items.get(0)).getX();}<p>
	 * this would get the first item in the {@code items} ArrayList, fetch the x value, and
	 * store it as an integer called 'x'.<p>
	 */
	public ArrayList<Object> items;
}
