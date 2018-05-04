package com.thinkful.noteful.tags;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import com.thinkful.noteful.NoteException;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class TagDeserializer extends StdDeserializer<Tag> {

  @Autowired
  TagRepository tagRepository;

  public TagDeserializer() {
    this(null);
  }

  public TagDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Tag deserialize(JsonParser jp, DeserializationContext ctx) 
        throws IOException, JsonProcessingException {
    JsonNode tagNode = jp.getCodec().readTree(jp);      
    Tag tag = tagRepository
          .findById(tagNode.get("id").asLong())
          .orElseThrow(() -> new NoteException());
    return new Tag();
  }
}