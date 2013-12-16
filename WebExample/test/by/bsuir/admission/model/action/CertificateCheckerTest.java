package by.bsuir.admission.model.action;

import by.bsuir.admission.model.beans.Certificate;
import by.bsuir.admission.model.beans.Discipline;
import by.bsuir.admission.model.beans.User;
import by.bsuir.admission.util.MessageManager;
import by.bsuir.admission.xml.NoPropertyException;
import by.bsuir.admission.xml.XMLManager;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

/**
 * This class test the action class <code>CertificateChecker</code>
 * @author AndreAY
 */
@RunWith(Parameterized.class)
public class CertificateCheckerTest {

    private User user;
    private Certificate newCertificate;
    private boolean expected;

    @Parameters
    public static Collection<Object[]> userAndCertificatesValues() {
        User user;
        Certificate newCertificate;
        Object[] parameters;
        Collection<Object[]> parametersList = new ArrayList<Object[]>();
        //all right
        user = buildUser(new int[]{1, 3, 4}, "MC1411654");
        newCertificate = buildCertificate(6, "MC1411654");
        parameters = new Object[]{user, newCertificate, true};
        parametersList.add(parameters);
        //all right
        user = buildUser(new int[]{}, "");
        newCertificate = buildCertificate(1, "MC1411654");
        parameters = new Object[]{user, newCertificate, true};
        parametersList.add(parameters);
        //this discipline already exist
        user = buildUser(new int[]{1, 3, 4}, "MC1411654");
        newCertificate = buildCertificate(1, "MC1411654");
        parameters = new Object[]{user, newCertificate, false};
        parametersList.add(parameters);
        //certificate collection full, can't add more certificates
        user = buildUser(new int[]{1, 3, 4, 5}, "MC1411654");
        newCertificate = buildCertificate(2, "MC1411654");
        parameters = new Object[]{user, newCertificate, false};
        parametersList.add(parameters);
        //passport number not correspond
        user = buildUser(new int[]{1, 3, 4}, "MC1411654");
        newCertificate = buildCertificate(2, "MC2345657");
        parameters = new Object[]{user, newCertificate, false};
        parametersList.add(parameters);
        //new certificate is null
        user = buildUser(new int[]{1, 3, 4}, "MC1411654");
        parameters = new Object[]{user, null, false};
        parametersList.add(parameters);
        //user is null
        newCertificate = buildCertificate(2, "MC2345657");
        parameters = new Object[]{null, newCertificate, false};
        parametersList.add(parameters);
        //user and certificate are null;
        parameters = new Object[]{null, null, false};
        parametersList.add(parameters);

        return parametersList;
    }

    public CertificateCheckerTest(User user, Certificate newCertificate, boolean expected) {
        this.user = user;
        this.newCertificate = newCertificate;
        this.expected = expected;
    }

    /**
     * Test of checkCertificate method, of class CertificateChecker.
     */
    @Test
    public void testCheckCertificate() {
        XMLManager.parse("D:\\JavaCourse\\AdmissionSystem\\web");
        CertificateChecker instance =new CertificateChecker(new MessageManager());
        try{
        boolean result = instance.checkCertificate(newCertificate, user);
        assertEquals(expected, result);
        }catch(NoPropertyException ex){
            ex.printStackTrace();
        }
    }

    private static Certificate buildCertificate(int disciplineId, String passportNumber) {
        Certificate certificate=new Certificate();
        Discipline discipline = new Discipline(disciplineId, null, 0);
        certificate.setDiscipline(discipline);
        certificate.setPassportNumber(passportNumber);
        return certificate;
    }

    private static User buildUser(int[] disciplineIdArray, String passportNumber) {
        User user = new User();
        for (int disciplineId : disciplineIdArray) {
            user.addCertificate(buildCertificate(disciplineId, passportNumber));
        }
        return user;
    }
}
