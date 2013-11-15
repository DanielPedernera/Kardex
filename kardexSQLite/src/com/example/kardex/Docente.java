package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.docente, menu);
		return true;
	}

}
