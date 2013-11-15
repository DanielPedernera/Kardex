package com.example.kardex;

import com.example.kardex.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@SuppressLint("ShowToast")
	public void Aceptar(View v) {
		EditText user = (EditText) findViewById(R.id.txtCodigo);
		EditText pass = (EditText) findViewById(R.id.txtPass);
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);
			String[] columnas = new String[] { "idUsuario", "password", "tipo" };
			try {
				Cursor cursor = db.query("Usuario", columnas, null, null, null,
						null, null);
				boolean buscar = true;
				String tipo = "";
				int iCod = cursor.getColumnIndex("idUsuario");
				int iPass = cursor.getColumnIndex("password");
				int iTipo = cursor.getColumnIndex("tipo");

				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					String dbusu = cursor.getString(iCod).toString();
					String dbpass = cursor.getString(iPass).toString();
					String dbtipo = cursor.getString(iTipo).toString();
					if (buscar
							&& dbusu.equals(user.getText().toString().trim())
							&& dbpass.equals(pass.getText().toString().trim())) {
						buscar = false;
						tipo = dbtipo;
					}
				}
				if(!buscar && tipo.equals("docente")){
					Intent p = new Intent(this,Docente.class);
					p.putExtra("id", user.getText().toString());
					p.putExtra("tipo", tipo);
					startActivity(p);
				}else{
					if(!buscar && tipo.equals("estudiante")){
						Intent p = new Intent(this,Estudiante.class);
						p.putExtra("id", user.getText().toString());
						p.putExtra("tipo", tipo);
						startActivity(p);
					}
					else{
						String[] columnasacceso = new String[] { "idAcceso", "tipoAcceso",
						"permiso" };
						Cursor acceso = db.query("Acceso", columnasacceso, null, null,
								null, null, null);
						int[] permisos = new int[5];
						String[] access = { "docente", "estudiante", "personal", "parada", "mauricio" };
						int ind = 0;
						permisos[0] = 7;
						permisos[1] = 7;
						permisos[2] = 7;
						int iCodAcceso = acceso.getColumnIndex("idAcceso");
						int iAcceso = acceso.getColumnIndex("tipoAcceso");
						int iPermiso = acceso.getColumnIndexOrThrow("permiso");
						for (acceso.moveToFirst(); !acceso.isAfterLast(); acceso.moveToNext()) {
							String dbidac = acceso.getString(iCodAcceso).toString();
							String dbtabla = acceso.getString(iAcceso).toString();
							int dbpermiso = Integer.parseInt(acceso.getString(iPermiso).toString());
							if (dbidac.equals(user.getText().toString().trim())) {
								access[ind] = dbtabla;
								permisos[ind] = dbpermiso;
								ind++;
							}

						}
						if (user.getText().toString().equals("PPD020791") && pass
								.getText().toString().equals("123456")) {
							// pass.setText(user.getText());
							Intent p = new Intent(this, Administrador.class);
							p.putExtra("permisos", permisos);
							p.putExtra("accesos", access);
							startActivity(p);
						} else {
							AlertDialog.Builder alert = new AlertDialog.Builder(this);
							alert.setTitle("Usuario");
							alert.setMessage("Usuario invalido");
							alert.setPositiveButton("Ok", null);
							alert.show();
						}
					}
				}
				user.setText("");
				pass.setText("");

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Error en listado " + e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Error en conexion " + e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
