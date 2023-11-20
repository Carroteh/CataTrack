package com.carrot.catatrack.model;

import java.sql.Date;

/**
 * @author Philip Mathee
 * @version 1.0
 * Eye model that mirrors that of the database table
 */
public class Eye {
    private int patient_id;
    private char side;
    private String lens;
    private String va_init;
    private String va_postop;
    private String va_2weeks;
    private String va_6weeks;
    private String va_final;
    private String surg_place;
    private Date surg_date;
    private String surg_type;
    private String surg_notes;

    public Eye() {}

    /**
     * Full constructor for the Eye class
     * @param patient_id the patient ID
     * @param side the eye in question, Left/Right -> L/R
     * @param lens the lens status of the eye
     * @param va_init the initial visual acuity
     * @param va_postop the visual acuity post surgery
     * @param va_2weeks visual acuity 2 weeks after surgery
     * @param va_6weeks visual acuity 6 weeks after surgery
     * @param surg_date date of the surgery
     * @param surg_type type of surgery
     * @param surg_notes notes related to the surgery
     */
    public Eye(int patient_id, char side, String lens, String va_init, String va_postop, String va_2weeks, String va_6weeks,String surg_place, Date surg_date, String surg_type, String surg_notes) {
        this.patient_id = patient_id;
        this.side = side;
        this.lens = lens;
        this.va_init = va_init;
        this.va_postop = va_postop;
        this.va_2weeks = va_2weeks;
        this.va_6weeks = va_6weeks;
        this.va_final = determineFinalVA(va_init, va_postop, va_2weeks, va_6weeks);
        this.surg_place = surg_place;
        this.surg_date = surg_date;
        this.surg_type = surg_type;
        this.surg_notes = surg_notes;
    }

    /**
     * Function that assigns the latest VA to final VA
     * @param init the initial VA
     * @param postop the VA after the surgery
     * @param week2 the VA 2 weeks after the surgery
     * @param week6 the VA 6 weeks after the surgery
     * @return the final VA
     */
    private String determineFinalVA(String init, String postop, String week2, String week6) {
        String finalVA = "";

        //Check for the latest available VA
        if(week6 != null) {
            finalVA = week6;
        }
        else if(week2 != null) {
            finalVA = week2;
        }
        else if(postop != null) {
            finalVA = postop;
        }
        else if(init != null) {
            finalVA = init;
        }
        else {
            finalVA = "";
        }

        return finalVA;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        if(side != 'L' && side != 'R') {
            System.err.println("Invalid eye side.");
        }
        else {
            this.side = side;
        }
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public String getVa_init() {
        return va_init;
    }

    public void setVa_init(String va_init) {
        this.va_init = va_init;
    }

    public String getVa_postop() {
        return va_postop;
    }

    public void setVa_postop(String va_postop) {
        this.va_postop = va_postop;
    }

    public String getVa_2weeks() {
        return va_2weeks;
    }

    public void setVa_2weeks(String va_2weeks) {
        this.va_2weeks = va_2weeks;
    }

    public String getVa_6weeks() {
        return va_6weeks;
    }

    public void setVa_6weeks(String va_6weeks) {
        this.va_6weeks = va_6weeks;
    }

    public String getVa_final() {
        return va_final;
    }

    public void setVa_final() {
        this.va_final = determineFinalVA(this.va_init, this.va_postop, this.va_2weeks, this.va_6weeks);
    }

    public String getSurg_place() {
        return surg_place;
    }

    public void setSurg_place(String surg_place) {
        this.surg_place = surg_place;
    }

    public Date getSurg_date() {
        return surg_date;
    }

    public void setSurg_date(Date surg_date) {
        this.surg_date = surg_date;
    }

    public String getSurg_type() {
        return surg_type;
    }

    public void setSurg_type(String surg_type) {
        this.surg_type = surg_type;
    }

    public String getSurg_notes() {
        return surg_notes;
    }

    public void setSurg_notes(String surg_notes) {
        this.surg_notes = surg_notes;
    }
}
