package com.avaya.oa.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="oa_build")
public class OABuild {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name ="schedular_email")
        private String schedularEmail;

        @Column(name ="schedule_id")
        private long scheduleId;

        @Column(name ="schedule_start_time")
        private Timestamp scheduleStartTime;

        @Column(name ="schedule_end_time")
        private Timestamp scheduleEndTime;

        @OneToMany(mappedBy = "oaBuild", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
        private List<EnvBuild> envBuild;

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public String getSchedularEmail() {
                return schedularEmail;
        }

        public void setSchedularEmail(String schedularEmail) {
                this.schedularEmail = schedularEmail;
        }

        public long getScheduleId() {
                return scheduleId;
        }

        public void setScheduleId(long scheduleId) {
                this.scheduleId = scheduleId;
        }

        public Timestamp getScheduleStartTime() {
                return scheduleStartTime;
        }

        public void setScheduleStartTime(Timestamp scheduleStartTime) {
                this.scheduleStartTime = scheduleStartTime;
        }

        public Timestamp getScheduleEndTime() {
                return scheduleEndTime;
        }

        public void setScheduleEndTime(Timestamp scheduleEndTime) {
                this.scheduleEndTime = scheduleEndTime;
        }

        public List<EnvBuild> getEnvBuild() {
                return envBuild;
        }

        public void setEnvBuild(List<EnvBuild> envBuild) {
                this.envBuild = envBuild;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + (int) (id ^ (id >>> 32));
                result = prime * result + (int) (scheduleId ^ (scheduleId >>> 32));
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (!(obj instanceof OABuild))
                        return false;
                OABuild other = (OABuild) obj;
                if (id != other.id)
                        return false;
                if (scheduleId != other.scheduleId)
                        return false;
                return true;
        }
}