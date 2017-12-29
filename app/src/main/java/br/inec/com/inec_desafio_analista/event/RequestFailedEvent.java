package br.inec.com.inec_desafio_analista.event;

/* Evento quando o request dá errado. */

public class RequestFailedEvent {

    String message;
    int code;

    public RequestFailedEvent(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
