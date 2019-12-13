package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A MedicalRecordAuthorization.
 */
@Entity
@Table(name = "medical_record_authorization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicalrecordauthorization")
public class MedicalRecordAuthorization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "authorization_date_time")
    private ZonedDateTime authorizationDateTime;

    @Column(name = "authorization_start_date_time")
    private ZonedDateTime authorizationStartDateTime;

    @Column(name = "authorization_end_date_time")
    private ZonedDateTime authorizationEndDateTime;

    @Column(name = "observations")
    private String observations;

    @ManyToOne
    @JsonIgnoreProperties("medicalRecordAuthorizations")
    private Patient recordOwner;

    @ManyToOne
    @JsonIgnoreProperties("medicalRecordAuthorizations")
    private Patient authorizationGrantee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getAuthorizationDateTime() {
        return authorizationDateTime;
    }

    public MedicalRecordAuthorization authorizationDateTime(ZonedDateTime authorizationDateTime) {
        this.authorizationDateTime = authorizationDateTime;
        return this;
    }

    public void setAuthorizationDateTime(ZonedDateTime authorizationDateTime) {
        this.authorizationDateTime = authorizationDateTime;
    }

    public ZonedDateTime getAuthorizationStartDateTime() {
        return authorizationStartDateTime;
    }

    public MedicalRecordAuthorization authorizationStartDateTime(ZonedDateTime authorizationStartDateTime) {
        this.authorizationStartDateTime = authorizationStartDateTime;
        return this;
    }

    public void setAuthorizationStartDateTime(ZonedDateTime authorizationStartDateTime) {
        this.authorizationStartDateTime = authorizationStartDateTime;
    }

    public ZonedDateTime getAuthorizationEndDateTime() {
        return authorizationEndDateTime;
    }

    public MedicalRecordAuthorization authorizationEndDateTime(ZonedDateTime authorizationEndDateTime) {
        this.authorizationEndDateTime = authorizationEndDateTime;
        return this;
    }

    public void setAuthorizationEndDateTime(ZonedDateTime authorizationEndDateTime) {
        this.authorizationEndDateTime = authorizationEndDateTime;
    }

    public String getObservations() {
        return observations;
    }

    public MedicalRecordAuthorization observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Patient getRecordOwner() {
        return recordOwner;
    }

    public MedicalRecordAuthorization recordOwner(Patient patient) {
        this.recordOwner = patient;
        return this;
    }

    public void setRecordOwner(Patient patient) {
        this.recordOwner = patient;
    }

    public Patient getAuthorizationGrantee() {
        return authorizationGrantee;
    }

    public MedicalRecordAuthorization authorizationGrantee(Patient patient) {
        this.authorizationGrantee = patient;
        return this;
    }

    public void setAuthorizationGrantee(Patient patient) {
        this.authorizationGrantee = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalRecordAuthorization)) {
            return false;
        }
        return id != null && id.equals(((MedicalRecordAuthorization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicalRecordAuthorization{" +
            "id=" + getId() +
            ", authorizationDateTime='" + getAuthorizationDateTime() + "'" +
            ", authorizationStartDateTime='" + getAuthorizationStartDateTime() + "'" +
            ", authorizationEndDateTime='" + getAuthorizationEndDateTime() + "'" +
            ", observations='" + getObservations() + "'" +
            "}";
    }
}
