package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.request.ProductCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.ProductUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.ProductResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Product;
import kz.iitu.itse1910.variant2issenbayev.entity.Uom;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "categoryId", source = "entity.category.id")
    @Mapping(target = "categoryName", source = "entity.category.name")
    @Mapping(target = "supplierId", source = "entity.supplier.id")
    @Mapping(target = "supplierName", source = "entity.supplier.name")
    @Mapping(target = "purchaseUom", source = "entity.uom.purchaseUom")
    @Mapping(target = "saleUom", source = "entity.uom.saleUom")
    @Mapping(target = "uomConversionRate", source = "entity.uom.conversionRate")
    ProductResp toDto(Product entity);

    Product toEntity(ProductCreationReq reqDto);

    @Mapping(target = "conversionRate", source = "uomConversionRate")
    Uom toUomEntity(ProductCreationReq creationReqDto);

    @Mapping(target = "conversionRate", source = "uomConversionRate")
    Uom toUomEntity(ProductUpdateReq updateReqDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Product entity, ProductUpdateReq reqDto);
}
