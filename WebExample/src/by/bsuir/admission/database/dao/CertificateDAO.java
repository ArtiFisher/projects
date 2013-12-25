package by.bsuir.admission.database.dao;

import by.bsuir.admission.database.DBConnector;
import by.bsuir.admission.model.beans.Certificate;
import by.bsuir.admission.model.beans.Discipline;
import by.bsuir.admission.resource.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
/**
 * This class implements the DAO pattern and contains methods that bind
 * the entity <code>Certificate</ code> with the database
 * @author AndreAY
 */
public class CertificateDAO extends AbstractDAO {

    public static final String SQL_GET_USER_CERTIFICATES = "sql.get.user.certificates";
    public static final String SQL_GET_CERTIFICATE_BY_ID_AND_PASSPORT = "sql.get.certificate.by.id.and.passport";
    public static final String SQL_SET_CERTIFICATE_TO_USER = "sql.set.certificate.to.user";
/**
 * This method return a map of users certificates
 * @param userId a id of user
 * @return a map of users certificates
 * @throws SQLException a SQLException
 */
    public HashMap<Integer, Certificate> getUserCertificates(int userId) throws SQLException {
        connector=new DBConnector();
        HashMap<Integer, Certificate> certificatesMap = new HashMap<Integer, Certificate>();
        try {
            Statement statement = connector.getStatement();
            resultSet = statement.executeQuery(Resource.getStr(SQL_GET_USER_CERTIFICATES) + String.valueOf(userId) + "'");
            while (resultSet.next()) {
                Certificate currentCertificate = new Certificate();
                Discipline discipline = new Discipline();
                currentCertificate.setCertificateId(resultSet.getInt(1));
                currentCertificate.setPassportNumber(resultSet.getString(2));
                currentCertificate.setMark(resultSet.getInt(3));
                discipline.setName(resultSet.getString(4));
                discipline.setDisciplineId(resultSet.getInt(5));
                currentCertificate.setDiscipline(discipline);
                certificatesMap.put(discipline.getDisciplineId(), currentCertificate);
            }
        } finally {
            close();
        }
        return certificatesMap;
    }
/**
 * This method find a certificate by certificate number and passport number
 * @param certificate a certificate which sought
 * @return a certificate if it exist
 * @throws SQLException a SQLException
 */
    public Certificate getCertificateByIdAndPassport(Certificate certificate) throws SQLException {
        connector=new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_GET_CERTIFICATE_BY_ID_AND_PASSPORT));
            statement.setInt(1, certificate.getCertificateId());
            statement.setString(2, certificate.getPassportNumber());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Discipline discipline = new Discipline();
                certificate.setMark(resultSet.getInt(1));
                discipline.setName(resultSet.getString(2));
                discipline.setDisciplineId(resultSet.getInt(3));
                certificate.setDiscipline(discipline);
            }else{
                certificate=null;
            }
        } finally {
            close();
        }
        return certificate;
    }
/**
 * This method sets the certificate to user
 * @param userId a user to which sets the certificate
 * @param certificateId the certificate
 * @throws SQLException a SQLException
 */
    public void setCeritificateToUser(int userId, int certificateId) throws SQLException {
        connector=new DBConnector();
        try {
            PreparedStatement statement = connector.getPreparedStatement(Resource.getStr(SQL_SET_CERTIFICATE_TO_USER));
            statement.setInt(1, userId);
            statement.setInt(2, certificateId);
            statement.executeUpdate();
        } finally {
            close();
        }
    }
}
