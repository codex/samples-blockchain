import com.google.protobuf.ByteString;
import com.mashape.unirest.http.Unirest;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.protobuf.*;
import sawtooth.sdk.client.Signing;

import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.ECGenParameterSpec;


import org.apache.commons.codec.binary.Hex;
import org.apache.http.ssl.PrivateKeyDetails;
import org.bitcoinj.core.ECKey;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.util.PrivateKeyFactory;
public class BatchSender {

    public static void main(String[] args) throws Exception{

        //byte[] publicKeyBytes = keyPair.getPublic().getEncoded();

    	ECKey privateKey = Signing.generatePrivateKey(null);  // new random privatekey
    	String publicKeyHex = privateKey.getPublicKeyAsHex();
        
        //String publicKeyHex = Utils.hash512(publicKeyBytes);
        ByteString publicKeyByteString = ByteString.copyFrom(new String(publicKeyHex),"UTF-8");


        String payload = "{'Name':'sonar', 'Value':'some_value'}";  // the actual payload data.
        //String payloadBytes = Utils.hash512(payload.getBytes());
        String payloadBytes = Utils.hash512(encodePayload(payload));
        

        ByteString payloadByteString  = ByteString.copyFrom(encodePayload(payload));
        //ByteString payloadByteString  = ByteString.copyFromUtf8(payload);
        

        TransactionHeader txnHeader = TransactionHeader.newBuilder().
        		clearBatcherPublicKey().
        		setBatcherPublicKey(publicKeyHex).
        		//setBatcherPublicKeyBytes(publicKeyByteString).        		
                setFamilyName("intkey").
                setFamilyVersion("1.0").
                addInputs("1cf1266e282c41be5e4254d8820772c5518a2c5a8c0c7f7eda19594a7eb539453e1ed7").
                setNonce("1").
                addOutputs("1cf1266e282c41be5e4254d8820772c5518a2c5a8c0c7f7eda19594a7eb539453e1ed7").
                //setPayloadEncoding("application/json").
                setPayloadSha512(payloadBytes).
                setSignerPublicKey(publicKeyHex).
                build();


        ByteString txnHeaderBytes = txnHeader.toByteString();
        //ecdsaSign.update(txnHeaderBytes.toByteArray());
        byte[] txnHeaderSignature = privateKey.signMessage(txnHeaderBytes.toString()).getBytes();



		String value =Signing.sign(privateKey, txnHeader.toByteArray()); 
		Transaction txn = Transaction.newBuilder()
				.setHeader(txnHeaderBytes)
				.setPayload(payloadByteString)
				//.setHeaderSignature(Utils.hash512(txnHeaderSignature))
				.setHeaderSignature(value)
				.build();

        BatchHeader batchHeader = BatchHeader
        		.newBuilder()
        		.clearSignerPublicKey()
        		.setSignerPublicKey(publicKeyHex)
        		.addTransactionIds(txn.getHeaderSignature())
        		.build();
        
        ByteString batchHeaderBytes = batchHeader.toByteString();



        //ecdsaSign.update(batchHeaderBytes.toByteArray());

        //byte[] batchHeaderSignature = ecdsaSign.sign();
        byte[] batchHeaderSignature = privateKey.signMessage(batchHeaderBytes.toString()).getBytes();
        String value_batch =Signing.sign(privateKey, batchHeader.toByteArray());
        Batch batch = Batch.newBuilder()
        		.setHeader(batchHeaderBytes)
        		//.setHeaderSignature(Utils.hash512(batchHeaderSignature))
        		.setHeaderSignature(value_batch)
        		.setTrace(true)
        		.addTransactions(txn)
        		.build();


        BatchList batchList = BatchList.newBuilder().addBatches( batch).build();


        ByteString batchBytes = batchList.toByteString();


        String serverResponse =  Unirest.post("http://127.0.0.1:8008/batches").header("Content-Type","application/octet-stream").body(batchBytes.toByteArray()).asString().getBody();

        System.out.println(serverResponse);
    }

    
    private static byte[] encodePayload(String payload) throws CborException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	new CborEncoder(baos).encode(new CborBuilder()
    	    .add(payload)                // add string
    	    .build());
    	byte[] encodedBytes = baos.toByteArray();
		return encodedBytes;
	}


	public void getSigner() {
    	
    }

}