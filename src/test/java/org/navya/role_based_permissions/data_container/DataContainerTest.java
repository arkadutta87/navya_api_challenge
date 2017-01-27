package org.navya.role_based_permissions.data_container;

import org.junit.Test;
import org.navya.role_based_permissions.model.Permission;
import org.navya.role_based_permissions.model.Role;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by arkadutta on 27/01/17.
 */
public class DataContainerTest {

    @Test
    public void loadingPermissionListTest() throws Exception  {

        DataContainer container = new DataContainer(null);
        Map<String, Permission> permissions = container.getPermissionMap();

        assertEquals(5,permissions.keySet().size());

    }

    @Test
    public void loadingRoleListTest() throws Exception  {

        DataContainer container = new DataContainer(null);
        Map<String, Role> roles = container.getRolesMap();

        assertEquals(2,roles.keySet().size());

    }

}
