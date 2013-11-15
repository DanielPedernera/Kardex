package com.example.kardex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class AdminSemestre extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_semestre);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_semestre, menu);
		return true;
	}
	public void addMat(View v) {
		 Intent p = new Intent(this,AdicionarMateria.class);
		 p.putExtra("modo", "add");
		 startActivity(p);
	}
	public void editMat(View v) {
		 Intent p = new Intent(this,EditarMateria.class);
		 p.putExtra("modo", "edit");
		 startActivity(p);
	}
	public void deleteMat(View v) {
		 Intent p = new Intent(this,EliminarMateria.class);
		 p.putExtra("modo", "delete");
		 startActivity(p);
	}
	public void paralelo(View v) {
		 Intent p = new Intent(this,Paralelo.class);
		 p.putExtra("modo", "paralelo");
		 startActivity(p);
	}
    public void prerequisito(View v) {
		Intent p = new Intent(this,AsignarRequisito.class);
		startActivity(p);
	}
	public void listDocs(View v){
	    Intent p = new Intent(this,EliminarMateria.class);
	    p.putExtra("modo", "listDocs");
	    startActivity(p);
	}
	public void listEsts(View v){
	    Intent p = new Intent(this,EliminarMateria.class);
	    p.putExtra("modo", "listEsts");
	    startActivity(p);
	}

}
