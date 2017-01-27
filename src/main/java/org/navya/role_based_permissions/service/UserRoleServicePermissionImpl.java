package org.navya.role_based_permissions.service;

import org.navya.role_based_permissions.common.Lock;
import org.navya.role_based_permissions.data_container.DataContainer;
import org.navya.role_based_permissions.model.Permission;
import org.navya.role_based_permissions.model.Role;
import org.navya.role_based_permissions.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by arkadutta on 27/01/17.
 */
@Service("permissionService")
@Transactional
public class UserRoleServicePermissionImpl implements UserRolePermissionService {

    @Autowired
    DataContainer container;

    /*
    required for proper concurrency maintainance
     */
    @Autowired
    Lock lock;

    public UserRoleServicePermissionImpl() {

    }

    public UserRoleServicePermissionImpl(DataContainer container, Lock lock) {
        this.container = container;
        this.lock = lock;
    }

    public Set<String> getPermissionFromUserId(String userId) throws Exception {

        Set<String> perms = null;
        if (userId != null) {
            lock.getLock().readLock().lock();
            Map<String, User> map = container.getUsersMap();

            User aUsr = map.get(userId);
            if (aUsr != null) {
                Set<Role> roles = aUsr.getRolesSet();
                if (roles != null) {
                    perms = new HashSet<>();

                    for (Role aRl : roles) {
                        Set<Permission> permSet = aRl.getPermissionSet();
                        if (permSet != null) {
                            for (Permission aObj : permSet) {
                                perms.add(aObj.getName());
                            }
                        }
                    }
                }

            }

            lock.getLock().readLock().unlock();
        }

        return perms;
    }

    public boolean checkpermission(String userId, String permissionId) throws Exception {

        boolean flag = false;
        if (userId != null && permissionId != null && !userId.trim().isEmpty() && !permissionId.trim().isEmpty()) {
            lock.getLock().readLock().lock();
            Map<String, User> map = container.getUsersMap();

            User aUsr = map.get(userId);
            if (aUsr != null) {
                Set<Role> roles = aUsr.getRolesSet();
                if (roles != null) {
                    Set<String> perms = new HashSet<>();

                    for (Role aRl : roles) {
                        Set<Permission> permSet = aRl.getPermissionSet();
                        if (permSet != null) {
                            for (Permission aObj : permSet) {
                                perms.add(aObj.getId());
                            }
                        }
                    }

                    flag = perms.contains(permissionId);
                }
            }

            lock.getLock().readLock().unlock();


        }

        return flag;

    }

    public boolean modifyRolePermission(String roleId, String[] permissionIds) throws Exception{
        boolean flag = false;
        if(roleId != null && permissionIds!= null && permissionIds.length > 0){
            lock.getLock().writeLock().lock();

            Map<String, Role> map = container.getRolesMap();
            Map<String, Permission> mapPerm  = container.getPermissionMap();
            Role aRl = map.get(roleId);
            int notPresent = 0;
            if(aRl != null){
                Set<Permission> perms = aRl.getPermissionSet();

                for(String permid : permissionIds){
                    Permission aPer = mapPerm.get(permid);
                    if(aPer != null){
                        perms.add(aPer);
                    }else{
                        notPresent++;
                        //break;
                    }
                }

                if(notPresent != permissionIds.length){
                    flag = true;
                }

            }

            lock.getLock().writeLock().unlock();
        }



        return flag;
    }

    public boolean deletePermission(String permissionId) throws Exception{
        boolean flag = false;

        if(permissionId != null && !permissionId.trim().isEmpty()){
            permissionId = permissionId.trim();

            lock.getLock().writeLock().lock();

            Map<String, Permission> perMap = container.getPermissionMap();
            Permission perm = perMap.get(permissionId);

            if(perm != null){
                Map<String,Role> roleMap = container.getRolesMap();
                Set<String> roleKeys = roleMap.keySet();

                for(String str : roleKeys){
                    roleMap.get(str).getPermissionSet().remove(perm);
                }

                perMap.remove(permissionId);
                flag = true;
            }

            lock.getLock().writeLock().unlock();

        }

        return flag;
    }
}
