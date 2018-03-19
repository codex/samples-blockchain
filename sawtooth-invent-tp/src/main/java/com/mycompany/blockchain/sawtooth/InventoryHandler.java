/**
 * 
 */
package com.mycompany.blockchain.sawtooth;

import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
 * 
 * 
 * @author dev
 *
 */
public class InventoryHandler implements TransactionHandler {
	private final Logger logger = Logger.getLogger(TransactionHandler.class.getName());

	private static final String familyName = "invent";
	private static final String VER = "1.0";
	private static final String CREATE_ACTION = "create";
	private static final String LIST_ACTION = "list";

	private String inventoryNameSpace;

	public InventoryHandler() {
		try {
			this.inventoryNameSpace = Utils.hash512(this.transactionFamilyName().getBytes("UTF-8")).substring(0, 6);
			logger.info("Namespace prefix caclcuated as - " + this.inventoryNameSpace);
		} catch (UnsupportedEncodingException usee) {
			usee.printStackTrace();
			this.inventoryNameSpace = "";
		}
	}

	public String transactionFamilyName() {
		return familyName;
	}

	public String getVersion() {
		return VER;
	}

	public Collection<String> getNameSpaces() {
		ArrayList<String> namespaces = new ArrayList<String>();
		namespaces.add(this.inventoryNameSpace);
		return namespaces;
	}

	public void apply(TpProcessRequest tpProcessRequest, State state)
			throws InvalidTransactionException, InternalError {
		String payload = decodePayload(tpProcessRequest.getPayload().toByteArray());
		logger.info("Payload Extracted as - " + payload);

		// To get TransactionData to get inventory Item pojo
		TransactionData transactionData = getUnpackedTransaction(tpProcessRequest);

		//Check the action and proceed accordingly
		if (CREATE_ACTION.equalsIgnoreCase(transactionData.getAction())) {
			createInventoryItem(transactionData, state);
		} else if (LIST_ACTION.equalsIgnoreCase(transactionData.getAction())) {
			try {
				getInventoryItems(transactionData.getAddresses(), state);
			} catch (UnsupportedEncodingException e) {
				logger.info(e.getMessage());
			}
		}

	}

	/**
	 * Method to create Inventory Item
	 * @param transactionData
	 * @param state
	 * @throws InternalError
	 * @throws InvalidTransactionException
	 */
	private void createInventoryItem(TransactionData transactionData, State state)
			throws InternalError, InvalidTransactionException {
		String address = getUniqueAddress(transactionData.getInventoryItem().getId());
		logger.info("Unique Address calculated as - " + address);

		Collection<String> addresses = new ArrayList<String>(0);
		// here we are just storing the data.

		Map.Entry<String, ByteString> entry = this.encodeState(address, transactionData.getInventoryItem().toString());
		Collection<Map.Entry<String, ByteString>> addressValues = Arrays.asList(entry);
		addresses = state.setState(addressValues);

		if (addresses.size() == 0) {
			throw new InternalError("State error!. Data size is zeroF");
		}
		logger.info("Data has been written to " + address);
	}

	/**
	 * Method to fetch Data from Blockchain 
	 * @param addresses
	 * @param state
	 * @throws InternalError
	 * @throws InvalidTransactionException
	 * @throws UnsupportedEncodingException
	 */
	private void getInventoryItems(List<String> addresses, State state)
			throws InternalError, InvalidTransactionException, UnsupportedEncodingException {
		List<String> sawtoothAddresses = new ArrayList<>();
		for (String inventoryId : addresses) {
			sawtoothAddresses.add(getUniqueAddress(inventoryId));
		}

		Map<String, ByteString> blockchainDataMap = state.getState(sawtoothAddresses);

		logger.info("Current Blockchain State :");
		for (Map.Entry<String, ByteString> data : blockchainDataMap.entrySet()) {
			logger.info(data.getKey() + "=" + data.getValue().toString("UTF-8"));
		}

	}

	/**
	 * The implementation that shoudl return a unique address as per the payload
	 * that is requested. The implementers have to take care of adding the necessary
	 * logic for uniqueness and durability.
	 * 
	 * 
	 * Note - For sender this evaluation for a payload need to be calculated in teh
	 * same for input and output transaction address
	 * 
	 * @param id
	 * @return
	 * @throws InternalError
	 */
	protected String getUniqueAddress(String id) throws InternalError {
		String address = null;
		try {
			String hashedName = Utils.hash512(id.getBytes("UTF-8"));
			address = this.inventoryNameSpace + hashedName.substring(hashedName.length() - 64);
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

	/**
	 * Helper function to retrieve action, and Inventory details from transaction
	 * request.
	 */
	private TransactionData getUnpackedTransaction(TpProcessRequest transactionRequest)
			throws InvalidTransactionException {
		String payload = transactionRequest.getPayload().toStringUtf8();
		ArrayList<String> payloadList = new ArrayList<>(Arrays.asList(payload.split(",")));
		if (CREATE_ACTION.equalsIgnoreCase(payloadList.get(0))) {
			if (payloadList.size() > 5) {
				throw new InvalidTransactionException("Invalid payload serialization");
			}
			while (payloadList.size() < 5) {
				payloadList.add("");
			}
			return new TransactionData(payloadList.get(0), payloadList.get(1), payloadList.get(2), payloadList.get(3),
					payloadList.get(4));
		} else if (LIST_ACTION.equalsIgnoreCase(payloadList.get(0))) {
			if (payloadList.size() < 2) {
				throw new InvalidTransactionException("Invalid payload serialization");
			}
			List<String> addresses = new ArrayList<>();
			for (int i = 1; i < payloadList.size(); i++) {
				addresses.add(payloadList.get(i));
			}
			return new TransactionData(payloadList.get(0), addresses);
		}
		throw new InvalidTransactionException("Invalid payload serialization. InCorrect action specified...");

	}
}

class TransactionData {
	final String action;
	List<String> addresses = new ArrayList<>();
	final InventoryItem inventoryItem;

	public TransactionData(String action, String id, String itemName, String colour, String price) {
		super();
		this.action = action;
		this.inventoryItem = new InventoryItem(id, itemName, colour, price);
	}

	public TransactionData(String action, List<String> addresses) {
		super();
		this.action = action;
		this.addresses = addresses;
		this.inventoryItem = new InventoryItem(null, null, null, null);
	}

	public String getAction() {
		return action;
	}

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public List<String> getAddresses() {
		return addresses;
	}
}

class InventoryItem {
	final String id;
	final String itemName;
	final String colour;
	final String price;

	public InventoryItem(String id, String itemName, String colour, String price) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.colour = colour;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public String getItemName() {
		return itemName;
	}

	public String getColour() {
		return colour;
	}

	public String getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "InventoryItem [id=" + id + ", itemName=" + itemName + ", colour=" + colour + ", price=" + price + "]";
	}
}
