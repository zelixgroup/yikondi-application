package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PatientInsuranceCoverage.
 */
@Entity
@Table(name = "patient_insurance_coverage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "patientinsurancecoverage")
public class PatientInsuranceCoverage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "reference_number")
    private String referenceNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @ManyToOne
    @JsonIgnoreProperties("patientInsuranceCoverages")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("patientInsuranceCoverages")
    private Insurance insurance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PatientInsuranceCoverage startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public PatientInsuranceCoverage endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public PatientInsuranceCoverage referenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Address getAddress() {
        return address;
    }

    public PatientInsuranceCoverage address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Patient getPatient() {
        return patient;
    }

    public PatientInsuranceCoverage patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public PatientInsuranceCoverage insurance(Insurance insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientInsuranceCoverage)) {
            return false;
        }
        return id != null && id.equals(((PatientInsuranceCoverage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PatientInsuranceCoverage{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            "}";
    }
}
