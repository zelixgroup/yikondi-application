package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A HealthCentreDoctor.
 */
@Entity
@Table(name = "health_centre_doctor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "healthcentredoctor")
public class HealthCentreDoctor implements Serializable {

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

    @Column(name = "consulting_fees", precision = 21, scale = 2)
    private BigDecimal consultingFees;

    @ManyToOne
    @JsonIgnoreProperties("healthCentreDoctors")
    private HealthCentre healthCentre;

    @ManyToOne
    @JsonIgnoreProperties("healthCentreDoctors")
    private Doctor doctor;

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

    public HealthCentreDoctor startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public HealthCentreDoctor endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getConsultingFees() {
        return consultingFees;
    }

    public HealthCentreDoctor consultingFees(BigDecimal consultingFees) {
        this.consultingFees = consultingFees;
        return this;
    }

    public void setConsultingFees(BigDecimal consultingFees) {
        this.consultingFees = consultingFees;
    }

    public HealthCentre getHealthCentre() {
        return healthCentre;
    }

    public HealthCentreDoctor healthCentre(HealthCentre healthCentre) {
        this.healthCentre = healthCentre;
        return this;
    }

    public void setHealthCentre(HealthCentre healthCentre) {
        this.healthCentre = healthCentre;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public HealthCentreDoctor doctor(Doctor doctor) {
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
        if (!(o instanceof HealthCentreDoctor)) {
            return false;
        }
        return id != null && id.equals(((HealthCentreDoctor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HealthCentreDoctor{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", consultingFees=" + getConsultingFees() +
            "}";
    }
}
