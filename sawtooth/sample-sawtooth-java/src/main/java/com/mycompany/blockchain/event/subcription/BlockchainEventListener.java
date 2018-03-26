package com.mycompany.blockchain.event.subcription;

import org.zeromq.ZMQ.Socket;

import com.google.protobuf.InvalidProtocolBufferException;

import sawtooth.sdk.protobuf.Event;
import sawtooth.sdk.protobuf.EventList;
import sawtooth.sdk.protobuf.Message;
import sawtooth.sdk.protobuf.Message.MessageType;

public class BlockchainEventListener {

	public static void listenEvents(Socket socket) throws InvalidProtocolBufferException {
		while (true) {
			System.out.println("Listening for events :");

			byte[] responseBytes = socket.recv();
			Message responseMessage = Message.parseFrom(responseBytes);
			if (responseMessage != null) {
				if (responseMessage.getMessageType() != MessageType.CLIENT_EVENTS) {
					System.out.println("Unexpected Message Type");
				}

				EventList eventList = EventList.parseFrom(responseMessage.getContent());
				System.out.println(eventList);

				for (Event event : eventList.getEventsList()) {
					System.out.println("Event Details ::::=>" + event.getEventType() + " ::::=> "
							+ event.toString());
				}
			}
		}
	}
}
