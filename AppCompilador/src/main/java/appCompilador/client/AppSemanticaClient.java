package appCompilador.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import appCompilador.domains.DVariable;
import appCompilador.domains.ResultadoSemantico;

// @FeignClient(url = "http://localhost:8083", name = "app-semantica")
@FeignClient(name = "app-semantica")
@Component
public interface AppSemanticaClient {

    @PostMapping("/semantica")
    public ResultadoSemantico analizarSemanticaClient(@RequestBody List<DVariable> variables);

}
