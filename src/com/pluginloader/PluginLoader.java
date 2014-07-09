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
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import packclient.MainView;

/**
 * Class permettant de charger les plugins
 * 
 * @author pascal
 * 
 */
public class PluginLoader {

	/**
	 * Prend en parametre le MainView qui est un parametre essentiel pour lancer
	 * les plugins developpés
	 * 
	 * @param mainView
	 */
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
					if (className.contains("PluginImpl")) {
						Class<?> c = cl.loadClass(className);
						Constructor<?> constructeur = c
								.getDeclaredConstructor(mainView.getClass());
						constructeur.newInstance(mainView);
						mainView.disablePlugin();
					}
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Le plugin n'a pas pu se charger.");
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(null,
						"Le plugin n'a pas pu se charger.");
			} catch (SecurityException e1) {
				JOptionPane.showMessageDialog(null,
						"Le plugin n'a pas pu se charger.");
			} catch (InstantiationException e1) {
				JOptionPane.showMessageDialog(null,
						"Le plugin n'a pas pu se charger.");
			} catch (IllegalAccessException e1) {
				JOptionPane.showMessageDialog(null,
						"Le plugin n'a pas pu se charger.");
			} catch (InvocationTargetException e1) {
				JOptionPane.showMessageDialog(null,
						"Le plugin n'a pas pu se charger.");
			} catch (NoSuchMethodException e1) {
				JOptionPane.showMessageDialog(null,
						"Le plugin n'a pas pu se charger.");
			}
		}
	}
}
