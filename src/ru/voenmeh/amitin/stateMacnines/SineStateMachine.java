package ru.voenmeh.amitin.stateMacnines;

/*ДКА, распознающий лексему "син"
Состояния автомата:
    0 - начальное 
    1- "с", можете перейти к 2
    2 - "и", может перейти к 3
    3 - "н", финальное, перехода нет
*/
public class SineStateMachine  extends StateMachine{
    public SineStateMachine(){
        super();
    }

    protected void switchState(char sym){
        if (Character.isLetter(sym)) {
            if(sym == 'с'){
                switch (state) {
                    case 0:
                        state = 1;
                        //isFinalState = false;
                        break;
                    default:
                        state = -1;
                        //isFinalState = false;
                        break;
                }
            }

            if(sym == 'и'){
                switch (state) {
                    case 1:
                        state = 2;
                        //isFinalState = false;
                        break;
                    default:
                        state = -1;
                        //isFinalState = false;
                        break;
                }
            }

            if(sym == 'н'){
                switch (state) {
                    case 2:
                        state = 3;
                        isFinalState = true;
                        break;
                    default:
                        state = -1;
                        isFinalState = true;
                        break;
                }
            }

            return;
        }
        state = -1;
    }
}
