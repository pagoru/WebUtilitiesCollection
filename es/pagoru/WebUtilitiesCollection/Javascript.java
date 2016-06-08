package es.pagoru.WebUtilitiesCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Pablo
 * @since 01/07/2016
 *
 */
public class Javascript {
	
	public static String FROM_PATH;
	public static String TO_PATH;
	
	private static List<Javascript> jss = new ArrayList<Javascript>();
	
	@Deprecated
	public static void minifyAll(){
	    for(Javascript l : jss){
		    l.minify();
	    }
    }
    public static void saveAll(){
	    for(Javascript l : jss){
		    l.save();
	    }
    }
    public static void loadAll(){
		File f = new File(FROM_PATH);
		jss.clear();
		for (File actual : f.listFiles()) {
			jss.add(new Javascript(
							FROM_PATH + actual.getName(), 
							TO_PATH + actual.getName()
							));
			
		}
    }
	
	private String fromPath;
	private String toPath;
	
	private String text;
	
	public Javascript(String fromPath, String toPath){
		this.fromPath = fromPath;
		this.toPath = toPath;
		text = "";
		load();
	}
	public void save(){
		try {
			FileWriter writer = new FileWriter(toPath);
			writer.append(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void load(){
		try {
			File f = new File(fromPath);
			Scanner s = new Scanner(new FileInputStream(f));
			while(s.hasNext()){
				text += s.nextLine() + "\n";
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void minify(){
		try {
			text = Util.getTextFromWeb("input=" + text, "http://javascript-minifier.com/raw");
			Thread.sleep(WebUtilitiesCollection.API_WAIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
