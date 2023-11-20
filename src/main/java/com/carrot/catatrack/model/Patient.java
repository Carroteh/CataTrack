package com.carrot.catatrack.model;

import java.sql.Date;

/**
 * @author Philip Mathee
 * @version 1.0
 * Patient model that mirrors that of the database table
 */
public class Patient {

    private int patient_id;
    private int id_num;
    private String surname;
    private String initials;
    private Date dob;
    private String status;
    private String contact;
    private String alt_contact;

    public Patient() {}

    public Patient(int patient_id, int id_num, String surname, String initials, Date dob, String status, String contact, String alt_contact) {
        this.patient_id = patient_id;
        this.id_num = id_num;
        this.surname = surname;
        this.initials = initials;
        this.dob = dob;
        this.status = status;
        this.contact = contact;
        this.alt_contact = alt_contact;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getId_num() {
        return id_num;
    }

    public void setId_num(int id_num) {
        this.id_num = id_num;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAlt_contact() {
        return alt_contact;
    }

    public void setAlt_contact(String alt_contact) {
        this.alt_contact = alt_contact;
    }


}
