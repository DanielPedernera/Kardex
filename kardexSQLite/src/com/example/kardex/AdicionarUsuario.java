package com.example.kardex;

import com.example.kardex.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AdicionarUsuario extends Activity {
	String modo = "";
	String tipo = "";
	String[] item = null;
	EditText nombre;
	EditText paterno;
	EditText materno;
	EditText codigo;
	CheckBox accessDocente;
	CheckBox accessEstudiante;
	CheckBox accessPersonal;
	CheckBox accessSemestre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adicionar_docente);
		nombre = (EditText) findViewById(R.id.txtNombreUsu);
		paterno = (EditText) findViewById(R.id.txtPaternoUsu);
		materno = (EditText) findViewById(R.id.txtMaternoUsu);
		codigo = (EditText) findViewById(R.id.txtCodigoUsu);

		accessDocente = (CheckBox) findViewById(R.id.cbtnDocente);
		accessEstudiante = (CheckBox) findViewById(R.id.cbtnEstudiante);
		accessPersonal = (CheckBox) findViewById(R.id.cbtnPersonal);
		accessSemestre = (CheckBox) findViewById(R.id.cbtnSemestre);
		
		

		Bundle b = this.getIntent().getExtras();
		modo = b.getString("modo");
		tipo = b.getString("tipo");
		if (modo.equals("edicion")) {
			item = b.getStringArray("item");
			codigo.setText(item[0]);
			nombre.setText(item[1]);
			paterno.setText(item[2]);
			materno.setText(item[3]);
			if(tipo.equals("Docente")){
				RadioButton rt =(RadioButton)findViewById(R.id.rbtnTitular);
				RadioButton rc =(RadioButton)findViewById(R.id.rbtnAcontrato);
				if(item[5].equals("1")){
					rt.setChecked(true);
					rc.setChecked(false);
				}
				else{
					rt.setChecked(false);
					rc.setChecked(true);
				}
			}
			else{
				if(tipo.equals("Administrativo")){
					if(item.length > 4){
						if( item[4].equals("docente")){
							accessDocente.setChecked(true);
							LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsDocente);
							ld.setVisibility(0);
							valuar(item[5]);
						}
						if(item[4].equals("estudiante")||item[6].equals("estudiante")){
							accessEstudiante.setChecked(true);
						}
						if(item[4].equals("admin")||item[6].equals("admin")||item[8].equals("admin")){
							accessPersonal.setChecked(true);
						}
					}

				}
			}

		}
		TextView ac = (TextView)findViewById(R.id.lblAccess);
		if (tipo.equals("Docente")) {
			TextView t = (TextView) findViewById(R.id.lblTipo);
			t.setVisibility(0);
			RadioGroup rg = (RadioGroup) findViewById(R.id.rbtngTipo);
			rg.setVisibility(0);
			ac.setVisibility(8);
			accessDocente.setVisibility(8);
			accessEstudiante.setVisibility(8);
			accessPersonal.setVisibility(8);
			accessSemestre.setVisibility(8);
		}
		else{
			if (tipo.equals("Estudiante")) {
				accessDocente.setVisibility(8);
				accessEstudiante.setVisibility(8);
				accessPersonal.setVisibility(8);
				accessSemestre.setVisibility(8);
			}
			else{

			}
		}
	}

	private void valuar(String permiso) {
		CheckBox pl = (CheckBox) findViewById(R.id.cbtnLeerDocente);
		CheckBox pe = (CheckBox) findViewById(R.id.cbtnEscribirDocente);
		CheckBox pd = (CheckBox) findViewById(R.id.cbtnEliminarDocente);
		if (permiso.equals("7")) {
			pl.setChecked(true); 
            pe.setChecked(true);
            pd.setChecked(true);
		}
		if (permiso.equals("6")) {
			pl.setChecked(true);
			pe.setChecked(true);
		//	pd.setChecked(false);
		}
		if (permiso.equals("5")) {
			pl.setChecked(true); 
		//	pe.setChecked(false); 
			pd.setChecked(true);
		}
		if (permiso.equals("4")) {
			pl.setChecked(true);
		//	pe.setChecked(false);
		//	pd.setChecked(false);
		}
		if (permiso.equals("3")) {
		//	pl.setChecked(false);
			pe.setChecked(true);
			pd.setChecked(true);
		}
		if (permiso.equals("2")) {
		//	pl.setChecked(false); 
            pe.setChecked(true);
        //  pd.setChecked(false);
		}
		if (permiso.equals("1")) {
	//		pl.setChecked(false);
	//		pe.setChecked(false);
            pd.setChecked(true);
		}	
	}

	public void permisosDoc(View v) {
		LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsDocente);
		if (accessDocente.isChecked()) {
			ld.setVisibility(0);
		} else {
			ld.setVisibility(8);
		}
	}

	public void permisosEst(View v) {
		LinearLayout le = (LinearLayout) findViewById(R.id.stackPermissionsEstudiante);
		if (accessEstudiante.isChecked()) {
			le.setVisibility(0);
		} else {
			le.setVisibility(8);
		}
	}
	public void permisosPer(View v) {
		LinearLayout le = (LinearLayout) findViewById(R.id.stackPermissionsPersonal);
		if (accessPersonal.isChecked()) {
			le.setVisibility(0);
		} else {
			le.setVisibility(8);
		}
	}

	public void adicionarDocenteDB(View v) {
		if (tipo.equals("Docente")) {
			addDoc();
		} else {
			if (tipo.endsWith("Estudiante")) {
				addEst();
			} else {
				if (tipo.equals("Administrativo")) {
					addAdm();
				} else {
					Toast.makeText(getApplicationContext(),
							"Aun en costruccion", Toast.LENGTH_LONG).show();
				}
			}
		}

	}

	private void addAdm() {
		ContentValues c = new ContentValues();
		c.put("idAdmin", codigo.getText().toString().trim());
		c.put("Nombre", nombre.getText().toString().trim());
		c.put("Paterno", paterno.getText().toString().trim());
		c.put("Materno", materno.getText().toString().trim());

		ContentValues cusu = new ContentValues();
		cusu.put("idUsuario", codigo.getText().toString());
		cusu.put("password", "123456");
		cusu.put("tipo", "admin");
		ContentValues cusa = new ContentValues();
		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);

			if (modo.equals("edicion")) {
				if (item != null) {
					String id = item[0];
					db.update("Admin", c, "idDocente like " + "'" + id + "'",
							null);
					db.update("Usuario", cusu, "idUsuario like " + "'" + id
							+ "'", null);
					if (accessDocente.isChecked()) {
						cusa.put("idAcceso", codigo.getText().toString());
						cusa.put("tipoAcceso", "docente");
						cusa.put("permiso", valuarDoc());
						db.update("Acceso", cusa,
								"idAcceso like " + "'" + id + "'", null);
					}
					if (accessEstudiante.isChecked()) {
						cusa.put("idAcceso", codigo.getText().toString());
						cusa.put("tipoAcceso", "estudiante");
						cusa.put("permiso", valuarEst());
						db.update("Acceso", cusa,
								"idAcceso like " + "'" + id + "'", null);
					}
					if (accessPersonal.isChecked()) {
						cusa.put("idAcceso", codigo.getText().toString());
						cusa.put("tipoAcceso", "admin");
						cusa.put("permiso", valuarAdmin());
						db.update("Acceso", cusa,
								"idAcceso like " + "'" + id + "'", null);
					}					
					Toast.makeText(getApplicationContext(),
							"Datos actualizados correctamente",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				db.insert("Admin", null, c);
				db.insert("Usuario", null, cusu);	
				if (accessDocente.isChecked()) {
					cusa.put("idAcceso", codigo.getText().toString());
					cusa.put("tipoAcceso", "docente");
					cusa.put("permiso", valuarDoc());
					db.insert("Acceso", null, cusa);
				}
				if (accessEstudiante.isChecked()) {
					cusa.put("idAcceso", codigo.getText().toString());
					cusa.put("tipoAcceso", "estudiante");
					cusa.put("permiso", valuarEst());
					db.insert("Acceso", null, cusa);
				}
				if (accessPersonal.isChecked()) {
					cusa.put("idAcceso", codigo.getText().toString());
					cusa.put("tipoAcceso", "admin");
					cusa.put("permiso", valuarAdmin());
					db.insert("Acceso", null, cusa);
				}				
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

	private void addEst() {
		ContentValues c = new ContentValues();
		c.put("idEstudiante", codigo.getText().toString().trim());
		c.put("Nombre", nombre.getText().toString().trim());
		c.put("Paterno", paterno.getText().toString().trim());
		c.put("Materno", materno.getText().toString().trim());
		ContentValues cusu = new ContentValues();
		cusu.put("idUsuario", codigo.getText().toString());
		cusu.put("password", "123456");
		cusu.put("tipo", "estudiante");

		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);

			if (modo.equals("edicion")) {
				if (item != null) {
					String id = item[0];
					db.update("Estudiante", c, "idEstudiante like " + "'" + id
							+ "'", null);
					db.update("Usuario", cusu, "idUsuario like " + "'" + id
							+ "'", null);
					Toast.makeText(getApplicationContext(),
							"Datos actualizados correctamente",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				db.insert("Estudiante", null, c);
				db.insert("Usuario", null, cusu);
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

	private void addDoc() {
		ContentValues c = new ContentValues();
		c.put("idDocente", codigo.getText().toString().trim());
		c.put("Nombre", nombre.getText().toString().trim());
		c.put("Paterno", paterno.getText().toString().trim());
		c.put("Materno", materno.getText().toString().trim());
		c.put("Grado", "licendicado");
		RadioButton contrato = (RadioButton) findViewById(R.id.rbtnAcontrato);
		if (contrato.isChecked()) {
			c.put("Titular", false);
		} else {
			c.put("Titular", true);
		}
		ContentValues cusu = new ContentValues();
		cusu.put("idUsuario", codigo.getText().toString());
		cusu.put("password", "123456");
		cusu.put("tipo", "docente");

		try {
			SQLiteDatabase db = SQLiteDatabase.openDatabase(
					"/mnt/sdcard/kardex", null,
					SQLiteDatabase.CREATE_IF_NECESSARY);

			if (modo.equals("edicion")) {
				if (item != null) {
					String id = item[0];
					db.update("Docente", c, "idDocente like " + "'" + id + "'",
							null);
					db.update("Usuario", cusu, "idUsuario like " + "'" + id
							+ "'", null);
					Toast.makeText(getApplicationContext(),
							"Datos actualizados correctamente",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				db.insert("Docente", null, c);
				db.insert("Usuario", null, cusu);
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

	private String valuarDoc() {
		String permiso = "0";
		CheckBox pl = (CheckBox) findViewById(R.id.cbtnLeerDocente);
		CheckBox pe = (CheckBox) findViewById(R.id.cbtnEscribirDocente);
		CheckBox pd = (CheckBox) findViewById(R.id.cbtnEliminarDocente);
		if (pl.isChecked() && pe.isChecked() && pd.isChecked()) {
			permiso = "7";
		}
		if (pl.isChecked() && pe.isChecked() && !pd.isChecked()) {
			permiso = "6";
		}
		if (pl.isChecked() && !pe.isChecked() && pd.isChecked()) {
			permiso = "5";
		}
		if (pl.isChecked() && !pe.isChecked() && !pd.isChecked()) {
			permiso = "4";
		}
		if (!pl.isChecked() && pe.isChecked() && pd.isChecked()) {
			permiso = "3";
		}
		if (!pl.isChecked() && pe.isChecked() && !pd.isChecked()) {
			permiso = "2";
		}
		if (!pl.isChecked() && !pe.isChecked() && pd.isChecked()) {
			permiso = "1";
		}
		return permiso;
	}
	private String valuarEst() {
		String permiso = "0";
		CheckBox pl = (CheckBox) findViewById(R.id.cbtnLeerEstudiante);
		CheckBox pe = (CheckBox) findViewById(R.id.cbtnEscribirEstudiante);
		CheckBox pd = (CheckBox) findViewById(R.id.cbtnEliminarEstudiante);
		if (pl.isChecked() && pe.isChecked() && pd.isChecked()) {
			permiso = "7";
		}
		if (pl.isChecked() && pe.isChecked() && !pd.isChecked()) {
			permiso = "6";
		}
		if (pl.isChecked() && !pe.isChecked() && pd.isChecked()) {
			permiso = "5";
		}
		if (pl.isChecked() && !pe.isChecked() && !pd.isChecked()) {
			permiso = "4";
		}
		if (!pl.isChecked() && pe.isChecked() && pd.isChecked()) {
			permiso = "3";
		}
		if (!pl.isChecked() && pe.isChecked() && !pd.isChecked()) {
			permiso = "2";
		}
		if (!pl.isChecked() && !pe.isChecked() && pd.isChecked()) {
			permiso = "1";
		}
		return permiso;
	}
	private String valuarAdmin() {
		String permiso = "0";
		CheckBox pl = (CheckBox) findViewById(R.id.cbtnLeerPersonal);
		CheckBox pe = (CheckBox) findViewById(R.id.cbtnEscribirPersonal);
		CheckBox pd = (CheckBox) findViewById(R.id.cbtnEliminarPersonal);
		if (pl.isChecked() && pe.isChecked() && pd.isChecked()) {
			permiso = "7";
		}
		if (pl.isChecked() && pe.isChecked() && !pd.isChecked()) {
			permiso = "6";
		}
		if (pl.isChecked() && !pe.isChecked() && pd.isChecked()) {
			permiso = "5";
		}
		if (pl.isChecked() && !pe.isChecked() && !pd.isChecked()) {
			permiso = "4";
		}
		if (!pl.isChecked() && pe.isChecked() && pd.isChecked()) {
			permiso = "3";
		}
		if (!pl.isChecked() && pe.isChecked() && !pd.isChecked()) {
			permiso = "2";
		}
		if (!pl.isChecked() && !pe.isChecked() && pd.isChecked()) {
			permiso = "1";
		}
		return permiso;
	}
}
