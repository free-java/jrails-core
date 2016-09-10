package net.rails.active_record;

import java.net.InetAddress;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rails.support.Support;
import net.rails.support.worker.AbsConfigWorker;

public final class DBResource {
	
	private Logger log = LoggerFactory.getLogger(DBResource.class);
	
	private final static AbsConfigWorker configur = Support.config().getConfig();
	public final static String READER = "reader";
	public final static String WRITER = "writer";
	
	private String env;	
	private String model;
	private Adapter adapter;
	private Map<String,Object> dbcnf;	
	protected Map<String,Object> db;	
	
	@SuppressWarnings("unchecked")
	public DBResource(String model,String rw){
		super();
		this.model = model;
		db = configur.get("database");
		env = getEnv(model).toString();
		String adaName = null;
		if(Support.map(db).gets(env,rw) == null){
			dbcnf = Support.map(db).gets(env);
			adaName = Support.map(db).gets(env,"adapter");
		}else{
			dbcnf = Support.map(db).gets(env,rw);
			adaName = Support.map(db).gets(env,rw,"adapter");
		}
		try {
			Class<Adapter> cls = null;
			if(adaName == null){
				cls = Adapter.class;
			}else{
				cls = (Class<Adapter>) Class.forName(adaName);
			}
			adapter = (Adapter) cls.getConstructor(Map.class,String.class).newInstance(dbcnf,this.model);			
		} catch (Exception e) {
			log.error(e.getCause().getMessage(),e.getCause());
		}
	}
	
	public String getEnv(){
		return env;
	}
	
	public Map<String,Object> getDbcnf(){
		return dbcnf;
	}
	
	public Adapter getAdapter(){
		return adapter;
	}
	
	protected String getEnv(String model){
		String key = "env";
		Map<String,Object> modcnf = Support.config().getModels().get(model);
		Object value = null;
		if(modcnf != null && modcnf.containsKey(key)){
			value = modcnf.get(key);
			if(value instanceof Map){
				Map<String,String> o = (Map<String,String>)value;
				value = o.get(getHostname());
			}
		}else{
			value = Support.env().getEnv();
		}
		return (String)value;
	}
	
	private String getHostname() {
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			return addr.getHostName();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
	
}