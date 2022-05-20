package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.request.SaleCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.SaleResp;
import kz.iitu.itse1910.variant2issenbayev.entity.SaleTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SaleMapper {
    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    @Mapping(target = "categoryId", source = "entity.category.id")
    @Mapping(target = "categoryName", source = "entity.category.name")
    @Mapping(target = "supplierId", source = "entity.supplier.id")
    @Mapping(target = "supplierName", source = "entity.supplier.name")
    @Mapping(target = "purchaseUom", source = "entity.uom.purchaseUom")
    @Mapping(target = "saleUom", source = "entity.uom.saleUom")
    @Mapping(target = "uomConversionRate", source = "entity.uom.conversionRate")
    SaleResp toDto(SaleTransaction entity);

    SaleTransaction toEntity(SaleCreationReq reqDto);
}
