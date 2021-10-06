package ru.voenmeh.amitin.stateMacnines;

/*КА, распознающий числа. Распознаёт числа с дробной частью, не поддерживает экспоненциальную форму
Состояния автомата:
    0 - начальное 
    1- цифра в целой части, может перейти в 1 и 2, финальное
    2 - "." (разделитель между целой и дробной частью), может перейти в 3
    3 - цифра в дробной части, может перейти к 3, финальное
*/
public class DigitsStateMachine extends StateMachine{

    public DigitsStateMachine(){
        super();
    }

    protected void switchState(char sym){
        if (Character.isDigit(sym)) {

            switch (state) {
                case 0:
                    state = 1;
                    isFinalState = true;
                case 1:
                    state = 1;
                    isFinalState = true;
                    break;
                case 2:
                    state = 3;
                    isFinalState = true;
                    break;
                case 3:
                    state = 3;
                    isFinalState = true;
                    break;
            
                default:
                    state = null;
            }
        }else{
            if (sym == '.') {
                switch (state) {
                    case 0:
                        state = -1;
                        isFinalState = true;
                        break;
                    case 1:
                        state = 2;
                        isFinalState = false;
                        break;
                    case 2:
                        state = -1;
                        isFinalState = false;
                        break;
                    case 3:
                        state = -1;
                        break;
                
                    default:
                         state = null;
                }
            }else state = -1;
        }
    }
}
