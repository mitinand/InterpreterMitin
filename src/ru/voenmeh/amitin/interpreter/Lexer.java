package ru.voenmeh.amitin.interpreter;

import java.util.ArrayList;

import ru.voenmeh.amitin.interpreter.exceptions.*;
import ru.voenmeh.amitin.interpreter.helpers.*;
import ru.voenmeh.amitin.stateMacnines.StateMachineManager;

/*Лексический анализатор
    Вход - строка символов
    Выход - таблица символов, содержащую идентификаторы токенов и их значения
*/
public class Lexer {
    private StringBuilder lexeme = new StringBuilder(); //текущая лексема
    private StringBuilder buffer; //поток нераспознанных символов
    private StateMachineManager sManager = new StateMachineManager(); //менеджер КА
    private ArrayList<Token> symbolTable = new ArrayList<>(); //таблица символов - результат работы лексера

    /*Основной метод лексера. Выделяет токены и записывает их в таблицу символов*/
    public ArrayList<Token> getTokens(String valueString) throws UnrecognizedTokenException{
        buffer = new StringBuilder(valueString); //считываем строку в буфер
        buffer.append("$"); //добавляем символ не в алфавите языка для обработки конечных лексем в буфере

        while (buffer.length() > 1) { //пока в буфере есть символы для распознавания
            prepareForNewLexeme(); //подготавливаем КА
            getLexeme(); //получаем следующую лексему
        }
        return symbolTable; //возвращаем таблицу символов
    }
    
    /*Получение следующей лексемы*/
    private void getLexeme() throws UnrecognizedTokenException{
        char symbol;
        TokenIdentificators currentID;

        while (true) { //цикл по символам, пока не распознана лексема
            symbol = buffer.charAt(0); //считываем первый символ из буфера

            currentID = sManager.switchStates(symbol); //передаём символ для обработки в менеджер КА
            if(currentID == null){ //если менеджер не вернул идентификатор, то лексема пока не распознана
                lexeme.append(symbol); //добавляем символ к текущей распознаваемой лексеме
                buffer.delete(0, 1); //удаляем символ из буфера
                continue; //продолжаем распознвание, передаём следующий символ в менеджер КА
            }
            
            switch (currentID) { //если менеджер вернул идентификатор
                case UNIDENTIFIED: //лексема не распознана ни одни КА, возвращаем лексическую ошибку
                    throw new UnrecognizedTokenException("Unrecognized token \"" + lexeme.append(symbol).toString() + "\"");
                default: //лексема распознана
                    symbolTable.add(new Token(currentID, lexeme.toString())); //добавляем её идентификатор и значение в таблицу символов
                    break;
            }
            break;
        }
    }

    /*метод, подготавливающий лексический анализатор для распознавания следующей лексемы*/
    private void prepareForNewLexeme(){
        lexeme.setLength(0); //очищаем текущую лексему
        sManager.reset(); //сбрасываем все КА до начального состояния
    }
}