package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.request.SupplierCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.SupplierUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.SupplierResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Supplier;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierResp toDto(Supplier entity);
    Supplier toEntity(SupplierCreationReq reqDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Supplier entity, SupplierUpdateReq reqDto);
}
