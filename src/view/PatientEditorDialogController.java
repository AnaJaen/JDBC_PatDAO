package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;


public class PatientEditorDialogController
{
	@FXML
	private TextField vornameField;
	@FXML
	private TextField nachnameField;
	@FXML
	private TextField adresseField;
	@FXML
	private ComboBox<Geschlecht> geschlechtCombo;
	@FXML
	private TextField svnrField;
	@FXML
	private DatePicker geburtsdatumField;
	@FXML
	private TextField krankenkasseField;
	
	
	private Stage dialogStage;
	private Patient patient;
	private boolean okClicked = false;
	
	private void initialize()
	{
		
	}
	
	public void setDialogStage(Stage dialogStage)
	{
		this.dialogStage = dialogStage;
	}
	
	public void setPatient(Patient patient)
	{
		this.patient = patient;
		
		vornameField.setText(patient.getVorname());
		nachnameField.setText(patient.getNachname());
		adresseField.setText(patient.getAdresse());
		geschlechtCombo.setValue(patient.getGeschlecht());
		geschlechtCombo.setItems(FXCollections.observableArrayList(Geschlecht.values()));
		svnrField.setText(Long.toString(patient.getSvnr()));
		geburtsdatumField.setValue(patient.getGeburtsdatum());
		krankenkasseField.setText(patient.getKrankenkasse());
		
	}
	
	public boolean isOkClicked()
	{
		return okClicked;
	}
	
	@FXML
	private void handleOk()
	{
		if (isInputValid())
		{
			patient.setVorname(vornameField.getText());
			patient.setNachname(nachnameField.getText());
			patient.setAdresse(adresseField.getText());
			patient.setGeschlecht(geschlechtCombo.getValue());
			patient.setSvnr(Long.parseLong(svnrField.getText()));
			patient.setGeburtsdatum(geburtsdatumField.getValue());
			patient.setKrankenkasse(krankenkasseField.getText());
			
			okClicked = true;
			dialogStage.close();
			
		}
	}
	
	@FXML
	private void handleCancel()
	{
		dialogStage.close();
	}
	
	private boolean isInputValid()
	{
		String errorMessage = "";
		
		if (vornameField.getText() == null || vornameField.getText().length() == 0)
		{
			errorMessage += "Fehler! Kein gültiger Vorname!\n";
		}
		
		if (nachnameField.getText() == null || nachnameField.getText().length() == 0)
		{
			errorMessage += "Fehler! Kein gültiger Nachname!\n";
		}
		
		if (adresseField.getText() == null || adresseField.getText().length() == 0)
		{
			errorMessage += "Fehler! Kein gültiger Adresse!\n";
		}
		
		if (geschlechtCombo.getValue() == null)
		{
			errorMessage += "Fehler! Wählen Sie ein Geschlecht!\n";
		}
		
		if (svnrField.getText() == null || svnrField.getText().length() == 0) 
		{
            errorMessage += "Fehler! Ungültige SVNR!\n"; 
        }
		else
		{
			try
			{
				Long.parseLong(svnrField.getText());
			}
			catch (NumberFormatException e)
			{
				errorMessage += "Fehler! Numerische SVNR eingeben!\n";
			}
		}
		
		if (geburtsdatumField.getValue() == null)
		{
			errorMessage += "Fehler! Kein gültiges Geburtsdatum eingegeben!\n";
		}
		
		if (krankenkasseField.getText() == null || krankenkasseField.getText().length() == 0)
		{
			errorMessage += "Fehler! Keine gültige Krankenkasse eingegeben!\n";
		}
		
		if (errorMessage.length() == 0)
		{
			return true;
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Ungültige Felder");
			alert.setHeaderText("Bitte, korrigieren Sie die Felder");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
		}
		
	}
	
}
