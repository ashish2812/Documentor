package org.example.base.file;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Getter
public class FileReaderUtil {

	private String fileName;
	private BufferedReader bufferedReader;

	public FileReaderUtil(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		this.bufferedReader = (new BufferedReader(new FileReader(fileName)));
	}

}