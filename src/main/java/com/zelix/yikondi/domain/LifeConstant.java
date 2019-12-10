package com.zelix.yikondi.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.zelix.yikondi.domain.enumeration.LifeConstantName;

import com.zelix.yikondi.domain.enumeration.LifeConstantUnit;

/**
 * A LifeConstant.
 */
@Entity
@Table(name = "life_constant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lifeconstant")
public class LifeConstant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "life_constant_name")
    private LifeConstantName lifeConstantName;

    @Enumerated(EnumType.STRING)
    @Column(name = "life_constant_unit")
    private LifeConstantUnit lifeConstantUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LifeConstantName getLifeConstantName() {
        return lifeConstantName;
    }

    public LifeConstant lifeConstantName(LifeConstantName lifeConstantName) {
        this.lifeConstantName = lifeConstantName;
        return this;
    }

    public void setLifeConstantName(LifeConstantName lifeConstantName) {
        this.lifeConstantName = lifeConstantName;
    }

    public LifeConstantUnit getLifeConstantUnit() {
        return lifeConstantUnit;
    }

    public LifeConstant lifeConstantUnit(LifeConstantUnit lifeConstantUnit) {
        this.lifeConstantUnit = lifeConstantUnit;
        return this;
    }

    public void setLifeConstantUnit(LifeConstantUnit lifeConstantUnit) {
        this.lifeConstantUnit = lifeConstantUnit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LifeConstant)) {
            return false;
        }
        return id != null && id.equals(((LifeConstant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LifeConstant{" +
            "id=" + getId() +
            ", lifeConstantName='" + getLifeConstantName() + "'" +
            ", lifeConstantUnit='" + getLifeConstantUnit() + "'" +
            "}";
    }
}
