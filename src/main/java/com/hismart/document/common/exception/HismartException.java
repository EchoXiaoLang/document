package com.hismart.document.common.exception;

/**
 * HISMART 系统内部异常
 */
public class HismartException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public HismartException(String message) {
        super(message);
    }
}
