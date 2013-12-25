package by.bsuir.admission.model.action;

import by.bsuir.admission.model.beans.Certificate;
import by.bsuir.admission.model.beans.Discipline;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.model.beans.User;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

/**
 *This class test the action class <code>DisciplineListChecker</code>
 * @author AndreAY
 */
@RunWith(Parameterized.class)
public class DisciplineListCheckerTest {
    private User user;
    private Speciality speciality;
    private boolean expected;

    @Parameters
    public static Collection<Object[]> userAndCertificatesValues() {
        User user;
        Speciality speciality;
        Object[] parameters;
        Collection<Object[]> parametersList = new ArrayList<Object[]>();
        //all right
        user = buildUser(new int[]{1,2,4,9});
        speciality = buildSpeciality(new int[][]{{1,0},{2,0},{3,1},{4,1},{9,0}});
        parameters = new Object[]{user, speciality, true};
        parametersList.add(parameters);
        //missing required discipline
        user = buildUser(new int[]{1,5,4,9});
        speciality = buildSpeciality(new int[][]{{1,0},{2,0},{3,1},{4,1},{9,0}});
        parameters = new Object[]{user, speciality, false};
        parametersList.add(parameters);
        //missing optional discipline
        user = buildUser(new int[]{1,2,5,9});
        speciality = buildSpeciality(new int[][]{{1,0},{2,0},{3,1},{4,1},{9,0}});
        parameters = new Object[]{user, speciality, false};
        parametersList.add(parameters);
        //speciality is null;
        user = buildUser(new int[]{1,2,3,9});
        parameters = new Object[]{null, null, false};
        parametersList.add(parameters);
        //user is null;
        speciality = buildSpeciality(new int[][]{{1,0},{2,0},{3,1},{4,1},{9,0}});
        parameters = new Object[]{null, null, false};
        parametersList.add(parameters);
        //user and speciality are null;
        parameters = new Object[]{null, null, false};
        parametersList.add(parameters);

        return parametersList;
    }

    public DisciplineListCheckerTest(User user, Speciality speciality, boolean expected) {
        this.user = user;
        this.speciality = speciality;
        this.expected = expected;
    }
    
    /**
     * Test of check method, of class DisciplineListChecker.
     */
    @Test
    public void testCheck() {
        DisciplineListChecker instance = new DisciplineListChecker(user, speciality);
        boolean result = instance.check();
        assertEquals(expected, result);
    }

    private static Certificate buildCertificate(int disciplineId) {
        Certificate certificate=new Certificate();
        Discipline discipline = new Discipline(disciplineId, null, 0);
        certificate.setDiscipline(discipline);
        return certificate;
    }

    private static User buildUser(int[] disciplineIdArray) {
        User user = new User();
        for (int disciplineId : disciplineIdArray) {
            user.addCertificate(buildCertificate(disciplineId));
        }
        return user;
    }

    private static Speciality buildSpeciality(int[][] disciplineParamArray) {
        Speciality speciality = new Speciality();
        for (int[] disciplineParam : disciplineParamArray) {
            speciality.addDiscipline(new Discipline(disciplineParam[0], null, disciplineParam[1]));
        }
        return speciality;
    }
}
