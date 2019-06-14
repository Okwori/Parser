package com.ef.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BlockedIp {

    @Id
    private String ipAddress;

    private String comment;
}