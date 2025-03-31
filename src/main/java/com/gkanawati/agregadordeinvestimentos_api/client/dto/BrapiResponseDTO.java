package com.gkanawati.agregadordeinvestimentos_api.client.dto;

import java.util.List;

public record BrapiResponseDTO(List<StockDTO> results) {
}
