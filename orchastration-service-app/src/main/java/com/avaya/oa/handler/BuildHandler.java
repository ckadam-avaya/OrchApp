package com.avaya.oa.handler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.avaya.oa.entity.Lab;
import com.avaya.oa.entity.OABuild;
import com.avaya.oa.entity.Schedule;
import com.avaya.oa.exception.LabNotAvailableException;
import com.avaya.oa.exception.ProcessFailException;
import com.avaya.oa.exception.ScheduleIdAbsentException;
import com.avaya.oa.exception.UserEmailNotFoundException;
import com.avaya.oa.model.TestAPIModel;
import com.avaya.oa.service.BuildService;
import com.avaya.oa.service.ScheduleService;
import com.avaya.oa.util.DateUtil;

@Component
public class BuildHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(BuildHandler.class);
	
	public static final String EAMIL = "email";
	public static final String SCHEDULE_ID = "scheduleId";
	public static final String SCH_START_TIME = "startTime";
	public static final String SCH_END_TIME = "endTime";
	
	@Autowired
	public BuildService buildService;
	
	@Autowired
	public ScheduleService scheduleService;
	
	@Value("${home.directory}")
	String homeDirectory;

	public List<Long> getBuilds() {
		logger.info("Inside getBuilds of orchestationService " );
		return new ArrayList<Long>();
	}

	public String getActaulMappedBuildById(long build_Id) {
		logger.info("Inside getActaulMappedBuildById of orchestationService " );
		return "Test";
	}

	
	public Long createBuild(TestAPIModel<String> testAPIRequest) throws UserEmailNotFoundException, 
	ScheduleIdAbsentException, ProcessFailException, LabNotAvailableException {
		logger.info("Inside createBuild of orchestationService " );
		
		//Step 1. Get user email from body
		Map<String, String> bodyJson = testAPIRequest.getBodyJson();
		String schedularEmail = bodyJson.get(EAMIL);
		if(schedularEmail == null || schedularEmail.equals("")) {
			logger.error("BAD Request : Scheduler email is madatory!");
			throw new UserEmailNotFoundException("BAD Request : Scheduler email is madatory!");
		}
		
		//Step1.1 Get user's schedule id from body 
		String schedulId = bodyJson.get(SCHEDULE_ID);
		long schedId = 0;
		if(schedulId == null || schedulId.equals("")) {
			logger.error("BAD Request : Schedule id is madatory!");
			throw new ScheduleIdAbsentException("BAD Request : Schedule id is madatory!");
		}else {
			try {
				schedId = Long.parseLong(schedulId);
			} catch (NumberFormatException e) {
				throw new ScheduleIdAbsentException("Schedule id is madatory!");
			}
			
		}
		
		//Step 2. Create new and unique build id into the database (Before create new table OA_Build (id, schedular_email, schedule id, schedule start time, schedule end time)
		
		
		//Step 3. Find ready to build Env 
		// 3.1 Create env_build table that will hold the environment information Env_Id, Env_Name, Folder_Path, Buil_File_Name
		// 3.2 Create join table Build_Environment - many to many relation between build and environment with sysdate current date
		// 3.3 Find which environment is free (if nothing any free then what to do???)
		
		//Step 5 - Gel all labs 
		List<Lab> allFreeLabs = buildService.getAllLabs();
		
		// Step 6 - Find available labs from current time to SCH_END_TIME
		Timestamp currTime = new Timestamp(System.currentTimeMillis());
		Timestamp schStartTime = DateUtil.convertToUTCTime(bodyJson.get(SCH_START_TIME));
		Timestamp schEndTime = DateUtil.convertToUTCTime(bodyJson.get(SCH_END_TIME));
		
		List<Schedule> schedule = scheduleService.getScheduleBetween(currTime, schEndTime);
		
		List<Lab> labsInSchedule = schedule.stream().map(s -> new Lab(s.getLabId())).collect(Collectors.toList());
		
		// Step 7 - remove busy labs from free labs
		allFreeLabs.removeAll(labsInSchedule);
		
		if(allFreeLabs.isEmpty()) {
			throw new LabNotAvailableException("No Lab available exception");
		}
		
		// Step 8 - create Schedule on free lab.
		Schedule sch = new Schedule();
		sch.setLabId(allFreeLabs.get(0).getId());
		sch.setStartTime(currTime);
		sch.setEndTime(schEndTime);
		
		scheduleService.save(sch);
		
		OABuild oaBuild = new OABuild();
		oaBuild.setSchedularEmail(schedularEmail);
		oaBuild.setScheduleStartTime(schStartTime);
		oaBuild.setScheduleEndTime(schEndTime);
		oaBuild.setScheduleId(schedId);
		long oaBuildId = buildService.createBuild(oaBuild);
		
		sch.setOaBuilId(oaBuildId);
		scheduleService.save(sch);
		
		
		//Step 5. Process Env_Id -> call build.sh for this environment
		//EnvBuild envBuildDetails = oaBuild.getBuildDetails() 
		
		/*
		 * Process process ; try { process = Runtime.getRuntime()
		 * .exec(String.format("sh -c ls %s", homeDirectory));
		 * 
		 * StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(),
		 * System.out::println);
		 * Executors.newSingleThreadExecutor().submit(streamGobbler); int exitCode =
		 * process.waitFor(); assert exitCode == 0;
		 * 
		 * } catch (IOException |InterruptedException e) {
		 * logger.error("Process can not be instantiated IO Exception ", e );
		 * buildService.deleteOABuild(oaBuildId); throw new
		 * ProcessFailException("Build Process failed!"); }
		 */
				
		//Step 6. new build id to build controller
		
		return oaBuildId;
	}

	
	public void deleteBuild(String buildId) {
		logger.info("Inside deleteBuild of orchestationService buildId - {}", buildId);
		
		Long buildIdVal = 0l;
		
		try {
			buildIdVal = Long.valueOf(buildId);
		}catch(NumberFormatException e) {
			
			// for this may be having different flow
		}
		scheduleService.tearDownSchedule(buildIdVal);
		
	}
	
	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private Consumer<String> consumer;

	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	    }

	    @Override
	    public void run() {
	        new BufferedReader(new InputStreamReader(inputStream)).lines()
	          .forEach(consumer);
	    }
	}
}


