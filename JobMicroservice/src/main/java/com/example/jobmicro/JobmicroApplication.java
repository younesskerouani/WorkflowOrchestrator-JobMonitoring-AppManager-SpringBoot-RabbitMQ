package com.example.jobmicro;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.repository")

public class JobmicroApplication {

    public static void main(String[] args) throws IOException {

            SpringApplication.run(JobmicroApplication.class, args);

        int i=1;
        while(i > 0 ) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{}");
            Request request = new Request.Builder()
                    .url("http://localhost:9292/job/rwrw?EventType=CREATE")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

        }

//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"jobType\" :\r\n   { \r\n       \"S\" : \"like\"       \r\n    }\r\n}");
//        Request request = new Request.Builder()
//                .url("http://localhost:9292/job/jobs?startrow=0&limit=100")
//                .method("POST", body)
//                .addHeader("startrow", "120")
//                .addHeader("limit", "220")
//                .addHeader("Content-Type", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();


    }


}
