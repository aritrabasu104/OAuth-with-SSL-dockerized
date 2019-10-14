package myapp.exceptions;

public class InvalidMatchNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2693995568063437678L;

	public InvalidMatchNameException(String matchname) {
		super("Invalid match name provided : " + matchname);
		// TODO Auto-generated constructor stub
	}
}
