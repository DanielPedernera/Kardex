package com.example.kardex;

import com.example.kardex.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminUsuario extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_docente);
		Bundle b = this.getIntent().getExtras();
		int permiso = b.getInt("permiso");
		int[] permisos = b.getIntArray("permisos");
		String tipo = b.getString("tipo");
		Button ba = (Button) findViewById(R.id.btnAddDocente);
		Button bl = (Button) findViewById(R.id.btnListarDocente);
		Button bm = (Button) findViewById(R.id.btnEditDocente);
		Button bd = (Button) findViewById(R.id.btnDeleteDocente);
		Button bam = (Button) findViewById(R.id.btnMateria);
		Button bda = (Button) findViewById(R.id.btnAux);
		TextView titulo = (TextView)findViewById(R.id.txtTipo);
		titulo.setText(tipo);
		if (tipo.equals("Docente")) {
			bam.setVisibility(0);
		}
		if (tipo.equals("Estudiante")) {
			bda.setVisibility(0);
		}
		switch (permisos[permiso]) {
		case 7:
			ba.setVisibility(0);
			bl.setVisibility(0);
			bm.setVisibility(0);
			bd.setVisibility(0);
			break;
		case 6:
			ba.setVisibility(0);
			bl.setVisibility(0);
			bm.setVisibility(0);
			break;
		case 5:
			bd.setVisibility(0);
			bl.setVisibility(0);
			break;
		case 4:
			bl.setVisibility(0);
			break;
		case 3:
			ba.setVisibility(0);
			bd.setVisibility(0);
			bm.setVisibility(0);
			break;
		case 2:
			ba.setVisibility(0);
			bm.setVisibility(0);
			break;
		case 1:
			bd.setVisibility(0);
			break;
		default:
			break;
		}
	}

	public void addDocente(View v) {
		Bundle b = this.getIntent().getExtras();
		Intent p = new Intent(this, AdicionarUsuario.class);
		p.putExtra("modo", "adicionar");
		p.putExtra("tipo", b.getString("tipo"));
		startActivity(p);
	}

	public void listarDocente(View v) {
		Bundle b = this.getIntent().getExtras();
		Intent p = new Intent(this, EliminarUsuario.class);
		p.putExtra("modo", "listar");
		p.putExtra("tipo", b.getString("tipo"));
		startActivity(p);
	}

	public void eliminarDocente(View v) {
		Bundle b = this.getIntent().getExtras();
		Intent p = new Intent(this, EliminarUsuario.class);
		p.putExtra("modo", "eliminar");
		p.putExtra("tipo", b.getString("tipo"));
		startActivity(p);
	}

	public void editDocente(View v) {
		Bundle b = this.getIntent().getExtras();
		Intent p = new Intent(this, EditarUsuario.class);
		p.putExtra("tipo", b.getString("tipo"));
		startActivity(p);
	}
	public void asignarMateria(View v) {
		Intent p = new Intent(this,AsignarMateria.class);
		startActivity(p);
	}
	public void auxiliatura(View v){
		Intent p = new Intent(this,Auxiliatura.class);
		startActivity(p);
	}

}
