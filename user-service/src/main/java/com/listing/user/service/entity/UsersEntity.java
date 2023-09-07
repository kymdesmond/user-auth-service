package com.listing.user.service.entity;

import lombok.Getter;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(name = "users", schema = "auth")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "date_created")
    private Timestamp dateCreated;
    @Basic
    @Column(name = "date_updated")
    private Timestamp dateUpdated;
    @Basic
    @Column(name = "created_by")
    private Integer createdBy;
    @OneToMany(mappedBy = "usersEntity")
    private List<RoleMapEntity> roleMapEntityList;
    @ManyToOne
    @JoinColumn(name = "entity_id")
    private EntitiesEntity entitiesEntity;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setEntitiesEntity(EntitiesEntity entitiesEntity) {
        this.entitiesEntity = entitiesEntity;
    }

    public void setRoleMapEntityList(List<RoleMapEntity> roleMapEntityList) {
        this.roleMapEntityList = roleMapEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!username.equals(that.username)) return false;
        if (!phone.equals(that.phone)) return false;
        if (!email.equals(that.email)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (!Objects.equals(dateCreated, that.dateCreated)) return false;
        if (!Objects.equals(dateUpdated, that.dateUpdated)) return false;
        if (!Objects.equals(createdBy, that.createdBy)) return false;
        if (!Objects.equals(roleMapEntityList, that.roleMapEntityList))
            return false;
        return entitiesEntity.equals(that.entitiesEntity);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + username.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateUpdated != null ? dateUpdated.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (roleMapEntityList != null ? roleMapEntityList.hashCode() : 0);
        result = 31 * result + entitiesEntity.hashCode();
        return result;
    }
}
