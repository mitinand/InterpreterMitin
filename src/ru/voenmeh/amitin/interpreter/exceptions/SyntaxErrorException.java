package ru.voenmeh.amitin.interpreter.exceptions;

/* Ошибки в работе синтаксического анализатора */
public class SyntaxErrorException extends Exception{
    public SyntaxErrorException(String message){
        super(message);
    }
}
