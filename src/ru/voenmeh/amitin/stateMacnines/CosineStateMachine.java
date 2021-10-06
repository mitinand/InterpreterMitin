package ru.voenmeh.amitin.stateMacnines;

/*ДКА, распознающий лексему "кос"
Состояния автомата:
    0 - начальное 
    1- "к", можете перейти к 2
    2 - "о", может перейти к 3
    3 - "с", финальное, перехода нет
*/
public class CosineStateMachine extends StateMachine{
    public CosineStateMachine(){
        super();
    }

    protected void switchState(char sym){
        if (Character.isLetter(sym)) {
            if(sym == 'к'){
                switch (state) {
                    case 0:
                        state = 1;
                        isFinalState = false;
                        break;
                    default:
                        state = -1;
                        isFinalState = false;
                        break;
                }
            }

            if(sym == 'о'){
                switch (state) {
                    case 1:
                        state = 2;
                        break;
                    default:
                        state = -1;
                        break;
                }
            }

            if(sym == 'с'){
                switch (state) {
                    case 2:
                        state = 3;
                        isFinalState = true;
                        break;
                    default:
                        state = -1;
                        break;
                }
            }

            return;
        }
        state = -1;
    }
}
