package ru.voenmeh.amitin.stateMacnines;

/*ДКА, распознающий терминал открывающей скобки "(" 
Состояния автомата:
    0 - начальное 
    1- "(", перехода нет, финальное
*/
public class LeftBracketStateMachine extends StateMachine{
    public LeftBracketStateMachine(){
        super();
    }

    protected void switchState(char sym){
        if(sym == '('){
            switch (state) {
                case 0:
                    state = 1;
                    isFinalState = true;
                    break;
            
                default:
                    state = -1;
            }
            return;
        }
        state = -1;
    }
}
