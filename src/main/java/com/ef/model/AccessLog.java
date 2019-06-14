package com.ef.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@Entity @IdClass(AccessLog.AccessLogID.class)
@Getter @Setter @NoArgsConstructor
public class AccessLog {

    @Id
    @Column(columnDefinition = "DATETIME(3)")
    private Date date;

    @Id
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

    @Data
    static class AccessLogID implements Serializable {
        Date date;
        String ipAddress;
    }
}