package net.rails.support.worker;


public class ObjectWorker<O> {

	private O target;
	
	public ObjectWorker(O target) {
		super();
		this.target = target;
	}
	
	public boolean nil(){
		return target == null;
	}
	
	public boolean blank(){
		return nil() || target.toString().trim().equals("");
	}
	
	public O def(O def){
		if(blank())
			return def;
		else
			return target;
	}

}
