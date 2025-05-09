package com.jamk.pet.food.finder.api.admin.controller;

import com.jamk.pet.food.finder.api.admin.model.ProductStatisticsDto;
import com.jamk.pet.food.finder.api.admin.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR', 'USER')")
    @GetMapping
    public ResponseEntity<ProductStatisticsDto> getStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}
