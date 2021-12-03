package db;

public class DatabaseIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DatabaseIntegrityException(String message) {
		super(message);
	}
}
