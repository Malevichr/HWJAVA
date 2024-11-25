package org.task1;

public interface GuessResult {
    String guessMessage();
    Boolean isRightAnswered();
    abstract class Abstract implements GuessResult{
        private String message = "";
        private Boolean isRightAnswered = false;
        public Abstract(String message, Boolean isRight){
            this.message = message;
            this.isRightAnswered = isRight;
        }
        @Override
        public String guessMessage() {
            return message;
        }

        @Override
        public Boolean isRightAnswered() {
            return isRightAnswered;
        }

    }
    class RightAnswered extends Abstract{
        public RightAnswered(){
            super("Right answered, you won", true);
        }
    }
    class LowerResult extends Abstract{
        public LowerResult(){
            super("Wrong result, try larger number", false);
        }
    }
    class LargerResult extends Abstract{
        public LargerResult(){
            super("Wrong result, try lower number", false);
        }
    }
}
