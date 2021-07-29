package com.avaya.oa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.avaya.oa.entity.Lab;

public interface ILabDao extends JpaRepository<Lab, Long> {

}
