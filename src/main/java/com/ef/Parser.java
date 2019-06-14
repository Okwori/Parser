package com.ef;

import com.ef.model.AccessLog;
import com.ef.model.BlockedIp;
import com.ef.service.AccessLogService;
import com.ef.service.ApplicationArgumentParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Parser implements ApplicationRunner {

    @Autowired
    AccessLogService logService;

    public static void main(String[] args) {
        SpringApplication.run(Parser.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Application Started!");

        ApplicationArgumentParameter parameter = new ApplicationArgumentParameter(args);

        if (!parameter.checkArguments()) {
            System.out.println("Help: java -jar parser-0.0.1.jar --accesslog=./access.log --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500");
        } else {
            if (parameter.isErrorFlag()) {
                log.error("JobExecution: FAILED! -- Restart Application with the right parameters");
            } else {
                System.out.println("JobExecution: " + logService.load());
                List<AccessLog> logs = logService.getLogsByDateDurationAndLimit(parameter.getStartDate(), parameter.getEndDate(), parameter.getThreshold());

                List<BlockedIp> blockedIpList = logService.saveBlockedIps(logs, parameter);
                blockedIpList.forEach(blockedIp -> System.out.println(blockedIp.getComment()));

                System.out.println(logs.size() == 1 | logs.size() == 0 ? "Found " + logs.size() + " IP Address" : "Found " + logs.size() + " IP Addresses");
                System.out.println("Success!!");
            }
        }
    }
}