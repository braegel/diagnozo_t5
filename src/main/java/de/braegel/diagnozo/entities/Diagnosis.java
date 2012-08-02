package de.braegel.diagnozo.entities;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import net.vz.mongodb.jackson.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.codehaus.jackson.map.*;

public class Diagnosis {
	private String diagnosis;
	private String _id; // TODO _id is not just a String
	private Map<String, Set<String>> tags;
	
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Map<String, Set<String>> getTags() {
		return tags;
	}

	public void setTags(Map<String, Set<String>> tags) {
		this.tags = tags;
	}

	private static Mongo m;
	private static DB db;

	public static JacksonDBCollection<Diagnosis, String> JacksonDBCollectionMongo() throws MongoException, IOException{
		FileInputStream inputStream = new FileInputStream("diagnosis.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		inputStream.close();
		
		String MongoDBHost = properties.getProperty("MongoDBHost");
		int MongoDBPort = Integer.parseInt(properties.getProperty("MongoDBPort"));
		String MongoDBDatabase = properties.getProperty("MongoDBDatabase");
		String MongoDBCollection = properties.getProperty("MongoDBCollection");
		
		m = new Mongo( MongoDBHost , MongoDBPort );
    	db= m.getDB( MongoDBDatabase );
       	DBCollection coll = db.getCollection(MongoDBCollection);
       	
       	ObjectMapper mapper = new ObjectMapper();
       	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	return JacksonDBCollection.wrap(coll, Diagnosis.class, String.class, mapper);
	}
	
}