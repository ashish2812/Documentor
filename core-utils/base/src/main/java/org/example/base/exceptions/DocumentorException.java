/**
 * 
 */
package org.example.base.exceptions;

/**
 * @author Ashish
 *
 * @date 04-Feb-2024
 *
 **/
public class DocumentorException extends RuntimeException {

	private static final long serialVersionUID = -3368655266237942363L;

	public DocumentorException(String message) {
		super(message);
	}

	public DocumentorException(Throwable cause) {
		super(cause);
	}

	public DocumentorException(String message, Throwable cause) {
		super(message, cause);
	}

}