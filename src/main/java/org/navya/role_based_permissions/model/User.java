package org.navya.role_based_permissions.model;

import java.util.HashSet;
import java.util.Set;
/**
 * Created by arkadutta on 27/01/17.
 */
public class User {

    private final String id;
    private final Set<Role> roles;

    public User(final String id){
        this.id = id;
        this.roles = new HashSet<Role>();

        //roles.remove()
    }

    public void addRole(Role obj){
        this.roles.add(obj);
    }

    public Set<Role> getRolesSet(){
        return this.roles;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", roles=" + roles +
                '}';
    }
}
