package kz.iitu.itse1910.variant2issenbayev.mapper;

import kz.iitu.itse1910.variant2issenbayev.dto.request.UserSignupReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.UserUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.UserResp;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResp toDto(User entity);
    User toEntity(UserSignupReq dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget User entity, UserUpdateReq dto);
}
