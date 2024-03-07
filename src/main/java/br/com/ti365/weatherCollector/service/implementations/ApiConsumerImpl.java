package br.com.ti365.weatherCollector.service.implementations;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ti365.weatherCollector.service.interfaces.ApiConsumer;

@Service
public class ApiConsumerImpl implements ApiConsumer{

	@Override
	public JSONObject getData(String url) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
		HttpResponse<String> response = null;
		
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		JSONObject responseJson = new JSONObject(response.body().toString());
		return responseJson;
	}

}
