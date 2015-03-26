package net.sxnic.comm.exception;


@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super();
    }


    public UnauthorizedException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public UnauthorizedException(String arg0) {
        super(arg0);
    }


    public UnauthorizedException(Throwable arg0) {
        super(arg0);
    }
}