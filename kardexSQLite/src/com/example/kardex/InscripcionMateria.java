package com.example.kardex;

import java.util.StringTokenizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InscripcionMateria extends Activity implements OnItemSelectedListener,OnItemClickListener {

	Spinner miSpinner;
	Spinner miSpinner2;
	String materia="";
	int limite=6;
	String estudiante ;
	boolean cambios = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscripcion_materia);
		Bundle b = this.getIntent().getExtras();
		if(b.getString("modo").equals("listar"))
		{
			TextView l1 = (TextView)findViewById(R.id.lblMaterias);
			l1.setText("Se inscribio a las siguientes materias");
			l1 =(TextView)findViewById(R.id.lblParalelos);
			l1.setVisibility(8);
			Button b1 =(Button)findViewById(R.id.btnAceptarIns);
			b1.setVisibility(8);
			b1=(Button)findViewById(R.id.btnFinalizarIns);
			b1.setVisibility(8);
			String id = b.getString("id");
			String V[] = { "Error en lectura" };

			try {
				SQLiteDatabase db = SQLiteDatabase.openDatabase(
						"/mnt/sdcard/kardex", null,
						SQLiteDatabase.CREATE_IF_NECESSARY);
				String[] columnas = new String[] { "idEstudiante","Sigla","Nombre"};	
				String[]colpar = new String[]{"idParalelo","Nombre","Sigla","Cupo","Horario1","Duracion1","Dia1","Aula1"
						                     ,"Horario1","Duracion1","Dia1","Aula1"};
				try {
					Cursor cursor = db.query("Inscribir", columnas, "idEstudiante like " + "'"+id+"'", null, null,
							null, null);
					String resultado = "";
					int iSig = cursor.getColumnIndex("Sigla");
					int iPar = cursor.getColumnIndex("Nombre");
					for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
						String par = cursor.getString(iPar);
						Cursor cp = db.query("Paralelo",colpar,"Nombre like "+"'"+par+"'",null,null,null,null);
						int iHor1=cp.getColumnIndex("Horario1");
						int iDur1 = cp.getColumnIndex("Duracion1");
						int iDia1 =cp.getColumnIndex("Dia1");
						int iAula1 = cp.getColumnIndex("Aula1");
						int iHor2=cp.getColumnIndex("Horario2");
						int iDur2 = cp.getColumnIndex("Duracion2");
						int iDia2 =cp.getColumnIndex("Dia2");
						int iAula2 = cp.getColumnIndex("Aula2");
						for (cp.moveToFirst();!cp.isAfterLast();cp.moveToNext()) {
							resultado = resultado + cursor.getString(iSig) + "   "
									+ "   "+par
									+ "   "+cp.getString(iDia1)
									+ "   "+cp.getString(iAula1)
									+ "   "+cp.getString(iHor1)
									+ "   "+cp.getString(iDur1)
									+ "   "+cp.getString(iDia2)
									+ "   "+cp.getString(iAula2)
									+ "   "+cp.getString(iHor2)
									+ "   "+cp.getString(iDur2)
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
				ListView miList = (ListView) findViewById(R.id.listInscritas);
				miList.setAdapter(adapter);
				miList.setOnItemClickListener(this);


			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
						.show();
			}
		}
		else{
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
				miSpinner = (Spinner) findViewById(R.id.cboxMateria);
				miSpinner.setVisibility(0);
				miSpinner.setAdapter(adapter);
				miSpinner.setOnItemSelectedListener(this);

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
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
			String[] colDic = new String[] { "idDocente","Nombre","Sigla"};	
			String[] colDoc = new String[] { "idDocente","Nombre","Paterno","Materno","Grado","Titular"};	
			try {
				Cursor cp = db.query("Dicta", colDic, "Sigla like " + "'"+id+"'", null, null, null , null);	
				String par ="";
				int iPar = cp.getColumnIndex("idDocente");
				int iPara = cp.getColumnIndex("Nombre");
				for(cp.moveToFirst();!cp.isAfterLast();cp.moveToNext()){
					String paralelo = cp.getString(iPara);
					String idD = cp.getString(iPar);
					Cursor cd = db.query("Docente", colDoc, "idDocente like " + "'"+idD+"'", null, null, null , null);
					int iNomDoc = cd.getColumnIndex("Nombre");
					int iPatDoc = cd.getColumnIndex("Paterno");
					int iGradDoc = cd.getColumnIndex("Grado");
					for(cd.moveToFirst();!cd.isAfterLast();cd.moveToNext()){
					par = par+paralelo
							+ "   "+cd.getString(iGradDoc)+":"
							+ "   "+cd.getString(iNomDoc)
							+ "   "+cd.getString(iPatDoc)
							+"\n";
					}
				}
				VP=par.split("\n");
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en listado " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
					R.layout.filasimple, VP);
			miSpinner2 = (Spinner) findViewById(R.id.cboxParalelo);
			miSpinner2.setVisibility(0);
			miSpinner2.setAdapter(adapter2);

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscripcion_materia, menu);
		return true;
	}
	public void adicionar(View v) {
		StringTokenizer st = new StringTokenizer(miSpinner.getSelectedItem().toString());
		String id = st.nextToken();
		id = id + " "+st.nextToken();
		String paralelo = miSpinner2.getSelectedItem().toString();
		if(limite>0 )
		{
			if( !materia.contains(id)){
				materia = materia+ id
						+ "   "
						+ paralelo
						+"\n";
				String[]vec=materia.split("\n");
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						R.layout.filasimple, vec);
				ListView miList = (ListView) findViewById(R.id.listInscritas);
				miList.setAdapter(adapter);
				miList.setOnItemClickListener(this);
				limite--;
				Bundle b = this.getIntent().getExtras();
				estudiante = b.getString("id");
				ContentValues c = new ContentValues();
				c.put("idEstudiante",estudiante );
				c.put("Sigla", id);
			    st = new StringTokenizer(paralelo);
				c.put("Nombre",st.nextToken());
				try {
					SQLiteDatabase db = SQLiteDatabase.openDatabase(
							"/mnt/sdcard/kardex", null,
							SQLiteDatabase.CREATE_IF_NECESSARY);
					db.insert("Inscribir", null, c);

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
					"Ya añadio todas las materias posibles ", Toast.LENGTH_SHORT)
					.show();
			finalizar(v);
		}

	}
	public void finalizar(View v) {
		miSpinner.setVisibility(8);
		miSpinner2.setVisibility(8);
		TextView l1 = (TextView)findViewById(R.id.lblMaterias);
		l1.setText("Se inscribio a las siguientes materias");
		l1 =(TextView)findViewById(R.id.lblParalelos);
		l1.setVisibility(8);
		Button b1 =(Button)findViewById(R.id.btnAceptarIns);
		b1.setVisibility(8);
		b1=(Button)findViewById(R.id.btnFinalizarIns);
		b1.setVisibility(8);
		cambios=false;
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (cambios) {
			StringTokenizer st = new StringTokenizer(arg0.getItemAtPosition(arg2)
					.toString());
			String id = st.nextToken();
			id = id +" "+st.nextToken();
			try {
				SQLiteDatabase db = SQLiteDatabase.openDatabase(
						"/mnt/sdcard/kardex", null,
						SQLiteDatabase.CREATE_IF_NECESSARY);
				if (db.isOpen()) {
					db.delete("Inscribir", "Sigla like " + "'" + id + "'", null);
					String[]vec=materia.split("\n");
					materia="";
					for (int i = 0; i < vec.length; i++) {
						if(!vec[i].contains(id))
						{
							materia=materia+vec[i]+"\n";
						}
					}
					vec=materia.split("\n");
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
							R.layout.filasimple, vec);
					ListView miList = (ListView) findViewById(R.id.listInscritas);
					miList.setAdapter(adapter);

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

}
