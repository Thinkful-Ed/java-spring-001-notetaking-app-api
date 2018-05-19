package com.thinkful.noteful.notes;

import com.thinkful.noteful.NoteException;
import com.thinkful.noteful.NoteStatus;
import com.thinkful.noteful.folders.Folder;
import com.thinkful.noteful.folders.FolderRepository;
import com.thinkful.noteful.tags.Tag;
import com.thinkful.noteful.tags.TagRepository;
import com.thinkful.noteful.users.User;
import com.thinkful.noteful.users.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/notes")
public class NoteController {

  @Autowired
  NotesRepository notesRepository;

  @Autowired
  TagRepository tagRepository;

  @Autowired
  FolderRepository folderRepository;

  @Autowired
  UserRepository userRepository;

  /**
   * Retrieves all notes.
   */
  @RequestMapping(method = RequestMethod.GET)
  public List<Note> getAllNotes() {
    List<Note> notes = new ArrayList<>();
    notesRepository.findAll().forEach(notes::add);
    return notes;
  }

  /**
   * Retrieve the note with ID noteId.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Note getNoteById(@PathVariable(value = "id") Long noteId) {
    return notesRepository
          .findById(noteId)
          .orElseThrow(() -> new NoteException(
            "Note", 
            "id", 
            noteId, 
            "Note with ID not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));
  }

  /**
   * Create a new Note with values provided in body.
   * {
   *    "title":"Note Title",
   *    "content": "Note Content"
   * }
   */
  @RequestMapping(method = RequestMethod.POST)
  public Note createNote(@RequestBody Note note) {

    if (note.getTitle() == null || note.getTitle().trim().length() == 0) {
      throw new NoteException(
        "Note",
        "title",
        note.getTitle(),
        "missing title in request body",
        HttpStatus.UNPROCESSABLE_ENTITY,
        NoteStatus.VALIDATION_ERROR
      );
    }

    if (note.getTags() != null) {
      List<Tag> tags = note.getTags();
      note.setTags(tags.stream()
            .map(tag -> tag.getId())
            .map(tagRepository::findById)
            .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
            .collect(Collectors.toList()));
    }

    if (note.getFolder() != null) {
      Folder folder = folderRepository
            .findById(note.getFolder().getId())
            .orElse(null);
      note.setFolder(folder);
    }

    String username = SecurityContextHolder
          .getContext()
          .getAuthentication()
          .getPrincipal()
          .toString();
    User user = userRepository.findByUsername(username);
    note.setUser(user);

    return notesRepository.save(note);
  }

  /**
   * Update a Note with the given values.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Note updateNote(@PathVariable(value = "id") Long noteId, @RequestBody Note updatedNote) {
    Note note = notesRepository.findById(noteId)
          .orElseThrow(() -> new NoteException(
            "Note", 
            "id", 
            noteId, 
            "Note with ID not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));

    if (updatedNote.getTitle() != null && updatedNote.getTitle().trim().length() > 0) {            
      note.setTitle(updatedNote.getTitle());
    }

    if (updatedNote.getContent() != null) {
      note.setContent(updatedNote.getContent());
    }

    if (updatedNote.getTags() != null) {
      List<Tag> tags = updatedNote.getTags();
      note.setTags(tags.stream()
            .map(tag -> tag.getId())
            .map(tagRepository::findById)
            .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
            .collect(Collectors.toList()));
    }

    if (updatedNote.getFolder() != null) {
      Folder folder = folderRepository
            .findById(updatedNote.getFolder().getId())
            .orElse(null);
      note.setFolder(folder);
    }
    
    Note updated = notesRepository.save(note);
    return updated;
  }

  /**
   * Delete the Note with the given ID.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Note> deleteNote(@PathVariable(value = "id") Long noteId) {
    Note note = notesRepository.findById(noteId)
          .orElseThrow(() -> new NoteException(
            "Note", 
            "id", 
            noteId, 
            "Note with ID not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));

    notesRepository.delete(note);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Add a Tag to a Note.
   * @param id The id of the Note
   * @param tagId The id of the Tag
   * @return Note The note updated with the given Tag
   * @throws NoteException If either the note id or tag id are not found
   */
  @RequestMapping(value = "/{id}/tags/{tagId}", method = RequestMethod.PATCH)
  public Note addTagToNote(
      @PathVariable(value = "id") Long id,
      @PathVariable(value = "tagId") Long tagId
  ) {
    Note note = notesRepository.findById(id)
          .orElseThrow(() -> new NoteException(
            "Note", 
            "id", 
            id, 
            "Note with ID not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));

    Tag tag = tagRepository.findById(tagId)
          .orElseThrow(() -> new NoteException(
            "Tag", 
            "id", 
            tagId, 
            "Tag with ID not found",
            HttpStatus.UNPROCESSABLE_ENTITY,
            NoteStatus.DATA_INTEGRITY_ERROR));

    note.addTag(tag);
    
    note = notesRepository.save(note);
    return note;
  }
}