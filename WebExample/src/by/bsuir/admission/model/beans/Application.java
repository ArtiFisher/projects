package by.bsuir.admission.model.beans;

import java.io.Serializable;

public class Application implements Serializable {

    /**
     * Application ID
     */
    private int applicationId;
    /**
     * The user, who has made an application
     */
    private User user;
    /**
     * Speciality, on which the application has been submitted
     */
    private Speciality speciality;
    /**
     * Paid or free education
     */
    private boolean isPaid;
    /**
     * Name of faculty
     */
    private String facultyName;
    /**
     * Name of university
     */
    private String universityName;

    public Application() {
    }

    public Application(User user, Speciality speciality, boolean isPaid) {
        this.user = user;
        this.speciality = speciality;
        this.isPaid = isPaid;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
