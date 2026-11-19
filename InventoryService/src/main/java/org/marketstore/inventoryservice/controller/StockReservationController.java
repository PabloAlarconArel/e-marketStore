package org.marketstore.inventoryservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.marketstore.inventoryservice.dto.request.StockReservationRequestDto;
import org.marketstore.inventoryservice.dto.response.StockReservationResponseDto;
import org.marketstore.inventoryservice.service.StockReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("api/inventory/reservations")
@RequiredArgsConstructor
public class StockReservationController {

    private final StockReservationService stockReservationService;

    @PostMapping
    public Mono<ResponseEntity<StockReservationResponseDto>> reserveItems(@Valid @RequestBody StockReservationRequestDto request){
        return stockReservationService.reserveItems(request)
                .map(response ->ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(response));
    }

