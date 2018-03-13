import com.google.protobuf.ByteString;
import com.mashape.unirest.http.Unirest;
import sawtooth.sdk.processor.Utils;
import sawtooth.sdk.protobuf.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import org.apache.commons.codec.binary.Hex;
public class BatchSender {

    public static void main(String[] args) throws Exception{


        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec parameterSpec = new ECGenParameterSpec("secp256k1");

        keyPairGenerator.initialize(parameterSpec);

        KeyPair keyPair = keyPairGenerator.generateKeyPair(); // generate the originator or signer key pair.
        System.out.println("Key Pair Generated PVT as :"+keyPair.getPrivate());
        System.out.println("Key Pair Generated PUB as :"+keyPair.getPublic());
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");

        ecdsaSign.initSign(keyPair.getPrivate());


        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        String publicKeyHex = Hex.encodeHexString(publicKeyBytes);

        ByteString publicKeyByteString = ByteString.copyFrom(new String(publicKeyBytes),"UTF-8");


        String payload = "{'Name':'foo', 'Value':'bar'}";  // the actual payload data.
        String payloadBytes = Utils.hash512(payload.getBytes());

        ByteString payloadByteString  = ByteString.copyFrom(payload.getBytes());
        
        

        TransactionHeader txnHeader = TransactionHeader.newBuilder().
        		clearBatcherPublicKey().
        		setBatcherPublicKeyBytes(publicKeyByteString).     		
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
        ecdsaSign.update(txnHeaderBytes.toByteArray());
        byte[] txnHeaderSignature = ecdsaSign.sign();



		Transaction txn = Transaction.newBuilder()
				.setHeader(txnHeaderBytes)
				.setPayload(payloadByteString)
				.setHeaderSignature(Hex.encodeHexString(txnHeaderSignature)).build();

        BatchHeader batchHeader = BatchHeader
        		.newBuilder()
        		.clearSignerPublicKey()
        		.setSignerPublicKey(publicKeyHex)
        		.addTransactionIds(txn.getHeaderSignature())
        		.build();
        
        ByteString batchHeaderBytes = batchHeader.toByteString();



        ecdsaSign.update(batchHeaderBytes.toByteArray());

        byte[] batchHeaderSignature = ecdsaSign.sign();

        Batch batch = Batch.newBuilder()
        		.setHeader(batchHeaderBytes)
        		.setHeaderSignature(Hex.encodeHexString(batchHeaderSignature))
        		.setTrace(true)
        		.addTransactions(txn)
        		.build();


        BatchList batchList = BatchList.newBuilder().addBatches( batch).build();


        ByteString batchBytes = batchList.toByteString();


        String serverResponse =  Unirest.post("http://127.0.0.1:8008/batches").header("Content-Type","application/octet-stream").body(batchBytes.toByteArray()).asString().getBody();

        System.out.println(serverResponse);
    }


}