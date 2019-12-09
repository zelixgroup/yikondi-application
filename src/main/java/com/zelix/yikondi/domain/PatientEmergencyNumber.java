package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A PatientEmergencyNumber.
 */
@Entity
@Table(name = "patient_emergency_number")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "patientemergencynumber")
public class PatientEmergencyNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "emergency_number")
    private String emergencyNumber;

    @Column(name = "full_name")
    private String fullName;

    @ManyToOne
    @JsonIgnoreProperties("patientEmergencyNumbers")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public PatientEmergencyNumber emergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
        return this;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public PatientEmergencyNumber fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientEmergencyNumber patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientEmergencyNumber)) {
            return false;
        }
        return id != null && id.equals(((PatientEmergencyNumber) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PatientEmergencyNumber{" +
            "id=" + getId() +
            ", emergencyNumber='" + getEmergencyNumber() + "'" +
            ", fullName='" + getFullName() + "'" +
            "}";
    }
}
