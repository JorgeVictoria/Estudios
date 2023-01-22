package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**Clase para controlar los diferentes elementos del apartado editar contactos de la aplicacion.
* La clase extiende de Main para poder hacer uso de los objetos Persona que aparecen en la agenda.
* En primer lugar aparecen todos los elementos del layout con los que se puede interactuar
* Posteriormente aparecen los metodos que controlan las distintas funciones de este apartado.
* @author Jorge Victoria Andreu
* @since 22dic2021
* @version 1.4
*/
public class EditarContactoController extends Main {
	
	//variables globales
	String email;		//para almacenar el email en el campo que nos encontramos
	String movil;		//para almacener el movil en el campo que nos encontramos
	int pos;			//almacena la posicion en la que nos encontramos mientras nos movemos por la coleccion
	
	//variables de los elementos del layout
	@FXML
	private TextField tfNombre;
	
	@FXML
	private TextField tfEmail;
	
	@FXML
	private TextField tfMovil;
	
	@FXML
	private TextField tfBuscar;
	
	@FXML
	private Label lbMensajes;
	
	@FXML
	private Button btnBuscar;
	
	@FXML
	private Button btnAnterior;
	
	@FXML
	private Button btnSiguiente;
	
	@FXML
	private Button btnGuardar;
	
	/**
	 * metodo para controlar el comportamiento inicial al cargar este scene.
	 * En este caso mostramos el primer elemento del array de objetos Persona.
	 * Si el array est� vacio, mostramos los correspodientes campos vacios.
	 */
	public void initialize() {
		
		//posicion inicial que vamos a cargar
		pos=0;
		
		//si la coleccion tiene datos, imprimimos la primera posicion, y sino los campos deben estar en blanco y los botones desactivados
		if(personas.size() > 0) {
			tfNombre.setText(personas.get(0).getNombre());
			tfEmail.setText(personas.get(0).getEmail());
			tfMovil.setText(personas.get(0).getMovil());
			//copiamos los valores del correo y telefono, para posterior comparacion si queremos 
			copiarTexto(personas.get(0).getEmail(), personas.get(0).getMovil());
			//como partimos de la primera posicion de la coleccion, el boton para retroceder en la coleccion lo desactivamos
			btnAnterior.setDisable(true);
			
		} else {
			tfNombre.setText("");
			tfEmail.setText("");
			tfMovil.setText("");
			btnSiguiente.setDisable(true);
			btnAnterior.setDisable(true);
			btnBuscar.setDisable(true);
			btnGuardar.setDisable(true);
		}
		
	}
	
	/**
	 * Metodo para buscar un usuario de la agenda.
	 * Comparamos el dato introducido con la coleccion de datos y
	 * si encontramos un dato igual, mostramos los datos y podemos modificar.
	 *  En caso contrario, mostramos un warning
	 * @param event: Listener que recoge el click sobre el boton
	 */
	@FXML
	public void buscarContacto(ActionEvent event) {
		
		boolean encontrado = false;
		
		for(int i = 0; i < personas.size(); i++) {
			if (tfBuscar.getText().toLowerCase().equals(personas.get(i).getNombre().toLowerCase()) ||   
				tfBuscar.getText().toLowerCase().equals(personas.get(i).getEmail().toLowerCase())  ||
				tfBuscar.getText().toLowerCase().equals(personas.get(i).getMovil().toLowerCase())) {
					encontrado = true;
					tfNombre.setText(personas.get(i).getNombre());
					tfEmail.setText(personas.get(i).getEmail());
					tfMovil.setText(personas.get(i).getMovil());
					lbMensajes.setText("contacto encontrado");
					copiarTexto(personas.get(i).getEmail(), personas.get(i).getMovil());
					
			}
		}
		
		if(!encontrado) lbMensajes.setText("contacto no encontrado");
		
	}
	
	/**
	 * Metodo para gestionar el boton siguiente y correr la coleccion hacia posiciones superiores.
	 *  Si llega a la ultima posicion del array, se desactiva
	 * @param event: Listener que recoge el click sobre el boton
	 */
	@FXML
	public void anteriorContacto(ActionEvent event) {
		
		if(pos > 0) pos--;
		if(pos < personas.size()-1) btnSiguiente.setDisable(false);
		if(pos == 0) btnAnterior.setDisable(true);
		
		tfNombre.setText(personas.get(pos).getNombre());
		tfEmail.setText(personas.get(pos).getEmail());
		tfMovil.setText(personas.get(pos).getMovil());
		copiarTexto(personas.get(pos).getEmail(), personas.get(pos).getMovil());
		
	}
	
	/**
	 * Metodo para gestionar el boton anterior y correr la coleccion hacia posiciones inferiores.
	 *  Si llega a la primers posicion del array, se desactiva.
	 * @param event: Listener que recoge el click sobre el boton
	 */
	@FXML
	public void siguienteContacto(ActionEvent event) {
		
		if(pos < personas.size()-1) pos++;
		if(pos > 0) btnAnterior.setDisable(false);
		if(pos == personas.size()-1) btnSiguiente.setDisable(true);
		
		tfNombre.setText(personas.get(pos).getNombre());
		tfEmail.setText(personas.get(pos).getEmail());
		tfMovil.setText(personas.get(pos).getMovil());
		copiarTexto(personas.get(pos).getEmail(), personas.get(pos).getMovil());
		
	}
	
	/**
	 * Metodo para guardar los cambios introducidos
	 * @param event: Listener que recoge el click sobre el boton
	 */
	@FXML
	public void guardarContacto(ActionEvent event) {
		
		
		boolean datoValido = true;
		
		//tenemos que comprobar que el campo que hemos modificado no sea igual al que tenemos almacenado en los temporales
		//he usado dos variables temporales que alamacenan el email y tfno del registro que tenemos en pantalla
		//si hemos modificado algun campo, no coincide con el temporal y se guarda el refgistro
		//si pulsamos el boton guardar pero no han habido cambios, lo indicamos en un warning
		if(!tfEmail.getText().toLowerCase().equals(email.toLowerCase()) || !tfMovil.getText().toLowerCase().equals(movil.toLowerCase())) {
			
			//coprobamos que el campo movil tenga datos numericos
			try {
				 Integer.parseInt(tfMovil.getText());
			}catch (Exception ex) {
				lbMensajes.setText("El numero no es correcto");
				datoValido = false;
			}
			
			//si el dato del campo movil es correcto y tiene una longitud de nueve digitos, guardamos los datos
			if(datoValido && tfMovil.getText().length() == 9) {
				
				personas.get(pos).setEmail(tfEmail.getText());
				
				personas.get(pos).setMovil(tfMovil.getText());
				lbMensajes.setText("datos modificados");
			}
			else lbMensajes.setText("El numero no es correcto");
			
			
		}
		else lbMensajes.setText("No hay nada que modificar");
		
	}
	
	/**
	 * Metodo para guardar de forma temporal los datos que mostramos en el layout.
	 * Estos datos se van guardando conforme avanzamos o retrocedemos en el listado.
	 * Lo utilizaremos en el caso que el usuario decida modificar algun dato, para comparar y detectar los cambios
	 * @param correo: direccion de correo electronico del usuario
	 * @param tfno: Telefono movil del usuario
	 */
	public void copiarTexto(String correo, String tfno) {
		
		 email= correo;
		 movil = tfno;
	}
	
	
	
	
	

}
