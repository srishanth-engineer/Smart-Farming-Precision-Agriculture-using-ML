package com.SpringLogin.SpringLoginPage.service;

import com.SpringLogin.SpringLoginPage.model.CropRecommendation;
import com.SpringLogin.SpringLoginPage.repository.CropRec;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CropService {

    @Autowired
    private CropRec cropRepo;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_URL = "http://127.0.0.1:8000/predict";

    public String PredictBestCrop(CropRecommendation crop) {
        cropRepo.save(crop);
        try {
            // Creating JSON request body
//            System.out.println("Received Crop Data: N=" + crop.getN() + ", P=" + crop.getP() + ", K=" + crop.getK() + ", Temp=" + crop.getTemp());

            JSONObject requestJson = new JSONObject();
            requestJson.put("n", crop.getN());
            requestJson.put("p", crop.getP());
            requestJson.put("k", crop.getK());
            requestJson.put("temp", crop.getTemp());
            requestJson.put("humidity", crop.getHumidity());
            requestJson.put("ph", crop.getPh());
            requestJson.put("rainfall", crop.getRainfall());
            requestJson.put("soilType", crop.getSoilType());

            // Setting headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Creating request entity
            HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers);
            System.out.println(entity);
            // Sending POST request
            ResponseEntity<String> response = restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, entity, String.class);
            System.out.println("FastAPI Response: " + response.getBody());
            // Parsing response JSON
            JSONObject responseBody = new JSONObject(response.getBody());
            return responseBody.optString("recommended_crops", "No recommendation available");

        } catch (Exception e) {
            e.printStackTrace();  // Log error for debugging
            return "Error fetching recommendation: " + e.getMessage();
        }
    }
}
