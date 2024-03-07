package br.com.ti365.weatherCollector.service.interfaces;

import org.json.JSONObject;

public interface ApiConsumer {

	public JSONObject getData(String url);
	
}
