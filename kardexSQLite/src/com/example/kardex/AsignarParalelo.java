package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AsignarParalelo extends Activity {

	private EditText paralelo;
	private EditText cupo;
	private EditText dia1;
	private TimePicker horario1;
	private EditText duracion1;
	private EditText aula1;
	private EditText dia2;
	private TimePicker horario2;
	private EditText duracion2;
	private EditText aula2;
	private String[]item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asignar_paralelo);
		Bundle b = this.getIntent().getExtras();
		item = b.getStringArray("item");
		paralelo = (EditText)findViewById(R.id.txtParalelo);
		cupo =(EditText)findViewById(R.id.txtCup);
		dia1=(EditText)findViewById(R.id.txtDia1);
		horario1=(TimePicker)findViewById(R.id.timePickerHorario1);
		duracion1=(EditText)findViewById(R.id.txtDuracionDia1);
		aula1 = (EditText)findViewById(R.id.txtAulaDia1);
		dia2=(EditText)findViewById(R.id.txtDia2);
		horario2=(TimePicker)findViewById(R.id.timePickerHorario2);
		duracion2=(EditText)findViewById(R.id.txtDuracionDia2);
		aula2 = (EditText)findViewById(R.id.txtAulaDia2);
		TextView materia =(TextView)findViewById(R.id.lblMateria);
		if (item!=null) {
			materia.setText(item[0]+" "+item[1]);
		}
		
		
	}
    public void addParalelo(View v){
    	ContentValues c = new ContentValues();
		c.put("Nombre", paralelo.getText().toString().trim());
		c.put("Cupo",Integer.parseInt( cupo.getText().toString().trim()));
		c.put("Dia1", dia1.getText().toString());
		c.put("Horario1",horario1.getCurrentHour()+horario1.getCurrentMinute());
		c.put("Duracion1", Integer.parseInt(duracion1.getText().toString().trim()));
		c.put("Aula1",aula1.getText().toString().trim());
		c.put("Dia2", dia2.getText().toString());
		c.put("Horario2",horario2.getCurrentHour()+horario1.getCurrentMinute());
		c.put("Duracion2", Integer.parseInt(duracion2.getText().toString().trim()));
		c.put("Aula2",aula2.getText().toString().trim());
		if (item!=null) {
			c.put("Sigla", item[0]);
		}
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);

//			if (modo.equals("edit")) {
//				if (item != null) {
//					String id = item[0];
//					db.update("Materia", c, "Sigla like " + "'" + id
//							+ "'", null);
//					Toast.makeText(getApplicationContext(),
//							"Datos actualizados correctamente",
//							Toast.LENGTH_SHORT).show();
//				}
//			} else {
					db.insert("Paralelo", null, c);
					Toast.makeText(getApplicationContext(),
							"Datos insertados correctamente", Toast.LENGTH_SHORT)
							.show();

//			}
			finish();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT)
			.show();
		}
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignar_paralelo, menu);
		return true;
	}

}
