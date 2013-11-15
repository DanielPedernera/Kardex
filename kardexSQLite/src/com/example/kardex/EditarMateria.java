package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EditarMateria extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_materia);
		listar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_materia, menu);
		return true;
	}
	public void listar(){
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
		Intent p = new Intent(this,AdicionarMateria.class);
		p.putExtra("modo", "edit");
		p.putExtra("item", materia);
		startActivity(p);
		finish();
	}

}
