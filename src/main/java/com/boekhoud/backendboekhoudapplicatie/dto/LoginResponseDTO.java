package com.boekhoud.backendboekhoudapplicatie.dto;

public class LoginResponseDTO {
    private String message;

    public LoginResponseDTO(String message) {
        this.message = message;
    }

    // Getter en Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
