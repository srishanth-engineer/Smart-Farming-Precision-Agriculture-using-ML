package com.SpringLogin.SpringLoginPage.service;

import com.SpringLogin.SpringLoginPage.model.WeedPredtictor;
import com.SpringLogin.SpringLoginPage.repository.WeedPred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeedService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_URL = "http://127.0.0.1:8000/predict-weed"; // Your FastAPI endpoint

    @Autowired
    private WeedPred weedRepo;
    public String predictWeed(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();

            WeedPredtictor weedPred = new WeedPredtictor(file.getOriginalFilename(), file.getContentType(), fileBytes);
            weedRepo.save(weedPred);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Wrap file as ByteArrayResource
            ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // Necessary to avoid boundary issues
                }
            };

            // Create multipart request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Send request to FastAPI
            ResponseEntity<String> response = restTemplate.exchange(FASTAPI_URL, HttpMethod.POST, requestEntity, String.class);

            return response.getBody();
        } catch (IOException e) {
            return "Error processing image: " + e.getMessage();
        }

    }
}
