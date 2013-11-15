package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Estudiante extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.estudiante);
		TextView titulo = (TextView) findViewById(R.id.lblTituloEstudiante);
		Bundle b = this.getIntent().getExtras();
		String tipo = b.getString("tipo");
		if (!tipo.equals("")) {
			titulo.setText(titulo.getText().toString() + tipo);
		}
	}

	public void volver(View v) {
		finish();
	}
    public void inscribirse(View v){
    	Button b1 = (Button)findViewById(R.id.btnInscribirEst);
    	b1.setVisibility(8);
         Intent p= new Intent(this,InscripcionMateria.class);
         Bundle b = this.getIntent().getExtras();
         p.putExtra("id", b.getString("id"));
         p.putExtra("modo", "inscribir");
         startActivity(p);
    }
    public void inscrito(View v)
    {
    	Intent p= new Intent(this,InscripcionMateria.class);
        Bundle b = this.getIntent().getExtras();
        p.putExtra("id", b.getString("id"));
        p.putExtra("modo", "listar");
        startActivity(p);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.estudiante, menu);
		return true;
	}

}
