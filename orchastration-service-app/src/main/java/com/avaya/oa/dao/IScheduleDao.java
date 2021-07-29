package com.avaya.oa.dao;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avaya.oa.entity.Schedule;

public interface IScheduleDao extends JpaRepository<Schedule, Long> {


        @Query("From Schedule WHERE toreDown = false AND ( "
                        + "   (startTime > :currTime  AND startTime < :endTime ) " +
                        " OR  (endTime >   :currTime  AND endTime   < :endTime) " +
                        " OR  (startTime < :currTime  AND endTime   > :endTime) " +
                        " OR (startTime <= :currTime  AND endTime   >= :endTime) "
                        + ")" )
        public List<Schedule> getScheduleBetween(Timestamp currTime, Timestamp endTime);


        @Query("From Schedule where oaBuilId = :buildId")
        public Schedule getByBuildId(long buildId);

}
