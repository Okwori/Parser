package com.ef.service;

import com.ef.dao.AccessLogRepository;
import com.ef.dao.BlockedIpRepository;
import com.ef.model.AccessLog;
import com.ef.model.BlockedIp;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccessLogService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    AccessLogRepository logRepository;

    @Autowired
    BlockedIpRepository blockedIpRepository;

    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);

        return jobExecution.getStatus();
    }

    public void saveLogs(List<? extends AccessLog> accessLogs){
        logRepository.saveAll(accessLogs);
    }

    public List<AccessLog> getLogsByDateDurationAndLimit(Date startDate, Date endDate, Integer threshold){
        return logRepository.findLogsByDatesAndThreshold(startDate, endDate, Long.valueOf(threshold));
    }

    public List<BlockedIp> saveBlockedIps(List<AccessLog> accessLogs, ApplicationArgumentParameter parameter){

        List<BlockedIp> blockedIps = new ArrayList<>();

        accessLogs.forEach(accessLog -> {
            BlockedIp blockedIp = new BlockedIp();
            blockedIp.setIpAddress(accessLog.getIpAddress());
            blockedIp.setComment("IP Address: " + accessLog.getIpAddress() + " is beyond it threshold limit of "
                    + parameter.getThreshold().toString() + " within the Dates " + parameter.getStartDate()
                    + " and " + parameter.getEndDate());
            blockedIps.add(blockedIp);
        });

        blockedIpRepository.saveAll(blockedIps);

        return blockedIps;
    }
}