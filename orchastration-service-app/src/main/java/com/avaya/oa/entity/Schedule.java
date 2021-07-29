package com.avaya.oa.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="poc_schedule")
public class Schedule {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "lab_id")
        private long labId;

        @Column(nullable = false)
        private Timestamp startTime;

        @Column
        private Timestamp endTime;

        @Column(name = "oa_build_id", nullable = true)
        private Long oaBuilId;

        @Column
        private boolean toreDown;

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public long getLabId() {
                return labId;
        }

        public void setLabId(long labId) {
                this.labId = labId;
        }

        public Timestamp getStartTime() {
                return startTime;
        }

        public void setStartTime(Timestamp startTime) {
                this.startTime = startTime;
        }

        public Timestamp getEndTime() {
                return endTime;
        }

        public void setEndTime(Timestamp endTime) {
                this.endTime = endTime;
        }

        public Long getOaBuilId() {
                return oaBuilId;
        }

        public void setOaBuilId(Long oaBuilId) {
                this.oaBuilId = oaBuilId;
        }

        public boolean isToreDown() {
                return toreDown;
        }

        public void setToreDown(boolean toreDown) {
                this.toreDown = toreDown;
        }

}
