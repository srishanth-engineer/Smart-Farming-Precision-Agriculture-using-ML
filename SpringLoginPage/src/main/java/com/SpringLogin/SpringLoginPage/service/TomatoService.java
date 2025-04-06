package com.SpringLogin.SpringLoginPage.service;

import com.SpringLogin.SpringLoginPage.model.Maize;
import com.SpringLogin.SpringLoginPage.model.Tomato;
import com.SpringLogin.SpringLoginPage.repository.MaizeRepo;
import com.SpringLogin.SpringLoginPage.repository.TomatoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TomatoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_URL = "http://127.0.0.1:8000/predict-tomato";

    @Autowired
    private TomatoRepo tomatoRepo;
    public String predictTomatoDisease(MultipartFile file) {
        try {

            byte[] fileBytes = file.getBytes();
            System.out.println("Entered maize service");
            // Save the file in the H2 database
            Tomato tomato = new Tomato(file.getOriginalFilename(), file.getContentType(), fileBytes);
            tomatoRepo.save(tomato);

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
            System.out.println(response);
            return response.getBody();
        } catch (IOException e) {
            return "Error processing image: " + e.getMessage();
        }

    }
}
