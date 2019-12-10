package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PatientLifeConstant.
 */
@Entity
@Table(name = "patient_life_constant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "patientlifeconstant")
public class PatientLifeConstant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "measurement_datetime")
    private ZonedDateTime measurementDatetime;

    @Column(name = "measured_value")
    private String measuredValue;

    @ManyToOne
    @JsonIgnoreProperties("patientLifeConstants")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("patientLifeConstants")
    private LifeConstant lifeConstant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getMeasurementDatetime() {
        return measurementDatetime;
    }

    public PatientLifeConstant measurementDatetime(ZonedDateTime measurementDatetime) {
        this.measurementDatetime = measurementDatetime;
        return this;
    }

    public void setMeasurementDatetime(ZonedDateTime measurementDatetime) {
        this.measurementDatetime = measurementDatetime;
    }

    public String getMeasuredValue() {
        return measuredValue;
    }

    public PatientLifeConstant measuredValue(String measuredValue) {
        this.measuredValue = measuredValue;
        return this;
    }

    public void setMeasuredValue(String measuredValue) {
        this.measuredValue = measuredValue;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientLifeConstant patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LifeConstant getLifeConstant() {
        return lifeConstant;
    }

    public PatientLifeConstant lifeConstant(LifeConstant lifeConstant) {
        this.lifeConstant = lifeConstant;
        return this;
    }

    public void setLifeConstant(LifeConstant lifeConstant) {
        this.lifeConstant = lifeConstant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientLifeConstant)) {
            return false;
        }
        return id != null && id.equals(((PatientLifeConstant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PatientLifeConstant{" +
            "id=" + getId() +
            ", measurementDatetime='" + getMeasurementDatetime() + "'" +
            ", measuredValue='" + getMeasuredValue() + "'" +
            "}";
    }
}
