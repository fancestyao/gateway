package study.neo.gateway.service.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.neo.gateway.dto.LoanApplicationRequestDTO;
import study.neo.gateway.dto.LoanOfferDTO;
import study.neo.gateway.service.interfaces.FeignApplicationClient;
import study.neo.gateway.service.interfaces.GatewayApplicationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayApplicationServiceImpl implements GatewayApplicationService {
    private final FeignApplicationClient feignApplicationClient;

    @Override
    public List<LoanOfferDTO> createLoanApplicationRequestAPI(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.info("Запрос на передачу loanApplicationRequest поступил в GatewayApplicationService");
        List<LoanOfferDTO> result =
                feignApplicationClient.sendLoanApplicationToApplicationMS(loanApplicationRequestDTO);
        log.info("Ответ с Deal MS: {}", result);
        return result;
    }

    @Override
    public void applyOfferRequestAPI(LoanOfferDTO loanOfferDTO) {
        log.info("Запрос на передачу loanOffer поступил в GatewayApplicationService");
        feignApplicationClient.sendLoanOfferToApplicationMS(loanOfferDTO);
    }
}
