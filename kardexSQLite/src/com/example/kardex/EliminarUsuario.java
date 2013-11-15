package com.example.kardex;

import java.util.StringTokenizer;

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

public class EliminarUsuario extends Activity implements OnItemClickListener {
	String tipo = "";
	String modo = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_docente);
		Bundle b = this.getIntent().getExtras();
		tipo = b.getString("tipo");
		modo = b.getString("modo");
		TextView t = (TextView)findViewById(R.id.txtTipo);
		t.setText(tipo+"/s");
		String V[] = { "Error en lectura" };
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			if (tipo.equals("Docente")) {
				elimDoc(db, V);
			}
			else{
				if (tipo.equals("Estudiante")) {
					elimEst(db, V);
				} else {
					elimAdm(db, V);
				}
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void elimAdm(SQLiteDatabase db, String[] V) {
		String[] columnas = new String[]{ "idAdmin", "Nombre",
				"Paterno", "Materno" };
		try {
			Cursor cursor = db.query("Admin", columnas, null, null, null,
					null, null);
			String resultado = "";
			int iCod = cursor.getColumnIndex("idAdmin");
			int iNom = cursor.getColumnIndex("Nombre");
			int iPat = cursor.getColumnIndex("Paterno");
			int iMat = cursor.getColumnIndex("Materno");
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				resultado = resultado + cursor.getString(iCod) + "   "
						+ cursor.getString(iNom) + "   "
						+ cursor.getString(iPat) + "   "
						+ cursor.getString(iMat) + "   ";
				String id = cursor.getString(iCod);
				String[] col = new String[]{"idAcceso","tipoAcceso","permiso","idProp"};
				Cursor cur = db.query("Acceso", col,"idAcceso like " + "'" + id + "'",null,null,null,null);
				int iTip = cur.getColumnIndex("tipoAcceso");
				int iPer = cur.getColumnIndex("permiso");
				for(cur.moveToFirst(); !cur.isAfterLast(); cur
						.moveToNext()) {
				resultado = resultado
						+ cur.getString(iTip)+ "   "
						+ cur.getString(iPer)+ "   ";
				}
				resultado=resultado+"\n";
			}
			V = resultado.split("\n");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en listado " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.filasimple, V);
		ListView miLista = (ListView) findViewById(R.id.listDocentes);
		miLista.setAdapter(adapter);
		miLista.setOnItemClickListener(this);
	}

	private void elimEst(SQLiteDatabase db, String[] V) {
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

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.filasimple, V);
		ListView miLista = (ListView) findViewById(R.id.listDocentes);
		miLista.setAdapter(adapter);
		miLista.setOnItemClickListener(this);

	}

	private void elimDoc(SQLiteDatabase db, String[] V) {
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

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.filasimple, V);
		ListView miLista = (ListView) findViewById(R.id.listDocentes);
		miLista.setAdapter(adapter);
		miLista.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		StringTokenizer st = new StringTokenizer(arg0.getItemAtPosition(arg2)
				.toString());
		String id = st.nextToken();
		if (!modo.equals("listar")) {
			try {
				SQLiteDatabase db = SQLiteDatabase.openDatabase(
						"/mnt/sdcard/kardex", null,
						SQLiteDatabase.CREATE_IF_NECESSARY);
				if (db.isOpen()) {
					if(tipo.equals("Docente")){
						db.delete("Docente", "idDocente like " + "'" + id + "'", null);
						db.delete("Dicta", "idDocente like " + "'" + id + "'", null);
					}
					else{
						if (tipo.equals("Estudiante")) {
							db.delete("Estudiante", "idEstudiante like " + "'" + id + "'", null);
							db.delete("Inscribir", "idEstudiante like " + "'" + id + "'", null);
						} else {
							db.delete("Admin", "idAdmin like " + "'" + id + "'", null);
							db.delete("Acceso", "idAcceso like " + "'" + id + "'", null);
						}
					}
					db.delete("Usuario", "idUsuario like " + "'" + id + "'", null);
					Toast.makeText(getApplicationContext(),
							"Se ha eliminado correctamente", Toast.LENGTH_SHORT)
							.show();
					finish();
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
		else{
			String V[] = { "Error en lectura" };
			try {
				SQLiteDatabase db = SQLiteDatabase.openDatabase(
						"/mnt/sdcard/kardex", null,
						SQLiteDatabase.CREATE_IF_NECESSARY);
				if (db.isOpen()) {
					if(tipo.equals("Docente")){
						String[] columnas = new String[] { "idDocente", "Nombre",
								"Paterno", "Materno", "Grado" };
						String[] colDicta = new String[]{"idDocente", "Sigla", "Nombre"};
						try {
							Cursor cursor = db.query("Docente", columnas, "idDocente like " + "'" + id + "'", null, null,
									null, null);
							String resultado = "";
							int iNom = cursor.getColumnIndex("Nombre");
							int iPat = cursor.getColumnIndex("Paterno");
							int iMat = cursor.getColumnIndex("Materno");
							int iGrado = cursor.getColumnIndex("Grado");
							for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
									.moveToNext()) {
								Cursor curDic = db.query("Dicta", colDicta, "idDocente like " + "'" + id + "'", null, null,null,null);
								int iSig = curDic .getColumnIndex("Sigla");
								int iPar = curDic.getColumnIndex("Nombre");
								for(curDic.moveToFirst();!curDic.isAfterLast();curDic.moveToNext()){
									resultado = resultado  + "   "
											+ cursor.getString(iGrado) + "   "
											+ cursor.getString(iNom) + "   "
											+ cursor.getString(iPat) + "   "
											+ cursor.getString(iMat) + "   "
											+ curDic.getString(iSig) + "   "
											+ curDic.getString(iPar) + "   "
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
						ListView miLista = (ListView) findViewById(R.id.listDocentes);
						miLista.setAdapter(adapter);
					}
					else{
						if (tipo.equals("Estudiante")) {
							String[] columnas = new String[] { "idEstudiante", "Nombre",
									"Paterno", "Materno" };
							String[] colIns = new String[]{"idEstudiante", "Sigla", "Nombre"};
							try {
								Cursor cursor = db.query("Estudiante", columnas, "idEstudiante like " + "'" + id + "'", null, null,
										null, null);
								String resultado = "";
								int iNom = cursor.getColumnIndex("Nombre");
								int iPat = cursor.getColumnIndex("Paterno");
								int iMat = cursor.getColumnIndex("Materno");
								for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
										.moveToNext()) {
									Cursor curIns = db.query("Inscribir", colIns, "idEstudiante like " + "'" + id + "'", null, null,null,null);
									int iSig = curIns .getColumnIndex("Sigla");
									int iPar = curIns.getColumnIndex("Nombre");
									for(curIns.moveToFirst();!curIns.isAfterLast();curIns.moveToNext()){
										
												resultado = resultado + "   "
														+ cursor.getString(iNom) + "   "
														+ cursor.getString(iPat) + "   "
														+ cursor.getString(iMat) + "   "
														+ curIns.getString(iSig) + "   "
														+ curIns.getString(iPar) + "   "
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
							ListView miLista = (ListView) findViewById(R.id.listDocentes);
							miLista.setAdapter(adapter);
							miLista.setOnItemClickListener(this);
						} 
						else {
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
}
