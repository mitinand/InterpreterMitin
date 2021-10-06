package ru.voenmeh.amitin.interpreter.exceptions;

/* Ошибки в работе лексического анализатора */
public class UnrecognizedTokenException extends Exception{
    public UnrecognizedTokenException(String message){
        super(message);
    }
    
}
