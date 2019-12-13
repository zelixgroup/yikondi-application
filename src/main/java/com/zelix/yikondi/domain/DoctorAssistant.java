package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A DoctorAssistant.
 */
@Entity
@Table(name = "doctor_assistant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "doctorassistant")
public class DoctorAssistant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "can_prescribe")
    private Boolean canPrescribe;

    @ManyToOne
    @JsonIgnoreProperties("doctorAssistants")
    private HealthCentreDoctor healthCentreDoctor;

    @ManyToOne
    @JsonIgnoreProperties("doctorAssistants")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCanPrescribe() {
        return canPrescribe;
    }

    public DoctorAssistant canPrescribe(Boolean canPrescribe) {
        this.canPrescribe = canPrescribe;
        return this;
    }

    public void setCanPrescribe(Boolean canPrescribe) {
        this.canPrescribe = canPrescribe;
    }

    public HealthCentreDoctor getHealthCentreDoctor() {
        return healthCentreDoctor;
    }

    public DoctorAssistant healthCentreDoctor(HealthCentreDoctor healthCentreDoctor) {
        this.healthCentreDoctor = healthCentreDoctor;
        return this;
    }

    public void setHealthCentreDoctor(HealthCentreDoctor healthCentreDoctor) {
        this.healthCentreDoctor = healthCentreDoctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public DoctorAssistant patient(Patient patient) {
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
        if (!(o instanceof DoctorAssistant)) {
            return false;
        }
        return id != null && id.equals(((DoctorAssistant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DoctorAssistant{" +
            "id=" + getId() +
            ", canPrescribe='" + isCanPrescribe() + "'" +
            "}";
    }
}
