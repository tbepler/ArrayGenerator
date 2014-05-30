package jprobe;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.ImageIcon;

public class Constants {
	
	public static final String NAME = "JProbe";
	public static final String VERSION = "v0.5.1";
	public static final String AUTHOR = "Tristan Bepler";
	
	public static final String URL_PATH_SEPARATOR = "/";
	
	public static final String JAR_URL = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	public static final String JAR_DIR = JAR_URL.substring(0, JAR_URL.lastIndexOf(URL_PATH_SEPARATOR));
	
	public static final String PREFERENCES_DIR = JAR_DIR + File.separator + "preferences";

	public static final String CONFIG_FILE = PREFERENCES_DIR + File.separator + "jprobe.pref";
	
	public static final String LOG_DIR = JAR_DIR + File.separator + "logs";
	
	public static final String RESOURCES_PATH = "/jprobe/resources";
	
	public static final String INDEXED_FUNC_REGEX = "^.+\\[\\d+\\]$";
	public static final String SHORT_FLAG_PREFIX = "-";
	public static final String LONG_FLAG_PREFIX = "--";
	
	public static final String HELP_LONG_FLAG = "help";
	public static final String HELP_SHORT_FLAG = "h";
	public static final String HELP_REGEX = "(" + SHORT_FLAG_PREFIX + HELP_SHORT_FLAG + ")|(" + LONG_FLAG_PREFIX + HELP_LONG_FLAG + ")";
	
	public static final String HELP_FILE_PATH = RESOURCES_PATH + "/help.txt";
	public static final String HELP_MESSAGE = readHelpMessage();
			
	private static String readHelpMessage(){
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(Constants.class.getResourceAsStream(HELP_FILE_PATH)));
			String msg = reader.readLine();
			if(msg == null){
				reader.close();
				return "";
			}
			String line;
			while((line = reader.readLine()) != null){
				msg +=  "\n" + line;
			}
			reader.close();
			return msg;
		} catch (Exception e){
			System.out.println("Error on help file");
			e.printStackTrace();
			return "";
		}
	}
	
	public static final String ARG_INTERACTIVE_MODE = "-interactive";
	public static final String ARG_COMMAND_MODE = "-command";
	
	public static final Collection<String> RESERVED_COMMANDS = generateReservedCommands();
	
	private static Collection<String> generateReservedCommands(){
		Collection<String> res = new HashSet<String>();
		res.add(ARG_INTERACTIVE_MODE);
		res.add(ARG_COMMAND_MODE);
		res.add(LONG_FLAG_PREFIX+HELP_LONG_FLAG);
		return Collections.unmodifiableCollection(res);
	}
	
	public static final String X_PATH = RESOURCES_PATH+"/x.gif";
	public static final String X_HIGHLIGHTED_PATH = RESOURCES_PATH+"/xhighlight.gif";
	public static final String X_CLICKED_PATH = RESOURCES_PATH+"/xclicked.gif";
	
	public static final ImageIcon X_ICON = new ImageIcon(Constants.class.getResource(X_PATH));
	public static final ImageIcon X_HIGHLIGHTED_ICON = new ImageIcon(Constants.class.getResource(X_HIGHLIGHTED_PATH));
	public static final ImageIcon X_CLICKED_ICON = new ImageIcon(Constants.class.getResource(X_CLICKED_PATH));
	
}
