package de.febanhd.cloud.wrapper.util;

import de.febanhd.cloud.server.ServerType;

public class FormatUtil {

    public static boolean isServerType(String in) {
        try {
            ServerType.valueOf(in);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public static boolean isBoolean(String in) {
        try {
            Boolean.valueOf(in);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public static boolean isInteger(String in) {
        try {
            Integer.valueOf(in);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
