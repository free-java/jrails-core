package net.rails.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientFile implements Serializable{
	
	private String name;
	private byte[] data;
	
	public ClientFile(String name,InputStream is) throws IOException{
		super();
		this.name = name;
		if(is != null){
			byte[] buf = new byte[1024];
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			int len = -1;
			while((len = is.read(buf)) > -1){
				os.write(buf,0,len);
				os.flush();
			}
			data = os.toByteArray();
			os.close();
			if(is != null)
				is.close();
		}
	}

	public String getName() {
		return name;
	}

	public byte[] getData() {
		return data;
	}
	
	@Override
	public String toString(){
		return "[Name:" + name +",Size: " + data.length +"]";
	}

}
