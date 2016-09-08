package net.rails.support.worker;

import java.util.List;
import java.util.Map;
import net.rails.support.Support;

@SuppressWarnings("unchecked")
public final class EnvWorker {
	
	public EnvWorker(){
		super();
	}

	public Map<String,Object> getRoot(){
		return Support.config().getConfig().get("env");
	}
	
	public Number getNumber(String key,Number def){
		return Support.number(getNumber(key)).def(def);
	}
	
	public Number getNumber(String key){
		return (Number)get(key);
	}
	
	public String getString(String key,String def){
		return Support.string(getString(key)).def(def);
	}
	
	public String getString(String key){
		return (String)get(key);
	}
	
	public Boolean getBoolean(String key,Boolean def){
		return (Boolean) Support.object(getBoolean(key)).def(def);
	}
	
	public Boolean getBoolean(String key){
		return (Boolean)get(key);
	}
	
	public String getApplicationCharset(){
		return getString("application_charset","UTF-8");
	}
	
	public String getServerCharset(){
		return getString("server_charset","ISO-8859-1");
	}
	
	public String getLocale(){
		return getString("locale","default");
	}
	
	public String getEnv(){
		return getString("env","production");
	}
	
	public String getPrefix(){
		return getString("prefix","");
	}
	
	public List<Object> getList(String key){
		return (List<Object>)get(key);
	}
	
	public Map<String,Object> getMap(String key){
		return (Map<String,Object>)get(key);
	}
	
	public <T extends Object> T get(String key) {
		return (T) getRoot().get(key);
	}
	
	public <T extends Object> T get(String key,T def) {
		T t = (T) getRoot().get(key);
		if(t == null){
			return def;
		}
		return t;
	}
	
	public Object gets(String keys){
		return Support.map(getRoot()).gets(keys);
	}
	
	public Object gets(String...keyarr){
		return Support.map(getRoot()).gets(keyarr);
	}

}
