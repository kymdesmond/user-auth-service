package com.listing.user.service.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "permission_map", schema = "auth")
public class PermissionMapEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RolesEntity rolesEntity;
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private PermissionsEntity permissionsEntity;

    public void setId(int id) {
        this.id = id;
    }

    public void setRolesEntity(RolesEntity rolesEntity) {
        this.rolesEntity = rolesEntity;
    }

    public void setPermissionsEntity(PermissionsEntity permissionsEntity) {
        this.permissionsEntity = permissionsEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionMapEntity that = (PermissionMapEntity) o;

        if (id != that.id) return false;
        if (!rolesEntity.equals(that.rolesEntity)) return false;
        return permissionsEntity.equals(that.permissionsEntity);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + rolesEntity.hashCode();
        result = 31 * result + permissionsEntity.hashCode();
        return result;
    }
}
