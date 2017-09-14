package com.sss.engine.core.exception;

import org.springframework.boot.ExitCodeGenerator;

public class EmptyProfileSetException extends Exception implements ExitCodeGenerator {

	public EmptyProfileSetException(String path) {
		// TODO Auto-generated constructor stub
		super("No profiles found in: " + path + " for parsing");
	}
	
	@Override
	public int getExitCode() {
		// TODO Auto-generated method stub
		return 6;
	}

}
