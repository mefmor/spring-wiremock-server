# spring-wiremock-server
Standalone WireMock server in spring boot module

## Using

### Get full documentation
Open in browser /__admin/docs/

### Get currently registered stub mapping
GET request to http://host:port/__admin

### Set response
POST request to **http://host:port/__admin/mappings/new** with settings
```json
{ "request": { "url": "/endpoint", "method": "GET" }, "response": { "status": 200, "body": "Here it is!\n" }}
```
**OR**
you can put a .json file with the same content into **src/main/resources/mappings**

### Getting all requests
Send GET to **http://host:port/__admin/requests**

### Criteria queries
Send POST to
- **http://host:port/__admin/requests/find** - to get full info about queries
- **http://host:port/__admin/requests/count** - to get count of queries

with body like
```json
{
    "method": "POST",
    "url": "/resource/to/count",
    "headers": {
        "Content-Type": {
            "matches": ".*/xml"
        }
    }
}
```

### Resetting the request journal
Send DELETE to **http://host:port/__admin/requests**

### Reset to default
**Important! In addition to the query log, it erases all stubs and mappings.**
POST empty request to **http://host:port/__admin/mappings/reset**

### Shutdown server
POST empty request **http://host:port/__admin/shutdown**
