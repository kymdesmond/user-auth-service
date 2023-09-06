package com.listing.user.service.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@Table(name = "role_map", schema = "auth")
public class RoleMapEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RolesEntity rolesEntity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity usersEntity;

    public void setId(int id) {
        this.id = id;
    }

    public void setRolesEntity(RolesEntity rolesEntity) {
        this.rolesEntity = rolesEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleMapEntity that = (RoleMapEntity) o;

        if (id != that.id) return false;
        if (!rolesEntity.equals(that.rolesEntity)) return false;
        return usersEntity.equals(that.usersEntity);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + rolesEntity.hashCode();
        result = 31 * result + usersEntity.hashCode();
        return result;
    }
}
