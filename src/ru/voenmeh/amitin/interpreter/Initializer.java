package ru.voenmeh.amitin.interpreter;

import java.io.File;
import java.nio.file.Paths;

/*Инициализатор. Отвечает за работу с файлами и вызов интерпретатора
    Вход - путь до файла
*/
public class Initializer {
    public static void main(String[] args) {
        File file;
        file = new File(Paths.get("").toAbsolutePath() + "/lib/Source.myscript"); //определяем абсолютный путь до файла

        if(file.canRead()){ //проверяем на возможность чтения
            Interpreter interpreter = new Interpreter();
            interpreter.interpret(file); //передаём файл в интерпретатор
        }
        else System.out.println("Can't read file");
    }
}
