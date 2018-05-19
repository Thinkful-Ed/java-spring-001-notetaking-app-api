package com.thinkful.noteful.folders;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DateFormat;


public class FolderSerializer extends StdSerializer<Folder> {
     
  public FolderSerializer() {
      this(null);
  }
 
  public FolderSerializer(Class<Folder> t) {
      super(t);
  }

  @Override
  public void serialize(
        Folder value, JsonGenerator jgen, SerializerProvider provider) 
        throws IOException, JsonProcessingException {

    jgen.writeStartObject();
    jgen.writeNumberField("id", value.getId());
    jgen.writeStringField("name", value.getName());
    jgen.writeNumberField("userId", value.getUser().getId());
    jgen.writeStringField("created", 
          DateFormat.getDateInstance(DateFormat.FULL).format(value.getCreatedAt()));
    jgen.writeStringField("updated", 
          DateFormat.getDateInstance(DateFormat.FULL).format(value.getUpdatedAt()));    
    jgen.writeEndObject();
  }
}

