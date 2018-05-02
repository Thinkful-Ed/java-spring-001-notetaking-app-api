package com.thinkful.noteful;

import com.thinkful.noteful.notes.Note;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class WebConfiguration extends RepositoryRestConfigurerAdapter{

  @Override 
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    super.configureRepositoryRestConfiguration(config);
    config.exposeIdsFor(Note.class);
  }
  
}