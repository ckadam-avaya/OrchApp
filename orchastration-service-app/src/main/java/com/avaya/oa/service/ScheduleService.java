package com.avaya.oa.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaya.oa.dao.IScheduleDao;
import com.avaya.oa.entity.Schedule;


@Service
@Transactional
public class ScheduleService {
	
	@Autowired
	IScheduleDao scheduleDao;
	
	public List<Schedule> getScheduleBetween(Timestamp currTime, Timestamp endTime) {
		return scheduleDao.getScheduleBetween( currTime, endTime);
	}
	
	public void save(Schedule sch) {
		scheduleDao.save(sch);
	}
	
	public void tearDownSchedule(long buildId) {
		
		Schedule schedule = scheduleDao.getByBuildId(buildId);
		
		// Set end time as current time and tore down to True
		schedule.setEndTime(new Timestamp(System.currentTimeMillis()));
		schedule.setToreDown(true);
		
		scheduleDao.save(schedule);
	}
}