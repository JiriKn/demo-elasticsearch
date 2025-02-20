package com.jk.elastic.controller;

import com.jk.elastic.model.WeatherData;
import com.jk.elastic.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class WeatherDataController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/searchByStationName/{stationName}")
    public List<WeatherData> searchByStationName(@PathVariable String stationName) throws IOException {
        return elasticsearchService.searchByStationName(stationName);
    }

    @GetMapping("/searchByDateRange")
    public List<WeatherData> searchByDateRange(@RequestParam String startDate, @RequestParam String endDate) throws IOException {
        return elasticsearchService.searchByDateRange(startDate, endDate);
    }
}
