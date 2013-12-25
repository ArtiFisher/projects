package by.bsuir.admission.xml;

import by.bsuir.admission.resource.Resource;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

enum PropertyEnum {

    MAX_CERTIFICATE_NUMBER, STATE,
    HOST, PORT, USER, PASSWORD, PROTOCOL;
}

/**
 * This class test the action class <code>XMLManagerTest</code>
 * @author AndreAY
 */
@RunWith(Parameterized.class)
public class XMLManagerTest {

    private String name;
    public static final String NO_PROPERTY = "error.no.property";

    @Parameters
    public static Collection<Object[]> resourceValues() {
        Collection<Object[]> parametersList = new ArrayList<Object[]>();
        parametersList.add(new Object[]{"max-certificate-number"});
        parametersList.add(new Object[]{"state"});
        parametersList.add(new Object[]{"host"});
        parametersList.add(new Object[]{"port"});
        parametersList.add(new Object[]{"user"});
        parametersList.add(new Object[]{"password"});
        parametersList.add(new Object[]{"protocol"});
        parametersList.add(new Object[]{"otherProperty"});
        return parametersList;
    }

    public XMLManagerTest(String name) {
        this.name = name;
    }

    /**
     * Test of getFirstElement method, of class XMLManager.
     * This method checks presence a parameter in XML file
     */
    @Test
    public void testGetFirstElement() {
        XMLManager.parse("D:\\JavaCourse\\AdmissionSystem\\web");
        try {
            String result = XMLManager.getFirstElement(name);
            assertTrue(result != null);
        } catch (NoPropertyException ex) {
            try {
                PropertyEnum.valueOf(name.toUpperCase().replace("-", "_"));
                fail();
            } catch (IllegalArgumentException er) {
                assertEquals(ex.getMessage(), Resource.getStr(NO_PROPERTY) + name);
            }
        }
    }
}
