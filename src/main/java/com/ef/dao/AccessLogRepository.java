package com.ef.dao;

import com.ef.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AccessLogRepository  extends JpaRepository<AccessLog, Long> {

    @Query("select u from User u where u.date between ?1 and ?2 group by u.ipAddress having count(u.ipAddress) >= ?3")
    List<AccessLog> findLogsByDatesAndThreshold(Date startDate, Date endDate, Long threshold);
}
