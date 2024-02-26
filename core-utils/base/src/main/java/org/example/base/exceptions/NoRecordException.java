/**
 * 
 */
package org.example.base.exceptions;

import java.io.Serializable;

/**
 * 
 * @author Ashish
 *
 * @date 24-Feb-2024
 *
 */
public class NoRecordException extends RuntimeException implements RetryableException, Serializable {

	private static final long serialVersionUID = -3368655266237942363L;

	public NoRecordException(String message) {
		super(message);
	}

	public NoRecordException(Throwable cause) {
		super(cause);
	}

	public NoRecordException(String message, Throwable cause) {
		super(message, cause);
	}

}