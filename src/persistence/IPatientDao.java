package persistence;

import java.io.Closeable;
import java.util.List;

import model.Patient;

public interface IPatientDao extends Closeable
{
	
	/**
	 * liefert als Rückgabewert eine Liste aller in der Datenbank vorhandener Patienten
	 * @return Liste der Patienten
	 */
	public List<Patient> allePatienten();
	
	/**
	 * fügt den übergebenen Patienten in die Datenbank ein
	 * @param p Referenz auf einzufügenden Patienten
	 */
	public void newPatient(Patient p);
	
	/**
	 * aktualisiert die Daten des übergebenen Patienten in der Datenbank
	 * @param p Referenz auf zu aktualisierenden Patienten
	 */
	public void editPatient(Patient p);
	
	/**
	 * entfernt den übergebenen Patienten aus der Datenbank
	 * @param p Referenz auf den zu entfernenden Patienten
	 */
	public void deletePatient(Patient p);
	
}
