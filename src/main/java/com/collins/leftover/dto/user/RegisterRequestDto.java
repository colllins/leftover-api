package com.collins.leftover.dto.user;

import com.collins.leftover.model.PayFrequency;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RegisterRequestDto {

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    @Email
    @Size(max = 254)
    private String email;

    @NotBlank
    @Size(max = 60)
    private  String pwd;

    @NotBlank
    private String role;

    @NotNull
    private PayFrequency payFrequency;

    @NotNull
    @PastOrPresent
    private LocalDate onBoardingDate;

}
