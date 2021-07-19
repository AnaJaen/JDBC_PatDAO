package persistence;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Geschlecht;
import model.Patient;
import persistence.IPatientDao;


public class PatientDaoJdbc implements IPatientDao
{
	// SQL statements
	private final static String SELECT_ALL_PATIENTEN = "SELECT ID, VORNAME, NACHNAME, ADRESSE, GESCHLECHT, GEBURTSDATUM, SVNR, KRANKENKASSE FROM patient";
	private final static String INSERT_PATIENT = "INSERT INTO patient (VORNAME, NACHNAME ADRESSE, GESCHLECHT, GEBURTSDATUM, SVNR, KRANKENKASSE) VALUES (?,?, ?, ?, ?, ?)";
	private final static String UPDATE_PATIENT = "UPDATE patient SET VORNAME=?, NACHNAME=?, ADRESSE=?, GESCHLECHT=?, GEBURTSDATUM=?, SVNR=?, KRANKENKASSE=? WHERE ID=?";      
	private final static String DELETE_PATIENT = "DELETE FROM patient WHERE ID=?";
	
	private Connection con;
	private final static Logger LOGGER = Logger.getLogger(PatientDaoJdbc.class.getName());
	
	// Open database connection
	public PatientDaoJdbc(String driver, String url, String user, String pwd)
	{
		try
		{
			Class.forName(driver);
			LOGGER.log(Level.INFO, "Driver loaded");
			con = DriverManager.getConnection(url, user, pwd);
			LOGGER.log(Level.INFO, "DB connection established");
		} 
		catch (ClassNotFoundException e)
		{
			LOGGER.log(Level.SEVERE, "Driver error", e);
		}
		catch (Exception e) 
		{
			LOGGER.log(Level.SEVERE, "DB connection error", e);
		}
	}
	
	// Close database connection
		@Override
		public void close()
		{
			try
			{
				if (con != null)
				{
					con.close();
					LOGGER.log(Level.INFO, "DB connection closed");
				}
			} 
			catch (Exception e)
			{
				LOGGER.log(Level.SEVERE, "Error closing DB", e);
			}

		}
		
		// Get all persons from database
		@Override
		public List<Patient> allePatienten()
		{
			List<Patient> patients = new ArrayList<>();
			try (PreparedStatement pstmt = con.prepareStatement(SELECT_ALL_PATIENTEN))
			{
				LOGGER.log(Level.INFO, "SQL: SELECT all patients");
				try (ResultSet rs = pstmt.executeQuery())
				{
					while (rs.next())
					{
						
						// SQLite
						Patient p = new Patient(rs.getInt("ID"), rs.getString("VORNAME"), rs.getString("NACHNAME"),
								Geschlecht.valueOf(rs.getString("GESCHLECHT")), LocalDate.parse(rs.getString("GEBURTSDATUM")), rs.getString("ADRESSE"), Long.valueOf(rs.getInt("SVNR")).longValue(), rs.getString("KRANKENKASSE"));
						patients.add(p);
					}
				} catch (SQLException ex)
				{
					LOGGER.log(Level.SEVERE, "Error getting patients", ex);
				}
			} 
			catch (SQLException ex)
			{
				LOGGER.log(Level.SEVERE, "Error getting patients", ex);
			}
			return patients;
		}

		// Insert new person into database
		@Override
		public void newPatient(Patient p)
		{
			String generatedColumns[] = {"ID" };
			
			try (PreparedStatement pstmt = con.prepareStatement(INSERT_PATIENT, generatedColumns))
			{
				pstmt.setString(1, p.getVorname());
				pstmt.setString(2, p.getNachname());
				pstmt.setString(3, p.getGeschlecht().toString());
				pstmt.setString(4, p.getGeburtsdatum().toString());
				pstmt.setString(5, p.getAdresse());
				pstmt.setInt(6, (int) p.getSvnr());
				pstmt.setString(7, p.getKrankenkasse());
				
				// SQLite:
				LOGGER.log(Level.INFO, "SQL: INSERT " + p);
				pstmt.executeUpdate();
				try (ResultSet rs = pstmt.getGeneratedKeys())
				{
					if (rs.next())
					{
						p.setId(rs.getInt(1));
						LOGGER.log(Level.INFO, "New ID: " + p.getId());
					} 
					else
					{
						LOGGER.log(Level.SEVERE, "Error getting primary key");
					}
				}
			} 
			catch (SQLException ex)
			{
				LOGGER.log(Level.SEVERE, "Error inserting person", ex);
			}
		}

		// Update existing person in database
		@Override
		public void editPatient(Patient p)
		{
			try (PreparedStatement pstmt = con.prepareStatement(UPDATE_PATIENT))
			{
				pstmt.setString(1, p.getVorname());
				pstmt.setString(2, p.getNachname());
				pstmt.setString(3, p.getGeschlecht().toString());
				pstmt.setString(4, p.getGeburtsdatum().toString());
				pstmt.setString(5, p.getAdresse());
				pstmt.setInt(6, (int) p.getSvnr());
				pstmt.setString(7, p.getKrankenkasse());
				
				// SQLite:
				pstmt.setInt(8, p.getId());
				LOGGER.log(Level.INFO, "SQL: UPDATE " + p);
				if (pstmt.executeUpdate() != 1)
				{
					LOGGER.log(Level.SEVERE, "Patient not existing");
				}
			} 
			catch (SQLException ex)
			{
				LOGGER.log(Level.SEVERE, "Error updating patient", ex);
			}
		}

		// Delete person from database
		@Override
		public void deletePatient(Patient p)
		{
			try (PreparedStatement pstmt = con.prepareStatement(DELETE_PATIENT))
			{
				pstmt.setInt(1, p.getId());
				LOGGER.log(Level.INFO, "SQL: DELETE " + p);
				if (pstmt.executeUpdate() != 1)
				{
					LOGGER.log(Level.SEVERE,"Patient not existing");
				}
			}
			catch (SQLException ex)
			{
				LOGGER.log(Level.SEVERE, "Error deleting patient", ex);
			}
	}
}
