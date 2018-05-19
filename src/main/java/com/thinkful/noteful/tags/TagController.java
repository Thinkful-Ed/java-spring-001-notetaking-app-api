package com.thinkful.noteful.tags;

import com.thinkful.noteful.NoteException;
import com.thinkful.noteful.NoteStatus;
import com.thinkful.noteful.users.User;
import com.thinkful.noteful.users.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
public class TagController {

  @Autowired
  TagRepository tagRepository;

  @Autowired
  UserRepository userRepository;

  /**
   * Retrieve all tags.
   */
  @RequestMapping(method = RequestMethod.GET)
  public List<Tag> getAllTags() {
    List<Tag> tags = new ArrayList<>();
    tagRepository.findAll().forEach(tags::add);
    return tags;
  }

  /**
   * Retrieve a Tag by given id.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Tag getTagById(@PathVariable(value = "id") Long tagId) {
    return tagRepository
          .findById(tagId)
          .orElseThrow(() -> new NoteException(
            "Tag", 
            "id", 
            tagId, 
            "Tag with given id not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));
  }

  /**
   * Create a new Tag with provided values.
   */
  @RequestMapping(method = RequestMethod.POST)
  public Tag createTag(@RequestBody Tag tag) {

    if (tag.getName() == null || tag.getName().trim().length() == 0) {
      throw new NoteException(
        "Tag",
        "name",
        tag.getName(),
        "missing name in request body",
        HttpStatus.UNPROCESSABLE_ENTITY,
        NoteStatus.VALIDATION_ERROR
      );
    }

    String username = SecurityContextHolder
          .getContext()
          .getAuthentication()
          .getPrincipal()
          .toString();
    User user = userRepository.findByUsername(username);
    tag.setUser(user);
    return tagRepository.save(tag);
  }

  /**
   * Update the tag name.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Tag updateTag(
        @PathVariable(value = "id")Long tagId, 
        @RequestBody Tag updatedTag) {

    if (updatedTag.getName() == null || updatedTag.getName().trim().length() == 0) {
      throw new NoteException(
        "Tag",
        "name",
        updatedTag.getName(),
        "missing name in request body",
        HttpStatus.UNPROCESSABLE_ENTITY,
        NoteStatus.VALIDATION_ERROR
      );
    }      

    Tag tag = tagRepository.findById(tagId)
          .orElseThrow(() -> new NoteException(
            "Tag", 
            "id", 
            tagId, 
            "Tag with given id not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));

    tag.setName(updatedTag.getName());

    Tag updated = tagRepository.save(tag);
    return updated;
  }

  /**
   * Delete tag with given id.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Tag deleteTag(@PathVariable(value = "id") Long tagId) {
    Tag tag = tagRepository.findById(tagId)
          .orElseThrow(() -> new NoteException(
            "Tag", 
            "id", 
            tagId, 
            "Tag with given id not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));
    tagRepository.delete(tag);
    return tag;      
  }

}