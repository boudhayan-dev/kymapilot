package com.sap.clm.sl.cias.kyma.KymaPilot.controller;

import com.sap.clm.sl.cias.kyma.KymaPilot.config.UserInfo;
//import com.sap.clm.sl.cias.kyma.KymaPilot.repository.EmployeeRepository;
//import com.sap.clm.sl.cias.kyma.KymaPilot.repository.EmployeeRepository;
import com.sap.clm.sl.cias.kyma.KymaPilot.utils.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class GenericController {

    private static final Logger oLogger = LoggerFactory.getLogger(GenericController.class);

//    @Autowired
//    private EmployeeRepository employeeRepository;

    @Autowired
    private UserInfo userInfo;

    @Autowired
    CustomWebClient webclient;

    @Autowired
    CustomWebClient httpClient;

    @Autowired
    EnvironmentProcessor environmentProcessor;
//
//    @GetMapping(value = "crud/list", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('crud')")
//    public ResponseEntity<List<Employee>> getEmployeeList() {
//        try {
//            List<Employee> employees = employeeRepository.findAll();
//            return new ResponseEntity<>(employees, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PreAuthorize("hasAuthority('crud')")
//    @PostMapping(value = "crud/save", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> validateCEMetadata(@RequestBody Employee employee) {
//        try {
//            JSONObject response = new JSONObject();
//            response.put("Status","success");
//            employeeRepository.save(employee);
//            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
//        } catch (Exception e) {
//            oLogger.error(e.getMessage());
//            JSONObject response = new JSONObject();
//            response.put("Status","error");
//            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @GetMapping("env")
    public  ResponseEntity<Map<String, String>> test(){
        Map<String, String> env = System.getenv();
        return new ResponseEntity<>(env, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("token")
    public ResponseEntity<String> returnUserTokenInfo() {
        oLogger.info("Retreiving user token");
        return new ResponseEntity<>(userInfo.getToken() , HttpStatus.OK);
    }

    @GetMapping(value = "destinations", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('dest')")
    public ResponseEntity<String> getDestinations() {
        try {
            // Get Access Token

            JSONObject cred = environmentProcessor.getCredentials("destination_");
            if(null==cred || cred.isEmpty()){
                return new ResponseEntity<>("Could not retrieve credentials", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String url = cred.getString("url");
            String path = "/oauth/token";
            String credentials = cred.getString("clientid") + ":" + cred.getString("clientsecret");


            Map<String, Object> params = new HashMap<>();
            params.put("grant_type", "client_credentials");

            Map<String, String> headers = new HashMap<>();
            headers.put(HttpHeaders.AUTHORIZATION,
                    "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes()));
            CustomHTTPRequest request = new CustomHTTPRequest();
            request.setBaseURL(url);
            request.setPath(path);
            request.setParams(params);
            request.setHeaders(headers);
            request.setHttpMethod(HttpMethod.GET);
            request.setReturnClassType(String.class);
            request.setName("destination");

            String token = this.retrieveToken(request,"destination", "access_token");


            headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);
            request = new CustomHTTPRequest();
            request.setBaseURL(cred.getString("uri"));
            request.setPath("/destination-configuration/v1/subaccountDestinations");
            request.setReturnClassType(String.class);
            request.setName("Get all the Destinations");
            request.setHeaders(headers);

            oLogger.info("Sending request to the server to fetch all the destinations for tenant: CFCIAS");
            Mono<?> result = httpClient.retrieve(request);
            String response = (String) result.block();
            oLogger.info("Response successfully recieved from server");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            oLogger.error(e.getMessage());
            return new ResponseEntity<>("Error getting destinations", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private String retrieveToken(CustomHTTPRequest request, String configName, String accessToken) throws CustomHTTPException {
        Mono<?> result;
        result = webclient.retrieve(request);
        JSONObject response = new JSONObject((String) result.block());
        if (response.has(accessToken)) {
            return response.getString(accessToken);
        } else {
            String error = String.format("Error retrieving %s for service - %s", accessToken, configName);
            oLogger.error(error);
            return "";
        }
    }

}
