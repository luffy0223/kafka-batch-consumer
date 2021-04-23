package com.luffy.parse.consumer;

import com.luffy.parse.constant.MyConstants;
import com.luffy.parse.service.ParseService;
import com.luffy.parse.service.RecordListService;
import com.luffy.parse.service.kafka.ContainerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2021/4/20 10:17 上午
 */
@Component
public class KafkaConsumer {

    @Autowired
    RecordListService recordListService;

    @Autowired
    ContainerService containerService;

    @KafkaListener(id = "0", topics = "my-topic", containerFactory = "batchFactory")
    public void listener(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        System.out.println(LocalDateTime.now());
        System.out.println(records.size() + "条数被消费");
        recordListService.appendList(records);

        try {
            ack.acknowledge();
        } catch (Exception ex) {

        }

    }
}
