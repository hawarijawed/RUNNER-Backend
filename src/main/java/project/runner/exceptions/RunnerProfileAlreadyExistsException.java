package project.runner.exceptions;

public class RunnerProfileAlreadyExistsException extends RuntimeException{
    public RunnerProfileAlreadyExistsException(String message){
        super(message);
    }
}
