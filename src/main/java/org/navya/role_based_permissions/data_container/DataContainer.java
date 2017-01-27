package org.navya.role_based_permissions.data_container;

import org.json.JSONArray;
import org.json.JSONObject;
import org.navya.role_based_permissions.common.Util;
import org.navya.role_based_permissions.model.Permission;
import org.navya.role_based_permissions.model.Role;
import org.navya.role_based_permissions.model.User;

import javax.jws.soap.SOAPBinding;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by arkadutta on 27/01/17.
 */
public class DataContainer {

    private int val;
    private static final String DATA_FILE = "data.txt";

    private Map<String, User> usersMap;
    private Map<String, Role> rolesMap;
    private Map<String, Permission> permissionMap;

    public DataContainer(String fileName) throws Exception{
        System.out.println("----------I am being created------------############\n\n");
        val = 3;

        //reading the data file once and loading into memory
        if(fileName == null){
            fileName = DATA_FILE;
        }
        readData(fileName);

        Set<String> keys = usersMap.keySet();
        for (String aKey : keys) {
            User aUser = usersMap.get(aKey);
            System.out.println(aUser);
            System.out.println("\n\n");
        }
    }

    public DataContainer() throws Exception {
        this(null);
    }

    public void readData(String fileName) throws Exception {
        InputStream stream = DataContainer.class.getClassLoader().getResourceAsStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String str = br.readLine().trim();
        int flag = 0;
        List<JSONObject> users = new LinkedList<>();
        List<JSONObject> roles = new LinkedList<>();
        List<JSONObject> permissions = new LinkedList<>();
        while (str != null) {
            System.out.println(str);
            str = str.trim();
            if (str.equals("Users")) {
                str = br.readLine();
                flag = 1;

            } else if (str.equals("Roles")) {
                str = br.readLine();
                flag = 2;
            } else if (str.equals("Permissions")) {
                str = br.readLine();
                flag = 3;
            }

            if (!(str.trim().isEmpty() || str.trim().contains("=="))) {
                if (flag == 1) {
                    JSONObject jsonObj = new JSONObject(str);
                    users.add(jsonObj);
                } else if (flag == 2) {
                    JSONObject jsonObj = new JSONObject(str);
                    roles.add(jsonObj);
                } else if (flag == 3) {
                    JSONObject jsonObj = new JSONObject(str);
                    permissions.add(jsonObj);
                }
            }

            //List<>

            str = br.readLine();
        }

        //data has been read now create the maps
        permissionMap = new HashMap<>();
        for (JSONObject aObj : permissions) {
            Permission perm = new Permission((String) (aObj.get("id")), (String) (aObj.get("name")));
            permissionMap.put(perm.getId(), perm);

        }

        rolesMap = new HashMap<>();
        for (JSONObject aObj : roles) {
            Role aRole = new Role((String) aObj.get("id"));
            JSONArray arr = (JSONArray) aObj.get("permissions");
            String[] perms = Util.covertJSONArrayToStringArray(arr);

            for (String a1 : perms) {
                Permission obj1 = permissionMap.get(a1.trim());
                if (obj1 != null) {
                    aRole.addPermission(obj1);
                }
            }

            rolesMap.put(aRole.getId(), aRole);

        }

        usersMap = new HashMap<>();
        for (JSONObject aObj : users) {
            User aUSer = new User((String) aObj.get("id"));
            JSONArray arr = (JSONArray) aObj.get("roles");
            String[] rolesArr = Util.covertJSONArrayToStringArray(arr);

            for (String a1 : rolesArr) {
                Role obj1 = rolesMap.get(a1.trim());
                if (obj1 != null) {
                    aUSer.addRole(obj1);
                }
            }

            usersMap.put(aUSer.getId(), aUSer);

        }


    }

    public Map<String, User> getUsersMap() {
        return usersMap;
    }

    public Map<String, Role> getRolesMap() {
        return rolesMap;
    }

    public Map<String, Permission> getPermissionMap() {
        return permissionMap;
    }

    public int getVal() {
        return this.val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
