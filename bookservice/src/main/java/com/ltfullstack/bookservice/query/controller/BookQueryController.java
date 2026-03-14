package com.ltfullstack.bookservice.query.controller;

import com.ltfullstack.commonservice.model.BookResponseCommonModel;
import com.ltfullstack.bookservice.query.queries.GetAllBooksQuery;
import com.ltfullstack.commonservice.queries.GetBookDetailQuery;
import com.ltfullstack.commonservice.service.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BookResponseCommonModel> getBooks() {
        GetAllBooksQuery query = new GetAllBooksQuery();
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseCommonModel.class)).join();
    }

    @GetMapping("{bookId}")
    public BookResponseCommonModel getBook(@PathVariable("bookId") String bookId) {
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);
        return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
    }


    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) {
        kafkaService.sendMessage("test", message);
    }
}
