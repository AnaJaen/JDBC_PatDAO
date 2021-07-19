package model;

import javafx.beans.property.*;
import java.time.LocalDate;


public class Patient extends Person
{
	private final StringProperty krankenkasse;
	

	public Patient(int id, String vorname, String nachname, Geschlecht geschlecht, LocalDate geburtsdatum,
			String adresse, long svnr, String krankenkasse)
	{
		super(id, vorname, nachname, geschlecht, geburtsdatum, adresse, svnr);
		this.krankenkasse = new SimpleStringProperty(krankenkasse);
	}
	
	
	public String getKrankenkasse()
	{
		return krankenkasse.get();
	}

	 public void setKrankenkasse(String krankenkasse) {
	    	this.krankenkasse.set(krankenkasse);
	 }
	    public StringProperty krankenkasseProperty() {
	         return krankenkasse;
	        }	

}
