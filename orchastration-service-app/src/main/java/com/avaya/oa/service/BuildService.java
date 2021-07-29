package com.avaya.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.oa.dao.ILabDao;
import com.avaya.oa.dao.IOABuildDao;
import com.avaya.oa.entity.Lab;
import com.avaya.oa.entity.OABuild;

@Service
public class BuildService {

        @Autowired
        IOABuildDao oaBuildDao;

        @Autowired
        ILabDao labDao;

        public long createBuild(OABuild oaBuild) {
                oaBuildDao.save(oaBuild);
                return oaBuild.getId();
        }

        public void deleteOABuild(long oaBuildId) {
                oaBuildDao.deleteById(oaBuildId);
        }

        public List<Lab> getAllLabs(){
                return labDao.findAll();
        }

}
