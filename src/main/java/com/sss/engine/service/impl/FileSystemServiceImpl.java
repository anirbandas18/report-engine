package com.sss.engine.service.impl;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.sss.engine.dto.FileWrapper;
import com.sss.engine.service.FileSystemService;

public class FileSystemServiceImpl implements FileSystemService {

	@Override
	public List<String> readFilesFromDirectory(String dirLocation) throws IOException {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(dirLocation);
		DirectoryStream<Path> filePaths = Files.newDirectoryStream(dirPath);
		List<String> fileLocations = new ArrayList<>();
		for(Path fp : filePaths) {
			fileLocations.add(fp.toString());
		}
		return fileLocations;
	}

	@Override
	public Boolean writeFilesToDirectory(String dirLocation, List<FileWrapper> files) throws IOException {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(dirLocation);
		if(!Files.exists(dirPath)) {
			dirPath = Files.createDirectories(dirPath);
		}
		for(FileWrapper f : files) {
			Path filePath = Paths.get(dirPath.toString(), f.getName());
			filePath = Files.write(filePath, f.getContent());
		}
		return null;
	}

}
