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
	EditText ci;
	EditText nroitem;
	EditText fecnac;
	EditText grado;
	String codigo;
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
		ci = (EditText)findViewById(R.id.txtCi);
		nroitem = (EditText)findViewById(R.id.txtItem);
		fecnac = (EditText) findViewById(R.id.txtFecNac);
		grado = (EditText) findViewById(R.id.txtGrado);

		accessDocente = (CheckBox) findViewById(R.id.cbtnDocente);
		accessEstudiante = (CheckBox) findViewById(R.id.cbtnEstudiante);
		accessPersonal = (CheckBox) findViewById(R.id.cbtnPersonal);
		accessSemestre = (CheckBox) findViewById(R.id.cbtnSemestre);


		Bundle b = this.getIntent().getExtras();
		modo = b.getString("modo");
		tipo = b.getString("tipo");
		if (modo.equals("edicion")) {
			item = b.getStringArray("item");
			codigo=item[0];
			nombre.setText(item[1]);
			paterno.setText(item[2]);
			materno.setText(item[3]);
			ci.setText(item[4]);
			fecnac.setText(item[5]);
			nroitem.setText(item[6]);
			if(tipo.equals("Docente")){
				grado.setText(item[7]);
				RadioButton rt =(RadioButton)findViewById(R.id.rbtnTitular);
				RadioButton rc =(RadioButton)findViewById(R.id.rbtnAcontrato);
				if(item[8].equals("1")){
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
					if(item.length > 7){
						if( item[7].equals("docente")){
							accessDocente.setChecked(true);
							LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsDocente);
							ld.setVisibility(0);
							valuar(item[8],item[7]);
						}
						else{
							if(item[7].equals("estudiante"))
							{
								accessEstudiante.setChecked(true);
								LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsEstudiante);
								ld.setVisibility(0);
								valuar(item[8],item[7]);
							}
							else{
								if(item[7].equals("admin"))
								{
									accessPersonal.setChecked(true);
									LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsPersonal);
									ld.setVisibility(0);
									valuar(item[8],item[7]);
								}
							}
						}
					}
					if(item.length >9)
					{
						if(item[9].equals("estudiante")){
							accessEstudiante.setChecked(true);
							LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsEstudiante);
							ld.setVisibility(0);
							valuar(item[10],item[9]);
						}
						else{
							if(item[9].equals("admin"))
							{
								accessPersonal.setChecked(true);
								LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsPersonal);
								ld.setVisibility(0);
								valuar(item[10],item[9]);
							}
						}
					}
					if(item.length >11)
					{
						if(item[11].equals("admin")){
							accessPersonal.setChecked(true);
							LinearLayout ld = (LinearLayout) findViewById(R.id.stackPermissionsPersonal);
							ld.setVisibility(0);
							valuar(item[12],item[11]);
						}

					}

				}
			}

		}
		TextView ac = (TextView)findViewById(R.id.lblAccess);
		if (tipo.equals("Docente")) {
			TextView g = (TextView)findViewById(R.id.lblLevel);
			g.setVisibility(0);
			grado.setVisibility(0);
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
				TextView t = (TextView)findViewById(R.id.lblItem);
				t.setText("Matricula");
				accessDocente.setVisibility(8);
				accessEstudiante.setVisibility(8);
				accessPersonal.setVisibility(8);
				accessSemestre.setVisibility(8);
			}
			else{

			}
		}
	}

	private void valuar(String permiso, String tipo) 
	{
		CheckBox pl = (CheckBox) findViewById(R.id.cbtnLeerDocente);
		CheckBox pe = (CheckBox) findViewById(R.id.cbtnEscribirDocente);
		CheckBox pd = (CheckBox) findViewById(R.id.cbtnEliminarDocente);
		if(tipo.equals("estudiante")){
			pl = (CheckBox) findViewById(R.id.cbtnLeerEstudiante);
			pe = (CheckBox) findViewById(R.id.cbtnEscribirEstudiante);
			pd = (CheckBox) findViewById(R.id.cbtnEliminarEstudiante);
		}
		else{
			if(tipo.equals("admin")){
				pl = (CheckBox) findViewById(R.id.cbtnLeerPersonal);
				pe = (CheckBox) findViewById(R.id.cbtnEscribirPersonal);
				pd = (CheckBox) findViewById(R.id.cbtnEliminarPersonal);
			}
		}
		if (permiso.equals("7")) {
			pl.setChecked(true); 
			pe.setChecked(true);
			pd.setChecked(true);
		}
		if (permiso.equals("6")) {
			pl.setChecked(true);
			pe.setChecked(true);
		}
		if (permiso.equals("5")) {
			pl.setChecked(true);  
			pd.setChecked(true);
		}
		if (permiso.equals("4")) {
			pl.setChecked(true);;
		}
		if (permiso.equals("3")) {
			pe.setChecked(true);
			pd.setChecked(true);
		}
		if (permiso.equals("2")) {
			pe.setChecked(true);
		}
		if (permiso.equals("1")) {
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
		codigo = paterno.getText().charAt(0)+""+materno.getText().charAt(0)+""+nombre.getText().charAt(0)+""+fecnac.getText().toString();
		c.put("idAdmin", codigo.trim());
		c.put("Nombre", nombre.getText().toString().trim());
		c.put("Paterno", paterno.getText().toString().trim());
		c.put("Materno", materno.getText().toString().trim());
		c.put("Ci", ci.getText().toString().trim());
		c.put("Item", nroitem.getText().toString().trim());
		c.put("Fec_nac",fecnac.getText().toString().trim());
		ContentValues cusu = new ContentValues();
		cusu.put("idUsuario", codigo);
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
					db.update("Admin", c, "idAdmin like " + "'" + id + "'",
							null);
					db.update("Usuario", cusu, "idUsuario like " + "'" + id
							+ "'", null);
					if (accessDocente.isChecked()) {
						cusa.put("idAcceso", codigo);
						cusa.put("tipoAcceso", "docente");
						cusa.put("permiso", valuarDoc());
						db.update("Acceso", cusa,
								"idAcceso like " + "'" + id + "'", null);
					}
					if (accessEstudiante.isChecked()) {
						cusa.put("idAcceso", codigo);
						cusa.put("tipoAcceso", "estudiante");
						cusa.put("permiso", valuarEst());
						db.update("Acceso", cusa,
								"idAcceso like " + "'" + id + "'", null);
					}
					if (accessPersonal.isChecked()) {
						cusa.put("idAcceso", codigo);
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
					cusa.put("idAcceso", codigo);
					cusa.put("tipoAcceso", "docente");
					cusa.put("permiso", valuarDoc());
					db.insert("Acceso", null, cusa);
				}
				if (accessEstudiante.isChecked()) {
					cusa.put("idAcceso", codigo);
					cusa.put("tipoAcceso", "estudiante");
					cusa.put("permiso", valuarEst());
					db.insert("Acceso", null, cusa);
				}
				if (accessPersonal.isChecked()) {
					cusa.put("idAcceso", codigo);
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
		codigo = paterno.getText().charAt(0)+""+materno.getText().charAt(0)+""+nombre.getText().charAt(0)+""+fecnac.getText().toString();
		c.put("idEstudiante", codigo.trim());
		c.put("Nombre", nombre.getText().toString().trim());
		c.put("Paterno", paterno.getText().toString().trim());
		c.put("Materno", materno.getText().toString().trim());
		c.put("Ci", ci.getText().toString().trim());
		c.put("Matricula", nroitem.getText().toString().trim());
		c.put("Fec_nac",fecnac.getText().toString().trim());
		ContentValues cusu = new ContentValues();
		cusu.put("idUsuario", codigo);
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
		codigo = paterno.getText().charAt(0)+""+materno.getText().charAt(0)+""+nombre.getText().charAt(0)+""+fecnac.getText().toString();
		c.put("idDocente", codigo.trim());
		c.put("Nombre", nombre.getText().toString().trim());
		c.put("Paterno", paterno.getText().toString().trim());
		c.put("Materno", materno.getText().toString().trim());
		c.put("Grado",grado.getText().toString().trim());
		RadioButton contrato = (RadioButton) findViewById(R.id.rbtnAcontrato);
		if (contrato.isChecked()) {
			c.put("Titular", false);
		} else {
			c.put("Titular", true);
		}
		c.put("Item", nroitem.getText().toString().trim());
		c.put("Ci", ci.getText().toString().trim());
		c.put("Fec_nac",fecnac.getText().toString().trim());
		ContentValues cusu = new ContentValues();
		cusu.put("idUsuario", codigo);
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
