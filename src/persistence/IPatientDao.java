package persistence;

import java.io.Closeable;
import java.util.List;

import model.Patient;

public interface IPatientDao extends Closeable
{
	
	/**
	 * liefert als R�ckgabewert eine Liste aller in der Datenbank vorhandener Patienten
	 * @return Liste der Patienten
	 */
	public List<Patient> allePatienten();
	
	/**
	 * f�gt den �bergebenen Patienten in die Datenbank ein
	 * @param p Referenz auf einzuf�genden Patienten
	 */
	public void newPatient(Patient p);
	
	/**
	 * aktualisiert die Daten des �bergebenen Patienten in der Datenbank
	 * @param p Referenz auf zu aktualisierenden Patienten
	 */
	public void editPatient(Patient p);
	
	/**
	 * entfernt den �bergebenen Patienten aus der Datenbank
	 * @param p Referenz auf den zu entfernenden Patienten
	 */
	public void deletePatient(Patient p);
	
}
