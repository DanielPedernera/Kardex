package com.example.kardex;

import java.util.StringTokenizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class AsignarMateria extends Activity implements OnItemSelectedListener{
	Spinner docentes;
    Spinner materias;
    Spinner paralelos;
    String triplas="";
    String [] docs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asignar_materia);
		String VD[] = { "Error en lectura" };
		String V[] = { "Error en lectura" };
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			
			
			String[] columnas = new String[] { "Sigla", "Descripcion"};	
			String[] columnasD = new String[] { "idDocente", "Nombre",
					"Paterno", "Materno", "Grado", "Titular" };	
			try {
				Cursor cursorD = db.query("Docente", columnasD, null, null, null,
						null, null);
				String resultadoD = "";
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
					resultadoD = resultadoD
							+ cursorD.getString(iNom) + "   "
							+ cursorD.getString(iPat) + "   "
							+ cursorD.getString(iMat) + "   "
						    + "\n";
				}
				docs=docentes.split("\n");
				VD = resultadoD.split("\n");
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
			materias = (Spinner) findViewById(R.id.cboxMateriasAsig);
			materias.setOnItemSelectedListener(this);
			materias.setAdapter(adapter);
			//materias.setOnItemClickListener(this);
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
					R.layout.filasimple, VD);
			docentes = (Spinner) findViewById(R.id.cboxDocentesAsig);
			docentes.setAdapter(adapter2);

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void asignarMatDoc(View v) {
		String doc = buscarDocentes(docentes.getSelectedItem().toString(),docs);
		String mat = buscarMateria(materias.getSelectedItem().toString());
		String par = paralelos.getSelectedItem().toString();
		if( !triplas.contains(doc+ "   "+par+ "   "+mat)){
			triplas = triplas+ doc
					+ "   "
					+ par
					+ "   "
					+ mat
					+"\n";
			String[]vec=doc.split("\n");
			ContentValues c = new ContentValues();
			c.put("idDocente",doc );
			c.put("Sigla", mat);
			c.put("Nombre", par);
			try {
				SQLiteDatabase db = SQLiteDatabase.openDatabase(
						"/mnt/sdcard/kardex", null,
						SQLiteDatabase.CREATE_IF_NECESSARY);
				db.insert("Dicta", null, c);

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
					"Ya realizó esa asignación ", Toast.LENGTH_SHORT)
					.show();
		}
	}
	private String buscarMateria(String string) {
		StringTokenizer st = new StringTokenizer(string);
		return st.nextToken()+" "+st.nextToken();
	}
	private String buscarDocentes(String string, String[] docs2) {
	    for (int i = 0; i < docs2.length; i++) {
			if (docs2[i].contains(string)) {
				StringTokenizer st = new StringTokenizer(docs2[i]);
				return st.nextToken();
			}
		}
		return null;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignar_materia, menu);
		return true;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		StringTokenizer st = new StringTokenizer(arg0.getItemAtPosition(arg2)
				.toString());
		String id = st.nextToken();
		id = id +" "+st.nextToken();
		String VP[] = { "Error en lectura paralelos" };
		id=id+"";
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			String[] colPar = new String[] { "Nombre","Sigla"};	
			try {
				Cursor cp = db.query("Paralelo", colPar, "Sigla like " + "'"+id+"'", null, null, null , null);				
				String par ="";
				int iPar = cp.getColumnIndex("Nombre");
				for(cp.moveToFirst();!cp.isAfterLast();cp.moveToNext()){
					par = par+cp.getString(iPar)+ "   "
							+"\n";
				}
				VP=par.split("\n");
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en listado " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
					R.layout.filasimple, VP);
			paralelos = (Spinner) findViewById(R.id.cboxParalelosAsig);
			paralelos.setAdapter(adapter2);

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

}
