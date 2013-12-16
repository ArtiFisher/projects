package by.bsuir.admission.model.action.report;

import by.bsuir.admission.model.beans.Application;
import java.util.Comparator;

/**
 * This class compares the applications by first with
 * the user surname and then on user first name
 * @author AndreAY
 */
public class ApplicationsComparatorByUserName<T> implements Comparator<Application> {

    /**
     * This method compares the applications by first with
     * the user surname and then on user first name
     * @param o1 a first application
     * @param o2 a second application
     * @return a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second
     */
    public int compare(Application o1, Application o2) {
        int result = o1.getUser().getSurname().compareTo(o2.getUser().getSurname());
        if (result == 0) {
            result = o1.getUser().getFirstName().compareTo(o2.getUser().getFirstName());
        }
        return result;
    }
}
