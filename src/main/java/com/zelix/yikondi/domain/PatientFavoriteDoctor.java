package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PatientFavoriteDoctor.
 */
@Entity
@Table(name = "patient_favorite_doctor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "patientfavoritedoctor")
public class PatientFavoriteDoctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "activation_date")
    private ZonedDateTime activationDate;

    @ManyToOne
    @JsonIgnoreProperties("patientFavoriteDoctors")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("patientFavoriteDoctors")
    private Doctor doctor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getActivationDate() {
        return activationDate;
    }

    public PatientFavoriteDoctor activationDate(ZonedDateTime activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    public void setActivationDate(ZonedDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientFavoriteDoctor patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public PatientFavoriteDoctor doctor(Doctor doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientFavoriteDoctor)) {
            return false;
        }
        return id != null && id.equals(((PatientFavoriteDoctor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PatientFavoriteDoctor{" +
            "id=" + getId() +
            ", activationDate='" + getActivationDate() + "'" +
            "}";
    }
}
