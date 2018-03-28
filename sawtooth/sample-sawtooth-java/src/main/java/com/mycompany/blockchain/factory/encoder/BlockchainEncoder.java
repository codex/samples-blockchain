package com.mycompany.blockchain.factory.encoder;

import java.io.UnsupportedEncodingException;

import com.mycompany.blockchain.exceptions.BlockchainClientException;

import sawtooth.sdk.processor.exceptions.InternalError;

public interface BlockchainEncoder {

	/**
	 * builds an block chain address from key and and transaction family name
	 * 
	 * @param prefix
	 * @param key
	 * @throws InternalError
	 * @throws UnsupportedEncodingException
	 */
	public String getBloackchainAddressFromKey(String prefix, String key) throws BlockchainClientException;

	/**
	 * Encode the data
	 * @param bytes
	 * @return
	 * @throws BlockchainClientException
	 */
	public byte[] encode(byte[] bytes) throws BlockchainClientException;

	/**
	 * Decode the encoded data
	 * @param encodedString
	 * @return
	 * @throws BlockchainClientException
	 */
	public String decode(String encodedString) throws BlockchainClientException;
}
