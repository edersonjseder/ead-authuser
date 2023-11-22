package com.ead.authuser.utils;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.models.User;
import com.ead.authuser.responses.ImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.ead.authuser.constants.UserMessagesConstants.USUARIO_IMAGE_SUCESSO_MENSAGEM;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserUtils {
    private final DateUtils dateUtils;
    public UserDto toUserDto(User user) {
        log.debug("Method toUserDto user saved {} ", user.toString());
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .cpf(user.getCpf())
                .imageUrl(user.getImageUrl())
                .status(user.getUserStatus().name())
                .type(user.getUserType().name())
                .creationDate(dateUtils.parseDate(user.getCreationDate()))
                .lastUpdateDate(dateUtils.parseDate(user.getLastUpdateDate()))
                .currentPasswordDate(dateUtils.parseDate(user.getCurrentPasswordDate()))
                .build();
    }

    public ImageResponse toImageResponse(User user) {
        return ImageResponse.builder()
                .message(USUARIO_IMAGE_SUCESSO_MENSAGEM)
                .imageUrl(user.getImageUrl())
                .lastUpdatedDate(dateUtils.parseDate(user.getLastUpdateDate()))
                .build();
    }

    public Page<UserDto> toListUserDto(Page<User> userList) {
        return userList.map(user -> UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .cpf(user.getCpf())
                .imageUrl(user.getImageUrl())
                .status(user.getUserStatus().name())
                .type(user.getUserType().name())
                .creationDate(dateUtils.parseDate(user.getCreationDate()))
                .lastUpdateDate(dateUtils.parseDate(user.getLastUpdateDate()))
                .currentPasswordDate(dateUtils.parseDate(user.getCurrentPasswordDate()))
                .build());
    }

    public UserEventDto toUserEventDto(User user) {
        var userEventDto = new UserEventDto();
        BeanUtils.copyProperties(user, userEventDto);
        userEventDto.setUserStatus(user.getUserStatus().name());
        userEventDto.setUserType(user.getUserType().name());
        return userEventDto;
    }
}
