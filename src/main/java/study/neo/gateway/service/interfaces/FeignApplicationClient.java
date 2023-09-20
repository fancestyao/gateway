package study.neo.gateway.service.interfaces;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.neo.gateway.dto.LoanApplicationRequestDTO;
import study.neo.gateway.dto.LoanOfferDTO;

import java.util.List;

@FeignClient(name = "application", url = "${feign-client-application.url}")
public interface FeignApplicationClient {
    @PostMapping("/application")
    List<LoanOfferDTO> sendLoanApplicationToApplicationMS(@RequestBody @Parameter(description =
            "Входные параметры для расчета условий кредита для пользователя")
                                                           LoanApplicationRequestDTO loanApplicationRequestDTO);

    @PutMapping("application/offer")
    void sendLoanOfferToApplicationMS(@RequestBody @Parameter(description =
            "Входные параметры в виде рассчитанных условий кредита для пользователя")
                      LoanOfferDTO loanOfferDTO);
}
