package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Auxiliatura extends Activity implements OnItemSelectedListener{
	Spinner estudiantes;
	Spinner docentes;
    Spinner materias;
    Spinner paralelos;
    String triplas="";
    String [] docs;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auxiliatura);
		String VD[] = { "Error en lectura" };
		String V[] = { "Error en lectura" };
		String VE[] = { "Error en lectura" };
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			
			VE=LeerEstudiantes(db);
			ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
					R.layout.filasimple, VE);
			estudiantes = (Spinner) findViewById(R.id.cboxStudents);
			estudiantes.setAdapter(adapter3);
			
			VD=LeerDocentes(db);
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
					R.layout.filasimple, VD);
			docentes = (Spinner) findViewById(R.id.cboxDoc);
			docentes.setAdapter(adapter2);
			
			
				V=LeerMaterias(db, null);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.filasimple, V);
			materias = (Spinner) findViewById(R.id.cboxAsignatures);
			materias.setOnItemSelectedListener(this);
			materias.setAdapter(adapter);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
						.show();
			}			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auxiliatura, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	@SuppressWarnings("finally")
	public String[]LeerMaterias(SQLiteDatabase db, String id){
		String V[] = { "Error en lectura" };
		try {
			String[] columnas = new String[] { "Sigla", "Descripcion"};				
			Cursor cursor = db.query("Materia", columnas, null, null, null,
					null, null);

			String resultado = "";
			int iSig = cursor.getColumnIndex("Sigla");
			int iDes = cursor.getColumnIndex("Descripcion");
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				resultado = resultado + cursor.getString(iSig) + "   "
						+ cursor.getString(iDes) + "   "
						+ "\n";
			}
			V=resultado.split("\n");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en listado " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		finally{
			return V;
		}
	}
	@SuppressWarnings("finally")
	public String[]LeerDocentes(SQLiteDatabase db){
		String V[] = { "Error en lectura" };
		String[] columnasD = new String[] { "idDocente", "Nombre",
				"Paterno", "Materno"};
		try {
			Cursor cursorD = db.query("Docente", columnasD, null, null, null,
					null, null);
			String docentes ="";
			int iNom = cursorD.getColumnIndex("Nombre");
			int iPat = cursorD.getColumnIndex("Paterno");
			int iMat = cursorD.getColumnIndex("Materno");
			int iCodD = cursorD.getColumnIndex("idDocente");
			
			for (cursorD.moveToFirst(); !cursorD.isAfterLast(); cursorD
					.moveToNext()) {
				docentes = docentes
						+cursorD.getString(iCodD) + "   "
						+ cursorD.getString(iNom) + "   "
						+ cursorD.getString(iPat) + "   "
						+ cursorD.getString(iMat) + "   "
					    + "\n";
			}
			V = docentes.split("\n");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en listado " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		finally{
			return V;
		}
	}
	
	@SuppressWarnings("finally")
	public String[] LeerEstudiantes(SQLiteDatabase db)
	{
		String V[] = { "Error en lectura" };
		String[] columnas = new String[] { "idEstudiante", "Nombre",
				"Paterno", "Materno" };
		try {
			Cursor cursor = db.query("Estudiante", columnas, null, null, null,
					null, null);
			String resultado = "";
			int iCod = cursor.getColumnIndex("idEstudiante");
			int iNom = cursor.getColumnIndex("Nombre");
			int iPat = cursor.getColumnIndex("Paterno");
			int iMat = cursor.getColumnIndex("Materno");
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				resultado = resultado + cursor.getString(iCod) + "   "
						+ cursor.getString(iNom) + "   "
						+ cursor.getString(iPat) + "   "
						+ cursor.getString(iMat) + "   "+ "\n";
			}
			V = resultado.split("\n");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en listado " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		finally{
			return V;
		}
	}

}
