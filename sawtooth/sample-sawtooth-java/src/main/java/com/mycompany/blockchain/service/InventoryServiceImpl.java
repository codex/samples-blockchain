package com.mycompany.blockchain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bitcoinj.core.ECKey;

import com.google.protobuf.ByteString;
import com.googlecode.protobuf.format.JsonFormat;
import com.googlecode.protobuf.format.JsonFormat.ParseException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mycompany.blockchain.constants.Constants;
import com.mycompany.blockchain.exceptions.BlockchainClientException;
import com.mycompany.blockchain.factory.encoder.BlockchainEncoder;
import com.mycompany.blockchain.factory.encoder.BlockchainEncoderFactory;
import com.mycompany.blockchain.protobuf.Data;
import com.mycompany.blockchain.protobuf.StateData;

import sawtooth.sdk.client.Signing;
import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.protobuf.Batch;
import sawtooth.sdk.protobuf.BatchHeader;
import sawtooth.sdk.protobuf.BatchList;
import sawtooth.sdk.protobuf.Transaction;
import sawtooth.sdk.protobuf.TransactionHeader;

public class InventoryServiceImpl {

	private static final Logger logger = Logger.getLogger(InventoryServiceImpl.class.getName());
	
	private BlockchainEncoder encoder = BlockchainEncoderFactory.getEncoder(Constants.BASE64_ENCODER); 

	public void getDataFromBlockchain(String[] args) throws UnirestException, BlockchainClientException, ParseException {

		if (Constants.LIST_ACTION.equalsIgnoreCase(args[0])) {
			if (args.length < 2) {
				throw new BlockchainClientException(
						"Invalid parameters for list operation. It should be : list <itemId> or list all(For all items)");
			}

			String address = null;
			if (!Constants.LIST_ALL_ACTION.equalsIgnoreCase(args[1])) {
				// get unique address for input and output
				address = encoder.getBloackchainAddressFromKey(Constants.NS_IDEM, args[1]);
			}

			GetRequest getRequest = Unirest.get("http://localhost:8008/state");
			if (null != address) {
				getRequest.queryString("address", address);
			}
			String serverResponse = getRequest.asString().getBody();
			logger.info(serverResponse);

			StateData.Builder stateBuilder = StateData.newBuilder();
			JsonFormat.merge(serverResponse, stateBuilder);
			StateData stateData = stateBuilder.build();
			
			
			for (Data data : stateData.getDataList()) {
				logger.info("Decoded value is " + encoder.decode(data.getData()));
			}
		}
	}

	public void putDataToBLockchain(String[] args) throws BlockchainClientException, UnirestException {
		if (Constants.CREATE_ACTION.equalsIgnoreCase(args[0])) {
			if (args.length > 5) {
				throw new BlockchainClientException(
						"Invalid parameters for create operation. It should be : create <itemId> <itemName> <color> <price>");
			}

			ECKey privateKey = Signing.generatePrivateKey(null); // new random privatekey
			String publicKeyHex = privateKey.getPublicKeyAsHex();

			// Parameters in sequence : id,itemName,color,price
			String payload = args[0] + "," + args[1] + "," + args[2] + "," + args[3] + "," + args[4];
			logger.info("Sending payload as - " + payload);
			String payloadBytes = Utils.hash512(payload.getBytes()); 

			ByteString payloadByteString = ByteString.copyFrom(payload.getBytes());

			// Get unique address
			String address = encoder.getBloackchainAddressFromKey(Constants.NS_IDEM, args[1]);

			logger.info("Sending address as - " + address);
			TransactionHeader txnHeader = TransactionHeader.newBuilder().clearBatcherPublicKey()
					.setBatcherPublicKey(publicKeyHex).setFamilyName(Constants.NS_IDEM) // Idem Family
					.setFamilyVersion(Constants.VER).addInputs(address).setNonce("1").addOutputs(address)
					.setPayloadSha512(payloadBytes).setSignerPublicKey(publicKeyHex).build();

			ByteString txnHeaderBytes = txnHeader.toByteString();

			String value = Signing.sign(privateKey, txnHeader.toByteArray());
			Transaction txn1 = Transaction.newBuilder().setHeader(txnHeaderBytes).setPayload(payloadByteString)
					.setHeaderSignature(value).build();

			BatchHeader batchHeader = BatchHeader.newBuilder().clearSignerPublicKey().setSignerPublicKey(publicKeyHex)
					.addTransactionIds(txn1.getHeaderSignature()).build();

			ByteString batchHeaderBytes = batchHeader.toByteString();
			
			String value_batch = Signing.sign(privateKey, batchHeader.toByteArray());
			Batch batch = Batch.newBuilder().setHeader(batchHeaderBytes).setHeaderSignature(value_batch).setTrace(true)
					.addTransactions(txn1).addTransactions(txn1).build();
			BatchList batchList = BatchList.newBuilder().addBatches(batch).build();
			ByteString batchBytes = batchList.toByteString();

			String serverResponse = Unirest.post("http://localhost:8008/batches")
					.header("Content-Type", "application/octet-stream").body(batchBytes.toByteArray()).asString()
					.getBody();

			logger.info("Service Reponse :" + serverResponse);
		}
	}
}
