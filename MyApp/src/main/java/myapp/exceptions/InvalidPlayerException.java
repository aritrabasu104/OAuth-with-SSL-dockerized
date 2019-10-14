package myapp.exceptions;

public class InvalidPlayerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1476809157312087125L;

	public InvalidPlayerException(Long id) {
		super("Invalid user Id provided : " + id);
		// TODO Auto-generated constructor stub
	}
}
