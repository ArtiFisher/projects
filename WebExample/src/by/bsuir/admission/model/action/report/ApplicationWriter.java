package by.bsuir.admission.model.action.report;

import by.bsuir.admission.model.beans.Application;
import by.bsuir.admission.model.beans.Faculty;
import by.bsuir.admission.model.beans.Speciality;
import by.bsuir.admission.model.beans.University;
import by.bsuir.admission.resource.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * This class of records in excel file list of users who enter a university
 * @author AndreAY
 */
public class ApplicationWriter {

    /**
     * This is a Excel book
     */
    private WritableWorkbook workbook;
    /**
     * This is a current sheet in excel file
     */
    private WritableSheet currentSheet;
    /**
     * This is a number of current sheet
     */
    private int currentSheetNumber = 0;
    /**
     * This is a number of current row in the current sheet
     */
    private int currentRow;
    /**
     * This is a number of current column in the current sheet
     */
    private int currentColumn;
    public static final String TITLE_PAID = "jsp.table.paid";
    public static final String TITLE_NO_PAID = "jsp.table.no.paid";
    public static final String TITLE_FIRST_NAME = "jsp.table.applications.first.name";
    public static final String TITLE_SURNAME = "jsp.table.applications.surname";
    public static final String TITLE_APPLICATION_NUMBER = "title.application.number";

    /**
     * This constructor creates a new Excel book
     * @param fileName a name of new Excel file
     * @throws IOException an exception creating file
     */
    public ApplicationWriter(String fileName) throws IOException {
        workbook = Workbook.createWorkbook(new File(fileName));
    }

    /**
     * This method creates a new worksheet and displays the name of the University
     * @param university a writing university
     * @throws WriteException an exception writing in excel file
     */
    public void writeUniversity(University university) throws WriteException {
        currentRow = 0;
        currentColumn = 0;
        String sheetName=String.valueOf(currentSheetNumber+1)+") "+university.getName();
        currentSheet = workbook.createSheet(sheetName, currentSheetNumber);
        currentSheetNumber++;
        Label label = new Label(currentColumn, currentRow, university.getName());
        currentRow++;
        currentSheet.addCell(label);
    }

    /**
     * This method writes a name of faculty
     * @param faculty a current faculty
     * @throws WriteException an exception writing in excel file
     */
    public void writeFaculty(Faculty faculty) throws WriteException {
        currentColumn = 0;
        currentRow++;
        Label label = new Label(currentColumn, currentRow, faculty.getName());
        currentSheet.addCell(label);
        currentRow++;
    }

    /**
     * This method writes a name of speciality
     * @param speciality a current speciality
     * @param isPaid true if this is paid part of speciality
     * @throws WriteException an exception writing in excel file
     */
    public void writeSpeciality(Speciality speciality, boolean isPaid) throws WriteException {
        currentColumn = 0;
        currentRow++;
        Label label = new Label(currentColumn, currentRow, speciality.getName());
        currentSheet.addCell(label);
        currentRow++;
        if (isPaid) {
            label = new Label(currentColumn, currentRow, Resource.getStr(TITLE_PAID));
        } else {
            label = new Label(currentColumn, currentRow, Resource.getStr(TITLE_NO_PAID));
        }
        currentSheet.addCell(label);
        currentRow++;
    }

    /**
     * This method displays the table header and a list of all admissions
     * @param applicationList a list of application
     * @param passMark a pass mark to current speciality
     * @throws WriteException an exception writing in excel file
     */
    public void writeApplications(ArrayList<Application> applicationList, int passMark) throws WriteException {
        currentColumn = 0;
        Label label = new Label(currentColumn, currentRow, Resource.getStr(TITLE_APPLICATION_NUMBER));
        currentSheet.addCell(label);
        currentColumn++;
        label = new Label(currentColumn, currentRow, Resource.getStr(TITLE_SURNAME));
        currentSheet.addCell(label);
        currentColumn++;
        label = new Label(currentColumn, currentRow, Resource.getStr(TITLE_FIRST_NAME));
        currentSheet.addCell(label);
        int applicationNumber = 0;
        for (Application application : applicationList) {
            if (application.getUser().getTotalMark() >= passMark) {
                applicationNumber++;
                writeApplication(application, applicationNumber);
                currentRow++;
            }
        }
        currentRow++;
    }

    /**
     * This method writes an application
     * @param application a writing application
     * @param applicationNumber a number of current application in speciality
     * @throws WriteException an exception writing in excel file
     */
    private void writeApplication(Application application, int applicationNumber) throws WriteException {
        currentColumn = 0;
        Label label = new Label(currentColumn, currentRow, String.valueOf(applicationNumber));
        currentSheet.addCell(label);
        currentColumn++;
        label = new Label(currentColumn, currentRow, application.getUser().getSurname());
        currentSheet.addCell(label);
        currentColumn++;
        label = new Label(currentColumn, currentRow, application.getUser().getFirstName());
        currentSheet.addCell(label);
    }

    /**
     * This method tries to write the work book to hard drive and close work book
     * @throws IOException an exception writing in file
     * @throws WriteException an exception writing in excel file
     */
    public void close() throws IOException, WriteException {
        try {
            workbook.write();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}
