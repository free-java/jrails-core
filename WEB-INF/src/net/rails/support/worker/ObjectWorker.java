package net.rails.support.worker;


public final class ObjectWorker<O> {

	private O source;
	
	public ObjectWorker(O source) {
		super();
		this.source = source;
	}
	
	public boolean nil(){
		return source == null;
	}
	
	public boolean blank(){
		return nil() || source.toString().trim().equals("");
	}
	
	public O def(O defaultValue){
		if(blank())
			return defaultValue;
		else
			return source;
	}
	
	public O getSource(){
		return source;
	}

}
