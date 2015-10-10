package com.birdbrain2.plus;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

public class DataTaskParams {
    HttpClient client;
    HttpPost post;

    DataTaskParams(HttpClient client, HttpPost post) {
        this.client = client;
        this.post = post;
    }
}
