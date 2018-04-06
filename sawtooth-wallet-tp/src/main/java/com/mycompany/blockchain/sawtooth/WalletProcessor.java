/**
 * 
 */
package com.mycompany.blockchain.sawtooth;

import com.mycompany.blockchain.sawtooth.WalletHandler;
import sawtooth.sdk.processor.TransactionProcessor;

/**
 * @author dev
 *
 */
public class WalletProcessor {
	/**
	 * the method that runs a Thread with a TransactionProcessor in it.
	 */
	public static void main(String[] args) {

		TransactionProcessor transactionProcessor = new TransactionProcessor(args[0]);
		transactionProcessor.addHandler(new WalletHandler());
		Thread thread = new Thread(transactionProcessor);
		thread.start();
	}
}
