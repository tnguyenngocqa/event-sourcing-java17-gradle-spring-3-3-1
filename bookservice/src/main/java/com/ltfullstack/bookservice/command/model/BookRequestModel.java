package com.ltfullstack.bookservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestModel {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    String name;

    @NotBlank(message = "Author is mandatory")
    String author;

    Boolean isReady;
}
