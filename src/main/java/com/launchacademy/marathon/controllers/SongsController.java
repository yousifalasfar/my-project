package com.launchacademy.marathon.controllers;

import com.launchacademy.marathon.models.Song;
import com.launchacademy.marathon.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;


@Controller
@RequestMapping("/songs")
public class SongsController {
  private SongRepository songRepo;

  @Autowired
  public void SongsController(SongRepository songRepo) {
    this.songRepo = songRepo;
  }

  @GetMapping
  public String getSongsList(Model model){
    model.addAttribute("songs", songRepo.findByOrderByTitleDesc());
    return "songs/index";
  }
  @GetMapping("/new")
  public String newFriend(@ModelAttribute("song") Song song) {
    return "songs/new";
  }

  @PostMapping
  public String createSong(@ModelAttribute @Valid Song song, BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return "songs/new";
    } else
      songRepo.save(song);
    return "redirect:/songs";
  }
}
