package com.example.exception.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    @NotBlank
    private String id;

    @Length(min = 3)
    private String name;

    @Size(min = 19, max = 80)
    private Integer age;

}
