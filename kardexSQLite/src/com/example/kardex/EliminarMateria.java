package com.example.kardex;


import com.example.kardex.R;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EliminarMateria extends Activity implements OnItemClickListener {
	private String modo;
	TextView etiqueta ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_materia);
		etiqueta = (TextView)findViewById(R.id.lblMaterias);
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
				V = resultado.split("\n");
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en listado " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.filasimple, V);
			ListView miLista = (ListView) findViewById(R.id.listMaterias);
			miLista.setAdapter(adapter);
			Bundle b = this.getIntent().getExtras();
			modo = b.getString("modo");
			//if (!b.getString("modo").equals("delete")) {
			miLista.setOnItemClickListener(this);
			//}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String[] materia = ((String) arg0.getItemAtPosition(arg2)).split("   ");
		String id = materia[0];
		String mat = materia[1];
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			if (db.isOpen()) {
				if (modo.equals("delete")) {
					db.delete("Materia", "Sigla like " + "'" + id + "'", null);
					Toast.makeText(getApplicationContext(),
							"Se ha eliminado correctamente", Toast.LENGTH_SHORT)
							.show();
					finish();
				}
				else{
					String V[] = { "Error en lectura" };
					etiqueta.setText(mat);
					if(modo.equals("listDocs")){
						String[] columnas = new String[] { "idDocente","Sigla", "Nombre"};
						try {
							Cursor cursor = db.query("Dicta", columnas,"Sigla like " + "'" + id + "'" , null, null,
									null, null);
							String resultado = "";
							int iDoc = cursor.getColumnIndex("idDocente");
							int iPar = cursor.getColumnIndex("Nombre");
							String[] colDoc =  new String[] { "idDocente", "Nombre",
									"Paterno", "Materno", "Grado", "Titular" };
							for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
									.moveToNext()) {
								String idDoc = cursor.getString(iDoc);
								Cursor curDoc = db.query("Docente", colDoc, "idDocente like " + "'" + idDoc + "'", null, null,
										null, null);
								int iNom = curDoc.getColumnIndex("Nombre");
								int iPat = curDoc.getColumnIndex("Paterno");
								int iMat = curDoc.getColumnIndex("Materno");
								int iGrado = curDoc.getColumnIndex("Grado");
								for (curDoc.moveToFirst(); !curDoc.isAfterLast(); curDoc
										.moveToNext()) {
									resultado = resultado 
											+ curDoc.getString(iGrado) + "   "
											+ curDoc.getString(iNom) + "   "
											+ curDoc.getString(iPat) + "   "
											+ curDoc.getString(iMat) + "   "
											+ cursor.getString(iPar) + "   "
											+ "\n";
								}
							}
							V = resultado.split("\n");
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),
									"Error en listado " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
								R.layout.filasimple, V);
						ListView miLista = (ListView) findViewById(R.id.listMaterias);
						miLista.setAdapter(adapter);
					}
					else{
						if(modo.equals("listEsts")){
							String[] columnas = new String[] { "idEstudiante","Sigla", "Nombre"};
							try {
								Cursor cursor = db.query("Inscribir", columnas,"Sigla like " + "'" + id + "'" , null, null,
										null, null);
								String resultado = "";
								int iEst = cursor.getColumnIndex("idEstudiante");
								int iPar = cursor.getColumnIndex("Nombre");
								String[] colDoc =  new String[] { "idEstudiante", "Nombre",
										"Paterno", "Materno" };
								for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
										.moveToNext()) {
									String idEst = cursor.getString(iEst);
									Cursor curEst = db.query("Estudiante", colDoc, "idEstudiante like " + "'" + idEst + "'", null, null,
											null, null);
									int iNom = curEst.getColumnIndex("Nombre");
									int iPat = curEst.getColumnIndex("Paterno");
									int iMat = curEst.getColumnIndex("Materno");
									for (curEst.moveToFirst(); !curEst.isAfterLast(); curEst
											.moveToNext()) {
										resultado = resultado 
												+ curEst.getString(iNom) + "   "
												+ curEst.getString(iPat) + "   "
												+ curEst.getString(iMat) + "   "
												+ cursor.getString(iPar) + "   "
												+ "\n";
									}
								}
								V = resultado.split("\n");
							} catch (Exception e) {
								Toast.makeText(getApplicationContext(),
										"Error en listado " + e.getMessage(),
										Toast.LENGTH_SHORT).show();
							}
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
									R.layout.filasimple, V);
							ListView miLista = (ListView) findViewById(R.id.listMaterias);
							miLista.setAdapter(adapter);
						}
					}
				}
			} else {
				Toast.makeText(getApplicationContext(), "No se abrio la db",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

	}


}
