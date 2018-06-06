package com.thinkful.noteful;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoteException extends RuntimeException {
  private String resourceName;
  private String fieldName;
  private Object fieldValue;
  private String details;
  private HttpStatus status;
  private NoteStatus reason;

  /**
   * Create NoteException with default values.
   */
  public NoteException() {
    this(
          "Note Application", 
          "UNKNOWN", 
          "UNKNOWN", 
          "None provided", 
          HttpStatus.INTERNAL_SERVER_ERROR,
          NoteStatus.VALIDATION_ERROR);
  }

  /**
   * Create a new NoteException  with given values.
   * @param resourceName String The name of the resource causing the exception
   * @param fieldName String The name of the specific field involved with the exception
   * @param fieldValue Object The value of the involved field
   */
  public NoteException(
        String resourceName, 
        String fieldName, 
        Object fieldValue, 
        String details, 
        HttpStatus status,
        NoteStatus reason) {
    super(String.format("%s error on field %s with value = %s, [%s]", 
          resourceName, fieldName, fieldValue, details));
    this.resourceName = resourceName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;      
    this.details = details;
    this.status = status;
    this.reason = reason;
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

  public HttpStatus getStatus() {
    return this.status;
  }

  public NoteStatus getReason() {
    return this.reason;
  }

  /**
   * Return a String in the format.
   * {
   *   "message": "Username already taken",
   *   "error": {
   *      "message": "Username already taken",
   *      "status": 422,
   *      "reason": "ValidationError",
   *      "location": "username"
   *   }
   * }
   */
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    sb.append(String.format("\"message\":\"%s\",", this.getDetails()));
    sb.append("\"error\": {");
    sb.append(String.format("\"message\":\"%s\",", this.getDetails()));
    sb.append(String.format("\"status\":\"%d\",", this.getStatus().value()));
    sb.append(String.format("\"reason\":\"%s\",", this.getReason().value()));
    sb.append(String.format("\"location\":\"%s\"", this.getFieldName()));
    sb.append("}}");
    return sb.toString();
  }
}