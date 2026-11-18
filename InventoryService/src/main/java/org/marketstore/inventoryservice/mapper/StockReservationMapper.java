package org.marketstore.inventoryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marketstore.inventoryservice.dto.response.StockReservationResponseDto;
import org.marketstore.inventoryservice.model.StockReservation;

@Mapper(componentModel = "spring")
public interface StockReservationMapper {

    @Mapping(target="reservationId",source="reservation.id")
    @Mapping(target="status",source="reservation.status")
    @Mapping(target="message",source="message")
    StockReservationResponseDto toResponse(StockReservation reservation,String message);

}
