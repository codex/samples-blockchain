import java.io.ByteArrayOutputStream;

import org.bitcoinj.core.ECKey;

import com.google.protobuf.ByteString;
import com.mashape.unirest.http.Unirest;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import sawtooth.sdk.client.Signing;
import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.protobuf.Batch;
import sawtooth.sdk.protobuf.BatchHeader;
import sawtooth.sdk.protobuf.BatchList;
import sawtooth.sdk.protobuf.Transaction;
import sawtooth.sdk.protobuf.TransactionHeader;
public class IntKeySender {

    public static void main(String[] args) throws Exception{

    	ECKey privateKey = Signing.generatePrivateKey(null);  // new random privatekey
		
    	String publicKeyHex = privateKey.getPublicKeyAsHex();
        
        String payloadBytes = Utils.hash512(encodePayload());  //--fix for invaluid payload seriqalization
        
        ByteString payloadByteString  = ByteString.copyFrom(encodePayload());
        
        TransactionHeader txnHeader = TransactionHeader.newBuilder().
        		clearBatcherPublicKey().
        		setBatcherPublicKey(publicKeyHex).
                setFamilyName("intkey").
                setFamilyVersion("1.0").
                addInputs("1cf1264aa624fa573079918f86c958f503cecb210ec2b258092079105096dbbdd61976"). //note this has to be taken from state               
                setNonce("1").
                addOutputs("1cf1264aa624fa573079918f86c958f503cecb210ec2b258092079105096dbbdd61976").//note this has to be taken from state
                setPayloadSha512(payloadBytes).
                setSignerPublicKey(publicKeyHex).
                build();


        ByteString txnHeaderBytes = txnHeader.toByteString();

		String value =Signing.sign(privateKey, txnHeader.toByteArray()); 
		Transaction txn = Transaction.newBuilder()
				.setHeader(txnHeaderBytes)
				.setPayload(payloadByteString)
				.setHeaderSignature(value)
				.build();

        BatchHeader batchHeader = BatchHeader
        		.newBuilder()
        		.clearSignerPublicKey()
        		.setSignerPublicKey(publicKeyHex)
        		.addTransactionIds(txn.getHeaderSignature())
        		.build();
        
        ByteString batchHeaderBytes = batchHeader.toByteString();

        String value_batch =Signing.sign(privateKey, batchHeader.toByteArray());
        Batch batch = Batch.newBuilder()
        		.setHeader(batchHeaderBytes)
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
    	    .put("Name", "nishant")
    	    .put("Value", 10)
    	    .end()
    	    .build());
    	byte[] encodedBytes = baos.toByteArray();
		return encodedBytes;
	}


	public void getSigner() {
    	
    }

}
