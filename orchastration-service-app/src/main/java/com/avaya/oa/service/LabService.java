package com.avaya.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.oa.dao.ILabDao;
import com.avaya.oa.entity.Lab;


@Service
public class LabService {
	
	@Autowired
	ILabDao labDao;
	
	public List<Lab> getAllLabs() {		
		return labDao.findAll();
	}

}