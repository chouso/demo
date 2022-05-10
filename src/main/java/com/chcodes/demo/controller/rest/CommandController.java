package com.chcodes.demo.controller.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.chcodes.demo.domain.Command;
import com.chcodes.demo.service.CommandService;

import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/commands")
@RequiredArgsConstructor
public class CommandController {

	@Autowired
	private CommandService service;

	@PostMapping("/add")
	public ResponseEntity<Command> addCommand(@RequestBody Command command) {
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/addCommand").toUriString());
		return ResponseEntity.created(uri).body(service.saveCommand(command));
	}

	@PostMapping("/addCommands")
	public List<Command> addCommands(@RequestBody List<Command> Commands) {
		return service.saveCommands(Commands);
	}

	@GetMapping("")
	public List<Command> findAllCommands() {
		return service.getCommands();
	}

	@GetMapping("/CommandById/{id}")
	public Command findCommandById(@PathVariable int id) {
		return service.getCommandById(id);
	}


	@PutMapping("/update")
	public Command updateCommand(@RequestBody Command Command) {
		return service.updateCommand(Command);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteCommand(@PathVariable int id) {
		return service.deleteCommand(id);
	}
}
