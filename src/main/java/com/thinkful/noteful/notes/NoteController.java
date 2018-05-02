package com.thinkful.noteful.notes;

import java.util.ArrayList;
import java.util.List;

import com.thinkful.noteful.NoteException;

import org.springframework.beans.factory.annotation.Autowired;
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

  /**
   * Retrieves all notes.
   */
  @RequestMapping(method = RequestMethod.GET)
  public List<Note> getAllNotes() {
    List<Note> notes = new ArrayList<>();
    notesRepository.findAll().forEach(notes::add);
    return notes;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Note getNoteById(@PathVariable(value = "id") Long noteId) {
    return notesRepository
    .findById(noteId)
    .orElseThrow(() -> new NoteException("Note", "id", noteId, "Note with ID not found"));
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
    return notesRepository.save(note);
  }

  @RequestMapping(value="/{id}", method = RequestMethod.PUT)
  public Note updateNote(@PathVariable(value = "id") Long noteId, @RequestBody Note updatedNote) {
    Note note = notesRepository.findById(noteId)
          .orElseThrow(() -> new NoteException(
                "Note", "id", noteId, "Note with given ID not found"));

    note.setTitle(updatedNote.getTitle());
    note.setContent(updatedNote.getContent());
    
    Note updated = notesRepository.save(note);
    return updated;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Note deleteNote(@PathVariable(value = "id") Long noteId) {
    Note note = notesRepository.findById(noteId)
          .orElseThrow(() -> new NoteException(
                "Note", "id", noteId, "Note with given ID not found"));

    notesRepository.delete(note);
    
    return note;
  }
}