package by.bsuir.admission.model.beans;

import java.io.Serializable;

public class Certificate implements Serializable {

    /**
     * Certificate ID
     */
    private int certificateId;
    /**
     * Passport number of user
     */
    private String passportNumber;
    /**
     * Mark
     */
    private int mark;
    /**
     * Discipline on which the certificate has been received
     */
    private Discipline discipline;

    public Certificate() {
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
