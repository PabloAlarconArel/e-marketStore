package org.marketstore.inventoryservice.service;

import org.marketstore.inventoryservice.dto.request.StockReservationRequestDto;
import org.marketstore.inventoryservice.dto.response.StockReservationResponseDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StockReservationService {

    Mono<StockReservationResponseDto> reserveItems(StockReservationRequestDto request);
