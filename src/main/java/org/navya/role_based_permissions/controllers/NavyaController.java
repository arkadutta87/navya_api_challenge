package org.navya.role_based_permissions.controllers;

import org.navya.role_based_permissions.common.Lock;
import org.navya.role_based_permissions.common.Util;
import org.navya.role_based_permissions.data_container.DataContainer;
import org.navya.role_based_permissions.payload.GenericResponse;
import org.navya.role_based_permissions.payload.ModifyRolesPermissionRequest;
import org.navya.role_based_permissions.payload.UserPermissions;
import org.navya.role_based_permissions.service.UserRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by arkadutta on 27/01/17.
 */
@RestController
public class NavyaController {

    @Autowired
    UserRolePermissionService permissionService;

    private static final String INTERNAL_ERR = "INTERNAL SERVER ERROR";
    private static final String DATA_NOT_PRESENT = "DATA NOT PRESENT";
    private static final String SUCCESS = "SUCCESS";


    //api for getting the permissions name related to a user
    @RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
    public ResponseEntity<UserPermissions> getPermissionFromUserId(@PathVariable String userid, HttpServletRequest request) {

        UserPermissions response = null;
        HttpStatus resStatus = null;
        Set<String> permissions = null;
        try {
            permissions = permissionService.getPermissionFromUserId(userid);
            if (permissions == null || permissions.isEmpty()) {
                response = new UserPermissions(4, DATA_NOT_PRESENT);
                resStatus = HttpStatus.NO_CONTENT;
            } else {
                response = new UserPermissions(0, SUCCESS);
                for (String aStr : permissions) {
                    response.addPermission(aStr);
                }

                resStatus = HttpStatus.OK;
            }
        } catch (Exception e) {
            response = new UserPermissions(5, INTERNAL_ERR);
            resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<UserPermissions>(response, resStatus);
    }

    //api for checking whether a user has a permission-id or not
    @RequestMapping(value = "/checkpermission/", params = {"userid", "permissionid"}, method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkPermissionForUser(
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "permissionid") String permissionid) {
        System.out.println("UserId - " + userid + " --- PermissionId - " + permissionid + "\n\n");
        boolean flag = false;
        HttpStatus resStatus = null;
        try {
            flag = permissionService.checkpermission(userid, permissionid);
            resStatus = HttpStatus.OK;
        } catch (Exception e) {
            resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Boolean>(new Boolean(flag), resStatus);
    }

    //adding a permission to role
    @RequestMapping(value = "/roles/{roleid}", method = RequestMethod.POST)
    public ResponseEntity<GenericResponse> modifyRoles(@PathVariable String roleid, @RequestBody ModifyRolesPermissionRequest request) {
        GenericResponse response = null;
        HttpStatus resStatus = null;
        if (request == null) {
            response = Util.generateGenericResponse(1, "Input Format Wrong");
            resStatus = HttpStatus.BAD_REQUEST;
        } else {
            //lock.getLock().writeLock().lock();

            String[] perms = request.getPermissions();
            if (perms == null || perms.length == 0) {
                response = Util.generateGenericResponse(1, "Input Format Wrong");
                resStatus = HttpStatus.BAD_REQUEST;
            } else {

                boolean flag = false;
                try {
                    flag = permissionService.modifyRolePermission(roleid, perms);
                    if (flag) {
                        response = Util.generateGenericResponse(0, "Modified Successfully -- " + roleid);
                        resStatus = HttpStatus.OK;
                    } else {
                        response = Util.generateGenericResponse(2, "Couldnot be modified -- " + roleid);
                        resStatus = HttpStatus.NOT_ACCEPTABLE;
                    }
                } catch (Exception e) {
                    response = Util.generateGenericResponse(5, INTERNAL_ERR);//new UserPermissions(5, INTERNAL_ERR);
                    resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                }

            }
        }

        return new ResponseEntity<GenericResponse>(response, resStatus);
    }

    //Delete a permission
    @RequestMapping(value = "/permissions/{permission_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deletePermission(@PathVariable String permission_id, HttpServletRequest request) {

        boolean flag = false;
        HttpStatus resStatus = null;
        System.out.println("Permission Id -- " + permission_id + "\n\n");

        if (permission_id == null || permission_id.trim().isEmpty()) {
            resStatus = HttpStatus.BAD_REQUEST;
        } else {
            try {
                flag = permissionService.deletePermission(permission_id);
                if (flag) {
                    resStatus = HttpStatus.OK;
                } else {
                    resStatus = HttpStatus.NOT_ACCEPTABLE;
                }

            } catch (Exception e) {
                resStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

        }

        return new ResponseEntity<Boolean>(new Boolean(flag), resStatus);
    }
}
