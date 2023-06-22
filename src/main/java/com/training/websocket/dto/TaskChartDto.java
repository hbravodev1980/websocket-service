package com.training.websocket.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskChartDto {

    private List<String> status;
    private List<Long> quantity;

}
