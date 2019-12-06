package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.zelix.yikondi.domain.enumeration.DayOfTheWeek;

/**
 * A DoctorWorkingSlot.
 */
@Entity
@Table(name = "doctor_working_slot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "doctorworkingslot")
public class DoctorWorkingSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_the_week")
    private DayOfTheWeek dayOfTheWeek;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("doctorWorkingSlots")
    private DoctorSchedule doctorSchedule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public DoctorWorkingSlot dayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
        return this;
    }

    public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public DoctorWorkingSlot startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public DoctorWorkingSlot endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public DoctorWorkingSlot description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DoctorSchedule getDoctorSchedule() {
        return doctorSchedule;
    }

    public DoctorWorkingSlot doctorSchedule(DoctorSchedule doctorSchedule) {
        this.doctorSchedule = doctorSchedule;
        return this;
    }

    public void setDoctorSchedule(DoctorSchedule doctorSchedule) {
        this.doctorSchedule = doctorSchedule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoctorWorkingSlot)) {
            return false;
        }
        return id != null && id.equals(((DoctorWorkingSlot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DoctorWorkingSlot{" +
            "id=" + getId() +
            ", dayOfTheWeek='" + getDayOfTheWeek() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
