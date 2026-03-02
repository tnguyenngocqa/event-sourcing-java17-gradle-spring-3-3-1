package com.ltfullstack.bookservice.command.controller;

import com.ltfullstack.bookservice.command.command.CreateBookCommand;
import com.ltfullstack.bookservice.command.command.DeleteBookCommand;
import com.ltfullstack.bookservice.command.command.UpdateBookCommand;
import com.ltfullstack.bookservice.command.model.BookRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final CommandGateway commandGateway;

    /******************
     * * Add book
     * * Update book
     * * Delete book
     ******************/
    @PostMapping
    String addBook(@Valid @RequestBody BookRequestModel request) {
        CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(), request.getName(), request.getAuthor(), true);
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{id}")
    String updateBook(@PathVariable String id, @RequestBody BookRequestModel request) {
        UpdateBookCommand command = new UpdateBookCommand(id, request.getName(), request.getAuthor(), true);
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{id}")
    String deleteBook(@PathVariable String id) {
        DeleteBookCommand command = new DeleteBookCommand(id);
        return commandGateway.sendAndWait(command);
    }
}
