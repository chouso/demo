package com.chcodes.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chcodes.demo.domain.Command;
import com.chcodes.demo.repo.CommandRepo;

@Service
public class CommandService {
	@Autowired
	private CommandRepo commandRepo;

	public Command saveCommand(Command command) {
		return commandRepo.save(command);
	}

	public List<Command> saveCommands(List<Command> commands) {
		return commandRepo.saveAll(commands);
	}

	public List<Command> getCommands() {
		return commandRepo.findAll();
	}

	public Command getCommandById(int id) {
		return commandRepo.findById(id).orElse(null);
	}

	public String deleteCommand(int id) {
		commandRepo.deleteById(id);
		return "command removed !! " + id;
	}

	public Command updateCommand(Command command) {
		Command existingcommand = commandRepo.findById(command.getId()).orElse(null);
		existingcommand.setCommandDate(command.getCommandDate());
		existingcommand.setUser(command.getUser());
		return commandRepo.save(existingcommand);
	}

}
