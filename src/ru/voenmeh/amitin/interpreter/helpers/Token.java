package ru.voenmeh.amitin.interpreter.helpers;

/* Строка таблицы символов, содержит идентификатор и значение токена */
public class Token {
    public final TokenIdentificators id;
    public final String value;

    public Token(TokenIdentificators id, String value){
        this.id = id;
        this.value = value;
    }
    
}
