/**
 * 
 */
package com.mycompany.blockchain.sawtooth;

import com.mycompany.blockchain.sawtooth.InventoryHandler;
import sawtooth.sdk.processor.TransactionProcessor;

/**
 * @author dev
 *
 */
public class InventoryProcessor {
	/**
	 * the method that runs a Thread with a TransactionProcessor in it.
	 */
	public static void main(String[] args) {

		TransactionProcessor transactionProcessor = new TransactionProcessor(args[0]);
		transactionProcessor.addHandler(new InventoryHandler());
		Thread thread = new Thread(transactionProcessor);
		thread.start();
	}
}
