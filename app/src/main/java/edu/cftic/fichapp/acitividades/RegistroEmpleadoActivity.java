package edu.cftic.fichapp.acitividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.cftic.fichapp.R;
import edu.cftic.fichapp.bean.Empleado;
import edu.cftic.fichapp.persistencia.DB;
import edu.cftic.fichapp.util.FocusListenerFormularios;


public class RegistroEmpleadoActivity extends AppCompatActivity {

    EditText cajatextonombre;
    EditText cajatextoapellidos;
    EditText cajatextousername;
    EditText cajatextocontraseña;
    EditText cajatextorepcontraseña;


    Empleado empleado;
    String gestor ="empleado";
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activarFocoValidacion();

        Intent intent = getIntent();

        empleado = (Empleado) intent.getSerializableExtra("erer");

        if (empleado != null) {
            logicaBotones(empleado);
        }




        db = new DB();

    }

    public void logicaBotones(Empleado e) {

        if (e.getRol().equals("empleado")) {
            Button botonR = (Button) findViewById(R.id.buttonRegistrar);
            Button botonM = (Button) findViewById(R.id.buttonModificar);
            Button botonE = (Button) findViewById(R.id.buttonEliminar);
            botonR.setEnabled(false);
            botonM.setEnabled(false);
            botonE.setEnabled(false);
           /* Intent intent = new Intent(this,MenuEmpleadoActivity.class);
            intent.putExtra("EMPLEADO", e);
            startActivity(intent);*/
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // HA TOCADO LA FLECHA
        if (item.getItemId() == android.R.id.home)
        // android.R recurso global no solo de mi app
        {
            Log.d("MIAPP", "Tocada la flecha");
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void registrarUsuario(View view) {

        EditText editText = findViewById(R.id.cajanombre);
        String nombre = editText.getText().toString();
        EditText editText1 = findViewById(R.id.cajaapellidos);
        String apellidos = editText1.getText().toString();
        empleado.setNombre(nombre+apellidos);

        EditText editText2 = findViewById(R.id.cajacontraseña);
        String contraseña = editText2.getText().toString();
        empleado.setClave(contraseña);

        EditText editText3 = findViewById(R.id.cajausername);
        String username = editText3.getText().toString();
        empleado.setUsuario(username);
        empleado.setRol("empleado");
        if (gestor == "gestor") {
            empleado.setRol(gestor);

        }

        db.empleados.nuevo(empleado);




    }

    public void activarFocoValidacion() {
        FocusListenerFormularios focusListenerFormularios = new FocusListenerFormularios(this);

        cajatextonombre = (EditText) findViewById(R.id.cajanombre);
        cajatextonombre.setOnFocusChangeListener(focusListenerFormularios);

        cajatextoapellidos = (EditText) findViewById(R.id.cajaapellidos);
        cajatextoapellidos.setOnFocusChangeListener(focusListenerFormularios);

        cajatextocontraseña = (EditText) findViewById(R.id.cajacontraseña);
        cajatextocontraseña.setOnFocusChangeListener(focusListenerFormularios);

        cajatextorepcontraseña = (EditText) findViewById(R.id.cajarepcontraseña);
        cajatextorepcontraseña.setOnFocusChangeListener(focusListenerFormularios);

        cajatextousername = (EditText) findViewById(R.id.cajausername);
        cajatextousername.setOnFocusChangeListener(focusListenerFormularios);
    }
}