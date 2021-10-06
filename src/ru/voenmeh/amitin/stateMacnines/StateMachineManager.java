package ru.voenmeh.amitin.stateMacnines;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import ru.voenmeh.amitin.interpreter.helpers.TokenIdentificators;

/*Менеджер конечных автоматов. Управляет переключением КА и считывает результат с каждого для определения лексемы
    Вход - символ
    Выход - идентификатор токена, соответствующий успешно отработавшему КА
*/
public class StateMachineManager {
    /* Определение всех конечных автоматов */
    private DigitsStateMachine digits = new DigitsStateMachine(); //числа
    private Operators1StateMachine operators1 = new Operators1StateMachine(); //арифметические операции сложения и вычитания
    private Operators2StateMachine operators2 = new Operators2StateMachine(); //арифметические операторы умножения и деления
    private LeftBracketStateMachine lBracket = new LeftBracketStateMachine(); //левая (открывающая) скобка
    private RightBracketStateMachine rBracket = new RightBracketStateMachine(); //правая (закрывающая) скобка
    private SineStateMachine sine = new SineStateMachine(); //тригонометрическая функция синус
    private CosineStateMachine cosine = new CosineStateMachine(); //тригонометрическая функция косинус
    private Map<StateMachine, Integer> stateMachines = new HashMap<>(); //таблица для отслеживания текущего состояния всех КА

    public StateMachineManager(){

        /*Заполнение таблицы КА*/
        stateMachines.put(digits, 0);
        stateMachines.put(operators1, 0);
        stateMachines.put(operators2, 0);
        stateMachines.put(lBracket, 0);
        stateMachines.put(rBracket, 0);
        stateMachines.put(sine, 0);
        stateMachines.put(cosine, 0);
    }

    /* Основной метод менеджера КА, меняет состояние КА в зависимости от символа, и считывает результат */
    public TokenIdentificators switchStates(char c){
        Map<StateMachine, Integer> filteredMap; //вспомогательная структура, для отслеживания состояния автоматов

        stateMachines.forEach((stateMachine, state) ->{ //для каждого КА
            if (state!=1 && state!=-1)  //который ещё не завершил работу
            stateMachines.put(stateMachine, stateMachine.getResult(c)); //получить результат обработки символа
        });

        /* Все автоматы сломались, лексема не распознана */
        filteredMap = stateMachines.entrySet().stream().
        filter(a -> a.getValue() != -1).
        collect(Collectors.toMap(e->e.getKey(), e -> e.getValue()));

        if (filteredMap.isEmpty()) {
            return TokenIdentificators.UNIDENTIFIED; //возвращаем для проброса ошибки
        }

        /* Ожидаемый результат - один автомат завершился в финальном состоянии, лексема распознана */
        filteredMap = stateMachines.entrySet().stream(). //фильтр по успешно отработавшим КА
        filter(a -> a.getValue() == 1).
        collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        if (filteredMap.size() == 1) { //если есть только один успешно отработавший КА
            // то возвращаем идентификатор токена в зависимости от того какой КА успешно распознал лексему
            if(filteredMap.containsKey(digits)) return TokenIdentificators.NUM;
            if(filteredMap.containsKey(operators1)) return TokenIdentificators.OP1;
            if(filteredMap.containsKey(operators2)) return TokenIdentificators.OP2;
            if(filteredMap.containsKey(lBracket)) return TokenIdentificators.LBRACKET;
            if(filteredMap.containsKey(rBracket)) return TokenIdentificators.RBRACKET;
            if(filteredMap.containsKey(sine)) return TokenIdentificators.TRIGONOMETRIC;
            if(filteredMap.containsKey(cosine)) return TokenIdentificators.TRIGONOMETRIC;
        }
        return null; //если нет успешно завершённых КА (нужно больше символов для распознания лексемы), то передаём лексеру сигнал для приёма ещё одного символа
    }

    /* сброс всех КА до начального состояния*/
    public void reset() {
        stateMachines.forEach((stateMachine, state) ->{
            stateMachine.reset(); //вызываем метод сброса для конкретного КА
            stateMachines.put(stateMachine, 0); //обнуляем текущее состояние КА в таблице
        });
    }
}
