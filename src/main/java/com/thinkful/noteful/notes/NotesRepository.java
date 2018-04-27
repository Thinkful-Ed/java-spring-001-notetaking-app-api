package com.thinkful.noteful.notes;

import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Note, Long>{}