package test;

import net.rails.support.Support;

public class Test {

	public static void main(String[] args) {
		System.out.println(Support.config("deployer").get("java"));
		
	}
	
}
