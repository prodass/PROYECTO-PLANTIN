package com.comunicaciones.plantas.utils;

public class GenericConstant {
    // =============================================================================================
    // CODIGO DE ERROR DEL CLIENTE Y SERVIDOR
    // =============================================================================================
    // CLIENT ERRORS
    public static final String BAD_REQUEST = "400";
    public static final String UNAUTHORIZED = "401";
    public static final String FORBIDDEN = "403";
    public static final String NOT_FOUND = "404";
    public static final String METHOD_NOT_ALLOWED = "405";
    public static final String NOT_ACCEPTABLE = "406";
    public static final String CONFLICT = "409";
    public static final String UNPROCESSABLE_ENTITY = "422";
    public static final String EXPECTATION_FAILED = "417";
    public static final Float NR_VUELTO_DEFAULT = (float) 0;

    // SERVER ERRORS
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String NOT_IMPLEMENTED = "501";
    public static final String BAD_GATEWAY = "503";
    public static final String SERVICE_UNAVAILABLE = "504";
    public static final String GATEWAY_TIMEOUT = "505";
    public static final String NOT_VALIDATED = "506";

    // ERRORS
    public static final String PREFIX_SERVER_ERROR = "SRV-";
    public static final String PREFIX_CLIENT_ERROR = "CLI-";

    // STATE
    public static final String STATE_ACTIVE = "1";
    public static final String STATE_INACTIVE = "0";


    // =============================================================================================
    // API
    // =============================================================================================
    public static final String API_VERSION = "/v1";
    public static final String RESOURCE_GENERIC = API_VERSION + "/comunicaciones";
    public static final String RESOURCE_PRIVATE = "/private";
    public static final String RESOURCE_PUBLIC = "/public";
    public static final String RESOURCE_INTERNAL = "/internal";
    public static final String RESOURCE_PRIVATE_WEB = RESOURCE_PRIVATE + "/web";
    public static final String RESOURCE_GENERIC_PUBLIC = RESOURCE_GENERIC + RESOURCE_PUBLIC;
    public static final String RESOURCE_GENERIC_PRIVATE = RESOURCE_GENERIC + "/private";
    public static final String RESOURCE_PLANTAS = "/plantas";
    public static final String RESOURCE_PLANTA = "/planta";
    public static final String RESOURCE_PLANTA_ID = RESOURCE_PLANTA + "/{idPlanta}";
    public static final String RESOURCE_SENSORES = "/sensores";

    // =============================================================================================
    // GENERICS
    // =============================================================================================


    // =============================================================================================
    // DB
    // =============================================================================================
    public static final String SCHEMA_NAME = "plantas_db";
    public static final String TAB_NAME_PLANTA = "planta";
    public static final String TAB_NAME_SENSOR = "sensor";
}
