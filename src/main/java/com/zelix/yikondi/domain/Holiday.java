package com.zelix.yikondi.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Holiday.
 */
@Entity
@Table(name = "holiday")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "holiday")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "corresponding_date")
    private LocalDate correspondingDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Holiday label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDate getCorrespondingDate() {
        return correspondingDate;
    }

    public Holiday correspondingDate(LocalDate correspondingDate) {
        this.correspondingDate = correspondingDate;
        return this;
    }

    public void setCorrespondingDate(LocalDate correspondingDate) {
        this.correspondingDate = correspondingDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Holiday)) {
            return false;
        }
        return id != null && id.equals(((Holiday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Holiday{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", correspondingDate='" + getCorrespondingDate() + "'" +
            "}";
    }
}
