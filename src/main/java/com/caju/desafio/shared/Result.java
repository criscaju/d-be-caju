package com.caju.desafio.shared;


public class Result {
    private Boolean isSuccess;
    private Error error;

    public Error getError() {
        return error;
    }

    private void setError(Error error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return this.isSuccess;
    }

    private void setSuccess(Boolean success) {
        this.isSuccess = success;
    }

    private Result(Boolean isSuccess, Error error){
        if(isSuccess && error != Error.None ||
        !isSuccess && error == Error.None) {
            throw new RuntimeException("Invalid Error object");
        }
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public static Result success() { return new Result(true, Error.None); }

    public static Result failure(Error error) {
        return new Result(false, error);
    }
}
