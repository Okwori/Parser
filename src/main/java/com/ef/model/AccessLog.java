package com.ef.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
public class AccessLog {

    private Date date;

    private String ipAddress;

    private String request;

    private String status;

    private String userAgent;

    @Override
    public String toString() {
        return "Access Log {" + "Date=" + date +
                ", ipAddress='" + ipAddress + '\'' +
                ", request='" + request + '\'' +
                ", status=" + status +
                ", userAgent=" + userAgent +
                '}';
    }
}
