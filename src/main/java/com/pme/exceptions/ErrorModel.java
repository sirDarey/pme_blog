package com.pme.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class ErrorModel {

    private String errorMessage = "Error(s) with input field(s)";
    private Map<String, String> errors;

    public ErrorModel(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}