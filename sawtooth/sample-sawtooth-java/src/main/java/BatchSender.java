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
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.VersionedChecksummedBytes;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.util.PrivateKeyFactory;
public class BatchSender {

    public static void main(String[] args) throws Exception{

        //byte[] publicKeyBytes = keyPair.getPublic().getEncoded();

    	ECKey privateKey = Signing.generatePrivateKey(null);  // new random privatekey
		
    	String publicKeyHex = privateKey.getPublicKeyAsHex();
        
        //String publicKeyHex = Utils.hash512(publicKeyBytes);
        ByteString publicKeyByteString = ByteString.copyFrom(new String(publicKeyHex),"UTF-8");


        //String payloadBytes = Utils.hash512(payload.getBytes());
        String payloadBytes = Utils.hash512(encodePayload());  //--fix for invaluid payload seriqalization
        

        ByteString payloadByteString  = ByteString.copyFrom(encodePayload());
        //ByteString payloadByteString  = ByteString.copyFromUtf8(payload);
        

        TransactionHeader txnHeader = TransactionHeader.newBuilder().
        		clearBatcherPublicKey().
        		setBatcherPublicKey(publicKeyHex).
        		//setBatcherPublicKeyBytes(publicKeyByteString).        		
                setFamilyName("intkey").
                setFamilyVersion("1.0").
                //addInputs("1cf1266e282c41be5e4254d8820772c5518a2c5a8c0c7f7eda19594a7eb539453e1ed7").
                addInputs("1cf12671ff2ab0ba5c0d7e0e3ccda7e36eaa587b9fe8b7208c8848da5d8a816fd04076").
                setNonce("1").
                //addOutputs("1cf1266e282c41be5e4254d8820772c5518a2c5a8c0c7f7eda19594a7eb539453e1ed7").
                addOutputs("1cf12671ff2ab0ba5c0d7e0e3ccda7e36eaa587b9fe8b7208c8848da5d8a816fd04076").
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


        String serverResponse =  Unirest.post("http://localhost:8008/batches").header("Content-Type","application/octet-stream").body(batchBytes.toByteArray()).asString().getBody();

        System.out.println(serverResponse);
    }

    /**
     * The encoding with payload shoudl match with the expected with transactin processor
     * @return
     * @throws CborException
     */
    private static byte[] encodePayload() throws CborException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	new CborEncoder(baos).encode(new CborBuilder()
    		.addMap()
    		.put("Verb", "inc")
    	    .put("Name", "Sonar")
    	    .put("Value", 1055)
    	    .end()
    	    .build());
    	byte[] encodedBytes = baos.toByteArray();
		return encodedBytes;
	}


	public void getSigner() {
    	
    }

}
