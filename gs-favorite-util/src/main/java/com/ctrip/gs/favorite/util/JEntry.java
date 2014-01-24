package com.ctrip.gs.favorite.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JEntry<E> {
	public E value;
	public long cacheTime;

	public JEntry() {
	}

	public JEntry(E value) {
		this(value, System.currentTimeMillis());
	}

	public JEntry(E value, long cacheTime) {
		this.value = value;
		this.cacheTime = cacheTime;
	}

	public String toJson() {
		JSONObject json = null;
		if (value instanceof String) {
			json = new JSONObject();
			json.put("str", value);
		} else {
			json = (JSONObject) JSON.toJSON(value);
		}
		if (json != null) {
			json.put("cacheTime", cacheTime);
		}
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public static <T> JEntry<T> parseJson(String text, Class<T> clazz) {
		JSONObject json = JSON.parseObject(text);
		long cacheTime = json.getLongValue("cacheTime");
		json.remove("cacheTime");
		T value = null;
		if (clazz.getName().equals("java.lang.String")) {
			value = (T) json.get("str");
		} else {
			value = JSON.parseObject(json.toJSONString(), clazz);
		}
		// T value = JSON.toJavaObject(json, clazz);
		return new JEntry<T>(value, cacheTime);
	}
}
