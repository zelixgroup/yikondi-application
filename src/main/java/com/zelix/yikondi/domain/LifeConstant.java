package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

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

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("lifeConstants")
    private LifeConstantUnit lifeConstantUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LifeConstant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
            ", name='" + getName() + "'" +
            "}";
    }
}
