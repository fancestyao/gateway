package study.neo.gateway.service.interfaces;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import study.neo.gateway.dto.FinishRegistrationRequestDTO;

@FeignClient(name = "deal", url = "${feign-client-deal.url}")
public interface FeignDealClient {
    @PutMapping("deal/calculate/{applicationId}")
    void sendFinishRegistrationRequestToDealMS(@RequestBody @Parameter(description =
            "Входной параметр наполнения ScoringDataDTO") FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                   @Parameter(description = "По идентификатору ищется соответствующий application")
                   @PathVariable Long applicationId);

    @PostMapping("deal/document/{applicationId}/send")
    void sendCreateDocumentsRequestToDealMS(@Parameter(description = "По идентификатору ищется соответствующий application")
                      @PathVariable Long applicationId);

    @PostMapping("deal/document/{applicationId}/sign")
    void sendSignDocumentsRequestToDealMS(@Parameter(description = "По идентификатору ищется соответствующий application")
                      @PathVariable Long applicationId);

    @PostMapping("deal/document/{applicationId}/code")
    void sendVerifySesCodeRequestToDealMS(@Parameter(description = "По идентификатору ищется соответствующий application")
                      @PathVariable Long applicationId,
                      @Parameter(description = "В тело передается ses code для верификации на стадии MS Deal")
                      @RequestBody Integer sesCode);
}
