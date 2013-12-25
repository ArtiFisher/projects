package by.bsuir.admission.model.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class University implements Serializable{
    /**
     * University ID
     */
    private int universityId;
    /**
     * Name of univercity
     */
    private String name;
    /**
     * List of faculty in this univercity
     */
    private ArrayList<Faculty> facultiesList=new ArrayList<Faculty>();

    public University() {
    }

    public ArrayList<Faculty> getFacultiesList() {
        return facultiesList;
    }

    public void setFacultiesList(ArrayList<Faculty> facultiesList) {
        this.facultiesList = facultiesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }
}
