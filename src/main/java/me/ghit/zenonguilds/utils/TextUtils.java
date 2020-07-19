package me.ghit.zenonguilds.utils;

public class TextUtils {

    public static String convertToPascal(String raw) {
        if(raw == null || raw.isEmpty()) {
            return raw;
        }

        return raw.substring(0, 1).toUpperCase() + raw.substring(1);
    }

}
