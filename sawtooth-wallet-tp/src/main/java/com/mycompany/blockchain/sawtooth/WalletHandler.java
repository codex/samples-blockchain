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
import com.google.protobuf.InvalidProtocolBufferException;
import com.mycompany.blockchain.protobuf.SawtoothWalletTransactionPayload;
import com.mycompany.blockchain.protobuf.SawtoothWalletTransactionPayload.CreateWalletTransactionData;
import com.mycompany.blockchain.protobuf.SawtoothWalletTransactionPayload.DepositTransactionData;
import com.mycompany.blockchain.protobuf.SawtoothWalletTransactionPayload.SendPaymentTransactionData;
import com.mycompany.blockchain.protobuf.SawtoothWalletTransactionPayload.ShowWalletTransactionData;
import com.mycompany.blockchain.protobuf.SawtoothWalletTransactionPayload.WithdrawTransactionData;
import com.mycompany.blockchain.protobuf.Wallet;

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
public class WalletHandler implements TransactionHandler {
	private final Logger logger = Logger.getLogger(TransactionHandler.class.getName());

	private static final String familyName = "wallet";
	private static final String VER = "1.0";
	private static final String CREATE_WALLET = "create";
	private static final String DEPOSIT = "deposit";
	private static final String WITHDRAW = "withdraw";
	private static final String SEND_PAYMENT = "send_payment";
	private static final String SHOW_WALLET = "show";

	private String walletNameSpace;

	public WalletHandler() {
		try {
			this.walletNameSpace = Utils.hash512(this.transactionFamilyName().getBytes("UTF-8"))
					.substring(0, 6);
			logger.info("Namespace prefix caclcuated as - " + this.walletNameSpace);
		} catch (UnsupportedEncodingException usee) {
			usee.printStackTrace();
			this.walletNameSpace = "";
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
		namespaces.add(this.walletNameSpace);
		return namespaces;
	}

	public void apply(TpProcessRequest tpProcessRequest, State state)
			throws InvalidTransactionException, InternalError {
		String payload = decodePayload(tpProcessRequest.getPayload().toByteArray());
		logger.info("Payload Extracted as - " + payload);

		// To get TransactionData to get inventory Item pojo

		SawtoothWalletTransactionPayload walletPayload = null;
		try {
			walletPayload = getUnpackedTransaction(tpProcessRequest);
		} catch (InvalidProtocolBufferException e) {
			logger.info(e.getMessage() + ":" + e);
		}
		if (walletPayload == null) {
			throw new InternalError("Error while unpacking Transaction Payload...");
		}
		switch (walletPayload.getPayloadType()) {
		case CREATE_WALLET:
			createWallet(walletPayload.getCreateWalletData(), state);
			break;
		case DEPOSIT:
			depositToWallet(walletPayload.getDepositData(), state);
			break;
		case WITHDRAW:
			withdrawFromWallet(walletPayload.getWithdrawData(), state);
			break;
		case SEND_PAYMENT:
			sendPayment(walletPayload.getSendPaymentData(), state);
			break;
		case SHOW_WALLET:
			showWallet(walletPayload.getShowWalletData(), state);
			break;
		default:

		}

	}

	private void showWallet(ShowWalletTransactionData walletPayload, State state) {
		// TODO Auto-generated method stub

	}

	private void sendPayment(SendPaymentTransactionData sendPaymentData, State state) {

	}

	private void withdrawFromWallet(WithdrawTransactionData withdrawData, State state) {
		// TODO Auto-generated method stub

	}

	private void depositToWallet(DepositTransactionData depositData, State state)
			throws InternalError, InvalidTransactionException {
		String address = getUniqueAddress(String.valueOf(depositData.getCustomerId()));
		logger.info("Unique Address calculated as - " + address);

		// Retrieving exiting wallet amount to add the current amount
		Collection<String> addresses = new ArrayList<String>(0); // here we are just
		addresses.add(address);
		logger.info("Retriving existing wallet balance...");
		Map<String, ByteString> blockchainDataMap = state.getState(addresses);

		if (blockchainDataMap.isEmpty()) {
			throw new InternalError("Error while retrieving a wallet for deposit!");
		}

		// storing the data.
		Wallet wallet, oldWallet = null;
		int prevBalance = 0;
		try {
			for (Map.Entry<String, ByteString> data : blockchainDataMap.entrySet()) {
				//prevBalance = Integer.parseInt(data.getValue().toString("UTF-8"));
				oldWallet = decodeState(data.getValue());
			}
		} catch (NumberFormatException | InvalidProtocolBufferException e) {
			throw new InternalError("Error while retrieving a wallet for deposit :" + e);
		}

		wallet = Wallet.newBuilder().setCustomerId(depositData.getCustomerId())
				.setBalance(oldWallet.getBalance() + depositData.getAmount()).build();

		Map.Entry<String, ByteString> entry = this.encodeState(address, wallet.toByteString());
		Collection<Map.Entry<String, ByteString>> addressValues = Arrays.asList(entry);
		addresses = state.setState(addressValues);

		if (addresses.size() == 0) {
			throw new InternalError("State error while creating a wallet!. Data size is zeroF");
		}
		logger.info("Amount deposited to wallet at address :" + address);

	}

	/**
	 * Method to create Wallet
	 * 
	 * @param transactionData
	 * @param state
	 * @throws InternalError
	 * @throws InvalidTransactionException
	 */

	private void createWallet(CreateWalletTransactionData transactionData, State state)
			throws InternalError, InvalidTransactionException {
		String address = getUniqueAddress(String.valueOf(transactionData.getCustomerId()));
		logger.info("Unique Address calculated as - " + address);

		Collection<String> addresses = new ArrayList<String>(0); // here we are just
		// storing the data.

		Wallet wallet = Wallet.newBuilder().setCustomerId(transactionData.getCustomerId())
				.setBalance(0).build();
		Map.Entry<String, ByteString> entry = this.encodeState(address, wallet.toByteString());
		Collection<Map.Entry<String, ByteString>> addressValues = Arrays.asList(entry);
		addresses = state.setState(addressValues);

		if (addresses.size() == 0) {
			throw new InternalError("State error while creating a wallet!. Data size is zeroF");
		}
		logger.info("Wallet created to address :" + address);
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
			address = this.walletNameSpace + hashedName.substring(hashedName.length() - 64);
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
		return new String(byteArray);
	}

	/**
	 * Helper function to encode the State that will be stored at the address of the
	 * name.
	 * 
	 * The implementation doestn do any encoding and add teh data with java
	 * Serialized bytes.
	 */
	public Map.Entry<String, ByteString> encodeState(String address, ByteString data) {
		return new AbstractMap.SimpleEntry<String, ByteString>(address, data);
	}

	/**
	 * Helper function to decode State retrieved from the address of the name.
	 * @throws InvalidProtocolBufferException 
	 */
	public Wallet decodeState(ByteString bytes) throws InvalidProtocolBufferException {
		return Wallet.parseFrom(bytes);
	}

	/**
	 * Helper function to retrieve action, and Inventory details from transaction
	 * request.
	 * 
	 * @throws InvalidProtocolBufferException
	 */
	private SawtoothWalletTransactionPayload getUnpackedTransaction(
			TpProcessRequest transactionRequest)
			throws InvalidTransactionException, InvalidProtocolBufferException {
		return SawtoothWalletTransactionPayload
				.parseFrom(transactionRequest.getPayload().toByteArray());
	}
}