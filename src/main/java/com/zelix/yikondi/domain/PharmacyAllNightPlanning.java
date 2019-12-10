package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PharmacyAllNightPlanning.
 */
@Entity
@Table(name = "pharmacy_all_night_planning")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "pharmacyallnightplanning")
public class PharmacyAllNightPlanning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "planned_start_date")
    private LocalDate plannedStartDate;

    @Column(name = "planned_end_date")
    private LocalDate plannedEndDate;

    @ManyToOne
    @JsonIgnoreProperties("pharmacyAllNightPlannings")
    private Pharmacy pharmacy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPlannedStartDate() {
        return plannedStartDate;
    }

    public PharmacyAllNightPlanning plannedStartDate(LocalDate plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
        return this;
    }

    public void setPlannedStartDate(LocalDate plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public PharmacyAllNightPlanning plannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
        return this;
    }

    public void setPlannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public PharmacyAllNightPlanning pharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
        return this;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PharmacyAllNightPlanning)) {
            return false;
        }
        return id != null && id.equals(((PharmacyAllNightPlanning) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PharmacyAllNightPlanning{" +
            "id=" + getId() +
            ", plannedStartDate='" + getPlannedStartDate() + "'" +
            ", plannedEndDate='" + getPlannedEndDate() + "'" +
            "}";
    }
}
