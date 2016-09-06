package net.rails.tpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import net.rails.Define;
import net.rails.ext.AbsGlobal;
import net.rails.ext.IndexMap;
import net.rails.support.Support;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取模板文件内容类
 * @author Jack
 *
 */
public class TplText {

	private final static Logger log = LoggerFactory.getLogger(TplText.class);
	private AbsGlobal g;
	private String name;
	private StringBuffer text;
	private final Map<String,Object> params = new IndexMap<String,Object>();
	
	static{
		initViewPath();
	}
	
	/**
	 * 构造方法。
	 * @param name 标识名称一般用在日志记录
	 * @param g
	 * @param text
	 */
	public TplText(String name,AbsGlobal g,StringBuffer text) {
		super();
		this.g = g;
		this.name = name;
		this.text = text;
	}
	
	/**
	 * 构造方法。
	 * @param name 标识名称一般用在日志记录
	 * @param g
	 * @param tplFile 模板文件路径
	 * @throws IOException
	 */
	public TplText(String name,AbsGlobal g,String tplFile) throws IOException {
		super();
		this.g = g;
		this.name = name;
		String s = FileUtils.readFileToString(new File(Define.VIEW_PATH + "/" + tplFile),Support.env().getApplicationCharset());
		this.text = new StringBuffer(s);
	}
	
	/**
	 * 构造方法。
	 * @param name 标识名称一般用在日志记录
	 * @param g
	 * @param text 模板文件内容
	 * @param params 模板参数
	 */
	public TplText(String name,AbsGlobal g,StringBuffer text,Map<String,Object> params) {
		super();
		this.g = g;
		this.name = name;
		this.text = text;
		if(params != null)
			this.params.putAll(params);
	}
	
	/**
	 * 构造方法。
	 * @param name 标识名称一般用在日志记录
	 * @param g
	 * @param tplFile 模板文件路径
	 * @param params 模板参数
	 * @throws IOException
	 */
	public TplText(String name,AbsGlobal g,String tplFile,Map<String,Object> params) throws IOException {
		super();
		this.g = g;
		this.name = name;
		String s = FileUtils.readFileToString(new File(tplFile),Support.env().getApplicationCharset());
		this.text = new StringBuffer(s);
		if(params != null)
			this.params.putAll(params);
	}

	/**
	 * 获取标识名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置标识名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 设置模板内容
	 * @param text
	 */
	public void setText(StringBuffer text){
		this.text = text;
	}

	/**
	 * 获取模板内容
	 * @return
	 */
	public StringBuffer getText() {
		return text;
	}
	
	/**
	 * 模板参数
	 * @return
	 */
	public Map<String,Object> params(){
		return params;
	}
	
	private static void initViewPath() {	
		URL url = null;
		String path = null;
		try {
			if(Define.VIEW_PATH == null){
			    url = Thread.currentThread().getContextClassLoader().getResource("view/");
			    if(url != null){
					path = url.getFile();	
				}else{
					path = new File(System.getProperty("user.dir") + "/view/").getAbsolutePath();
				}
				Define.VIEW_PATH = URLDecoder.decode(path,System.getProperty("file.encoding"));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

}
