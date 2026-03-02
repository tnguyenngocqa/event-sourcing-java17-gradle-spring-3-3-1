package com.ltfullstack.bookservice.command.aggregate;

import com.ltfullstack.bookservice.command.command.CreateBookCommand;
import com.ltfullstack.bookservice.command.command.DeleteBookCommand;
import com.ltfullstack.bookservice.command.command.UpdateBookCommand;
import com.ltfullstack.bookservice.command.event.BookCreatedEvent;
import com.ltfullstack.bookservice.command.event.BookDeletedEvent;
import com.ltfullstack.bookservice.command.event.BookUpdatedEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class BookAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String author;
    private Boolean isReady;

    @CommandHandler
    public BookAggregate(CreateBookCommand command) {
        BookCreatedEvent event = new BookCreatedEvent();
        BeanUtils.copyProperties(command, event);
        // publish event
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateBookCommand command) {
        BookUpdatedEvent event = new BookUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        // publish event
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteBookCommand command) {
        BookDeletedEvent event = new BookDeletedEvent();
        BeanUtils.copyProperties(command, event);
        // publish event
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        // update state
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        // update state
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        // update state
        this.id = event.getId();
    }
}
