package com.pmdm.ud5sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddContactos extends Activity {
	EditText edNombre;
	EditText edTlf;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontactos);

		final Spinner s = (Spinner) findViewById(R.id.spinnerAdd);
		edNombre = (EditText) findViewById(R.id.editTextNombre);
		edTlf = (EditText) findViewById(R.id.editTextTelefono);

		// adapatador para manejar el spinner con los tipos de contacto
		//La lista de valores del spinner definida en values/tipos.xml como un array
		ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
				R.array.tipos, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);

		final Button botonAdd = (Button) findViewById(R.id.AddContactoBoton);

		// evento onClick del bot�n 'A�adir contactos'
		botonAdd.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				String nombre = edNombre.getText().toString();
				String tlf = edTlf.getText().toString();
				String tipo = (String) s.getSelectedItem();
				// a�ade los datos del contacto a la base de datos
				addBDContacto(nombre, tlf, tipo);
			}
		});
	}

	// m�todo para a�adir un contacto a la base de datos
	public void addBDContacto(String nombre, String tlf, String tipo) {

		if ((nombre.length() != 0) && (tlf.length() != 0)) {
			//obtiene una instancia de la base de datos
			AdaptadorBD db = new AdaptadorBD(this);
			//abre la BD
			db.open();
			//inserta un nuevo contacto
			db.insertarContacto(nombre, tipo, tlf);
			//cierra conexi�n
			db.close();
			Toast.makeText(this, "Almacenamiento base de datos correcta",
					Toast.LENGTH_SHORT).show();
			edNombre.setText("");
			edTlf.setText("");
		} else
			Toast.makeText(this, "Todos los campos son obligatorios",
					Toast.LENGTH_SHORT).show();
	}

}
