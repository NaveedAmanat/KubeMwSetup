package com.idev4.setup.web.rest.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class Common {

    public static String adminRoleLanId = "admin";

    public static Instant getZonedInstant(Instant ins) {
        return ins.atZone(ZoneId.ofOffset("UTC", ZoneOffset.of("+05:30"))).toInstant();
    }

    public static String GetFormattedDateForTab(Instant ins, boolean simpleFlag) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (simpleFlag)
            sdf = new SimpleDateFormat("dd-MM-yyyy");
        // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = Date.from(ins);
        return sdf.format(date);
    }
}
