package com.ead.authuser.clients.params;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CourseClientParams {
    private UUID userId;
    private int page;
    private int size;
    private String sort;
}
