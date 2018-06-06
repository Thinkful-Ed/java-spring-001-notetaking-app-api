package com.thinkful.noteful.notes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.thinkful.noteful.tags.Tag;

import java.io.IOException;
import java.text.DateFormat;


public class NoteSerializer extends StdSerializer<Note> {
     
  public NoteSerializer() {
      this(null);
  }
 
  public NoteSerializer(Class<Note> t) {
      super(t);
  }

  @Override
  public void serialize(
        Note value, JsonGenerator jgen, SerializerProvider provider) 
        throws IOException, JsonProcessingException {

    jgen.writeStartObject();
    jgen.writeNumberField("id", value.getId());
    jgen.writeStringField("title", value.getTitle());
    jgen.writeStringField("content", value.getContent());
    jgen.writeNumberField("userId", value.getUser().getId());
    jgen.writeNumberField("folderId", value.getFolder() != null ? value.getFolder().getId() : null);
    jgen.writeStringField("created", 
          DateFormat.getDateInstance(DateFormat.FULL).format(value.getCreatedAt()));
    jgen.writeStringField("updated", 
          DateFormat.getDateInstance(DateFormat.FULL).format(value.getUpdatedAt()));    
    jgen.writeArrayFieldStart("tags");
    for (Tag tag: value.getTags()) {
      jgen.writeObject(tag);
    }        
    jgen.writeEndArray();
    jgen.writeEndObject();
  }
}

