package com.luffy.parse.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;

/**
 * @author luffy
 * @version 1.0
 * @date 2021/4/21 10:57 上午
 */
@Service
public class ContainerService {

    @Autowired
    KafkaListenerEndpointRegistry registry;


    public void stopListen(String listenerId){
        MessageListenerContainer container = registry.getListenerContainer(listenerId);
        if (container.isRunning()){
            container.stop();
        }

    }

    public void pauseListen(String listenerId){
        MessageListenerContainer container = registry.getListenerContainer(listenerId);
        if (container.isRunning()){
            container.pause();
        }

    }

    public void startListen(String listenerId){
        MessageListenerContainer container = registry.getListenerContainer(listenerId);
        if (!container.isRunning()){
            container.start();
        }
        container.resume();
    }



}
