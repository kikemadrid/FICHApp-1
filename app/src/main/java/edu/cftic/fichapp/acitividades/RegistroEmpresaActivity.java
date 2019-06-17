package edu.cftic.fichapp.acitividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.cftic.fichapp.R;
import edu.cftic.fichapp.bean.Empleado;
import edu.cftic.fichapp.bean.Empresa;
import edu.cftic.fichapp.persistencia.DB;
import edu.cftic.fichapp.util.Constantes;
import edu.cftic.fichapp.util.FocusListenerFormularios;

public class RegistroEmpresaActivity extends AppCompatActivity {


    EditText cajatextomail;
    EditText cajatextocif;
    EditText cajatextoresp;
    EditText cajatextonombreempresa;
    Button botonM;
    Button botonN;
    Button botonE;
    DB dataBase;
    Empleado empleado;
    Empresa empresa;
    String accion_menu; // para saber desde que acción del menú del gestor nos esta llegando
    Intent intent;
    private Uri photo_uri; // para almacenar la ruta de la imagen
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);
        dataBase = new DB();
        //#########PRUEBA###########
        empresa = dataBase.empresas.primero();
        //########################
        // referenciamos todos los objetos de la vista
        referenciarObjetos();
        //Activamos el escuchador que al peder el foco relizará las posteriores validaciones por cada caja del formulario
        activarFocoValidacion();


        //Recuperación de los datos del Intent
        Intent intent = getIntent();
        empleado = (Empleado) intent.getSerializableExtra(Constantes.EMPLEADO);

        logicaBotonesGestor();

        // Esta instrucción pide los permisos para acceder a galería de fotos.
        ActivityCompat.requestPermissions(this, Constantes.PERMISOS, Constantes.CODIGO_PETICION_PERMISOS);

    }

    /**
     * METODO QUE RECIBI LA VUELTA DE LA PETICIÓN DE SERVICIOS, EN FUNCIÓN DE LA RESPUESTA PASA POR UNO U OTRO CAMINO
     *
     * @param requestCode  Código de respuesta
     * @param permissions  Array de String de permisos pedido
     * @param grantResults Resultado de estos permisos con su código
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            Log.d("MIAPP", "Me ha concedido los permisos");

        } else {
            Log.d("MIAPP", "NO ME ha concedido los permisos");
            Toast.makeText(this, "No puedes seguir", Toast.LENGTH_SHORT).show();
            this.finish();
        }


    }

    /**
     * METODO QUE SE LANZA AL PULSAR EL IMAGEBUTTON PARA SELECCIONAR UNA FOTO DE LA GALERÍA
     *
     * @param view ImageButton que activa el método
     */
    public void seleccionarFoto(View view) {
        Log.d("MIAPP", "Quiere seleccionar una foto");
        Intent intent_pide_foto = new Intent();
        intent_pide_foto.setAction(Intent.ACTION_GET_CONTENT);
        intent_pide_foto.setType("image/*"); // tipo mime
        startActivityForResult(intent_pide_foto, Constantes.CODIGO_PETICION_SELECCIONAR_FOTO);
    }

    /**
     * METODO QUE RECIBE LA VUELTA DE LA LLAMADA AL MÉTODO startActivityForResult
     *
     * @param requestCode Codigo envíado
     * @param resultCode  Resultado de la operación de la seleeción de foto Si/No
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data); //no llamamos al padre
        setearImagenDesdeArchivo(resultCode, data);

    }

    /**
     * MÉTODO QUE RECIBE LA FOTO Y EL RESULTADO (HA SELECCIONADO O NO LA FOTO DE LA GALERIA). EN CASO AFIRMATIVO
     * LA SETEA EN EL imageView Y CARGA EN EL OBJETO EMPRESA PARA SU POSTERIOR GUARDADO EN BD
     *
     * @param resultado Resultado de si ha seleccionado o no una foto
     * @param data      Intent implícito donde tenemos los datos de la foto
     */
    private void setearImagenDesdeArchivo(int resultado, Intent data) {
        switch (resultado) {
            case RESULT_OK:
                Log.d("MIAPP", "La foto ha sido seleccionada");
                this.photo_uri = data.getData();
                this.imageView.setImageURI(photo_uri);
                break;

            case RESULT_CANCELED:
                Log.d("MIAPP", "La foto no ha sido seleccionada canceló");
                break;
        }
    }

    /**
     * METODO QUE RECIBE EL OBJETO EMPLEADO Y EN FUNCIÓN DEL ROL Y LA ACCIÓN RECIBIDA EN EL INTENT HABILITA BOTONES
     * Y SETEA VALORES EN LOS EDITTEXT
     */
    public void logicaBotonesGestor() {
        // En principio nunca debería llegar a este menú un empleado, comentar con jefe de proyecto como proceder
        /*if (empleado != null && accion_menu != null && !accion_menu.equals("")) {
            if (empleado.getRol().equals("empleado")) {
                botonM.setEnabled(false);
                botonE.setEnabled(false);
                botonN.setEnabled(false);
                // para colocar la flecha hacia detras
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                // equivalente pero colocando una flecha personalizada
                //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
            } else if (accion_menu.equals(Constantes.ACCION_NUEVA_EMPRESA)) {
                botonE.setEnabled(false);
                botonM.setEnabled(false);
            } else if (accion_menu.equals(Constantes.ACCION_MODIFICACION_EMPRESA)) {
                empresa = dataBase.empresas.primero();
                if (empresa != null){
                    botonN.setEnabled(false);
                    setearDatosEmpresa();
                }else{
                    empresa= new Empresa();
                }


            }
        }*/

        if (empresa==null){
            //primera vez
            botonM.setEnabled(false);
            botonE.setEnabled(false);
        }else {
            botonN.setEnabled(false);
            setearDatosEmpresa();
        }
    }

    /**
     * MÉTODO QUE SETEA TODOS LOS VALORES DE LA EMPRESA
     */
    public void setearDatosEmpresa () {
        cajatextocif.setText(empresa.getCif());
        cajatextonombreempresa.setText(empresa.getNombre_empresa());
        cajatextoresp.setText(empresa.getResponsable());
        cajatextomail.setText(empresa.getEmail());
        imageView.setImageURI(Uri.parse(empresa.getRutalogo()));

    }

    /**
     * METODO QUE ACTIVA LA CLASE PARA VALIDAR LOS DATOS CUANDO VAMOS PERDIENDO EL FOCO EN CADA EDITTEXT
     */
    public void activarFocoValidacion() {

        FocusListenerFormularios focusListenerFormularios = new FocusListenerFormularios(this);
        cajatextocif.setOnFocusChangeListener(focusListenerFormularios);
        cajatextomail.setOnFocusChangeListener(focusListenerFormularios);
        cajatextoresp.setOnFocusChangeListener(focusListenerFormularios);
        cajatextonombreempresa.setOnFocusChangeListener(focusListenerFormularios);
    }

    /**
     * METODO QUE SE LANZA AL PULSAR EL BOTON REGISTRAR, GUARDA EN BASE DE DATOS LA NUEVA EMPRESA
     *
     * @param v Boton que lanza el método
     */
    public void registrar(View v) {

        dataBase.empresas.nuevo(recogerDatosCajas());
        lanzarIntentLogin();

    }

    /**
     * METODO QUE SE LANZA AL PULSAR EL BOTON MODIFICAR, GUARDA EN BASE DE DATOS LA EMPRESA MODIFICADA
     *
     * @param v Boton que lanza el método
     */
    public void modificar(View v) {

        dataBase.empresas.actualizar(recogerDatosCajas());
        lanzarIntentMenuGestor();

    }

    /**
     * METODO QUE SE LANZA AL PULSAR EL BOTON ELIMINAR, ELIMINA EN BASE DE DATOS LA EMPRESA, PREVIO AVISO DE CONFIRMACIÓN
     *
     * @param v Boton que lanza el método
     */
    public void eliminar(View v) {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("Si elimina la empresa todos los empleados dados de alta no tendrán empresa. ¿Desea continuar?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dataBase.empresas.eliminar(recogerDatosCajas().getId_empresa());
                lanzarIntentMenuGestor();
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

        return new Empresa(cajatextocif.getText().toString(), cajatextonombreempresa.getText().toString(), cajatextoresp.getText().toString(), cajatextomail.getText().toString(),photo_uri.toString());
    }

    /**
     * MÉTODO QUE LANZA EL INTENT HACIA EL LOGIN
     */
    public void lanzarIntentLogin() {
        intent = new Intent(this, LoginActivity.class);
        intent.putExtra("EMPLEADO", empleado);
        startActivity(intent);
        this.finish();
    }

    /**
     * MÉTODO QUE LANZA EL INTENT HACIA EL MENÚ DEL GESTOR
     */
    public void lanzarIntentMenuGestor() {
        intent = new Intent(this, MenuGestorActivity.class);
        intent.putExtra("EMPLEADO", empleado);
        startActivity(intent);
        this.finish();
    }


    /**
     * MÉTODO QUE REFERENCIA TODOS LOS OBJETOS DE LA VISTA PARA SU POSTERIOR USO
     */
    public void referenciarObjetos() {
        imageView = findViewById(R.id.imagen_empresa);
        botonM = (Button) findViewById(R.id.botonmodificar);
        botonE = (Button) findViewById(R.id.botoneliminar);
        botonN = (Button) findViewById(R.id.botonenviar);
        cajatextocif = (EditText) findViewById(R.id.cajacif);
        cajatextomail = (EditText) findViewById(R.id.cajaemail);
        cajatextoresp = (EditText) findViewById(R.id.cajaresponsable);
        cajatextonombreempresa = (EditText) findViewById(R.id.cajanombreempresa);
    }

}