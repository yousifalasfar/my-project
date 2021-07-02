package com.launchacademy.marathon.controllers;

import com.launchacademy.marathon.models.Song;
import com.launchacademy.marathon.repositories.SongRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/songs")
public class SongsRestController {

  private SongRepository songRepo;

  @Autowired
  public void SongsRestController(SongRepository songRepo) {
    this.songRepo = songRepo;
  }
  @GetMapping
  public List<Song> getSongs(Pageable pageable) {
    return songRepo.findByOrderByTitleDesc();
   }

  @GetMapping("/{id}")
  public Song getSong(@PathVariable Integer id) {
    return songRepo.findById(id).orElseThrow(() -> new UrlNotFoundException());
   }
  @PostMapping
  public ResponseEntity create(@Valid @RequestBody Song song, BindingResult result){
    if (result.hasErrors()) {
      return new ResponseEntity<List>(result.getAllErrors(),HttpStatus.NOT_ACCEPTABLE);
    } else {
      return new ResponseEntity<Song>(songRepo.save(song),HttpStatus.CREATED);
    }
  }


  @NoArgsConstructor
  private class UrlNotFoundException extends RuntimeException {
   }

  @ControllerAdvice
  private class UrlNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String urlNotFoundHandler(UrlNotFoundException ex) {
      return ex.getMessage();
    }
  }
}
