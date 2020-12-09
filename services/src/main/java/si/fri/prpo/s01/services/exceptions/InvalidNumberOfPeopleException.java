package si.fri.prpo.s01.services.exceptions;

public class InvalidNumberOfPeopleException extends RuntimeException{
    public InvalidNumberOfPeopleException(String msg) {
        super(msg);
    }
}