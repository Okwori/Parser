package com.ef.config;

import com.ef.model.AccessLog;
import com.ef.service.AccessLogService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogWriter implements ItemWriter<AccessLog> {

    @Autowired
    AccessLogService logService;

    @Override
    public void write(List<? extends AccessLog> list) throws Exception {
        logService.saveLogs(list);
    }
}