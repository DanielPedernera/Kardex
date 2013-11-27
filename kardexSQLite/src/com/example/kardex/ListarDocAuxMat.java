package com.example.kardex;

import com.example.kardex.R.id;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListarDocAuxMat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_doc_aux_mat);
		
		String V[] = { "Error en lectura" };
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
				listdocAux(db, V);
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
			
		
	}

	
	private void listdocAux(SQLiteDatabase db, String[] V) {
		String[] columnas = new String[] { "idDocente", "Nombre",
				"Paterno", "Materno", "Grado", "Titular" };
		try {
			Cursor cursor = db.query("Docente", columnas, null, null, null,
					null, null);
			String resultado = "";
			int iCod = cursor.getColumnIndex("idDocente");
			int iNom = cursor.getColumnIndex("Nombre");
			int iPat = cursor.getColumnIndex("Paterno");
			int iMat = cursor.getColumnIndex("Materno");
			int iGrado = cursor.getColumnIndex("Grado");
			int iTitular = cursor.getColumnIndex("Titular");
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				resultado = resultado + cursor.getString(iCod) + "   "
						+ cursor.getString(iNom) + "   "
						+ cursor.getString(iPat) + "   "
						+ cursor.getString(iMat) + "   "
						+ cursor.getString(iGrado) + "   "
						+ cursor.getString(iTitular) + "   " + "\n";
			}
			V = resultado.split("\n");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en listado " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.filasimple, V);
		ListView miLista = (ListView) findViewById(id.listadeauxdocmat);
		miLista.setAdapter(adapter);

	}
	

}
