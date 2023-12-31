package com.carrot.catatrack.model;

/**
 * @author Philip Mathee
 * @version 1.0
 * Class that contains a patient and their two respective eyes, to enable easier data transport
 */
public class Person {

    private Patient patient;
    private Eye rightEye;
    private Eye leftEye;

    public Person(Patient patient, Eye rightEye, Eye leftEye) {
        this.patient = patient;
        this.rightEye = rightEye;
        this.leftEye = leftEye;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Eye getRightEye() {
        return rightEye;
    }

    public void setRightEye(Eye rightEye) {
        this.rightEye = rightEye;
    }

    public Eye getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(Eye leftEye) {
        this.leftEye = leftEye;
    }
}
