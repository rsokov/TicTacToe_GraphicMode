package ru.graphictictactoe.app;

import javax.swing.*;

public class Game {
    private GameBoard board;
    private GamePlayer[] gamesPlayers = new GamePlayer[2];
    private int playersTurn = 0;



    public Game() {
        this.board = new GameBoard(this);
    }
    public void initGame(){
        gamesPlayers[0] = new GamePlayer(true, 'X');
        gamesPlayers[1] = new GamePlayer(false, 'O', true);
    }

    void passTurn(){
        if(playersTurn==0)
        {
            playersTurn = 1;
        }
        else
        {
            playersTurn = 0;
        }
    }

    GamePlayer getCurrentPlayer(){
        return gamesPlayers[playersTurn];
    }

    void showMessage(String messageText)
    {
        JOptionPane.showMessageDialog(board, messageText);
    }

}
