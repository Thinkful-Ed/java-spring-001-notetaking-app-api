package com.thinkful.noteful.folders;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thinkful.noteful.notes.Note;
import com.thinkful.noteful.users.Account;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@JsonSerialize(using = FolderSerializer.class)
public class Folder {

  public Folder() {}

  public Folder(Long id) {
    this.id = id;
  }

  /**
   * Simple convenience for interpreting ids sent as strings via HTTP.
   */
  public Folder(String id) {
    this(Long.parseLong(id));
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  private String name;

  @OneToMany(mappedBy = "folder")
  @JsonIgnore
  private List<Note> notes;

  @ManyToOne
  @JoinColumn(name = "userid")
  @JsonAlias({"userId"})
  private Account user;

  @Column(nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdAt;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date updatedAt;

  public Long getId(){
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<Note> getNotes() {
    return this.notes;
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }

  public Account getUser() {
    return this.user;
  }

  public void setUser(Account user) {
    this.user = user;
  }

  public String toString() {
    return String.format("Folder = (%d, %s)", getId(), getName());
  }
}