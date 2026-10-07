package project.runner.exceptions;

public class RunnerProfileNotFoundException extends RuntimeException{
    public RunnerProfileNotFoundException(String message){
        super(message);
    }
}
