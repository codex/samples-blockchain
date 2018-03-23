package com.mycompany.blockchain.utils;

import java.io.UnsupportedEncodingException;

import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.processor.exceptions.InternalError;

public class BlockchainUtils {

	/**
	 * Calculates and encoded value for Prefixing address. This method returns only first 6 digits of prefix.
	 * @param prefix
	 * @throws UnsupportedEncodingException 
	 */
	public  static  String prefixAddress(String prefix) throws UnsupportedEncodingException {
		return Utils.hash512(prefix.getBytes("UTF-8")).substring(0, 6);
	}
	
	/**
	 * Calculates an encoded value for a unique key address
	 * @param uniqueKey
	 * @throws InternalError 
	 */
	public static  String keyAddress(String key) throws InternalError {
		String address = null;
		try {
			String hashedName = Utils.hash512(key.getBytes("UTF-8"));
			address =  hashedName.substring(hashedName.length() - 64);
		} catch (UnsupportedEncodingException usee) {
			usee.printStackTrace();
			throw new InternalError("Internal Error, " + usee.toString());
		}
		return address;
	}
	
	/**
	 * builds an block chain address from key and and transaction family name
	 * @param prefix
	 * @param key
	 * @throws InternalError 
	 * @throws UnsupportedEncodingException 
	 */
	public static String getBloackchainAddressFromKey(String prefix,String key) throws UnsupportedEncodingException, InternalError {
		return prefixAddress(prefix).concat(keyAddress(key));
		
	}
}
