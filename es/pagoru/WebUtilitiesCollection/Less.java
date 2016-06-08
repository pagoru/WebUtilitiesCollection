package es.pagoru.WebUtilitiesCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lesscss.LessCompiler;
import org.lesscss.LessException;

/**
 * 
 * @author Pablo
 * @since 01/07/2016
 *
 */
public class Less {
	
	public static String FROM_PATH;
    public static String TO_PATH;
    
    private static List<Less> lesss = new ArrayList<Less>();
    
    public static void minifyAll(){
	    for(Less l : lesss){
		    l.minify();
	    }
    }
    public static void compileAll(){
	    for(Less l : lesss){
		    l.compile();
	    }
    }
    public static void saveAll(){
	    for(Less l : lesss){
		    l.save();
	    }
    }
    public static void loadAll(){
		File f = new File(FROM_PATH);
		lesss.clear();
		for (File actual : f.listFiles()) {
			lesss.add(new Less(
							FROM_PATH + actual.getName(), 
							TO_PATH + actual.getName().replace(".less", ".css").trim()
							));
		}
    }
	
	private String fromPath;
	private String toPath;
	
	private String text;
	
	private LessCompiler lc = new LessCompiler();
	
	public Less(String fromPath, String toPath){
		this.fromPath = fromPath;
		this.toPath = toPath;
		text = "";
		load();
	}
	public void save(){
		try {
			FileWriter writer = new FileWriter(toPath);
			writer.write(text);
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
	public void compile(){
		try {
			text = lc.compile(text);
		} catch (LessException e) {
			e.printStackTrace();
		}
	}
	public void minify(){
		try {
			text = Util.getTextFromWeb("input=" + text, "http://cssminifier.com/raw");
			Thread.sleep(WebUtilitiesCollection.API_WAIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
