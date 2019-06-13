package com.ef.dao;

import com.ef.model.BlockedIp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedIpRepository extends JpaRepository<BlockedIp, Long> {
}
