package model;

import javafx.beans.property.*;
import java.time.LocalDate;


public class Person
{
	private int id;
	private final StringProperty vorname;
	private final StringProperty nachname;
	private final ObjectProperty <Geschlecht> geschlecht;
	private final ObjectProperty <LocalDate> geburtsdatum;
	private final StringProperty adresse;
	private final LongProperty svnr;
	

	
	public Person(int id, String vorname, String nachname, Geschlecht geschlecht, LocalDate geburtsdatum, String adresse, long svnr)
	{
		this.id = id;
		this.vorname = new SimpleStringProperty (vorname);
		this.nachname = new SimpleStringProperty (nachname);
		this.geschlecht = new SimpleObjectProperty <Geschlecht> (geschlecht);
		this.geburtsdatum = new SimpleObjectProperty <LocalDate> (geburtsdatum);
		this.adresse = new SimpleStringProperty (adresse);
		this.svnr = new SimpleLongProperty (svnr);
	}
	
	public Person(String vorname, String nachname)
	{
		this.vorname = new SimpleStringProperty(vorname);
		this.nachname = new SimpleStringProperty(nachname);
		
		this.geschlecht = new SimpleObjectProperty<Geschlecht>(Geschlecht.MAENNLICH);
		this.geburtsdatum = new SimpleObjectProperty<LocalDate>(LocalDate.of(2003, 05, 23));
		this.adresse = new SimpleStringProperty("Innere Stadt");
		this.svnr = new SimpleLongProperty(2323);

	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getVorname()
	{
		return vorname.get();
	}
	
	public void setVorname(String vorname)
	{
		this.vorname.set(vorname);
	}
	
	public StringProperty vornameProperty()
	{
		return vorname;
	}
	
	public String getNachname()
	{
		return nachname.get();
	}
	
	public void setNachname(String nachname)
	{
		this.nachname.set(nachname);
	}
	
	public StringProperty nachnameProperty()
	{
		return nachname;
	}
	
	public Geschlecht getGeschlecht()
	{
		return geschlecht.get();
	}
	
	public void setGeschlecht(Geschlecht geschlecht)
	{
		this.geschlecht.set(geschlecht);
	}
	
	public ObjectProperty<Geschlecht> geschlechtProperty()
	{
		return geschlecht;
	}
	
	public String getAdresse()
	{
		return adresse.get();
	}
	
	public void setAdresse(String adresse)
	{
		this.vorname.set(adresse);
	}
	
	public StringProperty adresseProperty()
	{
		return adresse;
	}
	
	public long getSvnr()
	{
		return svnr.get();
	}
	
	public void setSvnr(long svnr)
	{
		this.svnr.set(svnr);
	}
	
	public LongProperty svnrProperty()
	{
		return svnr;
	}
	
	public LocalDate getGeburtsdatum()
	{
		return geburtsdatum.get();
	}
	
	public void setGeburtsdatum(LocalDate geburtsdatum)
	{
		this.geburtsdatum.set(geburtsdatum);
	}
	
	public ObjectProperty<LocalDate> geburtsdatumProperty()
	{
		return geburtsdatum;
	}
	
	
	
	@Override
	public String toString() 
	{
		return ("Vorname: " + vorname + ", Nachname: "+ nachname + ", SVNR: " + svnr + 
				", GebDatum: " + geburtsdatum + ", Geschlecht: " + geschlecht + ", adresse: " + adresse);
	}
}
