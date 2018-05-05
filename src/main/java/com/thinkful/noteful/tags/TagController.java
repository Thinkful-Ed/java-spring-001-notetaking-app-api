package com.thinkful.noteful.tags;

import com.thinkful.noteful.NoteException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
          .orElseThrow(() -> new NoteException("Tag", "id", tagId, "Tag with given id not found"));
  }

  /**
   * Create a new Tag with provided values.
   */
  @RequestMapping(method = RequestMethod.POST)
  public Tag createTag(@RequestBody Tag tag) {
    return tagRepository.save(tag);
  }

  /**
   * Update the tag name.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Tag updateTag(@PathVariable(value = "id")Long tagId, @RequestBody Tag updatedTag) {
    Tag tag = tagRepository.findById(tagId)
          .orElseThrow(() -> new NoteException("Tag", "id", tagId, "Tag with given id not found"));

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
          .orElseThrow(() -> new NoteException("Tag", "id", tagId, "Tag with given id not found"));
    tagRepository.delete(tag);
    return tag;      
  }

}