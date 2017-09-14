package com.sss.engine.service;

import java.io.IOException;
import java.util.List;

import com.sss.engine.dto.FileWrapper;

public interface FileSystemService {
	
	public List<String> readFilesFromDirectory(String dirPath) throws IOException;
	
	public Boolean writeFilesToDirectory(String dirLocation, List<FileWrapper> files) throws IOException;

}
