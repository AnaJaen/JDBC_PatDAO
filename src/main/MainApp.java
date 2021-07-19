package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Patient;
import persistence.IPatientDao;
import persistence.*;
import view.PatientEditorLayoutController;
import view.PatientEditorDialogController;



public class MainApp extends Application
{

	private Stage primaryStage;
	private BorderPane rootLayout;
	//private PatientEditorLayoutController layoutController;
	private IPatientDao patientDao;
	
	//los datos como observableList de pacientes
	private ObservableList<Patient> patientDaten = FXCollections.observableArrayList();
	
	
	//constructor
	public MainApp() {
		patientDao = new PatientDaoJdbc("org.sqlite.JDBC","jdbc:sqlite:patient.db","","");
		patientDaten = FXCollections.observableArrayList(patientDao.allePatienten());
	}
	
	//devolución de los pacientes introducidos
	public ObservableList<Patient> getPatientDaten() 
	{
		return patientDaten;
	}
	
	public IPatientDao getPatientDao()
	{
		return patientDao;
	}
	
	
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("PatientDAOApp"); //titulo app en ventana
		
		//llamamos al RootLayout y al PatientEditorLayout
		initRootLayout(); 
		showPatientEditorLayout();
	}
	

	//inicia el rootLayout
	public void initRootLayout() 
	{
		try {
			//cargamos el RootLayout.fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
	
			//se muestra la escena contenida en el rootLayout
			Scene scene = new Scene (rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}


	//se carga PatientEditorLayout.fxml y se muestra (overview)
	private void showPatientEditorLayout()
	{
		try
		{	//se carga la vista del paciente
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("..view/PatientEditorLayout.fxml"));
			AnchorPane patientEditorLayout = (AnchorPane) loader.load();
			
			// Set person overview into the center of root layout.
            rootLayout.setCenter(patientEditorLayout);
			
			//Da al controller acceso a la main app
			PatientEditorLayoutController controller = loader.getController();
			controller.setMainApp(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	 /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
	
	
	public boolean showPatientEditorDialog (Patient patient)
	{
		try
		{	//carga el archivo fxml y crea un nuevo stage para el diálogo popup 
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("..view/PatientEditorDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
		
			// Crea el dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Patient ändern");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        // Set the patient into the controller.
	        PatientEditorDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setPatient(patient);
	
	        // muestra el diálogo y espera hasta que el usuario lo cierra
	        dialogStage.showAndWait();
	
	        return controller.isOkClicked();
	    } 
		catch (IOException e) 
		{
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
	/**
     * Returns the main stage.
     * @return
     */
	public Stage getPrimaryStage() 
	{
		return primaryStage;
	}
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
}
