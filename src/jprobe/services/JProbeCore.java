package jprobe.services;

import java.io.File;

import org.osgi.framework.Bundle;


public interface JProbeCore {
	
	public enum Mode{
		COMMAND,
		GUI;
		
		@Override
		public String toString(){
			switch(this){
			case COMMAND:
				return "command";
			case GUI:
				return "gui";
			default:
				return null;
			}
		}
		
	}
	
	public Mode getMode();
	
	public String getName();
	public String getVersion();
	
	public String getPreferencesDir();
	public String getLogsDir();
	
	public void addCoreListener(CoreListener listener);
	public void removeCoreListener(CoreListener listener);
	
	public void shutdown();
	
	public DataManager getDataManager();
	public FunctionManager getFunctionManager();
	
	public void save(File toFile);
	public void load(File fromFile);
	
	public void addSaveable(Saveable add, Bundle bundle);
	public void removeSaveable(Saveable remove, Bundle bundle);
	
}
