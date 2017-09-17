package com.sss.engine.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sss.engine.core.exception.InvalidDirectoryPathException;
import com.sss.engine.dto.FileWrapper;

@Service
public interface FileSystemService {
	
	public List<String> readFilesFromDirectory(String dirPath) throws IOException, InvalidDirectoryPathException;
	
	public String writeFileToDirectory(String dirLocation, FileWrapper fw) throws IOException;
	
	public Boolean createDirectories(String dirPath)throws IOException, InvalidDirectoryPathException;
	
	public String getLastSegmentFromPath(String path) ;
	
	public String getFileNameFromPath(String path);

}
