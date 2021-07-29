package com.avaya.oa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.oa.exception.ScheduleIdAbsentException;
import com.avaya.oa.exception.UserEmailNotFoundException;
import com.avaya.oa.handler.BuildHandler;
import com.avaya.oa.model.TestAPIModel;

@CrossOrigin
@RestController
@RequestMapping(path = "/orchestationService")
public class BuildController {
	
	private static final Logger logger = LoggerFactory.getLogger(BuildController.class);
	private static final String  TEST_CONN_STR = "testConnection";
	
	@Autowired
	BuildHandler buildHandler;
	
	@GetMapping(path="/builds", produces = "application/json")
	public ResponseEntity<String> getBuilds(HttpServletRequest servletRequest) {
		logger.info("Inside getBuilds of orchestationService " );
		String testConn = servletRequest.getParameter(TEST_CONN_STR);
		if(testConn != null && testConn.equalsIgnoreCase("true") ) {
			return new ResponseEntity<>("", HttpStatus.OK);
		}
		
		
		return new ResponseEntity<>("Connection is successful", HttpStatus.OK);
	}

	@GetMapping(path = "/actual/builds/{id}", produces = "application/json")
	public ResponseEntity<Object> getActaulMappedBuildById(@PathVariable("id") long id, HttpServletRequest servletRequest) {
		logger.info("Inside testparams of orchestationService " );
		String testConn = servletRequest.getParameter(TEST_CONN_STR);
		if(testConn != null && testConn.equalsIgnoreCase("true") ) {
			return new ResponseEntity<>("", HttpStatus.OK);
		}
		
		return new ResponseEntity<>(buildHandler.getActaulMappedBuildById(id), HttpStatus.OK);
	}

	@PostMapping(path = "/builds", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createBuild(@RequestBody @Valid TestAPIModel<String> testAPI, HttpServletRequest servletRequest) {
		logger.info("Inside createBuild of orchestationService " );
		String testConn = servletRequest.getParameter(TEST_CONN_STR);
		if(testConn != null && testConn.equalsIgnoreCase("true") ) {
			return new ResponseEntity<>("0", HttpStatus.OK);
		}
		
		Long createBuild = 0L;
		
		try {
			createBuild = buildHandler.createBuild(testAPI);
		} catch (UserEmailNotFoundException | ScheduleIdAbsentException e ) {
			logger.error("Exception while create build - UserEmailNotFoundException | ScheduleIdAbsentException ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); 
		} catch (Exception e ) {
			logger.error("Exception while create build - ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); 
		} 
		
		if(createBuild <= 0) {
			return new ResponseEntity<>("Exception in build", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(createBuild+"", HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/builds/{buildId}")
	public ResponseEntity<String> deleteBuild(@PathVariable String buildId, HttpServletRequest servletRequest) {
		logger.info("Inside deleteBuild of orchestationService buildId - {}", buildId );
		String testConn = servletRequest.getParameter(TEST_CONN_STR);
		if(testConn != null && testConn.equalsIgnoreCase("true") ) {
			return new ResponseEntity<>("", HttpStatus.OK);
		}
		
		buildHandler.deleteBuild(buildId);
		
		return new ResponseEntity<>("Build Deleted Successfully", HttpStatus.OK);
	}

	
}
