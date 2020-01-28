# spring-wiremock-server
Standalone WireMock server in spring boot module

## Content
- **src/main/resources/__files**
- **src/main/resources/mappings** - files containing predefined mapping settings. Attention! Will be reset after executing the _/__admin/mappings/reset_ command.

## Rest Commands

### Get currently registered stub mapping
GET request to http://host:port/__admin

### Set response
POST request to **http://host:port/__admin/mappings/new** with settings
```
{ "request": { "url": "/endpoint", "method": "GET" }, "response": { "status": 200, "body": "Here it is!\n" }}
```

### Reset to default
POST empty request
```
http://<host>:<port>/__admin/mappings/reset
```

### Shutdown server
POST empty request
```
http://<host>:<port>/__admin/shutdown
```
