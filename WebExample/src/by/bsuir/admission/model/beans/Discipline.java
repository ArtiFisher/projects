package by.bsuir.admission.model.beans;

import java.io.Serializable;

public class Discipline implements Serializable{
    /**
     * Discipline ID
     */
    private int disciplineId;
    /**
     * nameof discipline
     */
    private String name;
    /**
     * Groupe of disciplines
     * 0 - all disciplines are obligatory
     * 1 and more - one discipline from group
     */
    private int optionalGroupe;

    public Discipline() {
    }

    public Discipline(int disciplineId, String name, int optionalGroupe) {
        this.disciplineId = disciplineId;
        this.name = name;
        this.optionalGroupe = optionalGroupe;
    }

    public int getOptionalGroupe() {
        return optionalGroupe;
    }

    public void setOptionalGroupe(int optionalGroupe) {
        this.optionalGroupe = optionalGroupe;
    }

    public int getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(int disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
