/**
 * 
 */
package com.mycompany.blockchain.sawtooth;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;

import sawtooth.sdk.processor.State;
import sawtooth.sdk.processor.TransactionHandler;
import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.processor.exceptions.InternalError;
import sawtooth.sdk.processor.exceptions.InvalidTransactionException;
import sawtooth.sdk.protobuf.TpProcessRequest;

/**
 * 
 * Handler class for sawtooth processor just keep data as it is sent.
 * 
 * @author dev
 *
 */
public class IdempotentHandler implements TransactionHandler {
	private final Logger logger = Logger.getLogger(TransactionHandler.class.getName());

	private static final String IDEM = "idem";
	private static final String VER = "1.0";
	private String idemNameSpace;

	public IdempotentHandler() {
		try {
			this.idemNameSpace = Utils.hash512(this.transactionFamilyName().getBytes("UTF-8")).substring(0, 6);
		} catch (UnsupportedEncodingException usee) {
			usee.printStackTrace();
			this.idemNameSpace = "";
		}
	}

	public String transactionFamilyName() {
		// TODO Auto-generated method stub
		return IDEM;
	}

	public String getVersion() {
		// TODO Auto-generated method stub
		return VER;
	}

	public Collection<String> getNameSpaces() {

		ArrayList<String> namespaces = new ArrayList<String>();
		namespaces.add(this.idemNameSpace);
		return namespaces;
	}

	public void apply(TpProcessRequest tpProcessRequest, State state)
			throws InvalidTransactionException, InternalError {
		String payload = decodePayload(tpProcessRequest.getPayload().toByteArray());
		logger.info("Payload Extracted as - " + payload);

		// do all the validation here and raise exceptions in case of invalidations.
		// Fail Fast approach.

		String address = getUniqueAddress(payload);

		logger.info("Unique Address calculated as - " + address);

		Collection<String> addresses = new ArrayList<String>(0);
		// here we are just storing the data.

		Map.Entry<String, ByteString> entry = this.encodeState(address, payload);
		Collection<Map.Entry<String, ByteString>> addressValues = Arrays.asList(entry);
		addresses = state.setState(addressValues);

		if (addresses.size() == 0) {
			throw new InternalError("State error!. Data size is zeroF");
		}
		logger.info("Data has been written to " + address + " . ");
	}

	/**
	 * The implementation that shoudl return a unique address as per the payload
	 * that is requested. The implementers have to take care of adding the necessary
	 * logic for uniqueness and durability.
	 * 
	 * @param payload
	 * @return
	 * @throws InternalError
	 */
	protected String getUniqueAddress(String payload) throws InternalError {
		String address = null;
		try {
			String hashedName = Utils.hash512(payload.getBytes("UTF-8"));
			address = this.idemNameSpace + hashedName.substring(hashedName.length() - 64);
		} catch (UnsupportedEncodingException usee) {
			usee.printStackTrace();
			throw new InternalError("Internal Error, " + usee.toString());
		}
		return address;
	}

	/**
	 * Implementers to provide logic for extracting data. Make is abstracts
	 * 
	 * @param byteArray
	 * @return
	 */
	protected String decodePayload(byte[] byteArray) {
		String payload = new String(byteArray);
		return payload;
	}

	/**
	 * Helper function to encode the State that will be stored at the address of the
	 * name.
	 * 
	 * The implementation doestn do any encoding and add teh data with java
	 * Serialized bytes.
	 */
	public Map.Entry<String, ByteString> encodeState(String address, String data) {

		return new AbstractMap.SimpleEntry<String, ByteString>(address, ByteString.copyFrom(data.getBytes()));
	}

	/**
	 * Helper function to decode State retrieved from the address of the name.
	 */
	public String decodeState(byte[] bytes) {
		return new String(bytes);
	}

}
