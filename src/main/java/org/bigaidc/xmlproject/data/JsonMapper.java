package org.bigaidc.xmlproject.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Vector;

public class JsonMapper {
    private ObjectMapper mapper = new ObjectMapper();

    public String map(Vector vector){
        try {
            String json = mapper.writeValueAsString(vector);
            System.out.println("JSON = " + json);

            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return "Could not map vector to json.";
        }
    }
}
