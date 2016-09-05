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
import net.rails.active_record.Database;
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

/**
 * Unit test for simple App.
 */
public class DBConnectionTest 
    extends TestCase
{
	public static AbsGlobal g;
	
	static{
		g = new GlobalUnit();
	}
	
    public DBConnectionTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( DBConnectionTest.class );
    }

    public void testApp() throws IOException
    {
    	Account a = null;
    	try{
        	g.setLocale("default");
        	a = new Account(g);
//        	a.useTransaction();;
        	a.setId(Support.code().id());
        	a.put("name","jack");
        	a.put("age","22");
        	a.onSave();
        	
        	Account a1  = new Account(g);
        	a1.useTransaction();
        	a1.setId(Support.code().id());
        	a1.put("name","jack 2");
        	a1.put("age","33");
        	a1.onSave();
        	
//        	int i = 1/0;
        	a.commit();
            assertTrue( true );
    	}catch(Exception e){
    		e.printStackTrace();
    		a.rollback();
    	}

    }
    
}