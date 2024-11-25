package org.task1;

import java.util.Objects;

public interface Guess {
    GuessResult guess(Integer number);

    class Base implements Guess {
        private Integer answer = 0;

        public Base(Integer answer) {
            this.answer = answer;
        }

        @Override
        public GuessResult guess(Integer number) {
            if (Objects.equals(number, answer)) {
                return new GuessResult.RightAnswered();
            } else if (number < answer)
                return new GuessResult.LowerResult();
            else{
                return new GuessResult.LargerResult();
            }
        }
    }
}
