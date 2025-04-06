package com.SpringLogin.SpringLoginPage.service;

import com.SpringLogin.SpringLoginPage.model.Fertilizer;
import com.SpringLogin.SpringLoginPage.repository.FertilizerRec;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FertilizerService {

    @Autowired
    private FertilizerRec fertilizerRecRepo;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_URL="http://127.0.0.1:8000/predict-fertilizer";

    public String findBestFertilizer(Fertilizer fertilizer) {

        fertilizerRecRepo.save(fertilizer);
        System.out.println("nitrogen "+fertilizer.getNitrogen()+" phosphorous "+fertilizer.getPhosphorous()+
                        " potassium "+fertilizer.getPotassium()+ " temperature "+fertilizer.getTemperature()+
                        " soilType "+fertilizer.getSoil_type()+" cropType "+fertilizer.getCrop_type() +
                        " humidity "+fertilizer.getHumidity() + " moisture "+fertilizer.getMoisture()
                );
        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("temperature",fertilizer.getTemperature());
            requestJson.put("humidity",fertilizer.getHumidity());
            requestJson.put("moisture",fertilizer.getMoisture());
            requestJson.put("soil_type",fertilizer.getSoil_type());
            requestJson.put("crop_type",fertilizer.getCrop_type());
            requestJson.put("nitrogen",fertilizer.getNitrogen());
            requestJson.put("phosphorous",fertilizer.getPhosphorous());
            requestJson.put("potassium",fertilizer.getPotassium());

            //Setting Http Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            //Creating Http Entity request entity
            HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers);
            System.out.println(entity);

            //Creating a POST request
            ResponseEntity<String> response = restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, entity, String.class);
            System.out.println("FastAPI Response: " + response.getBody());

            JSONObject responseBody = new JSONObject(response.getBody());

            if (responseBody.has("error")) {
                return "FastAPI Error: " + responseBody.getString("error");
            }
            return responseBody.optString("recommended_fertilizer", "No recommendation available");
//            return responseBody.optString("recommended_crops", "No recommendation available");
        }catch (Exception e) {
            e.printStackTrace();  // Log error for debugging
            return "Error fetching recommendation: " + e.getMessage();
        }

    }
}
