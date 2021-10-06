package ru.voenmeh.amitin.interpreter.helpers;

/* Перечисление, содержащее идентификаторы токенов */
public enum TokenIdentificators {
    NUM, //число
    OP1, //операторы сложения и вычитания
    OP2, //операторы умножения и деления
    LBRACKET, //левая (открывающия скобка)
    RBRACKET, //правая (закрывающая) скобка
    TRIGONOMETRIC, //тригонометрическая функция
    UNIDENTIFIED; //нераспознанный токен для выброса исключения
}
