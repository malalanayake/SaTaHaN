package com.sthn.messaging.impl;

import com.sthn.config.MessagingConfig;
import com.sthn.messaging.IMessageHandler;
import com.sthn.messaging.IMessageReceiver;
import com.sthn.model.Waki;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIMessageHandler implements IMessageHandler {
    Logger log = Logger.getLogger(this.getClass());
    private BlockingQueue<Waki> queue = new ArrayBlockingQueue<Waki>(MessagingConfig.QUEUE_CAPACITY, true);
    private List<IMessageReceiver> receivers = new ArrayList<IMessageReceiver>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public boolean handOver(Waki message) {
        boolean status = false;
        try {
            queue.put(message);
            status = true;
            executorService.execute(this);
            log.debug("Waki message handed over");
        } catch (Exception ex) {
            log.debug(ex);
        }

        return status;
    }

    @Override
    public void subscribe(IMessageReceiver messageReceiver) {
        this.receivers.add(messageReceiver);
    }

    @Override
    public void unsubscribe(IMessageReceiver messageReceiver) {
        this.receivers.remove(messageReceiver);
    }

    @Override
    public int receiverCount() {
        return this.receivers.size();
    }

    @Override
    public void run() {
        try {
            pushMessagesToReceivers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dispatch the messages to attached Receivers on parallel
     *
     * @throws InterruptedException
     */
    private void pushMessagesToReceivers() throws InterruptedException {
        Waki waki = queue.take();
        receivers.parallelStream().forEach(receiver -> {
            receiver.receive(waki);
        });
    }
}
