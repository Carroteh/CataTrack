package com.carrot.catatrack.model;

import javafx.scene.control.DatePicker;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Philip Mathee
 * @version 1.0
 * Utility class used to handle dates
 */
public class DateUtils {
    /**
     * Function that extracts the date from a datePicker
     * @param datePicker the DatePicker
     * @return A LocalDate from the DatePicker, "1000/01/01" if no date was entered
     */
    public static LocalDate getDate(DatePicker datePicker) {
        LocalDate returnDate;

        //Check if no date was entered
        if(datePicker.getEditor().getText().equals("")) {
            returnDate = LocalDate.of(1000,1,1);
            datePicker.setValue(null);
        }
        //Check if a date was entered in the TextField
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            returnDate = LocalDate.parse(datePicker.getEditor().getText(), formatter);
        }

        return returnDate;
    }

    /**
     * Checks if the date is the default date of 1000/01/01
     * @param date the date to check
     * @return true if it matches the default date, false otherwise
     */
    public static boolean isDefault(Date date) {
        return date.equals(Date.valueOf("1000-01-01"));
    }
}
