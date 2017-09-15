package com.sss.engine.service.impl;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sss.engine.core.exception.InvalidDirectoryPathException;
import com.sss.engine.dto.FileWrapper;
import com.sss.engine.service.FileSystemService;

@Component
public class FileSystemServiceImpl implements FileSystemService {

	@Override
	public List<String> readFilesFromDirectory(String dirLocation) throws IOException, InvalidDirectoryPathException {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(dirLocation);
		if(Files.isDirectory(dirPath)) {
			DirectoryStream<Path> filePaths = Files.newDirectoryStream(dirPath);
			List<String> fileLocations = new ArrayList<>();
			for(Path fp : filePaths) {
				fileLocations.add(fp.toString());
			}
			return fileLocations;
		} else {
			throw new InvalidDirectoryPathException(dirLocation);
		}
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

	@Override
	public Boolean createDirectories(String dirLocation) throws IOException, InvalidDirectoryPathException {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(dirLocation);
		if(Files.isDirectory(dirPath)) {
			if(!Files.exists(dirPath)) {
				Path x = Files.createDirectories(dirPath);
				return x.equals(dirPath);
			} else {
				return false;
			}
		} else {
			throw new InvalidDirectoryPathException(dirLocation);
		}
	}

	@Override
	public String getLastSegmentFromPath(String path) {
		// TODO Auto-generated method stub
		String pathSeparator = FileSystems.getDefault().getSeparator();
		String dirLocation = path.toString();
		int beginIndex = dirLocation.lastIndexOf(pathSeparator) + 1;
		String lastDirName = dirLocation.substring(beginIndex);
		return lastDirName;
	}

}
