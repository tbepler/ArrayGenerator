package jprobe.save;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import jprobe.services.Saveable;

public class SaveUtil {
	
	public static void save(File saveTo, Map<String,Saveable> saveables) throws SaveException{
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveTo));
			for(String tag : saveables.keySet()){
				ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				saveables.get(tag).save(byteOut);
				Tag header = new Tag(tag, byteOut.size());
				out.writeObject(header);
				byteOut.writeTo(out);
				byteOut.close();
			}
			out.close();
		} catch (FileNotFoundException e) {
			throw new SaveException(e);
		} catch (IOException e) {
			throw new SaveException(e);
		}
	}
	
	public static void load(File loadFrom, Map<String, Saveable> saveables) throws LoadException{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(loadFrom));
			boolean finished = false;
			while(!finished){
				try {
					//read a block of saved data
					Tag header = (Tag) in.readObject();
					String id = header.getId();
					int size = header.getNumBytes();
					if(saveables.containsKey(id)){
						//read the bytes into a new inputstream
						ByteArrayOutputStream bytes = new ByteArrayOutputStream();
						for(int i=0; i<size; i++){
							bytes.write(in.read());
						}
						ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes.toByteArray());
						bytes.close();
						//pass the new inputstream to the saveable for loading
						try{
							saveables.get(id).load(byteIn);
						} catch (Exception e){
							//an error occurred in the saveable while loading, ignore it and move on
						}
						byteIn.close();
					}else{
						//there is no saveable loaded to read this data, so skip it
						for(int i=0; i<size; i++){
							in.read();
						}
					}
				} catch (Exception e) {
					//an exception occurred, so loading is finished
					finished = true;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new LoadException(e);
		} catch (IOException e) {
			throw new LoadException(e);
		}
	}
}
