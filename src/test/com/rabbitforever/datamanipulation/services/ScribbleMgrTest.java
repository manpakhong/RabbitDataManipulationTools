package com.rabbitforever.datamanipulation.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ScribbleMgrTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			String in = "cmsapps";
			String in2 = "dbau2018";
					
			String out = null;
			String out2 = null;
			
			
			ScribbleMgr scribbleMgr = new ScribbleMgr();
			out = scribbleMgr.scribbleWords(in);
			
			out2 = scribbleMgr.scribbleWords(in2);
			
			
			System.out.println("in=" + in + ",out=" + out);
			System.out.println("in2=" + in2 + ",out2=" + out2);
			
			in = scribbleMgr.scribbleWords(out);
			
			in2 = scribbleMgr.scribbleWords(out2);
			
			
			
			System.out.println("out=" + out + ",in=" + in);
			System.out.println("out2=" + out2 + ",in2=" + in2);
		} catch (Exception e) {
			fail("Exception" + e);
		}

	}

}
