/*
 * Description :
 * 
 *   This file contains a class which converts a UTF-8 string into a cipher string, and vice versa.
 *   The class uses 128-bit AES Algorithm in Cipher Block Chaining (CBC) mode with a UTF-8 key
 *   string and a UTF-8 initial vector string which are hashed by MD5. PKCS5Padding is used
 *   as a padding mode and binary output is encoded by Base64. 
 * 
 * Since :
 * 
 *   2007.10.20
 * 
 * Author :
 * 
 *   JO Hyeong-ryeol (http://www.hyeongryeol.com/6)
 * 
 * Copyright :
 * 
 *   Permission to copy, use, modify, sell and distribute this software is granted provided this
 *   copyright notice appears in all copies. This software is provided "as is" without express
 *   or implied warranty, and with no claim as to its suitability for any purpose.
 *   
 *   Copyright (C) 2007 by JO Hyeong-ryeol.
 * 
 * $Id: StringEncrypter.java 65 2007-12-14 15:29:49Z JO Hyeong-ryeol $
 * 
 */
package com.bvc.data.util;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

	private int min, max;

	public InputFilterMinMax(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public InputFilterMinMax(String min, String max) {
		this.min = Integer.parseInt(min);
		this.max = Integer.parseInt(max);
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		try {
			int input = Integer.parseInt(dest.toString() + source.toString());
//			Logger.loge("input  :  "   +  input);
			if (isInRange(min, max, input))
				return null;
		} catch (NumberFormatException nfe) {

		}
		return "";
	}

	private boolean isInRange(int a, int b, int c) {
		return b > a ? c >= a && c <= b : c >= b && c <= a;
	}
}