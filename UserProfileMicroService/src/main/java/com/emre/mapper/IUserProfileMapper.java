package com.emre.mapper;

import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.rabbitmq.model.SaveAuthModel;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    UserProfile toUserProfile(final UserProfileSaveRequestDto dto);

    UserProfile toUserProfile(final SaveAuthModel model);
}
