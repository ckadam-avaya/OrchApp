package com.avaya.oa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.avaya.oa.entity.EnvBuild;

public interface IEnvBuildDao extends JpaRepository<EnvBuild, Long>, JpaSpecificationExecutor<EnvBuild> {

}