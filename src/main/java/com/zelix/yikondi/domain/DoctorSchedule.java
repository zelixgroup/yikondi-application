package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A DoctorSchedule.
 */
@Entity
@Table(name = "doctor_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "doctorschedule")
public class DoctorSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "schedule_start_date")
    private ZonedDateTime scheduleStartDate;

    @Column(name = "schedule_end_date")
    private ZonedDateTime scheduleEndDate;

    @ManyToOne
    @JsonIgnoreProperties("doctorSchedules")
    private HealthCentreDoctor healthCentreDoctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getScheduleStartDate() {
        return scheduleStartDate;
    }

    public DoctorSchedule scheduleStartDate(ZonedDateTime scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
        return this;
    }

    public void setScheduleStartDate(ZonedDateTime scheduleStartDate) {
        this.scheduleStartDate = scheduleStartDate;
    }

    public ZonedDateTime getScheduleEndDate() {
        return scheduleEndDate;
    }

    public DoctorSchedule scheduleEndDate(ZonedDateTime scheduleEndDate) {
        this.scheduleEndDate = scheduleEndDate;
        return this;
    }

    public void setScheduleEndDate(ZonedDateTime scheduleEndDate) {
        this.scheduleEndDate = scheduleEndDate;
    }

    public HealthCentreDoctor getHealthCentreDoctor() {
        return healthCentreDoctor;
    }

    public DoctorSchedule healthCentreDoctor(HealthCentreDoctor healthCentreDoctor) {
        this.healthCentreDoctor = healthCentreDoctor;
        return this;
    }

    public void setHealthCentreDoctor(HealthCentreDoctor healthCentreDoctor) {
        this.healthCentreDoctor = healthCentreDoctor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorSchedule)) {
            return false;
        }
        return id != null && id.equals(((DoctorSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DoctorSchedule{" +
            "id=" + getId() +
            ", scheduleStartDate='" + getScheduleStartDate() + "'" +
            ", scheduleEndDate='" + getScheduleEndDate() + "'" +
            "}";
    }
}
