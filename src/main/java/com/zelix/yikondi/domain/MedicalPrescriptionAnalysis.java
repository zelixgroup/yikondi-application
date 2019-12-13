package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MedicalPrescriptionAnalysis.
 */
@Entity
@Table(name = "medical_prescription_analysis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "medicalprescriptionanalysis")
public class MedicalPrescriptionAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("medicalPrescriptionAnalyses")
    private MedicalPrescription medicalPrescription;

    @ManyToOne
    @JsonIgnoreProperties("medicalPrescriptionAnalyses")
    private Analysis analysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicalPrescription getMedicalPrescription() {
        return medicalPrescription;
    }

    public MedicalPrescriptionAnalysis medicalPrescription(MedicalPrescription medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
        return this;
    }

    public void setMedicalPrescription(MedicalPrescription medicalPrescription) {
        this.medicalPrescription = medicalPrescription;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public MedicalPrescriptionAnalysis analysis(Analysis analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalPrescriptionAnalysis)) {
            return false;
        }
        return id != null && id.equals(((MedicalPrescriptionAnalysis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicalPrescriptionAnalysis{" +
            "id=" + getId() +
            "}";
    }
}
