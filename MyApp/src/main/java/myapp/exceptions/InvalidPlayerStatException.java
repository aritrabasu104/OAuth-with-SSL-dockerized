package myapp.exceptions;

public class InvalidPlayerStatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3416947244162850451L;

	public InvalidPlayerStatException(Long id) {
		super("No Information present for user : " + id);
		// TODO Auto-generated constructor stub
	}
}
