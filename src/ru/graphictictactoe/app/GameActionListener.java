package ru.graphictictactoe.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton button;

    public GameActionListener(int row, int cell, GameButton gButton){
        this.row = row;
        this.cell = cell;
        this.button = gButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        GameBoard board = button.getBoard();

        if(board.isTurnable(row,cell)){
            updateByPlayersData(board);

            if(board.isFull()){
                board.getGame().showMessage("Ничья!");
                board.emptyField();
            } else
            {
                if (!board.getGame().getCurrentPlayer().isRealPlayer())
                updateByAiData(board);
            }
        } else{
            board.getGame().showMessage("Некорректный ход!");
        }
    }
    private void updateByPlayersData(GameBoard board){
        board.updateGameField(row,cell);

        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin())
        {
            button.getBoard().getGame().showMessage("Поздравляем! Вы выиграли!");
            board.emptyField();
        } else
        {
            board.getGame().passTurn();
        }

    }

    private void updateByAiData(GameBoard board)
    {
        if (board.getGame().getCurrentPlayer().isClever())
        {
            cleverModeAction(board);
        }
        else
        {
            simpleModeAction(board);
        }


    }

    private void simpleModeAction(GameBoard board){
        int x, y;
        Random rnd = new Random();

        do {
            x = rnd.nextInt(GameBoard.dimension);
            y = rnd.nextInt(GameBoard.dimension);
        } while (!board.isTurnable(x,y));

        board.updateGameField(x,y);

        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
        checkAIWin(board);
    }

    private void cleverModeAction(GameBoard board)
    {
        int mapCounter = 0;
        int leftSlotCounter, rightSlotCounter, topSlotCounter, bottomSlotCounter, maxCounterValue, cellIndex=0;
        Map<Integer, Integer> FREE_SLOTS = new HashMap<Integer, Integer>();
        Map<Integer, Integer> COUNTER_OF_FREE_SLOTS = new HashMap<Integer, Integer>();

        if (board.getGame().getCurrentPlayer().getisFirstAIStep())
        {
            simpleModeAction(board);
            board.getGame().getCurrentPlayer().setisFirstAIStep(false);
        } else {
            for (int i = 0; i < GameBoard.dimension; i++) {
                for (int j = 0; j < GameBoard.dimension; j++) {
                    if (board.isTurnable(i, j)) {
                        mapCounter++;
                        FREE_SLOTS.put(mapCounter, GameBoard.dimension * i + j);
                    } else {
                    }
                }
            }

            for (Map.Entry<Integer, Integer> item : FREE_SLOTS.entrySet()) {
                int x = item.getValue() / GameBoard.dimension;
                int y = item.getValue() % GameBoard.dimension;
                //LeftSlotsCounter
                leftSlotCounter = 0;
                for (int i = y; i <= y; i++) {
                    for (int j = x - 1; j >= 0; j--) {
                        if (board.getGameField(i, j) == board.getGame().getCurrentPlayer().getPlayerSign()) {
                            leftSlotCounter++;
                        }
                    }
                }
                COUNTER_OF_FREE_SLOTS.put(item.getValue(), leftSlotCounter);
                //Right Slots Counter
                rightSlotCounter = 0;
                for (int i = y; i <= y; i++) {
                    for (int j = x; j < GameBoard.dimension; j++) {
                        if (board.getGameField(i, j) == board.getGame().getCurrentPlayer().getPlayerSign()) {
                            rightSlotCounter++;
                        }
                    }
                }
                COUNTER_OF_FREE_SLOTS.replace(item.getValue(), COUNTER_OF_FREE_SLOTS.get(item.getValue()) + rightSlotCounter);

                //Top Slots Counter
                topSlotCounter = 0;
                for (int j = x; j <= x; j++) {
                    for (int i = y - 1; i >= 0; i--) {
                        if (board.getGameField(i, j) == board.getGame().getCurrentPlayer().getPlayerSign()) {
                            topSlotCounter++;
                        }
                    }
                }
                COUNTER_OF_FREE_SLOTS.put(item.getValue(), COUNTER_OF_FREE_SLOTS.get(item.getValue()) + topSlotCounter);

                //Bottom Slots Counter
                bottomSlotCounter = 0;
                for (int j = x; j <= x; j++) {
                    for (int i = y; i < GameBoard.dimension; i++) {
                        if (board.getGameField(i, j) == board.getGame().getCurrentPlayer().getPlayerSign()) {
                            bottomSlotCounter++;
                        }
                    }
                }
                COUNTER_OF_FREE_SLOTS.put(item.getValue(), COUNTER_OF_FREE_SLOTS.get(item.getValue()) + bottomSlotCounter);
                System.out.println(item.getKey() + " " + item.getValue());
            }

            maxCounterValue = (Collections.max(COUNTER_OF_FREE_SLOTS.values()));
            for (Map.Entry<Integer, Integer> entry : COUNTER_OF_FREE_SLOTS.entrySet()) {
                if (entry.getValue() == maxCounterValue) {
                    cellIndex = entry.getKey();
                }
            }

            FREE_SLOTS.clear();
            COUNTER_OF_FREE_SLOTS.clear();

            board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
            checkAIWin(board);

        }


}
    private void checkAIWin(GameBoard board){
        if (board.checkWin())
        {
            button.getBoard().getGame().showMessage("Компьютер выиграл!");
            board.emptyField();

        } else
        {
            board.getGame().passTurn();

        }
    }
}
