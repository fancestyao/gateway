package study.neo.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.neo.gateway.dto.FinishRegistrationRequestDTO;
import study.neo.gateway.dto.LoanApplicationRequestDTO;
import study.neo.gateway.dto.LoanOfferDTO;
import study.neo.gateway.service.interfaces.GatewayApplicationService;
import study.neo.gateway.service.interfaces.GatewayDealService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Gateway API")
public class GatewayController {
    private final GatewayDealService gatewayDealService;
    private final GatewayApplicationService gatewayApplicationService;

    @PostMapping("/application")
    @Operation(summary = "Первичная валидация заявки, создание заявки",
               description = "Возвращает лист с 4 LoanOfferDTO по входному LoanApplicationRequestDTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция прошла успешно"),
            @ApiResponse(responseCode = "409", description = "Не удалось провалидировать данные"),
    })
    public ResponseEntity<List<LoanOfferDTO>> createLoanApplicationRequestAPI(@RequestBody @Parameter(description =
            "Входные параметры для расчета условий кредита для пользователя")
                                                          LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("Получен запрос в контроллер GatewayController на передачу loanApplicationRequestDTO: {} в Deal MS",
                loanApplicationRequestDTO);
        return new ResponseEntity<>(gatewayApplicationService
                .createLoanApplicationRequestAPI(loanApplicationRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/application/apply")
    @Operation(summary = "Выбор одного из предложений",
               description = "Перенаправляет запрос на обновление истории с входным параметром LoanOfferDTO" +
                    " на МС Deal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция прошла успешно"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена в БД"),
    })
    public void applyOfferRequestAPI(@RequestBody @Parameter(description =
            "Входные параметры в виде рассчитанных условий кредита для пользователя")
                      LoanOfferDTO loanOfferDTO) {
        log.info("Получен запрос в контроллер GatewayController на передачу в Deal MS" +
                "условий кредита с loanOffer: {}", loanOfferDTO);
        gatewayApplicationService.applyOfferRequestAPI(loanOfferDTO);
    }

    @PostMapping("/application/registration/{applicationId}")
    @Operation(summary = "Полный расчет параметров кредита",
               description = "Перенаправляет запрос на полный расчет параметров кредита для клиента " +
                       "по FinishRegistrationRequestDTO и applicationId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция прошла успешно"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена в БД"),
            @ApiResponse(responseCode = "409", description = "Неверные начальные данные")
    })
    public void finishRegistrationRequestAPI(@RequestBody @Parameter(description =
            "Входной параметр наполнения ScoringDataDTO") FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          @Parameter(description = "По идентификатору ищется соответствующий application")
                          @PathVariable Long applicationId) {
        log.info("Получен запрос в контроллер GatewayController на передачу в Deal MS " +
                        "finishRegistrationRequest: {} и applicationId: {}",
                finishRegistrationRequestDTO, applicationId);
        gatewayDealService.finishRegistrationRequestAPI(finishRegistrationRequestDTO, applicationId);
    }

    @PostMapping("/document/{applicationId}")
    @Operation(summary = "Запрос на отправку документов",
            description = "Перенаправляет запрос на отправку документов клиента по applicationId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция прошла успешно"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена в БД")
    })
    public void sendDocumentsRequestAPI(@Parameter(description = "По идентификатору ищется соответствующий application")
                                        @PathVariable Long applicationId) {
        log.info("Получен запрос в контроллер GatewayController на передачу в Deal MS applicationId: {} " +
                "для отправки документов", applicationId);
        gatewayDealService.createDocumentsRequestAPI(applicationId);
    }

    @PostMapping("/document/{applicationId}/sign")
    @Operation(summary = "Запрос на подписание документов",
            description = "Перенаправляет запрос на подписание документов клиента по applicationId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция прошла успешно"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена в БД")
    })
    public void signDocumentsRequestAPI(@Parameter(description = "По идентификатору ищется соответствующий application")
                                        @PathVariable Long applicationId) {
        log.info("Получен запрос в контроллер GatewayController на передачу в Deal MS applicationId: {} " +
                "для подписания документов", applicationId);
        gatewayDealService.signDocumentsRequestAPI(applicationId);
    }

    @PostMapping("/document/{applicationId}/sign/code")
    @Operation(summary = "Запрос на подтверждение подписанных документов",
            description = "Перенаправляет запрос на подтверждение документов клиента по applicationId " +
                    "и ses code в теле запроса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция прошла успешно"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена в БД")
    })
    public void codeDocument(@Parameter(description = "По идентификатору ищется соответствующий application")
                             @PathVariable Long applicationId,
                             @Parameter(description = "В тело передается ses code для верификации")
                             @RequestBody Integer sesCode) {
        log.info("Получен запрос в контроллер GatewayController на передачу в Deal MS applicationId: {} и sesCode: {}",
                applicationId, sesCode);
        gatewayDealService.verifySesCodeRequestAPI(applicationId, sesCode);
    }
}
