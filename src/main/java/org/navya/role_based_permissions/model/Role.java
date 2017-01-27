package org.navya.role_based_permissions.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by arkadutta on 27/01/17.
 */
public class Role {

    private final String id;
    private final Set<Permission> permissions;

    public Role(final String id){
        this.id = id;
        this.permissions = new HashSet<Permission>();
    }

    public void addPermission(Permission obj){
        this.permissions.add(obj);
    }

    public Set<Permission> getPermissionSet(){
        return this.permissions;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!id.equals(role.id)) return false;
        return permissions.equals(role.permissions);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + permissions.hashCode();
        return result;
    }
}
