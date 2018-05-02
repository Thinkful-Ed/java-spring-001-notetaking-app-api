package com.thinkful.noteful;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoteException extends RuntimeException {
  private String resourceName;
  private String fieldName;
  private Object fieldValue;
  private String details;

  public NoteException() {
    this("Note Application", "UNKNOWN", "UNKNOWN", "None provided");
  }

  /**
   * Create a new NoteException  with given values.
   * @param resourceName String The name of the resource causing the exception
   * @param fieldName String The name of the specific field involved with the exception
   * @param fieldValue Object The value of the involved field
   */
  public NoteException(String resourceName, String fieldName, Object fieldValue, String details) {
    super(String.format("%s error on field %s with value = %s, [%s]", 
          resourceName, fieldName, fieldValue, details));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;      
    this.details = details;
  }

  public String getResourceName() {
    return this.resourceName;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public Object getFieldValue() {
    return this.fieldValue;
  }

  public String getDetails() {
    return this.details;
  }
}