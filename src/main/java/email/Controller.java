package email;

import java.io.IOException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
//import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class Controller {

	@FXML
	private GridPane view;
	@FXML
	private TextField servidorText, puertoServidorText, emailRemitenteText, emailDestinatarioText, asuntoText;
	@FXML
	private PasswordField remitentePass;
	@FXML
	private CheckBox sslCheck;
	@FXML
	private TextArea mensajeText;
	@FXML
	private Button enviarButton, vaciarButton, cerrarButton;

	private Model model = new Model();

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();

		servidorText.textProperty().bindBidirectional(model.servidorProperty());
		puertoServidorText.textProperty().bindBidirectional(model.puertoProperty());
		emailRemitenteText.textProperty().bindBidirectional(model.remitenteProperty());
		remitentePass.textProperty().bindBidirectional(model.contraseñaProperty());
		emailDestinatarioText.textProperty().bindBidirectional(model.destinatarioProperty());
		asuntoText.textProperty().bindBidirectional(model.asuntoProperty());
		mensajeText.textProperty().bindBidirectional(model.mensajeProperty());
		sslCheck.selectedProperty().bindBidirectional(model.sslProperty());
	}

	@FXML
	private void onEnviarAction(ActionEvent e) {
		Alert alert;
		try {
			Email email = new SimpleEmail();
			email.setHostName(model.getServidor());
			email.setSmtpPort(Integer.parseInt(model.getPuerto()));
			email.setSSLOnConnect(model.isSsl());
			email.setFrom(model.getRemitente());
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getContraseña()));
			email.addTo(model.getDestinatario());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());
			email.send();

			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Mensaje enviado");
			alert.setHeaderText("Mensaje enviado con éxito a \'" + emailDestinatarioText.getText() + "\'.");
			alert.show();
		} catch (Exception e1) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se pudo enviar el email.");
			alert.setContentText(e1.getMessage());
			alert.show();
		}
	}

	@FXML
	private void onVaciarAction(ActionEvent e) {
		model.setServidor(null);
		model.setPuerto(null);
		model.setSsl(false);
		model.setRemitente(null);
		model.setContraseña(null);
		model.setDestinatario(null);
		model.setAsunto(null);
		model.setMensaje(null);
	}

	@FXML
	private void onCerrarAction(ActionEvent e) {
		App.primaryStage.close();
	}

	public GridPane getView() {
		return view;
	}
}
