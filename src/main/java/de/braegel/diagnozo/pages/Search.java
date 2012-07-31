package de.braegel.diagnozo.pages;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import net.vz.mongodb.jackson.JacksonDBCollection;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;

import de.braegel.diagnozo.entities.Diagnosis;

/**
 * Start page of application diagnozo.
 */
public class Search
{
   @Inject
    private AlertManager alertManager;
   
    @Property
    private String _Diagnose;
    
    @Property
    @Persist
    private Diagnosis focusDiagnose;
    
    public Set<String> getDiagnosis() throws MongoException, IOException
    {
    	Set<String> diagnosis = new HashSet<String>();

    	JacksonDBCollection<Diagnosis, String> jcoll = Diagnosis.JacksonDBCollectionMongo();
       	BasicDBObject query = new BasicDBObject();
       	
//       	Find all Documents with a "diagnosis"
       	query.put("diagnosis", new BasicDBObject("$gt", ""));
       	net.vz.mongodb.jackson.DBCursor<Diagnosis> cursor = jcoll.find(query);
       	try {
            while(cursor.hasNext()) {
                diagnosis.add(cursor.next().getDiagnosis());
            }
        } finally {
            cursor.close();
        }
        return diagnosis;
    }
    
    void onActionFromshowDiagnose(String diagnose) throws MongoException, IOException
    {
    	JacksonDBCollection<Diagnosis, String> jcoll = Diagnosis.JacksonDBCollectionMongo();
    	BasicDBObject query = new BasicDBObject();
    	query.put("diagnosis",diagnose);
    	net.vz.mongodb.jackson.DBCursor<Diagnosis> cursor = jcoll.find(query);
		try {
			while(cursor.hasNext()) {
				focusDiagnose=cursor.next();
			}
		} finally {
	    	cursor.close();
		}
    }
}
