package com.zelix.yikondi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A FamilyMember.
 */
@Entity
@Table(name = "family_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "familymember")
public class FamilyMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "observations")
    private String observations;

    @ManyToOne
    @JsonIgnoreProperties("familyMembers")
    private Patient owner;

    @ManyToOne
    @JsonIgnoreProperties("familyMembers")
    private Patient member;

    @ManyToOne
    @JsonIgnoreProperties("familyMembers")
    private FamilyRelationship relationship;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public FamilyMember observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Patient getOwner() {
        return owner;
    }

    public FamilyMember owner(Patient patient) {
        this.owner = patient;
        return this;
    }

    public void setOwner(Patient patient) {
        this.owner = patient;
    }

    public Patient getMember() {
        return member;
    }

    public FamilyMember member(Patient patient) {
        this.member = patient;
        return this;
    }

    public void setMember(Patient patient) {
        this.member = patient;
    }

    public FamilyRelationship getRelationship() {
        return relationship;
    }

    public FamilyMember relationship(FamilyRelationship familyRelationship) {
        this.relationship = familyRelationship;
        return this;
    }

    public void setRelationship(FamilyRelationship familyRelationship) {
        this.relationship = familyRelationship;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyMember)) {
            return false;
        }
        return id != null && id.equals(((FamilyMember) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
            "id=" + getId() +
            ", observations='" + getObservations() + "'" +
            "}";
    }
}
