package org.sevenhills.liueri19;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Logger {
	private TimeZone timeZone = TimeZone.getTimeZone("UTC"); //use UTC time for less confusion
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //ISO 8601 date format
	private Path logPath;
	private BufferedWriter writer;
	
	public Logger() {
		//you probably have a better way to do the files stuff
		
		int logNum = 0;
		dateFormat.setTimeZone(timeZone);
		String date = dateFormat.format(new Date());
		
		//create the logs directory
		new File("logs").mkdirs();
		
		logPath = Paths.get("logs/log-" + date + "-" + logNum + ".log");
		//test for a available file name
		while (Files.exists(logPath)) {
			logNum++;
			logPath = Paths.get("logs/log-" + date + "-" + logNum + ".log");
		}
		
		//create the writer
		try {
			writer = Files.newBufferedWriter(logPath);
		} catch (IOException e) {
			System.out.println("Failed to create new log file, here's the stack trace:");
			e.printStackTrace();
		}
	}
	
	public void log(String log, Object... args) {
		System.out.printf(log, args);
		try {
			writer.write(String.format(log, args));
		} catch (IOException e) {
			System.out.println("Failed to write log to file, here's the stack trace:");
			e.printStackTrace();
		}
	}
	
	public void closeFile() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to close witer, here's the stack trace:");
			e.printStackTrace();
		}
	}
}
