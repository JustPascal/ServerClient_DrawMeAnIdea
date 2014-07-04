package com.pluginloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import packClient.MainView;

public class PluginLoader {

	public PluginLoader(MainView mainView) {
		init(mainView);
	}

	private void init(MainView mainView) {

		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		fc.setDialogTitle("Choisir plugin");
		fc.setFileFilter(new FileNameExtensionFilter("JAR", "jar"));

		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String path = file.getAbsolutePath();

			JarFile jarFile = null;
			try {
				jarFile = new JarFile(path);
				Enumeration<?> e = jarFile.entries();
				URL[] urls = { new URL("jar:file:" + path + "!/") };
				URLClassLoader cl = URLClassLoader.newInstance(urls);
				while (e.hasMoreElements()) {
					JarEntry jarElement = (JarEntry) e.nextElement();
					if (jarElement.isDirectory()
							|| !jarElement.getName().endsWith(".class")) {
						continue;
					}
					String className = jarElement.getName().substring(0,
							jarElement.getName().length() - 6);
					className = className.replace('/', '.');
					if (className.contains("Impl")) {
						Class<?> c = cl.loadClass(className);
						Constructor<?> constructeur = c
								.getDeclaredConstructor(mainView.getClass());
						constructeur.newInstance(mainView);
					}
				}
			} catch (IOException e) {
				System.out.println("File Exception : " + e.getMessage());
			} catch (ClassNotFoundException e1) {
				System.out.println("Class not found Exception : "
						+ e1.getMessage());
			} catch (SecurityException e1) {
				System.out.println("Security Exception : " + e1.getMessage());
			} catch (InstantiationException e1) {
				System.out.println("Instanciation Exception : "
						+ e1.getMessage());
			} catch (IllegalAccessException e1) {
				System.out.println("Illegal Access Exception : "
						+ e1.getMessage());
			} catch (InvocationTargetException e1) {
				System.out.println("Invocation Target Exception : "
						+ e1.getMessage());
			} catch (NoSuchMethodException e1) {
				System.out.println("No such Method Exception : "
						+ e1.getMessage());
			}
			mainView.disablePlugin();
		}
	}
}
