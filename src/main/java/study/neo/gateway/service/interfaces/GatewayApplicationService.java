package study.neo.gateway.service.interfaces;

import study.neo.gateway.dto.LoanApplicationRequestDTO;
import study.neo.gateway.dto.LoanOfferDTO;

import java.util.List;

public interface GatewayApplicationService {
    List<LoanOfferDTO> createLoanApplicationRequestAPI(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void applyOfferRequestAPI(LoanOfferDTO loanOfferDTO);
}
