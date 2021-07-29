package com.avaya.oa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.avaya.oa.entity.OABuild;

public interface IOABuildDao extends JpaRepository<OABuild, Long>, JpaSpecificationExecutor<OABuild> {

}