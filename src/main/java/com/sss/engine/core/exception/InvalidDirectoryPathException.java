package com.sss.engine.core.exception;

import org.springframework.boot.ExitCodeGenerator;

public class InvalidDirectoryPathException extends Exception implements ExitCodeGenerator {
	
	public InvalidDirectoryPathException(String path) {
		// TODO Auto-generated constructor stub
		super("Non directory path: " + path);
	}

	@Override
	public int getExitCode() {
		// TODO Auto-generated method stub
		return 4;
	}

}
