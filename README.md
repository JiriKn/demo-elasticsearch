# Weather Service Demo App

## Input
Run `com.jk.elastic.DemoElasticsearchApplication`. All args not considered as parameters are used as input file paths.

### Index name
As configuration in `src/main/resources/application.properties`. You can override it as usual config value (for example with `--elasticsearch.index.name=<your-index>`)

### Mappings
used for this demo:
```json
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
```
It is stored in `weather-mapping.json` file as well.