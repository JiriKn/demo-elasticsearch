PUT /weather
{
    "mappings": {
      "dynamic": "false",
      "properties": {
        "avgTemp": {
          "type": "float"
        },
        "date": {
          "type": "date",
          "format": "yyyy-MM-dd||yyyy/MM/dd"
        },
        "stationName": {
          "type": "keyword"
        }
      }
    }
}