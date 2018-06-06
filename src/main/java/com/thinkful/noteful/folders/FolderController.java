package com.thinkful.noteful.folders;

import com.thinkful.noteful.NoteException;
import com.thinkful.noteful.NoteStatus;
import com.thinkful.noteful.users.Account;
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
@RequestMapping("/api/folders")
public class FolderController {

  @Autowired
  FolderRepository folderRepository;

  @Autowired
  UserRepository userRepository;

  /**
   * Retrieve all folders.
   */
  @RequestMapping(method = RequestMethod.GET)
  public List<Folder> getAllfolders() {
    List<Folder> folders = new ArrayList<>();
    folderRepository.findAll().forEach(folders::add);
    return folders;
  }

  /**
   * Retrieve a folder by given id.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Folder getfolderById(@PathVariable(value = "id") Long folderId) {
    return folderRepository
          .findById(folderId)
          .orElseThrow(() -> new NoteException(
                "Folder", 
                "id", 
                folderId, 
                "folder with given id not found",
                HttpStatus.UNPROCESSABLE_ENTITY,
                NoteStatus.DATA_INTEGRITY_ERROR));
  }

  /**
   * Create a new folder with provided values.
   */
  @RequestMapping(method = RequestMethod.POST)
  public Folder createfolder(@RequestBody Folder folder) {

    if (folder.getName() == null || folder.getName().trim().length() == 0) {
      throw new NoteException(
        "Folder",
        "name",
        folder.getName(),
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
    Account user = userRepository.findByUsername(username);
    folder.setUser(user);
    return folderRepository.save(folder);
  }

  /**
   * Update the folder name.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Folder updatefolder(@PathVariable(value = "id")Long folderId, 
        @RequestBody Folder updatedFolder) {

    if (updatedFolder.getName() == null || updatedFolder.getName().trim().length() == 0) {
      throw new NoteException(
        "Folder",
        "name",
        updatedFolder.getName(),
        "missing name in request body",
        HttpStatus.UNPROCESSABLE_ENTITY,
        NoteStatus.VALIDATION_ERROR
      );
    }      
    Folder folder = folderRepository.findById(folderId)
          .orElseThrow(() -> new NoteException(
                "Folder", 
                "id", 
                folderId, 
                "folder with given id not found",
                HttpStatus.UNPROCESSABLE_ENTITY,
                NoteStatus.DATA_INTEGRITY_ERROR));

    folder.setName(updatedFolder.getName());

    Folder updated = folderRepository.save(folder);
    return updated;
  }

  /**
   * Delete folder with given id.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public Folder deletefolder(@PathVariable(value = "id") Long folderId) {
    Folder folder = folderRepository.findById(folderId)
          .orElseThrow(() -> new NoteException(
                "Folder", 
                "id", 
                folderId, 
                "folder with given id not found",
                HttpStatus.UNPROCESSABLE_ENTITY,
                NoteStatus.DATA_INTEGRITY_ERROR));
    folderRepository.delete(folder);
    return folder;      
  }

}