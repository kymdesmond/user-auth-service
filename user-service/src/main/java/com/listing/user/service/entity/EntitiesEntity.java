package com.listing.user.service.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "entities", schema = "auth", catalog = "ipl")
public class EntitiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "entity_type_id")
    private Integer entityTypeId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Integer entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntitiesEntity that = (EntitiesEntity) o;
        return id == that.id && entityTypeId.equals(that.entityTypeId) && name.equals(that.name) && Objects.equals(msisdn, that.msisdn) && Objects.equals(email, that.email) && Objects.equals(website, that.website) && Objects.equals(address, that.address) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(createdBy, that.createdBy) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityTypeId, name, msisdn, email, website, address, dateCreated, createdBy, status);
    }
}
