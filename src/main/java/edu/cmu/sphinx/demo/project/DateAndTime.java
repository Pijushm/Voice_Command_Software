package edu.cmu.sphinx.demo.project;

import java.util.Date;

/**
 * Created by Pijush on 9/2/2017.
 */
public class DateAndTime {
    Date date = new Date();

    public String getDate() {
        return String.format("%td %<tB", date);
    }

    public String getTime() {
        return String.format("%tr", date);
    }

    public String getDay() {
        return String.format("%tA", date);
    }
}
//