package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A MedicalPrescription.
 */
@Entity
@Table(name = "medical_prescription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicalprescription")
public class MedicalPrescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "prescription_date_time")
    private ZonedDateTime prescriptionDateTime;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observations")
    private String observations;

    @ManyToOne
    @JsonIgnoreProperties("medicalPrescriptions")
    private Doctor doctor;

    @ManyToOne
    @JsonIgnoreProperties("medicalPrescriptions")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("medicalPrescriptions")
    private PatientAppointement appointement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPrescriptionDateTime() {
        return prescriptionDateTime;
    }

    public MedicalPrescription prescriptionDateTime(ZonedDateTime prescriptionDateTime) {
        this.prescriptionDateTime = prescriptionDateTime;
        return this;
    }

    public void setPrescriptionDateTime(ZonedDateTime prescriptionDateTime) {
        this.prescriptionDateTime = prescriptionDateTime;
    }

    public String getObservations() {
        return observations;
    }

    public MedicalPrescription observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public MedicalPrescription doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public MedicalPrescription patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PatientAppointement getAppointement() {
        return appointement;
    }

    public MedicalPrescription appointement(PatientAppointement patientAppointement) {
        this.appointement = patientAppointement;
        return this;
    }

    public void setAppointement(PatientAppointement patientAppointement) {
        this.appointement = patientAppointement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalPrescription)) {
            return false;
        }
        return id != null && id.equals(((MedicalPrescription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicalPrescription{" +
            "id=" + getId() +
            ", prescriptionDateTime='" + getPrescriptionDateTime() + "'" +
            ", observations='" + getObservations() + "'" +
            "}";
    }
}
