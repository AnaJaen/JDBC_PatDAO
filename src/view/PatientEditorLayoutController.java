package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.MainApp;
import model.Patient;
import java.time.format.DateTimeFormatter;


public class PatientEditorLayoutController
{		
	    //elementos tabla
	    @FXML
	    private TableView<Patient> patientTable;
	    @FXML
	    private TableColumn<Patient, String> vornameColumn;
	    private TableColumn<Patient, String> nachnameColumn;
	    
	   @FXML
	    private Label vornameLabel;
	    @FXML
	    private Label nachnameLabel;
	    @FXML
	    private Label adresseLabel;
	    @FXML
	    private Label geschlechtLabel;
	    @FXML
	    private Label svnrLabel;
	    @FXML
	    private Label geburtsdatumLabel;
	    @FXML
	    private Label krankenkasseLabel;
	
	    private MainApp mainApp;
	    
	    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	    
	    
	    /**
		 * The constructor. The constructor is called before the initialize() method.
		 */
	    public PatientEditorLayoutController() 
	    {
	    	
	    }
	    
	    
	    @FXML
	    private void initialize()
	    {
	    	vornameColumn.setCellValueFactory(cellData -> cellData.getValue().vornameProperty());
	    	nachnameColumn.setCellValueFactory(cellData -> cellData.getValue().nachnameProperty());
	    	
	    	//mostrar detalles paciente
	        showPatientDetails(null);
	       
	        patientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPatientDetails(newValue));
	        
	    }
	
	
		public MainApp getMainApp() 
		{
			return mainApp;
		}
		
	    public void setMainApp(MainApp mainApp)
	    {
			
	    	this.mainApp = mainApp;
			patientTable.setItems(mainApp.getPatientDaten());
		}
	    
	    /**
		 * Is called by the main application to give a reference back to itself.
		 * 
		 * @param mainEditor
		 */
	    
	    //Patientdetails
	    private void showPatientDetails(Patient patient) 
	    {
			if (patient != null) 
			{ //las etiquetas se rellenan con la info del paciente
				vornameLabel.setText(patient.getVorname());
				nachnameLabel.setText(patient.getNachname());
				adresseLabel.setText(patient.getAdresse());
				geschlechtLabel.setText(patient.getGeschlecht().toString());
				svnrLabel.setText(Long.toString(patient.getSvnr()));
				geburtsdatumLabel.setText(dateFormatter.format(patient.getGeburtsdatum()));
				krankenkasseLabel.setText(patient.getKrankenkasse());
			} 
			else
			{ //si no hay paciente las etiquetas se vacían
				vornameLabel.setText("");
				nachnameLabel.setText("");
				adresseLabel.setText("");
				geschlechtLabel.setText("");
				svnrLabel.setText("");
				geburtsdatumLabel.setText("");
				krankenkasseLabel.setText("");
			}
		}
	    
	    /**
		 * Called when the user clicks the new button. Opens a dialog to edit
		 * details for a new person.
		 */
	    //new
	    @FXML
	    private void handleNewPatient() 
	    {
	        Patient tempPatient = new Patient(0, null, null, null, null, null, 0, null);
	        boolean okClicked = mainApp.showPatientEditorDialog(tempPatient);
    		if (okClicked)
    		{
	            mainApp.getPatientDaten().add(tempPatient);
	            patientTable.getSelectionModel().select(tempPatient);
    		}
    		else
    		{
    			Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Keine Selektion");
	            alert.setHeaderText("Keinen Patient gewählt");
	            alert.setContentText("Bitte, wählen Sie einen Patient");
	
	            alert.showAndWait();
    		}
	    }
	    
	    /**
		 * Called when the user clicks the edit button. Opens a dialog to edit
		 * details for the selected person.
		 */
	    //edit
	    @FXML
	    void handleEditPatient()
	    {
	        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
	        if (selectedPatient != null) 
	        {
	        	boolean okClicked = mainApp.showPatientEditorDialog(selectedPatient);
	    		if (okClicked)
	    		{
	    			showPatientDetails(selectedPatient);		
	    		}
	        } 
	        else 
	        {
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Keine Selektion");
	            alert.setHeaderText("Keinen Patient gewählt");
	            alert.setContentText("Bitte, wählen Sie einen Patient");
	
	            alert.showAndWait();
	        }
	    } 
	    
	    /**
		 * Called when the user clicks on the delete button.
		 */
	    //delete
	    @FXML
	   	private void handleDeletePatient()
	    {
	   		int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();
	   		if (selectedIndex >= 0) 
	   		{
	   			patientTable.getItems().remove(selectedIndex);
	   		} 
	   		else 
	   		{
	   			Alert alert = new Alert(AlertType.WARNING);
	   			alert.initOwner(mainApp.getPrimaryStage());
	   			alert.setTitle("Keine Selektion");
	   			alert.setHeaderText("Keinen Patient gewählt");
	   			alert.setContentText("Bitte, wählen Sie einen Patient");
	   			alert.showAndWait();
	   		}
	    }  
}
