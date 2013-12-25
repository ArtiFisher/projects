package by.bsuir.admission.model.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Faculty implements Serializable{
    /**
     * Faculty ID
     */
    private int facultyId;
    /**
     * Name of faculty
     */
    private String name;
    /**
     * The list of specialities, which are at faculty
     */
    private ArrayList<Speciality> specialityList=new ArrayList<Speciality>();

    public Faculty() {
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Speciality> getSpecialityList() {
        return specialityList;
    }

    public void setSpecialityList(ArrayList<Speciality> specialityList) {
        this.specialityList = specialityList;
    }
}
