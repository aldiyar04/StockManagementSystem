package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.request.CategoryCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.CategoryUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.CategoryResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResp toDto(Category entity);
    Category toEntity(CategoryCreationReq reqDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Category entity, CategoryUpdateReq reqDto);
}
