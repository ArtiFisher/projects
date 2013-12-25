package by.bsuir.admission.model.action;

import by.bsuir.admission.model.beans.Discipline;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.model.beans.User;
import java.util.ArrayList;
import java.util.Set;

public class DisciplineListChecker {

    public static int REQUIRED_GROUPE_ID = 0;
    /**
     * This is user which wants to add an application
     */
    private User user;
    /**
     * This is speciality to which application will added
     */
    private Speciality speciality;
    /**
     * This object in which are written reports on missing disciplines
     */
    private ArrayList<String> missingDisciplineList = new ArrayList<String>();

    public DisciplineListChecker(User user, Speciality speciality) {
        this.user = user;
        this.speciality = speciality;
    }

    public ArrayList<String> getMissingDisciplineList() {
        return missingDisciplineList;
    }

    /**
     * This method checks there is beside user certificates on necessary discipline
     * @return true if beside user there is all necessary certificates
     */
    public boolean check() {
        boolean isCorrespond = true;
        if (user != null && speciality != null) {
            Set<Integer> disciplineGroupeSet = speciality.getDisciplineMap().keySet();
            for (Integer disciplineGroupeId : disciplineGroupeSet) {
                if (disciplineGroupeId.intValue() == REQUIRED_GROUPE_ID) {
                    isCorrespond &= checkRequiredDisciplines(disciplineGroupeId);
                } else {
                    isCorrespond &= checkOptionalDisciplines(disciplineGroupeId);
                }
            }
        } else {
            isCorrespond = false;
        }
        return isCorrespond;
    }

    /**
     * This method checks there is beside user all obligatory certificates
     * @param disciplineGroupeId a number of discipline groupe in map
     * @return true if user has got all required certificates
     */
    private boolean checkRequiredDisciplines(Integer disciplineGroupeId) {
        boolean isCorrespond = true;
        ArrayList<Discipline> disciplineList = speciality.getDisciplineMap().get(disciplineGroupeId);
        for (Discipline discipline : disciplineList) {
            if (user.getCertificatesMap().get(discipline.getDisciplineId()) == null) {
                missingDisciplineList.add(discipline.getName());
                isCorrespond = false;
            }
        }
        return isCorrespond;
    }

    /**
     * This method checks there is beside user one off optional certificates
     * @param disciplineGroupeId a number of discipline groupe in map
     * @return true if user has got one optional certificate
     */
    private boolean checkOptionalDisciplines(Integer disciplineGroupeId) {
        boolean isCorrespond = false;
        ArrayList<Discipline> disciplineList = speciality.getDisciplineMap().get(disciplineGroupeId);
        for (Discipline discipline : disciplineList) {
            if (user.getCertificatesMap().get(discipline.getDisciplineId()) != null) {
                return isCorrespond = true;
            }
        }
        if (!isCorrespond) {
            for (Discipline discipline : disciplineList) {
                missingDisciplineList.add(discipline.getName());
            }
        }
        return isCorrespond;
    }
}
