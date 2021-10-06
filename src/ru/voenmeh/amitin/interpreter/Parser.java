package ru.voenmeh.amitin.interpreter;

import java.util.ArrayList;

import ru.voenmeh.amitin.interpreter.helpers.Token;
import ru.voenmeh.amitin.interpreter.helpers.TokenIdentificators;
import ru.voenmeh.amitin.interpreter.exceptions.SyntaxErrorException;

/* Синтаксический анализатор. Проверяет поток лексем на соответствие продукциям грамматики методом рекурсивного спуска
Также вычисляет результат
    Вход -  таблица символов
    Выход - значение выражения текущей строки
*/
public class Parser {
    private ArrayList<Token> symbolTable; //таблица символов
    private Integer currentIndex; //номер текущей строки таблицы символов (нумерация с 0, т.к. таблица символов в виде массива строк)
    private Token token; //текущий токен (строка таблицы символов)

    public Parser(ArrayList<Token> symbolTable){ //при создании экземпляра класса
        this.symbolTable = symbolTable; //присваиваем ссылку на таблицу символов
        currentIndex = 0; //устанавливаем указать на первую строку таблицы для начала разбора
        token = symbolTable.get(currentIndex); //получаем первую строку таблицы символов
    }

    /* Основной метод синтаксического анализатора. Начинает разбор потока токенов, вычисляет и возвращает результат выражения */
    public Double parse() throws SyntaxErrorException{
        Double result; //результат вычисления
        result = expr(); //переходим к стартовой продукции
        if(currentIndex < symbolTable.size()) //если были разобраны не все токены таблицы
        throw new SyntaxErrorException("Syntax error"); //значит в строке есть продукции, не принадлежащие грамматике
        return result; //возвращаем общий результат вычисления выражения строки
    }

    /* expr -> term+digit | term-digit | term */
    private Double expr() throws SyntaxErrorException{
        Double result, right;
        String operation;

        result = term(); //спускаемся на продукцию ниже
        while (currentIndex < symbolTable.size()) { //пока не кончились токены
            operation = token.value; //сохраняем значение токена
            if (match(TokenIdentificators.OP1)) { // если это сложение или вычитание
                right = term(); //вычисляем правый операнд

                switch (operation) { //выполняем арифметическую операцию
                    case "+":
                        result += right;
                        break;
                    case "-":
                        result -= right;
                    default:
                        break;
                }
            }else break;
        }
        return result;
    }

    /* term-> func*digit | func/digit | func */
    private Double term() throws SyntaxErrorException{
        Double result, right;
        String operation;

        result = func(); //спускаемся на продукцию ниже
        while (currentIndex < symbolTable.size()) {// пока не кончились токены
            operation = token.value; //сохраняем значение токена
            if(match(TokenIdentificators.OP2)){ //если это операция умножения или деления 
                right = func(); //вычисляем правый операнд
    
                switch (operation) { //выполняем артифметическую операцию
                    case "*":
                        result *= right;
                        break;
                    case "/":
                        result /= right;
                        break;
                    default:
                        break;
                }
            }
            else break;
        }

        return result;
    }

    /* func -> func(digit) | factor  */
    private Double func() throws SyntaxErrorException{
        Double result;
        String operation = token.value;
        if(match(TokenIdentificators.TRIGONOMETRIC)){ //если идентификатор токена указывает на тригнометрическую функцию
            if(match(TokenIdentificators.LBRACKET)){ //проверяем на следующий символ в продукции - открывающую скобку
                result = factor(); //вычисляем значение выражения в скобках

                if (match(TokenIdentificators.RBRACKET)){ //после выражения ожидает закрывающая скобка
                    switch (operation) { //вычисляем результат тригонометрической функции
                        case "син":
                            result = Math.sin(result);
                            break;
                        default: //только две функции реализовано, если это не синус, то вычисляем косинус
                            result = Math.cos(result);
                            break;
                    }
                } else throw new SyntaxErrorException("Syntax error: \")\"  expected"); //если скобки нет - возвращаем синтаксическую ошибку
            }else throw new SyntaxErrorException("Syntax error:  \"(\" expected"); //если открывающей скобки нет - возвращаем синтаксическую ошибку
        }else result = factor(); //если это не функция, то переходим на продукцию ниже 
        return result;
    }

    /* factor ->  digit | (expr) */
    private Double factor() throws SyntaxErrorException{
        Double tokenValue;
        Double result;
        if (match(TokenIdentificators.LBRACKET)) { //если текущий токен - открывающая скобка
            result = expr(); //то переходим к вычислению подвыражения
            if (!match(TokenIdentificators.RBRACKET)) //выражение должно заканчиваться закрывающей скобкой
                throw new SyntaxErrorException("Syntax Error: \")\"  expected"); //если не так, то выводим синтаксическую ошибку
            return result;
        }

        if (token.id == TokenIdentificators.NUM){ //если это число
            tokenValue = Double.parseDouble(token.value); //преобразовываем в double
            getNextToken(); //получаем следующий токен
            return tokenValue; //возвращаем результат
        }
        else throw new SyntaxErrorException("Syntax error"); //спускаться ниже некуда, возвращаем синтаксическую ошибку
    }

    /* перехода на следующую строку таблицы символов и взятие следующего токена */
    private boolean getNextToken(){
        if (++currentIndex+1 > symbolTable.size()) return false; //если это последний токен, то вернуть false
        token = symbolTable.get(currentIndex); //взять токен по следующему индексу
        return true;
    }

    /* Проверка на соответствие текущего токена ожидаемому и взятие следующего токена */
    private boolean match(TokenIdentificators tid){
        if(tid == token.id){ //если текущий токен соответствует ожидаемому
            getNextToken(); //то взять следующий токен
            return true; //вернуть признак успешного сравнения
        }
        return false;
    } 
}