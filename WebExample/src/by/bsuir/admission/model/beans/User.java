package by.bsuir.admission.model.beans;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

    /**
     * User ID
     */
    private int userId;
    /**
     * Total mark of current user
     */
    private int totalMark;
    /**
     * Login of user
     */
    private String login;
    /**
     * Password of user
     */
    private String password;
    /**
     * First name of user
     */
    private String firstName;
    /**
     * Surname of user
     */
    private String surname;
    /**
     * Whether is the user the administrator
     */
    private boolean isAdmin;
    /**
     * E-mail of user
     */
    private String mail;
    /**
     * The list of certificates, which are for the user
     */
    private HashMap<Integer, Certificate> certificatesMap = new HashMap<Integer, Certificate>();

    public User() {
    }

    /**
     * This method calculates a sum of certificate marks
     */
    private void setTotalMark() {
        totalMark = 0;
        for (Certificate certificate : certificatesMap.values()) {
            totalMark += certificate.getMark();
        }
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addCertificate(Certificate certificate) {
        certificatesMap.put(certificate.getDiscipline().getDisciplineId(), certificate);
        setTotalMark();
    }

    public int getCertificateNumber() {
        return certificatesMap.size();
    }

    public HashMap<Integer, Certificate> getCertificatesMap() {
        return certificatesMap;
    }

    public void setCertificatesMap(HashMap<Integer, Certificate> certificatesMap) {
        this.certificatesMap = certificatesMap;
        setTotalMark();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
