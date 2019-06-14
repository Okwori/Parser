package com.ef.config;

import com.ef.model.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class LogFiledMapper extends BeanWrapperFieldSetMapper<AccessLog> {

    private SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public AccessLog mapFieldSet(FieldSet fs) {

        AccessLog accessLog = new AccessLog();

        accessLog.setIpAddress(fs.readString(1));
        accessLog.setRequest(fs.readString(2));
        accessLog.setStatus(fs.readString(3));
        accessLog.setUserAgent(fs.readString(4));

        String date = fs.readString(0);
        try {
            accessLog.setDate(dbDateFormat.parse(date));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        return accessLog;
    }
}