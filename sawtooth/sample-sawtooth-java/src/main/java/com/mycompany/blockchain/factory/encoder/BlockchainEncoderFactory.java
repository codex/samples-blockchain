package com.mycompany.blockchain.factory.encoder;

import com.mycompany.blockchain.constants.Constants;

public class BlockchainEncoderFactory {

	public static BlockchainEncoder getEncoder(String encoderType) {
		if (Constants.BASE64_ENCODER.equalsIgnoreCase(encoderType)) {
			new BlockchainBase64Encoder();
		}
		return null;
	}
}
