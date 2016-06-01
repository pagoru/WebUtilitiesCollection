package es.pagoru.WebUtilitiesCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * 
 * @author Pablo
 * @since 01/07/2016
 *
 */
public class WebUtilitiesCollection{
	
	static long API_WAIT = 500;
	private static final String DEFAULT_CONFIG =
	    "#localización de arcvihos less \n" +
	    "less = C:\\less\\ \n" +
	    "#localizacion donde guardar los archivos css \n" +
	    "min-css = C:\\min-css\\ \n" +
	    "# \n" + 
	    "#localización de arcvihos js \n" +
	    "js = C:\\js\\ \n" +
	    "#localizacion donde guardar los archivos js \n" +
	    "min-js = C:\\min-js\\ \n";
	
    public static void main( String[] args ){
	    
	    loadConfig();
	    
	    boolean loop = true;
	    while(loop){
		    String current = read();
		    
		    switch (current) {
			case "all":
				System.out.println("Saving and minifing css & js.");
				Javascript.loadAll();
				Javascript.minifyAll();
				Javascript.saveAll();
				Less.loadAll();
				Less.compileAll();
				Less.minifyAll();
				Less.saveAll();
				System.out.println("css & js saved and minified.");
				break;
			
			case "save":
				System.out.println("Saving css & js.");
				Javascript.loadAll();
				Javascript.saveAll();
				Less.loadAll();
				Less.compileAll();
				Less.saveAll();
				System.out.println("css & js saved.");
				break;
				
			case "js":
				System.out.println("Saving js.");
				Javascript.loadAll();
				Javascript.minifyAll();
				Javascript.saveAll();
				System.out.println("js saved.");
				break;
				
			case "css":
				System.out.println("Saving css.");
				Less.loadAll();
				Less.compileAll();
				Less.minifyAll();
				Less.saveAll();
				System.out.println("css saved.");
				break;
			
			case "load":
				System.out.println("Loading.");
				loadConfig();
				System.out.println("Loaded.");
				break;
				
			case "stop":
				System.out.println("Stoping.");
				loop = false;
				break;
				
			default:
				break;
			}
		    
	    }
	    
    }
    private static void createDefaultConfigFile(File f){
	    try{
	        FileWriter writer = new FileWriter(f);
	        writer.append(DEFAULT_CONFIG);
	        writer.close();
	    } catch(Exception e){
		    e.printStackTrace();
	    }
    }
    private static void loadConfig(){
		try {
			File f = new File("./config.txt");
			if(!f.exists()) createDefaultConfigFile(f);
		
			Scanner sc = new Scanner(new FileInputStream(f));
			while(sc.hasNext()){
				String line = sc.nextLine();
				
				if(line.startsWith("#")) continue;
				if(line.startsWith("less = ")){
					Less.FROM_PATH = line.replaceFirst("less =", "").trim();
				} else if(line.startsWith("min-css = ")){
					Less.TO_PATH = line.replaceFirst("min-css =", "").trim();
				} else if(line.startsWith("js = ")){
					Javascript.FROM_PATH = line.replaceFirst("js =", "").trim();
				} else if(line.startsWith("min-js = ")){
					Javascript.TO_PATH = line.replaceFirst("min-js =", "").trim();
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Less.loadAll();
		Javascript.loadAll();
    }
    
    private static Scanner sc = new Scanner(System.in);
	private static String read(){
		return sc.nextLine();
	}
    
}
