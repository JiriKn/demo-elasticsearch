package com.jk.elastic.service;

import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.jk.elastic.model.WeatherData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticsearchService {

    private final ElasticsearchClient client;

    @Value("${elasticsearch.index.name}")
    private String indexName;

    @Value("${elasticsearch.bulk.size}")
    private int bulkSize;

    public ElasticsearchService(ElasticsearchClient client) {
        this.client = client;
    }

    private WeatherData toWeatherData(CSVRecord record) {
        return new WeatherData(record.get(0), record.get(1), Float.parseFloat(record.get(2).trim()));
    }

    public void ingestCsv(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            int counter = 0;
            BulkRequest.Builder br = new BulkRequest.Builder();
            for (CSVRecord record : csvParser) {
                WeatherData weatherData = toWeatherData(record);
                br.operations(op -> op
                        .index(idx -> idx
                                .index(indexName)
                                .document(weatherData)
                        )
                );
                counter++;
                if(counter%bulkSize == 0) {
                    client.bulk(br.build());
                    br = new BulkRequest.Builder();
                }
            }
        }
    }

    public List<WeatherData> searchByStationName(String stationName) throws IOException {
        SearchRequest request = SearchRequest.of(s -> s
                .index(indexName)
                .query(q -> q
                        .term(t -> t
                                .field("stationName")
                                .value(stationName)
                        )
                )
        );

        SearchResponse<WeatherData> response = client.search(request, WeatherData.class);
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public List<WeatherData> searchByDateRange(String startDate, String endDate) throws IOException {
        SearchRequest request = SearchRequest.of(s -> s
                .index(indexName)
                .query(q -> q
                        .range(r -> r.term(t ->
                                t.field("date")
                                .gte(startDate)
                                .lte(endDate)
                                )
                        )
                )
        );

        SearchResponse<WeatherData> response = client.search(request, WeatherData.class);
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public String getIndexName() {
        return indexName;
    }
}