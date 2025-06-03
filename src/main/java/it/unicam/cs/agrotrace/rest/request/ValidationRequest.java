package it.unicam.cs.agrotrace.rest.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationRequest {

    @NotNull(message = "Validation status cannot be null")
    @Pattern(
            regexp = "^(APPROVED|NEED_CORRECTION|REJECTED)$",
            message = "Validation status must be APPROVED, NEED_CORRECTION, or REJECTED"
    )
    private String validationCode;

    private String comments;

}
