package test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;

import app.model.Account;
import net.rails.active_record.DBResource;
import net.rails.ciphertext.Ciphertext;
import net.rails.ciphertext.Ciphertext.DESWorker;
import net.rails.ciphertext.Ciphertext.ThreeDESWorker;
import net.rails.ciphertext.exception.CiphertextException;
import net.rails.ext.AbsGlobal;
import net.rails.support.Support;
//import net.rails.log.Log;
import net.rails.tpl.Tpl;
import net.rails.tpl.TplText;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TplTest 
    extends TestCase
{
	public static AbsGlobal g;
	
	static{
		g = new GlobalUnit();
	}
	
    public TplTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( TplTest.class );
    }

    public void testChinese() throws IOException
    {
    	try{
        	g.setLocale("default");
        	TplText text = new TplText("chinese", g,"chinese.tpl.html");
        	Tpl tpl = new Tpl(g,text);
        	System.out.println(tpl.generate());
            assertTrue( true );
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
    
}
