package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MedicalPrescriptionDrug.
 */
@Entity
@Table(name = "medical_prescription_drug")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicalprescriptiondrug")
public class MedicalPrescriptionDrug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "dosage")
    private String dosage;

    @ManyToOne
    @JsonIgnoreProperties("medicalPrescriptionDrugs")
    private MedicalPrescription medicalPrescription;

    @ManyToOne
    @JsonIgnoreProperties("medicalPrescriptionDrugs")
    private Drug drug;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDosage() {
        return dosage;
    }

    public MedicalPrescriptionDrug dosage(String dosage) {
        this.dosage = dosage;
        return this;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public MedicalPrescription getMedicalPrescription() {
        return medicalPrescription;
    }

    public MedicalPrescriptionDrug medicalPrescription(MedicalPrescription medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
        return this;
    }

    public void setMedicalPrescription(MedicalPrescription medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
    }

    public Drug getDrug() {
        return drug;
    }

    public MedicalPrescriptionDrug drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalPrescriptionDrug)) {
            return false;
        }
        return id != null && id.equals(((MedicalPrescriptionDrug) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicalPrescriptionDrug{" +
            "id=" + getId() +
            ", dosage='" + getDosage() + "'" +
            "}";
    }
}
