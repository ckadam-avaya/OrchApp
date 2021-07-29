package com.avaya.oa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="env_build")
public class EnvBuild {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private String instance_build_id;

        @ManyToOne
        @JoinColumn(name = "oa_build_id")
        private OABuild oaBuild;

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public String getInstance_build_id() {
                return instance_build_id;
        }

        public void setInstance_build_id(String instance_build_id) {
                this.instance_build_id = instance_build_id;
        }

        public OABuild getOaBuild() {
                return oaBuild;
        }

        public void setOaBuild(OABuild oaBuild) {
                this.oaBuild = oaBuild;
        }

}
