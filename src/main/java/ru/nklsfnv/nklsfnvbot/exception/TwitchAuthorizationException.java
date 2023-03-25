package ru.nklsfnv.nklsfnvbot.exception;

public class TwitchAuthorizationException extends RuntimeException {

    public TwitchAuthorizationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public TwitchAuthorizationException(Throwable throwable) {
        super(throwable);
    }

}
