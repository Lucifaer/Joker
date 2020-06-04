package com.lucifaer.jokerframework.joker.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author Lucifaer
 * @version 1.0.0.RELEASE
 * @since 2020/6/3
 */
public class GetRealPath {
    public static String getPath(String type) {
        String realPath = null;
        String filePath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        try {
            filePath = URLDecoder.decode(filePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (type.equals("exploit")) {
            realPath = filePath + fileSeparator + "exploitLib";
        }
        else if (type.equals("server")) {
            realPath = "";
        }
        return realPath;
    }
}
