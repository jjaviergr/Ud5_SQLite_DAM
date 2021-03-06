package com.pmdm.ud5sqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Consulta extends Activity {
	EditText contacto, tlf;
	Spinner s;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consulta);

		s = (Spinner) findViewById(R.id.spinnerConsulta);
		contacto = (EditText) findViewById(R.id.editTextContactoConsulta);
		tlf = (EditText) findViewById(R.id.editTextTlfConsulta);
		final Button botonConsulta = (Button) findViewById(R.id.consultaBoton);
		final Button botonTlf = (Button) findViewById(R.id.BotonLlamaTlf);

		// instancia de la base de datos
		AdaptadorBD db = new AdaptadorBD(this);
		// abre la base de datos
		db.open();
		//obtiene todos los contactos de la BD
		Cursor cursor = db.getTodosContactos();
		//adaptador para manejar los ID de contactos devueltos por la consulta
		//(contexto, layout-adaptador, objeto cursor, datos a mostrar 
		SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor,
				new String[] { "_id" }, new int[] { android.R.id.text1 });
		//adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter2);
		//total de contactos devueltos en consulta
		int numElementos = cursor.getCount();
		//si no hay datos
		if (numElementos == 0) {
			botonConsulta.setEnabled(false);
			s.setEnabled(false);
			contacto.setEnabled(false);
			tlf.setEnabled(false);
			Toast.makeText(this,
					"Base de Datos Vac�a. Inserte contactos en Menu Principal",
					Toast.LENGTH_LONG).show();
		}
		db.close();
		
		//evento onClick bot�n 'ver datos contacto' 
		botonConsulta.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				//muestra datos del contacto seleccionado
				Imprime();
				botonTlf.setEnabled(true);
			}
		});
		//evento onClick bot�n "llamar por telefono"
		botonTlf.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {

				String tlfS = tlf.getText().toString();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ tlfS));
				startActivity(intent);
			}
		});

	}
	//imprime los datos del contacto seleccionado en el spinner en las View correspondientes
	public void Imprime() {

		Cursor c = (Cursor) s.getSelectedItem();
		contacto.setText(c.getString(1));
		tlf.setText(c.getString(3));

	}
}
