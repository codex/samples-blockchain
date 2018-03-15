/**
 * 
 */
package com.mycompany.blockchain.sawtooth;

import com.mycompany.blockchain.sawtooth.IdempotentHandler;
import sawtooth.sdk.processor.TransactionProcessor;

/**
 * @author dev
 *
 */
public class IdempotentProcessor {
	/**
	 * the method that runs a Thread with a TransactionProcessor in it.
	 */
	public static void main(String[] args) {

		TransactionProcessor transactionProcessor = new TransactionProcessor(args[0]);
		transactionProcessor.addHandler(new IdempotentHandler());
		Thread thread = new Thread(transactionProcessor);
		thread.start();
	}
}
