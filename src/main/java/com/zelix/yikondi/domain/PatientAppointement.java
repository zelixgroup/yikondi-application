package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PatientAppointement.
 */
@Entity
@Table(name = "patient_appointement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "patientappointement")
public class PatientAppointement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "appointement_date_time")
    private ZonedDateTime appointementDateTime;

    @Column(name = "appointement_making_date_time")
    private ZonedDateTime appointementMakingDateTime;

    @ManyToOne
    @JsonIgnoreProperties("patientAppointements")
    private Patient booker;

    @ManyToOne
    @JsonIgnoreProperties("patientAppointements")
    private Patient consultationPatient;

    @ManyToOne
    @JsonIgnoreProperties("patientAppointements")
    private HealthCentreDoctor healthCentreDoctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getAppointementDateTime() {
        return appointementDateTime;
    }

    public PatientAppointement appointementDateTime(ZonedDateTime appointementDateTime) {
        this.appointementDateTime = appointementDateTime;
        return this;
    }

    public void setAppointementDateTime(ZonedDateTime appointementDateTime) {
        this.appointementDateTime = appointementDateTime;
    }

    public ZonedDateTime getAppointementMakingDateTime() {
        return appointementMakingDateTime;
    }

    public PatientAppointement appointementMakingDateTime(ZonedDateTime appointementMakingDateTime) {
        this.appointementMakingDateTime = appointementMakingDateTime;
        return this;
    }

    public void setAppointementMakingDateTime(ZonedDateTime appointementMakingDateTime) {
        this.appointementMakingDateTime = appointementMakingDateTime;
    }

    public Patient getBooker() {
        return booker;
    }

    public PatientAppointement booker(Patient patient) {
        this.booker = patient;
        return this;
    }

    public void setBooker(Patient patient) {
        this.booker = patient;
    }

    public Patient getConsultationPatient() {
        return consultationPatient;
    }

    public PatientAppointement consultationPatient(Patient patient) {
        this.consultationPatient = patient;
        return this;
    }

    public void setConsultationPatient(Patient patient) {
        this.consultationPatient = patient;
    }

    public HealthCentreDoctor getHealthCentreDoctor() {
        return healthCentreDoctor;
    }

    public PatientAppointement healthCentreDoctor(HealthCentreDoctor healthCentreDoctor) {
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
        if (!(o instanceof PatientAppointement)) {
            return false;
        }
        return id != null && id.equals(((PatientAppointement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PatientAppointement{" +
            "id=" + getId() +
            ", appointementDateTime='" + getAppointementDateTime() + "'" +
            ", appointementMakingDateTime='" + getAppointementMakingDateTime() + "'" +
            "}";
    }
}
