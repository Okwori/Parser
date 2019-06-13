package com.ef.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class BlockedIp {

    private String ipAddress;

    private String comment;
}
