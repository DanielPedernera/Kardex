package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class AsignarRequisito extends Activity {
 
	Spinner materia;
	Spinner requisito;
	String dupla="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asignar_requisito);
		String V[] = { "Error en lectura" };
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			String[] columnas = new String[] { "Sigla", "Descripcion"};		
			try {
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

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.filasimple, V);
			materia = (Spinner) findViewById(R.id.cboxMaterias);
			materia.setAdapter(adapter);
			//miSpinner.setOnItemClickListener(this);
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
					R.layout.filasimple, V);
			requisito = (Spinner) findViewById(R.id.cboxRequisito);
			requisito.setAdapter(adapter2);

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}
    public void asignarPrerequisito(View v) {
    	String mat = materia.getSelectedItem().toString();
		String req = requisito.getSelectedItem().toString();
		if(!mat.equals(req))
		{
			if( !dupla.contains(req+ "   "+mat)){
				dupla = dupla+ req
						+ "   "
						+ mat
						+"\n";
//				String[]vec=dupla.split("\n");
//				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//						R.layout.filasimple, vec);
//				ListView miList = (ListView) findViewById(R.id.listInscritas);
//				miList.setAdapter(adapter);
//				limite--;
//				Bundle b = this.getIntent().getExtras();
//				estudiante = b.getString("id");
				ContentValues c = new ContentValues();
				c.put("idMateria",mat );
				c.put("idMatReq", req);
				try {
					SQLiteDatabase db = SQLiteDatabase.openDatabase(
							"/mnt/sdcard/kardex", null,
							SQLiteDatabase.CREATE_IF_NECESSARY);
					db.insert("Prerequisito", null, c);

					Toast.makeText(getApplicationContext(),
							"Datos insertados correctamente", Toast.LENGTH_SHORT)
							.show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT)
					.show();
				}
			}
			else{
				Toast.makeText(getApplicationContext(),
						"Ya añadio esa materia ", Toast.LENGTH_SHORT)
						.show();
			}
		}
		else{
			Toast.makeText(getApplicationContext(),
					"Una materia no puede ser prerequisito de si misma ", Toast.LENGTH_SHORT)
					.show();
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignar_requisito, menu);
		return true;
	}

}
