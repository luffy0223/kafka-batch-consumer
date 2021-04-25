package com.luffy.parse.service;

import com.luffy.parse.BO.Packet;
import com.luffy.parse.service.convert.IConvert;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author luffy
 * @version 1.0
 * @date 2021/4/20 8:41 下午
 */
@Service
public class ParseService {

    @Autowired
    RecordListService recordListService;

    @Autowired
    IConvert<String, Packet> packetConverter;

    private HashMap<String,String> dataMap = new HashMap<>(8192);

    @Scheduled(fixedDelay = 10*60*1000L)
    public void parse(){
        synchronized (recordListService) {
            List<ConsumerRecord<String, String>> list = recordListService.getRecordList();
            list.stream().map(ConsumerRecord::value)
                    .map(u -> packetConverter.convert(u))
                    .filter(p -> !dataMap.containsKey(p))
                    .forEach(
                            p -> {
                                dataMap.put(p.getFiveElementNode(), p.getPacketData().toString());
                            }

                    );
            recordListService.clear();
        }
        /*
        解析packetdata，生成fiveNode 每次取5000个元素
        list->map<String,fiveNodeLinkedList>
        进行解析，解析后遗留的给个标志位置为可移除。下次5000条进来后一块处理，处理完了将可移除为true的记录从map里remove掉，
        保证添加进map的记录最多存活2个周期，理论map最大的记录数是10000个元素
         */



    }


}
