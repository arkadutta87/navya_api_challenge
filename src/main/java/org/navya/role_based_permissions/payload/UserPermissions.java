package org.navya.role_based_permissions.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by arkadutta on 27/01/17.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPermissions {

    private int code;
    private String message;

    private Set<String> permissions;

    public UserPermissions(int code, String message){
        this.code = code;
        this.message = message;

        this.permissions = new HashSet<>();
    }

    public void addPermission(String permission){
        this.permissions.add(permission);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return "UserPermissions{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
