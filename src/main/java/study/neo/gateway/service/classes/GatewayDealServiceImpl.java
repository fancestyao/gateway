package study.neo.gateway.service.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.neo.gateway.dto.FinishRegistrationRequestDTO;
import study.neo.gateway.service.interfaces.FeignDealClient;
import study.neo.gateway.service.interfaces.GatewayDealService;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayDealServiceImpl implements GatewayDealService {
    private final FeignDealClient feignDealClient;

    @Override
    public void finishRegistrationRequestAPI(FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                                             Long applicationId) {
        log.info("Запрос на передачу finishRegistrationRequestDTO поступил в GatewayDealService");
        feignDealClient.sendFinishRegistrationRequestToDealMS(finishRegistrationRequestDTO, applicationId);
    }

    @Override
    public void createDocumentsRequestAPI(Long applicationId) {
        log.info("Запрос на передачу запроса о создании документов поступил в GatewayDealService");
        feignDealClient.sendCreateDocumentsRequestToDealMS(applicationId);
    }

    @Override
    public void signDocumentsRequestAPI(Long applicationId) {
        log.info("Запрос на передачу запроса о подписании документов поступил в GatewayDealService");
        feignDealClient.sendSignDocumentsRequestToDealMS(applicationId);
    }

    @Override
    public void verifySesCodeRequestAPI(Long applicationId, Integer sesCode) {
        log.info("Запрос на передачу запроса о подтверждении ses code поступил в GatewayDealService");
        feignDealClient.sendVerifySesCodeRequestToDealMS(applicationId, sesCode);
    }
}
