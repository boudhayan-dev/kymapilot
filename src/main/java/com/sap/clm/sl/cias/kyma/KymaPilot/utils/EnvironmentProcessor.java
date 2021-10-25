package com.sap.clm.sl.cias.kyma.KymaPilot.utils;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EnvironmentProcessor {

    public JSONObject getCredentials(String servicePrefix){
        JSONObject result = new JSONObject();
        Map<String, String> env = System.getenv();

        for(Map.Entry<String, String> envVal : env.entrySet()){
            if(envVal.getKey().startsWith(servicePrefix)){
                if(this.isJsonObject(envVal.getValue())){
                    // iterate over the JSON Object and its keys in the result
                    JSONObject jsonConfig = new JSONObject(envVal.getValue());
                    for(int i =0; i< jsonConfig.names().length(); i++){
                        String key = jsonConfig.names().getString(i);
                        result.put(key, jsonConfig.getString(key));
                    }
                } else{
                    result.put(envVal.getKey().split(servicePrefix)[1], envVal.getValue());
                }
            }
        }
        return result;
    }


    private boolean isJsonObject(String config){
        try {
            new JSONObject(config);
        } catch(Exception e){
            return false;
        }
        return true;
    }




}
