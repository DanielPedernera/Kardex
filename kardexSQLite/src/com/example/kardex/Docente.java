package com.example.kardex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Docente extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.docente);
		TextView titulo = (TextView) findViewById(R.id.lblTituloDocente);
		Bundle b = this.getIntent().getExtras();
		String tipo = b.getString("tipo");
		if (!tipo.equals("")) {
			titulo.setText(titulo.getText().toString() + tipo);
		}
	}
	
	public void aux(View v){
		Intent p = new Intent(this,Auxiliares.class);
		Bundle b = this.getIntent().getExtras();
		p.putExtra("id",b.getString("id"));
		startActivity(p);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.docente, menu);
		return true;
	}

}
