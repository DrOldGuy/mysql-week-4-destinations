package destination.exception;

/**
 * This exception is used to turn checked exceptions into unchecked exceptions.
 * It is thrown if a SQLException is caught.
 * 
 * @author Promineo
 *
 */
@SuppressWarnings("serial")
public class DestinationException extends RuntimeException {

  public DestinationException(String message) {
    super(message);
  }

  public DestinationException(Throwable cause) {
    super(cause);
  }

}
