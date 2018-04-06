package com.mycompany.blockchain.client;

import com.googlecode.protobuf.format.JsonFormat.ParseException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mycompany.blockchain.constants.Constants;
import com.mycompany.blockchain.exceptions.BlockchainClientException;
import com.mycompany.blockchain.service.SawtoothWalletServiceImpl;

/**
 * 
 */

/**
 * @author dev
 *
 */
public class SawtoothWalletClientSender {

	public static void main(String[] args)
			throws UnirestException, BlockchainClientException, ParseException {

		System.out.println("Welcome to the Sawtooth Wallet client Application -");
		System.out.println("Supported operatoins are -");
		System.out.println("create <customerId> <amount> ");
		System.out.println("deposit <customerId> <amount> ");

		SawtoothWalletServiceImpl walletService = new SawtoothWalletServiceImpl();

		switch (args[0]) {
		case Constants.DEPOSIT_ACTION:
			walletService.putDataToBLockchain(args);
			break;
		case Constants.CREATE_ACTION:
			walletService.putDataToBLockchain(args);
			break;
		default:
			System.out.println("Invalid operation");
		}
	}
}
