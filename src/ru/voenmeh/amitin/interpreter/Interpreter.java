package ru.voenmeh.amitin.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import ru.voenmeh.amitin.interpreter.helpers.Token;

/*Интерпретатор. Инициализирует фазы анализа и разбора, и обрабатывает результат
    Вход - файл со строками
    Выход - вывод на экран результата вычисления
*/
public class Interpreter {
    private String currentLine = "";
    private Integer lineNumber = 0;
    private Lexer lexer;
    private Parser parser;
    private ArrayList<Token> symbolTable;

    public void interpret(File file) {
        try (FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) { //открываем файл на чтение
                    
                    while ((currentLine = bufferedReader.readLine())!=null) { //построчное считывание
                        lineNumber++; //счётчик строк для диагностики ошибок
                        if (currentLine.isBlank()) continue; //пропускаем пустые строки
                        try {
                            lexer = new Lexer(); //инициализируем лексический анализатор
                            symbolTable = lexer.getTokens(currentLine); //получаем таблицу символов

                            parser = new Parser(symbolTable); //инициализируем синтаксический анализатор
                            Double result = parser.parse(); //получаем результат разбора и вычисления от синтаксического анализатора
                            System.out.println(currentLine + " = " + result); //выводим результат
                        } catch (Exception e) { //пробрасываем исключения из лексера и парсера для вывода ошибки с номером строки
                            System.out.println("Error at line " + lineNumber + ": " + e.getMessage());
                        }
                    }
        } catch (Exception e) { //если файл не открылся
            System.out.println(e.getMessage());
        }
    }
}
