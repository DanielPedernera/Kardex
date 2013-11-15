package com.example.kardex;

import com.example.kardex.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Administrador extends Activity {

	private int posD = 100;
	private int posE = 100;
	private int posP = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administrador);
		Bundle b = this.getIntent().getExtras();
		String[] accesos = b.getStringArray("accesos");
		Button bd = (Button) findViewById(R.id.btnAdminDocente);
		Button bp = (Button) findViewById(R.id.btnAdminPersonal);
		Button be = (Button) findViewById(R.id.btnAdminEstudiante);
		Button ba = (Button) findViewById(R.id.btnAdminSemestre);
		// visible 0 , invisible 4, gone 8
		posD = contiene(accesos, "docente");
		if (posD != 100) {
			bd.setVisibility(0);
		}
		posE = contiene(accesos, "estudiante");
		if (posE != 100) {
			be.setVisibility(0);
		}
		posP = contiene(accesos, "personal");
		if (posP != 100) {
			bp.setVisibility(0);
		}
	}

	private int contiene(String[] accesos, String string) {
		for (int i = 0; i < accesos.length; i++) {
			if (accesos[i].equals(string)) {
				return i;
			}
		}
		return 100;
	}

	public void adicionarDocente(View v) {
		Bundle b = this.getIntent().getExtras();
		Intent p = new Intent(this, AdminUsuario.class);
		p.putExtra("permisos", b.getIntArray("permisos"));
		p.putExtra("permiso", posD);
		p.putExtra("tipo", "Docente");
		startActivity(p);
	}

	public void adicionarEstudiante(View v) {
		Bundle b = this.getIntent().getExtras();
		Intent p = new Intent(this, AdminUsuario.class);
		p.putExtra("permisos", b.getIntArray("permisos"));
		p.putExtra("permiso", posE);
		p.putExtra("tipo", "Estudiante");
		startActivity(p);
	}

	public void adicionarUsuario(View v) {
		Bundle b = this.getIntent().getExtras();
		Intent p = new Intent(this, AdminUsuario.class);
		p.putExtra("permisos", b.getIntArray("permisos"));
		p.putExtra("permiso", posP);
		p.putExtra("tipo", "Administrativo");
		startActivity(p);
	}
	public void semestre(View v){
		Intent p = new Intent(this,AdminSemestre.class);
		startActivity(p);
	}
}
