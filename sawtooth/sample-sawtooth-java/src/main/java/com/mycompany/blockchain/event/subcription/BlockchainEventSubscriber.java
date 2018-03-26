package com.mycompany.blockchain.event.subcription;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.google.protobuf.InvalidProtocolBufferException;

import sawtooth.sdk.protobuf.ClientBatchGetResponse.Status;
import sawtooth.sdk.protobuf.ClientEventsSubscribeRequest;
import sawtooth.sdk.protobuf.ClientEventsSubscribeResponse;
import sawtooth.sdk.protobuf.EventFilter;
import sawtooth.sdk.protobuf.EventFilter.FilterType;
import sawtooth.sdk.protobuf.EventSubscription;
import sawtooth.sdk.protobuf.Message;
import sawtooth.sdk.protobuf.Message.MessageType;

public class BlockchainEventSubscriber {

	public static void main(String[] args)
			throws InterruptedException, InvalidProtocolBufferException {

		// Event Subscription Request
		EventFilter eventFilter = EventFilter.newBuilder().setKey("address").setMatchString("aa5241*")
				.setFilterType(FilterType.REGEX_ANY).build();

		EventSubscription eventSubscription = EventSubscription.newBuilder()
				.setEventType("sawtooth/state-delta").addFilters(eventFilter).build();

		Context context = ZMQ.context(1);
		Socket socket = context.socket(ZMQ.DEALER);
		socket.connect("tcp://localhost:4004");

		ClientEventsSubscribeRequest request = ClientEventsSubscribeRequest.newBuilder()
				.addSubscriptions(0, eventSubscription).build();

		Message message = Message.newBuilder().setCorrelationId("122")
				.setMessageType(MessageType.CLIENT_EVENTS_SUBSCRIBE_REQUEST)
				.setContent(request.toByteString()).build();

		socket.send(message.toByteArray());

		Thread.sleep(5000);

		// Event Subscription Response

		byte[] responseBytes = socket.recv();
		Message responseMessage = Message.parseFrom(responseBytes);
		if (responseMessage != null) {
			if (responseMessage.getMessageType() != MessageType.CLIENT_EVENTS_SUBSCRIBE_RESPONSE) {
				System.out.println("Unexpected Message Type");
			}

			ClientEventsSubscribeResponse response = ClientEventsSubscribeResponse
					.parseFrom(responseMessage.getContent());

			if (response
					.getStatus() != sawtooth.sdk.protobuf.ClientEventsSubscribeResponse.Status.OK) {
				System.out.println("Subscription Failed...:" + response.getResponseMessage());
			} else {
				System.out.println("Subscription successfull...:" + response.getResponseMessage());
			}
		}
		
		
		BlockchainEventListener.listenEvents(socket);
		

	}

}
