package com.listing.user.service.entity;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Entity
@Table(name = "entities", schema = "auth")
public class EntitiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne()
    @JoinColumn(name = "entity_type_id")
    private EntityTypesEntity entityType;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "msisdn")
    private String msisdn;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "website")
    private String website;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "date_created")
    private Timestamp dateCreated;
    @Basic
    @Column(name = "created_by")
    private Integer createdBy;
    @Basic
    @Column(name = "status")
    private Integer status;

    public void setId(int id) {
        this.id = id;
    }

    public void setEntityType(EntityTypesEntity entityType) {
        this.entityType = entityType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntitiesEntity that = (EntitiesEntity) o;

        if (id != that.id) return false;
        if (!entityType.equals(that.entityType)) return false;
        if (!name.equals(that.name)) return false;
        if (!Objects.equals(msisdn, that.msisdn)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(website, that.website)) return false;
        if (!Objects.equals(address, that.address)) return false;
        if (!Objects.equals(dateCreated, that.dateCreated)) return false;
        if (!Objects.equals(createdBy, that.createdBy)) return false;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + entityType.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (msisdn != null ? msisdn.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
