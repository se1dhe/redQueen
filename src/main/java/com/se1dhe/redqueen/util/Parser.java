package com.se1dhe.redqueen.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Parser {

    public static String dateParser(Instant dateForParse) {
        Date date = Date.from(dateForParse);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        return formatter.format(date);
    }

}
