/*
 * Created on Aug 11, 2004
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

/**
 * @author Matthew D. Hicks
 */
public class JARClassLoader extends URLClassLoader {
	protected File _file;
	protected JarFile jar;
	
	public JARClassLoader(File file) throws MalformedURLException, IOException {
		super(new URL[] {file.toURL()});
		this._file = file;
		this.jar = new JarFile(file);
	}
	
	public Class[] getClasses() throws ClassNotFoundException {
		ArrayList list = new ArrayList(500);
		Enumeration e = jar.entries();
		JarEntry entry;
		while (e.hasMoreElements()) {
			entry = (JarEntry)e.nextElement();
			if ((!entry.isDirectory()) && (entry.getName().endsWith(".class"))) {
				list.add(entry.getName().replaceAll("/", ".").substring(0, entry.getName().length() - 6));
			}
		}
		Class[] classes = new Class[list.size()];
		for (int i = 0; i < list.size(); i++) {
			classes[i] = this.loadClass((String)list.get(i));
		}
		
		return classes;
	}
	
	public static void main(String[] args) throws Exception {
		File file = new File("myclasses.jar");
		JARClassLoader loader = new JARClassLoader(file);
		Class[] classes = loader.getClasses();
		for (int i = 0; i < classes.length; i++) {
			System.out.println("Class: " + classes[i].getName());
		}
	}
}