package com.example.test.service;

import com.azure.messaging.servicebus.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class ServiceBusSenderService {

    static String connectionString = "Endpoint=sb://spring-servicebusdemo.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=BIcFiYvkUmP0qCQltrZHlcotYOAwCp7Ln+ASbJGdkVo=";
    static String queueName = "demoqueue";

    public void sendMessage(String messageBody) {
        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName(queueName)
                .buildClient();

        // send one message to the queue
        senderClient.sendMessage(new ServiceBusMessage(messageBody));
    }

    public void receiveMessage() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ServiceBusProcessorClient serviceBusProcessorClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .processor()
                .queueName(queueName)
                .processMessage(ServiceBusSenderService::processMessage)
                .processError(context -> processError(context, countDownLatch))
                .buildProcessorClient();

        System.out.println("Processor has started!");
        serviceBusProcessorClient.start();
        TimeUnit.SECONDS.sleep(100);
        serviceBusProcessorClient.close();
        System.out.println("Processor has stopped!");
    }

    private void processError(ServiceBusErrorContext context, CountDownLatch countDownLatch) {
    }

    private static void processMessage(ServiceBusReceivedMessageContext serviceBusReceivedMessageContext) {
        System.out.printf("Processing messages: Session:  %s, Sequence:  %s, messageBody: %s\n",
                serviceBusReceivedMessageContext.getMessage().getMessageId(),
                serviceBusReceivedMessageContext.getMessage().getSequenceNumber(),
                serviceBusReceivedMessageContext.getMessage().getBody());
    }

}
