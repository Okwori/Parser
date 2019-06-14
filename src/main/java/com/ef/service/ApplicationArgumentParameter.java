package com.ef.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
public class ApplicationArgumentParameter{

    private ApplicationArguments arguments;
    private Date startDate;
    private Date endDate;
    private Integer threshold;
    private boolean errorFlag = false;

    public ApplicationArgumentParameter(ApplicationArguments arguments) {
        this.arguments = arguments;

        if (arguments.containsOption("startDate")) {
            String startDateStr = arguments.getOptionValues("startDate").get(0);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
            try {
                this.startDate = dateFormat.parse(startDateStr);
            } catch (ParseException e) {
                log.error(e.getMessage());
                this.errorFlag = true;
            }
            this.endDate = startDate;
        }
        if (arguments.containsOption("duration")) {
            String duration = arguments.getOptionValues("duration").get(0);
            switch (duration) {
                case "hourly":
                    this.endDate = new Date(this.startDate.getTime() + TimeUnit.HOURS.toMillis(1));
                    break;
                case "daily":
                    this.endDate = new Date(this.startDate.getTime() + TimeUnit.DAYS.toMillis(1));
                    break;
                default:
                    log.error("You can only use: hourly | daily.");
                    this.errorFlag = true;
                    break;
            }
        }

        if (arguments.containsOption("threshold")) {
            try {
                this.threshold = new Integer(arguments.getOptionValues("threshold").get(0));
            }catch (Exception e){
                log.error(e.getMessage());
                this.errorFlag = true;
            }
        }
    }

    public boolean checkArguments() {
        return  (this.arguments.containsOption("startDate")
                && this.arguments.containsOption("duration")
                && this.arguments.containsOption("threshold")
        );
    }
}