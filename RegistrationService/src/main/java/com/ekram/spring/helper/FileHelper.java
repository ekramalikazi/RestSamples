package com.ekram.spring.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ekram.spring.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Helper class to talk to files
 * 
 * @author kazi_e
 *
 */
public final class FileHelper {

	private static final Logger LOG = LogManager.getLogger();
	
	private static final String READING_ERROR = "ERROR reading from json file - ";
	private static final String WRITING_ERROR = "ERROR writing to json file - ";
	private static final String FILE_PERSISTENCE_PATH = "c:\\tmp\\userDataMap.json"; /* this should be re-factored to property */
	
	/* No need to construct object of FileHelper */
	private FileHelper() {}
	
	/**
	 * utility to write to json file.
	 * 
	 * @param userDataMap
	 */
	public static void writeToJson(Map<String, User> userDataMap) {
		Gson gson = new Gson();
		String json = gson.toJson(userDataMap);

		try {
			FileWriter writer = new FileWriter(FILE_PERSISTENCE_PATH);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			LOG.error(WRITING_ERROR + FILE_PERSISTENCE_PATH);
		}
	}
	
	/**
	 * utility to read from json file.
	 * @return
	 */
	public static Map<String, User> readFromJson() {
		
		Map<String, User> userDataMap = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILE_PERSISTENCE_PATH));

			Gson gson = new Gson();
			Type collectionType = new TypeToken<Map<String, User>>(){}.getType();
			userDataMap = gson.fromJson(br, collectionType);

		} catch (IOException e) {
			LOG.error(READING_ERROR + FILE_PERSISTENCE_PATH);
		}
		return userDataMap;
	}
}
