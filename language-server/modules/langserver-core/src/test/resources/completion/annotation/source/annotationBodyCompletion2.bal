import ballerina/http;

@http:ServiceConfig {
    c
}
service helloService on new http:Listener(8080) {
    resource function sayHello(http:Caller caller, http:Request request) {
        
    }
}