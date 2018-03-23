package com.mycompany.blockchain.factory.encoder;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.mycompany.blockchain.exceptions.BlockchainClientException;

import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.processor.exceptions.InternalError;

public class BlockchainBase64Encoder implements BlockchainEncoder {

	/**
	 * Calculates and encoded value for Prefixing address. This method returns only
	 * first 6 digits of prefix.
	 * 
	 * @param prefix
	 * @throws UnsupportedEncodingException
	 */
	private String prefixAddress(String prefix) throws BlockchainClientException {
		try {
			return Utils.hash512(prefix.getBytes("UTF-8")).substring(0, 6);
		} catch (UnsupportedEncodingException e) {
			throw new BlockchainClientException("Error while encoding prefix " + e.toString());
		}
	}

	/**
	 * Calculates an encoded value for a unique key address
	 * 
	 * @param uniqueKey
	 * @throws InternalError
	 */
	private String keyAddress(String key) throws BlockchainClientException {
		String address = null;
		try {
			String hashedName = Utils.hash512(key.getBytes("UTF-8"));
			address = hashedName.substring(hashedName.length() - 64);
		} catch (UnsupportedEncodingException usee) {
			usee.printStackTrace();
			throw new BlockchainClientException("Error while encoding key " + usee.toString());
		}
		return address;
	}

	@Override
	public String getBloackchainAddressFromKey(String prefix, String key) throws BlockchainClientException {
		return prefixAddress(prefix).concat(keyAddress(key));
	}

	@Override
	public byte[] encode(byte[] bytes) throws BlockchainClientException {
		return Base64.getEncoder().encode(bytes);
	}

	@Override
	public String decode(String encodedString) throws BlockchainClientException {
		return String.valueOf(Base64.getDecoder().decode(encodedString));
	}
}
