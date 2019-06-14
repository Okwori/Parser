package com.ef.dao;

import com.ef.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AccessLogRepository  extends JpaRepository<AccessLog, Long> {

    @Query("select al from AccessLog al where al.date between ?1 and ?2 group by al.ipAddress having count(al.ipAddress) >= ?3")
    List<AccessLog> findLogsByDatesAndThreshold(Date startDate, Date endDate, Long threshold);
}
