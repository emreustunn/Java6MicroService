package com.emre.mapper;

import com.emre.dto.request.RegisterRequestDto;
import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);
    Auth toAuth(final RegisterRequestDto dto);
    @Mapping(target = "authid",source = "id")
    UserProfileSaveRequestDto fromAuth(final Auth auth);
}
