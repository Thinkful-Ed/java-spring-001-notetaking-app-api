package com.thinkful.noteful;

public enum NoteStatus {
  
  VALIDATION_ERROR("ValidationError"),
  DATA_INTEGRITY_ERROR("DataIntegrityError");

  private final String value;

  NoteStatus(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

}