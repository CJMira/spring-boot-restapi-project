package com.sbproject.gameplatform.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {

    private Long id;

    private String title;

    private CompanyDTO company;

}
