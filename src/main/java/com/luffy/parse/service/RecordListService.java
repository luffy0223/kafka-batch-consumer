package com.luffy.parse.service;

import com.luffy.parse.constant.MyConstants;
import com.luffy.parse.service.kafka.ContainerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2021/4/21 4:57 下午
 */

@Service
public class RecordListService {
    @Autowired
    private ContainerService containerService;

    private boolean enableAppend = true;

    public boolean isEnableAppend() {
        return enableAppend;
    }

    public void setEnableAppend(boolean enableAppend) {
        this.enableAppend = enableAppend;
    }


    private List recordList;

    public synchronized List getRecordList() {
        return recordList;
    }

    public synchronized void appendList(List<ConsumerRecord<String, String>> records){
        this.recordList.addAll(records);
        if (recordList.size()> MyConstants.List_Limit){
            containerService.pauseListen("0");
        }
        notifyAll();
    }

    public synchronized void removeElement(List<String> removeRecords){
        //移除removerecords
        this.recordList.removeIf(s->removeRecords.contains(s));
        containerService.startListen("0");
    }

    public synchronized void refresh(){
        //定时清除部分数据
        this.recordList.remove(0);
    }

}
