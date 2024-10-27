package org.ibaqu;

import org.ibaqu.controller.GameLogic;
import org.ibaqu.controller.UserInputHandler;
import org.ibaqu.view.GameRenderer;

public class Solitaire {
    private final GameLogic gameLogic;
    private final UserInputHandler userInputHandler;
    private final GameRenderer gameRenderer;

    public Solitaire(GameLogic gameLogic, UserInputHandler userInputHandler, GameRenderer gameRenderer) {
        this.gameLogic = gameLogic;
        this.userInputHandler = userInputHandler;
        this.gameRenderer = gameRenderer;
    }

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        UserInputHandler userInputHandler = new UserInputHandler();
        GameRenderer gameRenderer = new GameRenderer();

        Solitaire solitaire = new Solitaire(gameLogic, userInputHandler, gameRenderer);
        solitaire.play();
    }

    public void play() {
        while (true) {
            gameRenderer.renderGame(gameLogic.getGameState());
            String userInput = userInputHandler.getUserInput();
            gameLogic.processUserInput(userInput);
        }
    }
}
