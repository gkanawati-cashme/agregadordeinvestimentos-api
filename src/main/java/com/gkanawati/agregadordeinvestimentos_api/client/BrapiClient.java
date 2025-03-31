package com.gkanawati.agregadordeinvestimentos_api.client;

import com.gkanawati.agregadordeinvestimentos_api.client.dto.BrapiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "BrapiClient",
        url = "https://brapi.dev/api/"
)
public interface BrapiClient {

  @GetMapping(value = "quote/{stockId}")
  BrapiResponseDTO getQuote(@RequestParam("token") String token, @PathVariable("stockId") String stockId);

}
