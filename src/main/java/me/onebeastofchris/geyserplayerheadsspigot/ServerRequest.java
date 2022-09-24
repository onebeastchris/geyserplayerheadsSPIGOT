package me.onebeastofchris.geyserplayerheadsspigot;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class ServerRequest{
  /**  public JsonObject webRequest(String pUrl) {
        var client = newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(pUrl))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .build();
        HttpResponse<String> resp;
        try {
            resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Gson().fromJson(resp.body(), JsonObject.class);
    } **/

  public JsonObject webRequest(String pUrl) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(pUrl);
    try {
      HttpResponse httpresponse = httpclient.execute(httpget);
      return new Gson().fromJson(String.valueOf(httpresponse.getEntity().getContent()), JsonObject.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}