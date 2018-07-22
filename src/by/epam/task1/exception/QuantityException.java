package by.epam.task1.exception;

public class QuantityException extends Exception{
	
	private static final long serialVersionUID = -5613234058125357194L;

	public QuantityException (String message) {
		super(message);
	}

	public QuantityException (String message, Throwable e) {
		super(message, e);
	}

	public QuantityException(Throwable e) {
		super(e);
	}
	
	
}
