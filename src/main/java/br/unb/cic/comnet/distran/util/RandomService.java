package br.unb.cic.comnet.distran.util;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class RandomService {
	
	private static RandomService service;
	
	public synchronized static RandomService getInstance() {
		if (service == null) {
			service = new RandomService();
		}
		return service;
	}
	
	private Map<String, Random> generators;
	
	private RandomService() {
		generators = new ConcurrentHashMap<String, Random>();
	}
	
	public Random getClassGenerator(@SuppressWarnings("rawtypes") Class clazz) {
		if (!hasGenerator(clazz.getName())) {
			createGenerator(clazz.getName());
		}
		return getGenerator(clazz.getName());
	}	
	
	public void createGenerator(Long seed, String name) {
		createGenerator(name);
		getGenerator(name).setSeed(seed);
	}
	
	public void createGenerator(String name) {
		generators.put(name, new SecureRandom());		
	}
	
	public Random getGenerator(String name) {
		return generators.get(name);
	}
	
	public boolean hasGenerator(String name) {
		return generators.containsKey(name);
	}
}
