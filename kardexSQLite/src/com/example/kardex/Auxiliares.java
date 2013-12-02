package com.example.kardex;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Auxiliares extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notas_materia);
		Bundle b=this.getIntent().getExtras();
		TextView t=(TextView)findViewById(R.id.textView1);
		t.setText("Auxilires Asignados");
		
		String id = b.getString("id");
		String V[] = { "Error en lectura" };

		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			String[] columnas = new String[] { "idEstudiante","idDocente","Sigla","Item","Gestion"};	
			String[]colmat = new String[]{"Sigla","Descripcion","Semestre"};
			String[]colest = new String[]{"idEstudiante","Nombre","Paterno","Materno","Ci","Matricula","Fec_nac","NroAleatorio"};
			try {
				Cursor cursor = db.query("Auxiliatura", columnas, "idDocente like " + "'"+id+"'", null, null,
						null, null);
				String resultado = "";
				int iSig = cursor.getColumnIndex("Sigla");
				int iEst = cursor.getColumnIndex("idEstudiante");
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					String par = cursor.getString(iSig);
					Cursor cp = db.query("Materia",colmat,"Sigla like "+"'"+par+"'",null,null,null,null);
					int iDes=cp.getColumnIndex("Descripcion");
					for (cp.moveToFirst();!cp.isAfterLast();cp.moveToNext()) {
						String est = cursor.getString(iEst);
						Cursor ce = db.query("Estudiante",colest,"idEstudiante like "+"'"+est+"'",null,null,null,null);
						int iNom = ce.getColumnIndex("Nombre");
						int iPat = ce.getColumnIndex("Paterno");
						int iMat = ce.getColumnIndex("Paterno");
						for(ce.moveToFirst();!ce.isAfterLast();ce.moveToNext()){
							resultado = resultado + ce.getString(iNom) + "   "
									+ "   "+ce.getString(iPat)
									+ "   "+ce.getString(iMat)
									+ "   "+cp.getString(iDes)
								+ "\n";
						}
						
					}
					
				}
				V=resultado.split("\n");
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en listado " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.filasimple, V);
			ListView miList = (ListView) findViewById(R.id.listView1);
			miList.setAdapter(adapter);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

	}

}
