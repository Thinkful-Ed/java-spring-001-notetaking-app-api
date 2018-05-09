package com.thinkful.noteful.folders;

import com.thinkful.noteful.NoteException;
import com.thinkful.noteful.users.User;
import com.thinkful.noteful.users.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
                "folder", "id", folderId, "folder with given id not found"));
  }

  /**
   * Create a new folder with provided values.
   */
  @RequestMapping(method = RequestMethod.POST)
  public Folder createfolder(@RequestBody Folder folder) {
    String username = SecurityContextHolder
          .getContext()
          .getAuthentication()
          .getPrincipal()
          .toString();
    User user = userRepository.findByUsername(username);
    folder.setUser(user);
    return folderRepository.save(folder);
  }

  /**
   * Update the folder name.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Folder updatefolder(@PathVariable(value = "id")Long folderId, 
        @RequestBody Folder updatedfolder) {
    Folder folder = folderRepository.findById(folderId)
          .orElseThrow(() -> new NoteException(
                "folder", "id", folderId, "folder with given id not found"));

    folder.setName(updatedfolder.getName());

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
                "folder", "id", folderId, "folder with given id not found"));
    folderRepository.delete(folder);
    return folder;      
  }

}