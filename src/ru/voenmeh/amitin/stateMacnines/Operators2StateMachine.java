package ru.voenmeh.amitin.stateMacnines;

/*ДКА, распознающий операторы умножения и деления
Состояния автомата:
    0 - начальное 
    1- "*" или "/", перехода нет, финальное
*/
public class Operators2StateMachine extends StateMachine{

    public Operators2StateMachine(){
        super();
    }

    protected void switchState(char sym) {
        if (isOperator(sym)) {
            switch (state) {
                case 0:
                    state = 1;
                    isFinalState = true;
                    break;
                case 1:
                    state = -1;
                    isFinalState = true;
                    break;
                default:
                state = -1;
                    break;
            }
        } else {
            state = -1;
        }
    }

    private boolean isOperator(char sym) {
        switch (sym) {
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }
}
