package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;

import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdicionarMateria extends Activity {

	private EditText sigla;
	private EditText desc;
	private String modo;
	private String[]item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adicionar_materia);
		sigla = (EditText)findViewById(R.id.txtSigla);
		desc = (EditText)findViewById(R.id.txtDescripcion);
		Bundle b = this.getIntent().getExtras();
		modo = b.getString("modo");
		item = b.getStringArray("item");
		if(item!=null){
			sigla.setText(item[0]);
			desc.setText(item[1]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adicionar_materia, menu);
		return true;
	}
	public void addMateria(View v) {
		ContentValues c = new ContentValues();
		c.put("Sigla", sigla.getText().toString().trim());
		c.put("Descripcion", desc.getText().toString().trim());

		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);

			if (modo.equals("edit")) {
				if (item != null) {
					String id = item[0];
					db.update("Materia", c, "Sigla like " + "'" + id
							+ "'", null);
					Toast.makeText(getApplicationContext(),
							"Datos actualizados correctamente",
							Toast.LENGTH_SHORT).show();
				}
			} else {
					db.insert("Materia", null, c);
					Toast.makeText(getApplicationContext(),
							"Datos insertados correctamente", Toast.LENGTH_SHORT)
							.show();

			}
			finish();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT)
			.show();
		}
	}
}
