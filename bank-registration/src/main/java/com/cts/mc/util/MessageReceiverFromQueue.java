//package com.cts.mc.util;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.gson.Gson;
//import com.microsoft.azure.servicebus.ExceptionPhase;
//import com.microsoft.azure.servicebus.IMessage;
//import com.microsoft.azure.servicebus.IMessageHandler;
//import com.microsoft.azure.servicebus.MessageHandlerOptions;
//import com.microsoft.azure.servicebus.QueueClient;
//import com.microsoft.azure.servicebus.ReceiveMode;
//import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
//@Service
//public class MessageReceiverFromQueue {
//	@Autowired
//	Gson gson;
//	public void run() throws Exception {
//	    // Create a QueueClient instance for receiving using the connection string builder
//        // We set the receive mode to "PeekLock", meaning the message is delivered
//        // under a lock and must be acknowledged ("completed") to be removed from the queue
//		
//		
//		String connectionString="Endpoint=sb://ctsmcbussvc.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=nQcL/e/ugGdrM2vQfIm4eDdpl8O8TscKUM7+f9EaL1Y=";
//		 String queueName="ctsmcqueue";
//        QueueClient receiveClient = new QueueClient(new ConnectionStringBuilder(connectionString, queueName), ReceiveMode.PEEKLOCK);
//        this.registerReceiver(receiveClient);
//
//        // shut down receiver to close the receive loop
//        receiveClient.close();
//    }
//    @SuppressWarnings("deprecation")
//	void registerReceiver(QueueClient queueClient) throws Exception {
//        // register the RegisterMessageHandler callback
//        queueClient.registerMessageHandler(new IMessageHandler() {
//		// callback invoked when the message handler loop has obtained a message
//            public CompletableFuture<Void> onMessageAsync(IMessage message) {
//			// receives message is passed to callback
//                if (message.getLabel() != null &&
//                    message.getContentType() != null &&
//                    message.getLabel().contentEquals("Scientist") &&
//                    message.getContentType().contentEquals("application/json")) {
//
//                       
//
//                        System.out.printf(
//                          	"\n\t\t\t\tMessage received: \n\t\t\t\t\t\tMessageId = %s, \n\t\t\t\t\t\tSequenceNumber = %s, \n\t\t\t\t\t\tEnqueuedTimeUtc = %s," +
//                            "\n\t\t\t\t\t\tExpiresAtUtc = %s, \n\t\t\t\t\t\tContentType = \"%s\",  \n\t\t\t\t\t\tContent: [ firstName = %s, name = %s ]\n",
//                            message.getMessageId(),
//                            message.getSequenceNumber(),
//                            message.getEnqueuedTimeUtc(),
//                            message.getExpiresAtUtc(),
//                            message.getContentType());
//                    }
//                    return CompletableFuture.completedFuture(null);
//		        }
//
//                // callback invoked when the message handler has an exception to report
//                public void notifyException(Throwable throwable, ExceptionPhase exceptionPhase) {
//			        System.out.printf(exceptionPhase + "-" + throwable.getMessage());
//		        }
//        },
//	    // 1 concurrent call, messages are auto-completed, auto-renew duration
//        new MessageHandlerOptions(1, true, Duration.ofMinutes(1)));
//    }
//}
