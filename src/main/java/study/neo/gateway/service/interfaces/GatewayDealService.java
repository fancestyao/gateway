package study.neo.gateway.service.interfaces;

import study.neo.gateway.dto.FinishRegistrationRequestDTO;

public interface GatewayDealService {
    void finishRegistrationRequestAPI(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Long applicationId);

    void createDocumentsRequestAPI(Long applicationId);

    void signDocumentsRequestAPI(Long applicationId);

    void verifySesCodeRequestAPI(Long applicationId, Integer sesCode);
}
