package com.SpringLogin.SpringLoginPage.service;


import com.SpringLogin.SpringLoginPage.model.CropRecNutrients;
import com.SpringLogin.SpringLoginPage.repository.CropRecNutrientss;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CropRecNutrientsService {

    @Autowired
    CropRecNutrientss cropRecNutrientssRepo;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_URL = "http://127.0.0.1:8000/predictCropNuts";

    public String findBestCrop(CropRecNutrients cropRecNutrients) {

        cropRecNutrientssRepo.save(cropRecNutrients);
        System.out.println("Received Crop Data: " +
                "N=" + cropRecNutrients.getN() +
                ", P=" + cropRecNutrients.getP() +
                ", K=" + cropRecNutrients.getK() +
                ", pH=" + cropRecNutrients.getPh() +
                ", EC=" + cropRecNutrients.getEc() +
                ", S=" + cropRecNutrients.getS() +
                ", Cu=" + cropRecNutrients.getCu() +
                ", Fe=" + cropRecNutrients.getFe() +
                ", Mn=" + cropRecNutrients.getMn() +
                ", Zn=" + cropRecNutrients.getZn() +
                ", B=" + cropRecNutrients.getB());

        try{
            JSONObject requestJson = new JSONObject();
            requestJson.put("n", cropRecNutrients.getN());
            requestJson.put("p", cropRecNutrients.getP());
            requestJson.put("k", cropRecNutrients.getK());
            requestJson.put("ph", cropRecNutrients.getPh());
            requestJson.put("ec", cropRecNutrients.getEc());
            requestJson.put("s", cropRecNutrients.getS());
            requestJson.put("cu", cropRecNutrients.getCu());
            requestJson.put("fe", cropRecNutrients.getFe());
            requestJson.put("mn", cropRecNutrients.getMn());
            requestJson.put("zn", cropRecNutrients.getZn());
            requestJson.put("b", cropRecNutrients.getB());

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


