package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto extends RepresentationModel<CourseDto> {
    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private String creationDate;
    private String lastUpdateDate;
    private String courseStatus;
    private String courseLevel;
    private UUID userInstructor;
}
