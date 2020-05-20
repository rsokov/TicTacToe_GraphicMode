package ru.graphictictactoe.app;

public class GamePlayer {
    private char playerSign;
    private boolean realPlayer = true;
    private boolean isClever;
    private boolean isFirstAIStep = true;

    public GamePlayer(boolean isRealPlayer, char playerSign){
        this.realPlayer = isRealPlayer;
        this.playerSign = playerSign;
    }

    public GamePlayer(boolean isRealPlayer, char playerSign, boolean isClever){
        this.realPlayer = isRealPlayer;
        this.playerSign = playerSign;
        this.isClever = isClever;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }

    public char getPlayerSign(){
        return playerSign;
    }

    public boolean isClever(){
       return isClever;
    }

    public boolean getisFirstAIStep(){
        return isFirstAIStep;
    }

    public void setisFirstAIStep(boolean isFirstAIStep){
        this.isFirstAIStep = isFirstAIStep;
    }
}
