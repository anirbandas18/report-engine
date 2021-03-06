package com.sss.engine.service.impl;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sss.engine.core.exception.InvalidDirectoryPathException;
import com.sss.engine.dto.FileWrapper;
import com.sss.engine.service.FileSystemService;

@Component
public class FileSystemServiceImpl implements FileSystemService {

	@Value("#{'${filter.files.with.extensions}'.split(',')}")
	private List<String> fileExtensions;

	@Override
	public List<String> readFilesFromDirectory(String dirLocation) throws IOException, InvalidDirectoryPathException {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(dirLocation);
		if (Files.isDirectory(dirPath)) {
			DirectoryStream<Path> filePaths = Files.newDirectoryStream(dirPath);
			List<String> fileLocations = new ArrayList<>();
			for (Path fp : filePaths) {
				// filter only .profile files
				if (!Files.isDirectory(fp)) {
					if (fileExtensions.contains(getFileExtensionFromPath(fp.toString()))) {
						fileLocations.add(fp.toString());
					}
				}
			}
			Collections.sort(fileLocations);
			return fileLocations;
		} else {
			throw new InvalidDirectoryPathException(dirLocation);
		}
	}

	@Override
	public String writeFileToDirectory(String dirLocation, FileWrapper fw) throws IOException {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(dirLocation);
		if (!Files.exists(dirPath)) {
			dirPath = Files.createDirectories(dirPath);
		}
		Path filePath = Paths.get(dirPath.toString(), fw.getName());
		filePath = Files.write(filePath, fw.getContent());
		return filePath.toString();
	}

	@Override
	public Boolean createDirectories(String dirLocation) throws IOException, InvalidDirectoryPathException {
		// TODO Auto-generated method stub
		Path dirPath = Paths.get(dirLocation);
		if (Files.isDirectory(dirPath)) {
			if (!Files.exists(dirPath)) {
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

	@Override
	public String getFileNameFromPath(String path) {
		// TODO Auto-generated method stub
		String fileName = getLastSegmentFromPath(path);
		String name = fileName.substring(0, fileName.lastIndexOf('.'));
		return name;
	}

	@Override
	public String getFileExtensionFromPath(String path) {
		// TODO Auto-generated method stub
		String fileName = getLastSegmentFromPath(path);
		String fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
		return fileExt;
	}

}
