package by.bsuir.admission.model.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Speciality implements Serializable {

    /**
     * Pass mark on free education
     */
    private int passMark;
    /**
     * Pass mark on paid education
     */
    private int passMarkPaid;
    /**
     * Speciality ID
     */
    private int specialityId;
    /**
     * Nameof speciality
     */
    private String name;
    /**
     * Intake for students on free education
     */
    private int intake;
    /**
     * Intake for students on paid education
     */
    private int intakePaid;
    /**
     * Map of groupe of disciplnes and them IDs
     */
    private HashMap<Integer, ArrayList<Discipline>> disciplineMap = new HashMap<Integer, ArrayList<Discipline>>();

    public Speciality() {
    }

    /**
     * This method adding discipline in map
     * @param discipline Discipline, which is necessary for adding in map
     */
    public void addDiscipline(Discipline discipline) {
        Integer disciplineGroupeKey = Integer.valueOf(discipline.getOptionalGroupe());
        if (disciplineMap.containsKey(disciplineGroupeKey)) {
            ArrayList<Discipline> disciplineGroupe = disciplineMap.get(disciplineGroupeKey);
            disciplineGroupe.add(discipline);
        } else {
            ArrayList<Discipline> newDisciplineGroupe = new ArrayList<Discipline>();
            newDisciplineGroupe.add(discipline);
            disciplineMap.put(disciplineGroupeKey, newDisciplineGroupe);
        }
    }

    public int getPassMark() {
        return passMark;
    }

    /**
     * This method return pass mark on free education or paid education, depending on that what this education
     * @param isPaid Whether paid this education
     * @return passMarkPaid or passMark Pass mark
     */
    public int getPassMark(boolean isPaid) {
        if (isPaid) {
            return passMarkPaid;
        } else {
            return passMark;
        }
    }

    public void setPassMark(int passMark) {
        this.passMark = passMark;
    }

    public int getPassMarkPaid() {
        return passMarkPaid;
    }

    public void setPassMarkPaid(int passMarkPaid) {
        this.passMarkPaid = passMarkPaid;
    }

    public HashMap<Integer, ArrayList<Discipline>> getDisciplineMap() {
        return disciplineMap;
    }

    public void setDisciplineMap(HashMap<Integer, ArrayList<Discipline>> disciplineMap) {
        this.disciplineMap = disciplineMap;
    }

    public int getIntake() {
        return intake;
    }

    public void setIntake(int intake) {
        this.intake = intake;
    }

    public int getIntakePaid() {
        return intakePaid;
    }

    public void setIntakePaid(int intakePaid) {
        this.intakePaid = intakePaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }
}
