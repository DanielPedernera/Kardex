package com.example.kardex;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Notas extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notas_materia);
		Bundle b=this.getIntent().getExtras();
		TextView t=(TextView)findViewById(R.id.textView1);
		t.setText("Notas");
		
		String id = b.getString("id");
		String V[] = { "Error en lectura" };

		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			String[] columnas = new String[] { "idEstudiante","Sigla","Nombre","Nota","Gestion"};	
			String[]colmat = new String[]{"Sigla","Descripcion","Semestre"};
			try {
				Cursor cursor = db.query("Inscribir", columnas, "idEstudiante like " + "'"+id+"'", null, null,
						null, null);
				String resultado = "";
				int iSig = cursor.getColumnIndex("Sigla");
				int iNota = cursor.getColumnIndex("Nota");
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
					String par = cursor.getString(iSig);
					Cursor cp = db.query("Materia",colmat,"Sigla like "+"'"+par+"'",null,null,null,null);
					int iDes=cp.getColumnIndex("Descripcion");
					for (cp.moveToFirst();!cp.isAfterLast();cp.moveToNext()) {
						resultado = resultado + cp.getString(iDes) + "   "
								+ "   "+cursor.getString(iNota)
							+ "\n";
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
