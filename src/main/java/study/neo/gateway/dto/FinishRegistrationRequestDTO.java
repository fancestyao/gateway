package study.neo.gateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.neo.gateway.enumeration.Gender;
import study.neo.gateway.enumeration.MaritalStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinishRegistrationRequestDTO {
    @Schema(description = "Пол клиента", example = "FEMALE")
    private Gender gender;
    @Schema(description = "Семейное положение клиента", example = "MARRIED")
    private MaritalStatus maritalStatus;
    @Schema(description = "Количество иждивенцев", example = "0")
    private Integer dependentAmount;
    @Schema(description = "Дата выдачи паспорта клиента в формате yyyy-MM-dd", example = "2022-05-27")
    private LocalDate passportIssueDate;
    @Schema(description = "Место выдачи паспорта клиента", example = "Saratov")
    private String passportIssueBranch;
    @Schema(description = "Информация о трудоустройстве клиента", example =
            "{\"employmentStatus\": \"SELF_EMPLOYED\"," +
                    " \"employmentINN\": \"1234567890\"," +
                    " \"salary\": \"50000\"," +
                    " \"workExperienceTotal\": 15," +
                    " \"workExperienceCurrent\": 5," +
                    " \"employmentPosition\": \"TOP_MANAGER\"}")
    private EmploymentDTO employment;
    @Schema(description = "Банковский счет клиента", example = "12341234123412341234")
    private String account;
}