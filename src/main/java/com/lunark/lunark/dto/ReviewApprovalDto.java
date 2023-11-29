package com.lunark.lunark.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewApprovalDto {
    private Long id;
    private boolean approved;
}
