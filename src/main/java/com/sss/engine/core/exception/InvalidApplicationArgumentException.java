package com.sss.engine.core.exception;

import org.springframework.boot.ExitCodeGenerator;

public class InvalidApplicationArgumentException extends RuntimeException implements ExitCodeGenerator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2763037707043619153L;

	public InvalidApplicationArgumentException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	@Override
	public int getExitCode() {
		// TODO Auto-generated method stub
		return 1;
	}

}
