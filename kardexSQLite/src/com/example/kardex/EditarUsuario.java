package com.example.kardex;

import com.example.kardex.R;

import android.app.Activity;
import android.content.Intent;
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

public class EditarUsuario extends Activity implements OnItemClickListener {
	Bundle b ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_docente);
		b=getIntent().getExtras();
		TextView titulo = (TextView)findViewById(R.id.txtTipo);
		titulo.setText(b.getString("tipo"));
		String V[] = { "Error en lectura" };
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			if(b.getString("tipo").equals("Docente")){
				V=listaDocentes(V, db);
			}
			else{
				if(b.getString("tipo").equals("Estudiante")){
					V=listaEstudiantes(V, db);
				}
				else{
					V=listaAdmin(V, db);
				}
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.filasimple, V);
			ListView miLista = (ListView) findViewById(R.id.listDocentes);
			miLista.setAdapter(adapter);
			miLista.setOnItemClickListener(this);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String[] item = arg0.getItemAtPosition(arg2).toString().split("   ");
		Intent intent = new Intent(this, AdicionarUsuario.class);
		intent.putExtra("tipo", b.getString("tipo"));
		intent.putExtra("item", item);
		intent.putExtra("modo", "edicion");
		startActivity(intent);
		finish();
	}
	public String[] listaDocentes(String V[],SQLiteDatabase db){
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
		return V;

	}
	public String[] listaEstudiantes(String V[], SQLiteDatabase db){
		String[] columnas = new String[] { "idEstudiante", "Nombre",
				"Paterno", "Materno" };
		try {
			Cursor cursor = db.query("Estudiante", columnas, null, null,
					null, null, null);
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
						+ cursor.getString(iMat) + "   " + "\n";
			}
			V = resultado.split("\n");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en listado " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		return V;
	}
	public String[] listaAdmin(String V[], SQLiteDatabase db){
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
		return V;
	}
}
