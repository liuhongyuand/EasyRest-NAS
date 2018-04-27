package com.easyrest.model;

/**
 * Created by SEELE on 2016/10/2.
 */
public class ResponseEntity<T>  {
    private String code = "1";
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    private ResponseEntity<T> setFailed(){
        setFailed("Failed");
        return this;
    }

    private ResponseEntity<T> setFailed(String message){
        setFailed("-1", message);
        return this;
    }

    private void setFailed(String code, String message){
        setCode(code);
        setMessage(message);
    }

    private void setSuccess(){
        setSuccess("Success");
    }

    private void setSuccess(String message){
        setSuccess("1", message);
    }

    private void setSuccess(String code, String message){
        setCode(code);
        setMessage(message);
    }

    private void setData(T data) {
        this.data = data;
    }

    public static ResponseEntity<Boolean> buildOkResponse(){
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>();
        responseEntity.setMessage("ok");
        return responseEntity;
    }

    public static <T>ResponseEntity<T> buildOkResponse(T t){
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(t);
        return responseEntity;
    }

    public static ResponseEntity<Boolean> buildFailedResponse(){
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>();
        responseEntity.setData(false);
        responseEntity.setFailed();
        return responseEntity;
    }

    public static <T>ResponseEntity<T> buildFailedResponse(T t){
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(t);
        responseEntity.setFailed();
        return responseEntity;
    }

    public static <T>ResponseEntity<T> buildCustomizeResponse(int code, String message, T t){
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(t);
        responseEntity.setCode(String.valueOf(code));
        responseEntity.setMessage(message);
        return responseEntity;
    }

    public static <T>ResponseEntity<T> buildBaseResponse(T t){
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(t);
        responseEntity.setCode(null);
        responseEntity.setMessage(null);
        return responseEntity;
    }

}
