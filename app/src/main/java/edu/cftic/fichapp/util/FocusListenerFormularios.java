package edu.cftic.fichapp.util;


import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.cftic.fichapp.R;


public class FocusListenerFormularios implements View.OnFocusChangeListener {

    private Activity actividad;
    private ViewGroup viewGroup;
    TextInputLayout wrapmail;

    public FocusListenerFormularios(Activity activity)
    {
        this.actividad = activity;

        viewGroup = actividad.findViewById(R.id.linearoot);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (!hasFocus) {

            String texto_introducido;
            String texto_tag;

            EditText cajatexto =  (EditText)v;

            texto_introducido = cajatexto.getText().toString();
            texto_tag = String.valueOf(cajatexto.getTag());

            switch (texto_tag){
                case "tagcif":
                    validarCif(texto_introducido,cajatexto);
                    break;
                case "tagnombreempresa":
                    validarNombreEmpresa(texto_introducido,cajatexto);
                    break;
                case "tagresponsable":
                    validarNombreResponsable(texto_introducido,cajatexto);
                    break;
                case "tagemail":
                    validarEmail(texto_introducido,cajatexto);
                    break;
                case "tagnombre":
                    validarNombreEmpleado(texto_introducido,cajatexto);
                    break;
                case "tagapellidos":
                    validarApellidosEmpleado(texto_introducido,cajatexto);
                    break;
                case "tagusername":
                    validarNombreUsuario(texto_introducido,cajatexto);
                    break;
                case "tagcontraseña":
                    validarPass(texto_introducido,cajatexto);
                case "tagrepcontraseña":
                    validarPassRep(texto_introducido,cajatexto);
                    break;
            }
        }
    }

    public void validarCif (String cif, EditText caja_texto){
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilcif);
        if (!Utilidades.nifValido (cif)) {
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("CIF Incorrecto");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }

    public void validarNombreEmpresa (String empresa, EditText caja_texto) {
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilnombreempresa);
        if (!Utilidades.validarNombre(empresa)){
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Incorrecto.");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }



    public void validarNombreResponsable (String nombre, EditText caja_texto) {
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilresponsable);
        if (!Utilidades.validarNombre(nombre)){
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Incorrecto.");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }


    public void validarEmail (String email, EditText caja_texto) {
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilemail);
        if (!Utilidades.emailValido (email)) {
            // Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Mail incorrecto");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }


    public void validarNombreEmpleado (String nombre, EditText caja_texto) {
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilcajanombre);
        if (!Utilidades.validarNombre(nombre)){
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Incorrecto. Entre 1 y 30 caracteres");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }

    public void validarNombreUsuario (String nombre, EditText caja_texto) {
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilcajausername);
        if (!Utilidades.validarNombre(nombre)){
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Incorrecto. Entre 1 y 30 caracteres");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }

    public void validarApellidosEmpleado (String nombre, EditText caja_texto) {
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilcajaapellidos);
        if (!Utilidades.validarNombre(nombre)){
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Incorrecto. Entre 1 y 30 caracteres");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }

    public void validarPass(String pass, EditText caja_texto){
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilcajacontraseña);
        if (!Utilidades.contrasenaValida(pass)){
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Contraseña invalida mín");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }

    }

    public void validarPassRep (String pass2, EditText caja_texto) {
        wrapmail = (TextInputLayout)actividad.findViewById(R.id.tilemail);
        if (!Utilidades.comprobarIgual(actividad.findViewById(R.id.cajacontraseña).toString(),pass2)){
            //Utilidades.limpiarFocusEditText(viewGroup,EditText.class);
            wrapmail.setError("Las contraseñas no coinciden");
            caja_texto.setText("");
        } else {
            wrapmail.setErrorEnabled(false);
        }
    }


}
