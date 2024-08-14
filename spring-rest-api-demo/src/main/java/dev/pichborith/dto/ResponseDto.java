package dev.pichborith.dto;

public class ResponseDto<T extends Object> {

    private boolean isSuccess = false;
    private String message;
    private T data = null;

    public ResponseDto(String message) {
        this(false, message, null);
    }

    public ResponseDto(boolean isSuccess, String message, T data) {
        this.data = data;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if (data != null) {
            this.isSuccess = true;
        }
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }
}
