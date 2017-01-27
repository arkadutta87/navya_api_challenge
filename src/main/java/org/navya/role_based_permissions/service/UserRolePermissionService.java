package org.navya.role_based_permissions.service;

import org.navya.role_based_permissions.data_container.DataContainer;

import java.util.List;
import java.util.Set;

/**
 * Created by arkadutta on 27/01/17.
 */
public interface UserRolePermissionService {

    public Set<String> getPermissionFromUserId(String userId) throws Exception;

    public boolean checkpermission(String userId, String permissionId) throws Exception;

    public boolean modifyRolePermission(String roleId, String[] permissionIds) throws Exception;

    public boolean deletePermission(String permissionId) throws Exception;


}
