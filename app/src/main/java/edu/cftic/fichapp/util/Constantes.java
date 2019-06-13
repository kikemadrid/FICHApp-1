package edu.cftic.fichapp.util;

import android.Manifest;

public class Constantes {

    public static final String TAG_APP = "FichApp";
    public static final String ROL_GESTOR = "gestor";
    public static final String ROL_EMPLEADO = "empleado";
    public static final String EMPLEADO = "objeto_empleado";
    public static final String EMPRESA = "objeto_empresa";

    public static final String ACCION_MENU_GESTOR = "accion_menu_gestor";
    public static final String ACCION_NUEVA_EMPRESA = "accion_nueva_empresa";
    public static final String ACCION_MODIFICACION_EMPRESA = "accion_modificacion_empresa";

    public static final String[] PERMISOS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int CODIGO_PETICION_PERMISOS = 150;
    public static final int CODIGO_PETICION_SELECCIONAR_FOTO = 100;




}
