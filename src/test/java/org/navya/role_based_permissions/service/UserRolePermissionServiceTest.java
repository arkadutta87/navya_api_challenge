package org.navya.role_based_permissions.service;

import org.junit.Test;
import org.navya.role_based_permissions.common.Lock;
import org.navya.role_based_permissions.data_container.DataContainer;
import org.navya.role_based_permissions.model.Permission;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by arkadutta on 27/01/17.
 */
public class UserRolePermissionServiceTest {

    @Test
    public void checkUserPermissions() throws Exception{
        DataContainer container = new DataContainer(null);

        Lock lck = new Lock();

        UserRolePermissionService service = new UserRoleServicePermissionImpl(container,lck);

        Set<String> permissions = service.getPermissionFromUserId("user1");

        String[] SET_VALUES = {"Can withdraw" ,
                "Can deposit" ,
                "Can Transfer",
                "Can check balance"};

        Set<String> perm2 = new HashSet<>(new ArrayList<String>(Arrays.asList(SET_VALUES)));

        assertEquals(permissions,perm2);

    }

    @Test
    public void checkUserPermissionsForWrongUser() throws Exception{
        DataContainer container = new DataContainer(null);

        Lock lck = new Lock();

        UserRolePermissionService service = new UserRoleServicePermissionImpl(container,lck);

        Set<String> permissions = service.getPermissionFromUserId("user2");


        assertEquals(permissions,null);

    }

    @Test
    public void checkUserPermissionsForNULLUser() throws Exception{
        DataContainer container = new DataContainer(null);

        Lock lck = new Lock();

        UserRolePermissionService service = new UserRoleServicePermissionImpl(container,lck);

        Set<String> permissions = service.getPermissionFromUserId(null);


        assertEquals(permissions,null);

    }

    @Test
    public void checkAPI2NULLValue() throws Exception{
        DataContainer container = new DataContainer(null);

        Lock lck = new Lock();

        UserRolePermissionService service = new UserRoleServicePermissionImpl(container,lck);

        boolean flag = service.checkpermission(null,"");


        assertFalse(flag);

    }

    @Test
    public void checkAPI2NULLRightValue() throws Exception{
        DataContainer container = new DataContainer(null);

        Lock lck = new Lock();

        UserRolePermissionService service = new UserRoleServicePermissionImpl(container,lck);

        boolean flag = service.checkpermission("user1","perm1");


        assertTrue(flag);

    }

    @Test
    public void checkAPI2NULLWrongValue() throws Exception{
        DataContainer container = new DataContainer(null);

        Lock lck = new Lock();

        UserRolePermissionService service = new UserRoleServicePermissionImpl(container,lck);

        boolean flag = service.checkpermission("user1","perm10");


        assertFalse(flag);

    }

    @Test
    public void checkAPI3RightValue() throws Exception{
        DataContainer container = new DataContainer(null);

        Lock lck = new Lock();

        UserRolePermissionService service = new UserRoleServicePermissionImpl(container,lck);

        boolean flag = service.modifyRolePermission("role1",new String[] {"perm8"});

        Set<Permission> perm1 = container.getRolesMap().get("role1").getPermissionSet();

        Set<Permission> perm2 = new HashSet<>();
        perm2.add(new Permission("perm1","Can check balance"));
        perm2.add(new Permission("perm5","Can deposit"));
        perm2.add(new Permission("perm8","Adhar Linked"));

        assertEquals(perm1,perm2);

    }


}
