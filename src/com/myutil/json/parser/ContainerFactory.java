package com.myutil.json.parser;


import java.util.List;

import com.myutil.json.JsonObject;

/**
 * Container factory for creating containers for JSON object and JSON array.
 * 
 * @see JSONParser#parse(java.io.Reader, ContainerFactory)
 *
 */
interface ContainerFactory {
	/**
	 * @return A Map instance to store JSON object, or null if you want to use JsonObject.
	 */
	JsonObject createObjectContainer();
	
	/**
	 * @return A List instance to store JSON array, or null if you want to use JsonArray.
	 */
	List creatArrayContainer();
}
