package com.luffy.parse.thread;

import com.luffy.parse.service.RecordListService;
import com.luffy.parse.service.kafka.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author luffy
 * @version 1.0
 * @date 2021/4/20 7:33 下午
 */
@Service
public class Task {
    @Autowired
    ContainerService container;

    @Autowired
    RecordListService recordListService;

    @Scheduled(cron = "0/20 * * * * ? ")
    public void consumerListenerTask(){
        if (recordListService.isEnableAppend()){
            container.startListen("0");
        }

    }


}
