package com.avaya.oa.util;

import java.sql.Timestamp;

public class DateUtil {

        public static Timestamp convertToUTCTime(String strTime) throws IllegalArgumentException {

                        if (strTime.contains("T")) {
                                strTime = strTime.replace('T', ' ');

                        }
                        if (strTime.contains("Z")) {
                                strTime = strTime.replace("Z", "");
                        }

                        return Timestamp.valueOf(strTime);
                }
}
