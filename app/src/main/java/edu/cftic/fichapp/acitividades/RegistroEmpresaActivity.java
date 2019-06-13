package edu.cftic.fichapp.acitividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



import java.util.List;

import edu.cftic.fichapp.R;
import edu.cftic.fichapp.bean.Empleado;
import edu.cftic.fichapp.bean.Empresa;
import edu.cftic.fichapp.persistencia.DB;
import edu.cftic.fichapp.util.FocusListenerFormularios;

public class RegistroEmpresaActivity extends AppCompatActivity {


    EditText cajatextomail;
    EditText cajatextocif;
    EditText cajatextoresp;
    EditText cajatextonombreempresa;
    DB dataBase;
    Empleado e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);

        // para colocar la flecha hacia detras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // equivalente pero colocando una flecha personalizada
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);


        //Activamos el escuchador que al peder el foco relizará las posteriores validaciones por cada caja del formulario
        activarFocoValidacion();

        // hay que determinar que vamos a recibir
        //Intent intent = getIntent();

        Intent intent = getIntent();

        e = (Empleado) intent.getSerializableExtra("erer");

        if (e != null) {
            logicaBotones(e);
        }


        dataBase = new DB();

    }

    public void logicaBotones(Empleado e) {

        if (e.getRol().equals("empleado")) {
            Button botonM = (Button) findViewById(R.id.botonmodificar);
            Button botonE = (Button) findViewById(R.id.botoneliminar);
            Button botonEn = (Button) findViewById(R.id.botonenviar);
            botonM.setEnabled(false);
            botonE.setEnabled(false);
            botonEn.setEnabled(false);
           /* Intent intent = new Intent(this,MenuEmpleadoActivity.class);
            intent.putExtra("EMPLEADO", e);
            startActivity(intent);*/
        }
    }

    public void activarFocoValidacion() {
        FocusListenerFormularios focusListenerFormularios = new FocusListenerFormularios(this);

        cajatextocif = (EditText) findViewById(R.id.cajacif);
        cajatextocif.setOnFocusChangeListener(focusListenerFormularios);

        cajatextomail = (EditText) findViewById(R.id.cajaemail);
        cajatextomail.setOnFocusChangeListener(focusListenerFormularios);

        cajatextoresp = (EditText) findViewById(R.id.cajaresponsable);
        cajatextoresp.setOnFocusChangeListener(focusListenerFormularios);

        cajatextonombreempresa = (EditText) findViewById(R.id.cajanombreempresa);
        cajatextonombreempresa.setOnFocusChangeListener(focusListenerFormularios);
    }


    public void registrar(View v) {

        dataBase.empresas.nuevo(recogerDatosCajas());

    }

    public void modificar(View v) {

        dataBase.empresas.actualizar(recogerDatosCajas());

    }

    public void eliminar(View v) {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("Si elimina la empresa todos los empleados dados de alta no tendrán empresa. ¿Desea continuar?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dataBase.empresas.eliminar(recogerDatosCajas().getId_empresa());
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                // Se cancela la operación de elmiminación
            }
        });
        dialogo1.show();
    }

    public Empresa recogerDatosCajas() {
        return  null;
        //return new Empresa(cajatextocif.getText().toString(), cajatextonombreempresa.getText().toString(), cajatextoresp.getText().toString(), cajatextomail.getText().toString());
    }


}