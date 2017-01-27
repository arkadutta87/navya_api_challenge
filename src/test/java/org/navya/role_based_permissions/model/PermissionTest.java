package org.navya.role_based_permissions.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arkadutta on 27/01/17.
 */
public class PermissionTest {

    @Test
    public void testCheckPermissionNotEquals()  {

        Permission obj1 = new Permission("perm1","Can check balance");
        Permission obj2 = new Permission("perm2","Can deposit");

        assertFalse(obj1.equals(obj2));

    }

    @Test
    public void testCheckPermissionEquals()  {

        Permission obj1 = new Permission("perm1","Can check balance");
        Permission obj2 = new Permission("perm1","Can check balance");

        assertTrue(obj1.equals(obj2));

    }
}
