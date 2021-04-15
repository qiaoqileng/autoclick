package com.qql.autokick.utils;

public class OsUtils {

    private static final int MAX_WAITING_TIME = 6000;

    public static String excCommand(String cmd){
        return new ExeCommand().run(cmd, MAX_WAITING_TIME).getResult();
    }
}
