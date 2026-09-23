package net.daneshkar.authorssystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "Category title is required")
    @Size(max = 150, message = "Category title must be at most 150 characters")
    private String title;
}
