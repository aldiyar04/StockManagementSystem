package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.request.CustomerCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.CustomerUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.CustomerResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResp toDto(Customer entity);

    Customer toEntity(CustomerCreationReq reqDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Customer entity, CustomerUpdateReq reqDto);
}
