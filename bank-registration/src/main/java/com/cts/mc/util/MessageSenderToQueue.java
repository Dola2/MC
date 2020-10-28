package com.cts.mc.util;
import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.cts.mc.model.RegistrationModel;
import com.google.gson.Gson;
@Service
public class MessageSenderToQueue {
	@Autowired
	Gson gson;
	//@Autowired
	//MessageReceiverFromQueue messageReceiverFromQueue;

	public void run(RegistrationModel model)  {
		
		String queueName="targetque";
		
		
		String connectionString="Endpoint=sb://ctssvcbus.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=4d4m5aw/r98kP41qC0sHHVqZTNya+WkWUsqZpf58sjU=";
		try {
			ServiceBusSenderClient sender = new ServiceBusClientBuilder()
				    .connectionString(connectionString)
				    .sender()
				    .queueName(queueName)
				    .buildClient();
				   // .buildAsyncClient();
			ServiceBusMessage message = new ServiceBusMessage(gson.toJson(model).getBytes(UTF_8));
			message.setSessionId("xxxxxxxx");
			sender.send(message);
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	    CompletableFuture<Void> sendMessagesAsync(QueueClient sendClient,RegistrationModel data) {
//	       
//	       
//
//	        List<CompletableFuture> tasks = new ArrayList<>();
//	       // for (int i = 0; i < data.size(); i++) {
//	            final String messageId = data.getUserName();
//	            Message message = new Message(gson.toJson(data).getBytes(UTF_8));
//	            message.setContentType("application/json");
//	            message.setLabel("Scientist");
//	            message.setMessageId(messageId);
//	            message.setTimeToLive(Duration.ofMinutes(2));
//	            System.out.printf("\nMessage sending: Id = %s", message.getMessageId());
//
//                sendClient.sendAsync(message).thenRunAsync(() -> {
//                	sendClient.closeAsync();
//                });
////	            tasks.add(
////	                    sendClient.sendAsync(message).thenRunAsync(() -> {
////	                        System.out.printf("\n\tMessage acknowledged: Id = %s", message.getMessageId());
////	                    }));
//	       // }
//	        return CompletableFuture.allOf(tasks.toArray(new CompletableFuture<?>[tasks.size()]));
//	    }
	
	
}
