package com.thinkful.noteful.notes;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thinkful.noteful.folders.Folder;
import com.thinkful.noteful.tags.Tag;
import com.thinkful.noteful.users.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@JsonSerialize(using = NoteSerializer.class)
public class Note {

  public Note() {}

  public Note(String title, String content) {
    this.setTitle(title);
    this.setContent(content);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "userid")
  @JsonAlias({"userId"})
  private User user;

  @ManyToOne
  @JsonAlias({"folderId"})
  private Folder folder;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "note_tag",
      joinColumns = @JoinColumn(name = "tagId", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "noteId", referencedColumnName = "id"))  
  private List<Tag> tags;

  @NotBlank
  private String title;

  private String content;

  @Column(nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private Date createdAt;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private Date updatedAt;

  /**
   * A convenience method for adding a tag to a Note.
   * This is important because Note <--> Tag association
   * is Many to Many bi-directional and it must be managed from one side
   * to ensure consistency.
   * 
   * @param tag Tag The Tag to add to the Note
   */
  public void addTag(Tag tag) {
    tag.addNote(this);
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tag);
  }

  /**
   * The getId method is necessary to force the 
   * JSON parser to output the id when converting to
   * JSON.
   * 
   * @return Long the id of the Note
   */
  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
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

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Folder getFolder() {
    return this.folder;
  }

  public void setFolder(Folder folder) {
    this.folder = folder;
  }

  public List<Tag> getTags() {
    return this.tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

}