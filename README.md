# spring-wiremock-server
Standalone WireMock server in spring boot module

## Using

### Get currently registered stub mapping
GET request to http://host:port/__admin

### Set response
POST request to **http://host:port/__admin/mappings/new** with settings
```
{ "request": { "url": "/endpoint", "method": "GET" }, "response": { "status": 200, "body": "Here it is!\n" }}
```
**OR**
you can put a .json file with the same content into **src/main/resources/mappings**

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
