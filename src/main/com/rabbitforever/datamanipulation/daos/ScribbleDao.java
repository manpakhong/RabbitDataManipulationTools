package com.rabbitforever.datamanipulation.daos;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScribbleDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String className = this.getClass().getName();
	private Map<String, String> scribbleMap;
	
	public ScribbleDao() {
		init();
	}
	
	private void init() {
		if (scribbleMap == null) {
			scribbleMap = new HashMap<String, String>();
			scribbleMap.put("a", "z");
			scribbleMap.put("b", "y");
			scribbleMap.put("c", "x");
			scribbleMap.put("d", "w");
			scribbleMap.put("e", "v");
			scribbleMap.put("f", "u");
			scribbleMap.put("g", "t");
			scribbleMap.put("h", "s");
			scribbleMap.put("i", "r");
			scribbleMap.put("j", "q");
			scribbleMap.put("k", "p");
			scribbleMap.put("l", "o");
			scribbleMap.put("m", "n");
			scribbleMap.put("n", "m");
			scribbleMap.put("o", "l");
			scribbleMap.put("p", "k");
			scribbleMap.put("q", "j");
			scribbleMap.put("r", "i");
			scribbleMap.put("s", "h");
			scribbleMap.put("t", "g");
			scribbleMap.put("u", "f");
			scribbleMap.put("v", "e");
			scribbleMap.put("w", "d");
			scribbleMap.put("x", "c");
			scribbleMap.put("y", "b");
			scribbleMap.put("z", "a");
			
			scribbleMap.put("A", "G");
			scribbleMap.put("B", "F");
			scribbleMap.put("C", "E");
			scribbleMap.put("D", "D");
			scribbleMap.put("E", "C");
			scribbleMap.put("F", "B");
			scribbleMap.put("G", "A");
			
			scribbleMap.put("H", "N");
			scribbleMap.put("I", "M");
			scribbleMap.put("J", "L");
			scribbleMap.put("K", "K");
			scribbleMap.put("L", "J");
			scribbleMap.put("M", "I");
			scribbleMap.put("N", "H");
			
			scribbleMap.put("O", "Z");
			scribbleMap.put("P", "Y");
			scribbleMap.put("Q", "X");
			scribbleMap.put("R", "W");
			scribbleMap.put("S", "V");
			scribbleMap.put("T", "U");
			scribbleMap.put("U", "T");
			scribbleMap.put("V", "S");
			scribbleMap.put("W", "R");
			scribbleMap.put("X", "Q");
			scribbleMap.put("Y", "P");
			scribbleMap.put("Z", "O");
			
			scribbleMap.put("1", "2");
			scribbleMap.put("2", "1");
			scribbleMap.put("3", "4");
			scribbleMap.put("4", "3");
			
			scribbleMap.put("5", "7");
			scribbleMap.put("6", "6");
			scribbleMap.put("7", "5");
			
			scribbleMap.put("8", "9");
			scribbleMap.put("9", "8");
			scribbleMap.put("0", "0");
			
		}
	}
	
	public String getScribble(String strIn) {
		String rtn = scribbleMap.get(strIn);
		return rtn;
	}
	
	public Map<String, String> getScribbleMap(){
		return scribbleMap;
	}
}
