package email;

import java.io.IOException;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
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

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}

	@FXML
	private void onEnviarAction(ActionEvent e) {
		Alert alert;
		try {
			Email email = new SimpleEmail();
			email.setHostName(servidorText.getText());
			email.setSmtpPort(Integer.parseInt(puertoServidorText.getText()));
			email.setSSLOnConnect(sslCheck.isSelected());
			email.setFrom(emailRemitenteText.getText());
			email.setAuthenticator(new DefaultAuthenticator(emailRemitenteText.getText(), remitentePass.getText()));
			email.addTo(emailDestinatarioText.getText());
			email.setSubject(asuntoText.getText());
			email.setMsg(mensajeText.getText());
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
		servidorText.setText(null);
		puertoServidorText.setText(null);
		sslCheck.setSelected(false);
		emailRemitenteText.setText(null);
		remitentePass.setText(null);
		emailDestinatarioText.setText(null);
		asuntoText.setText(null);
		mensajeText.setText(null);
	}

	@FXML
	private void onCerrarAction(ActionEvent e) {
		App.primaryStage.close();
	}

	public GridPane getView() {
		return view;
	}
}
