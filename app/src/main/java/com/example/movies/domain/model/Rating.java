package com.example.movies.domain.model;
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Rating {
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String source;
    public String value;
}
