package com.mycompany.blockchain.client;

import com.googlecode.protobuf.format.JsonFormat.ParseException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mycompany.blockchain.constants.Constants;
import com.mycompany.blockchain.exceptions.BlockchainClientException;
import com.mycompany.blockchain.service.InventoryServiceImpl;

/**
 * 
 */

/**
 * @author dev
 *
 */
public class InventoryClientSender {

	public static void main(String[] args) throws UnirestException, BlockchainClientException, ParseException {

		System.out.println("Welcome to the Inventory client Application -");
		System.out.println("Supported operatoins are -");
		System.out.println("create <itemId> <itemName> <color> <price>");
		System.out.println("list <itemId> or list all(For all items)");

		InventoryServiceImpl inventoryService = new InventoryServiceImpl();

		switch (args[0]) {
		case Constants.LIST_ACTION:
			inventoryService.getDataFromBlockchain(args);
			break;
		case Constants.CREATE_WALLET_ACTION:
			inventoryService.putDataToBLockchain(args);
			break;
		default:
			System.out.println("Invalid operation");
		}
	}
}
