package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PatientPathology.
 */
@Entity
@Table(name = "patient_pathology")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "patientpathology")
public class PatientPathology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "observation_date")
    private LocalDate observationDate;

    @Column(name = "observations")
    private String observations;

    @ManyToOne
    @JsonIgnoreProperties("patientPathologies")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("patientPathologies")
    private Pathology pathology;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getObservationDate() {
        return observationDate;
    }

    public PatientPathology observationDate(LocalDate observationDate) {
        this.observationDate = observationDate;
        return this;
    }

    public void setObservationDate(LocalDate observationDate) {
        this.observationDate = observationDate;
    }

    public String getObservations() {
        return observations;
    }

    public PatientPathology observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientPathology patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Pathology getPathology() {
        return pathology;
    }

    public PatientPathology pathology(Pathology pathology) {
        this.pathology = pathology;
        return this;
    }

    public void setPathology(Pathology pathology) {
        this.pathology = pathology;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientPathology)) {
            return false;
        }
        return id != null && id.equals(((PatientPathology) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PatientPathology{" +
            "id=" + getId() +
            ", observationDate='" + getObservationDate() + "'" +
            ", observations='" + getObservations() + "'" +
            "}";
    }
}
