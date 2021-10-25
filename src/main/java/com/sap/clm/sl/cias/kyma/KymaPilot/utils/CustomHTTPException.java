package com.sap.clm.sl.cias.kyma.KymaPilot.utils;

public class CustomHTTPException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String message;

    private String name;

    private String method;


    /**
     * @param message
     */
    public CustomHTTPException(String message) {
        super();
        this.message = message;
    }

    /**
     * @param message
     * @param name
     */
    public CustomHTTPException(String message, String name) {
        super();
        this.message = message;
        this.name = name;
    }

    /**
     * @param message
     * @param name
     * @param method
     */
    public CustomHTTPException(String message, String name, String method) {
        super();
        this.message = message;
        this.name = name;
        this.method = method;
    }

}