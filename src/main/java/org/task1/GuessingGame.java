package org.task1;

import org.main.Input;

import java.util.Random;

public interface GuessingGame {
    void startGame();

    class Base implements GuessingGame{
        private Integer attempts = 0;
        private final Input input = new Input();
        private final Guess guess;
        public Base(Integer attempts){
            this.attempts = attempts;
            Integer answer = new Random().nextInt(20);
            guess = new Guess.Base(answer);
        }
        @Override
        public void startGame() {
            for(int i = 0; i < attempts; i++){
                Integer userAnswer = input.input();
                GuessResult result = guess.guess(userAnswer);
                System.out.println(result.guessMessage());
                if(result.isRightAnswered()){
                    break;
                }
            }
        }
    }
}
