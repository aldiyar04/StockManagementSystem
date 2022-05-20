package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.response.SaleResp;
import kz.iitu.itse1910.variant2issenbayev.entity.SaleTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TxItemMapper.class})
public interface SaleMapper {
    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    @Mapping(target = "customerId", source = "entity.customer.id")
    @Mapping(target = "customerName", source = "entity.customer.firstLastName")
    @Mapping(target = "createdById", source = "entity.createdBy.id")
    @Mapping(target = "createdByUsername", source = "entity.createdBy.username")
    SaleResp toDto(SaleTransaction entity);
}
