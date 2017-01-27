package org.navya.role_based_permissions.common;

import org.json.JSONArray;
import org.navya.role_based_permissions.payload.GenericResponse;

/**
 * Created by arkadutta on 27/01/17.
 */
public class Util {

    public static GenericResponse generateGenericResponse(int code, String message) {
        GenericResponse obj = new GenericResponse();
        obj.setCode(code);
        obj.setMessage(message);

        return obj;
    }

    public static String[] covertJSONArrayToStringArray(JSONArray arr) {

        if (arr == null) {
            return null;
        }
        String[] arrStr = new String[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            arrStr[i] = (String) arr.get(i);
        }

        return arrStr;
    }
}
