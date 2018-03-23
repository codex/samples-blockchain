package com.mycompany.blockchain.client;

import java.io.UnsupportedEncodingException;

import com.googlecode.protobuf.format.JsonFormat.ParseException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mycompany.blockchain.constants.Constants;
import com.mycompany.blockchain.service.InventoryServiceImpl;

import sawtooth.sdk.processor.exceptions.InternalError;
import sawtooth.sdk.processor.exceptions.InvalidTransactionException;

/**
 * 
 */

/**
 * @author dev
 *
 */
public class InventoryClientSender {

	public static void main(String[] args) throws UnirestException, UnsupportedEncodingException, InternalError,
			ParseException, InvalidTransactionException {

		System.out.println("Welcome to the Inventory client Application -");
		System.out.println("Supported operatoins are -");
		System.out.println("create <itemId> <itemName> <color> <price>");
		System.out.println("list <itemId> or list all(For all items)");

		InventoryServiceImpl inventoryService = new InventoryServiceImpl();

		switch (args[0]) {
		case Constants.LIST_ACTION:
			inventoryService.getDataFromBlockchain(args);
			break;
		case Constants.CREATE_ACTION:
			inventoryService.putDataToBLockchain(args);
			break;
		default:
			System.out.println("Invalid operation");
		}
	}
}
