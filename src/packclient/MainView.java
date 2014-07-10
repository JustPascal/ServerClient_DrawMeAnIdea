package packclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.aboutframe.AboutFrame;
import com.pluginloader.PluginLoader;
import com.tools.MainViewWindowListener;
import com.tools.ResourcePaths;
import com.tools.SaveImage;

/**
 * La classe mainview represente la fenêtre et ses elements tels que le menu et
 * tool bar qui contient des elements permettant à l'utilisateur de naviger avec
 * facilité
 * 
 * @author pascal et yossi
 * 
 */
public class MainView extends JFrame implements ActionListener, MenuListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1653101569575262616L;

	private Logger logger = Logger.getLogger(MainView.class.getName());

	private final JMenuBar menuBar = new JMenuBar();

	/* Menu */

	private JMenu fichier;

	private JMenu checkForUsers;

	private JMenu inviter;

	private JMenu plugin;

	private JMenu invitation;

	private JMenu help;

	/* Menu Items */

	private JMenuItem chargerPlugin;

	private JMenuItem aboutFrame;

	private JMenuItem nouveau;

	private JMenuItem sauvegarde;

	private JMenuItem quitter;

	private JMenuItem checkUsers;

	private JMenuItem attendre;

	/* Tool Bar */

	private JToolBar toolBar = new JToolBar();

	private JButton save;

	private JButton square;

	private JButton circle;

	private JButton red;

	private JButton green;

	private JButton blue;

	private JButton orange;

	private JButton yellow;

	private JButton pink;

	private JButton black;

	private JButton white;

	/* Zone de dessin */
	public MianController mcController = null;

	public List<InetAddress> ipClients = new ArrayList<InetAddress>();

	public ServerSocket socketReception = null;

	/**
	 * Constructeur permettant d'initialiser les diffèrentes elements de la
	 * fenêtre
	 */
	public MainView() {

		logger.info("[BEGIN] MainView");

		setTitle("Draw Me an Idea");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setSize(d.width / 2, d.height / 2);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/* Initialisation Menu */
		initMenu();
		/* Initialisation Tool bar */
		initToolBar();
		// On positionne notre zone de dessin
		getContentPane().add(getDrawPanel(), BorderLayout.CENTER);
		MainViewWindowListener mainViewWindowListenner = new MainViewWindowListener();
		addWindowListener(mainViewWindowListenner);
		setVisible(true);
		logger.info("[END] MainView");
	}

	private void initMenu() {
		/* Menu Fichier */
		fichier = new JMenu("Fichier");
		/* Nouveau */
		nouveau = new JMenuItem("Effacer");
		nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_DOWN_MASK));
		nouveau.addActionListener(this);
		/* Sauvegarde */
		sauvegarde = new JMenuItem("Save");
		sauvegarde.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_DOWN_MASK));
		sauvegarde.addActionListener(this);
		/* quitter */
		quitter = new JMenuItem("Quitter");
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				KeyEvent.CTRL_DOWN_MASK));
		quitter.addActionListener(this);
		/* Ajout dans le menu fichier */
		fichier.add(nouveau);
		fichier.addSeparator();
		fichier.add(sauvegarde);
		fichier.addSeparator();
		fichier.add(quitter);
		fichier.setMnemonic('F');
		/*---------------*/

		/* Menu Edition */

		/* Menu Check Users */
		checkForUsers = new JMenu("Check Users");
		checkUsers = new JMenuItem("recent users");
		checkUsers.addActionListener(this);
		checkForUsers.add(checkUsers);
		/*---------------*/

		/* Menu Utilisateurs disponibles */
		inviter = new JMenu("Utilisateurs disponible");
		inviter.setEnabled(false);
		inviter.addMenuListener(this);

		/* Menu Plugin */
		plugin = new JMenu("Plugin");
		chargerPlugin = new JMenuItem("Charger un Plugin");
		chargerPlugin.addActionListener(this);
		plugin.add(chargerPlugin);
		/*-------------*/

		/* Menu Attente invitation */
		invitation = new JMenu("Invitation");
		attendre = new JMenuItem("Attendre une Invitation");
		attendre.addActionListener(this);
		invitation.add(attendre);
		/*------------*/

		/* Menu About */
		help = new JMenu("?");
		aboutFrame = new JMenuItem("A propos de nous");
		aboutFrame.addActionListener(this);
		help.add(aboutFrame);
		/*------------*/

		/* Ajout des menu dans le menubar */
		menuBar.add(fichier);
		menuBar.add(checkForUsers);
		menuBar.add(inviter);
		menuBar.add(plugin);
		menuBar.add(invitation);
		menuBar.add(help);

		setJMenuBar(menuBar);
	}

	private void initToolBar() {

		/* Save Button */

		save = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.save)));
		save.addActionListener(this);

		/* Forme pointeur */
		square = new JButton();
		square.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
				ResourcePaths.square)));
		circle = new JButton();
		circle.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
				ResourcePaths.circle)));
		square.addActionListener(this);
		circle.addActionListener(this);
		/* Couleur */
		red = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.red)));
		blue = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.blue)));
		green = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.green)));
		yellow = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.yellow)));
		orange = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.orange)));
		pink = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.pink)));
		black = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.black)));
		white = new JButton(new ImageIcon(getClass().getClassLoader()
				.getResource(ResourcePaths.white)));

		red.setBackground(Color.RED);
		blue.setBackground(Color.BLUE);
		green.setBackground(Color.GREEN);
		yellow.setBackground(Color.YELLOW);
		orange.setBackground(Color.ORANGE);
		pink.setBackground(Color.PINK);
		black.setBackground(Color.black);
		white.setBackground(Color.white);

		red.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);
		yellow.addActionListener(this);
		orange.addActionListener(this);
		pink.addActionListener(this);
		black.addActionListener(this);
		white.addActionListener(this);

		toolBar.add(save);
		toolBar.addSeparator();
		toolBar.add(square);
		toolBar.add(circle);
		toolBar.addSeparator();
		toolBar.add(red);
		toolBar.add(blue);
		toolBar.add(green);
		toolBar.add(yellow);
		toolBar.add(orange);
		toolBar.add(pink);
		toolBar.add(black);
		toolBar.add(white);

		getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public MianController getDrawPanel() {
		if (mcController == null)
			mcController = new MianController();
		return mcController;
	}

	public void setMainController(MianController mcController) {
		this.mcController = mcController;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Charge un Plugin
		if (e.getSource().equals(chargerPlugin)) {
			new PluginLoader(this);
		}
		// verifi si il y a un utilisateur present pour activer le menu inviter
		if (e.getSource().equals(checkUsers)) {
			final boolean hasOneClient = ipClients.size() == 1;
			inviter.setEnabled(!hasOneClient);
		}
		// Permet de sauvegarder le fichier
		if (e.getSource().equals(sauvegarde) || e.getSource().equals(save)) {
			SaveImage saveImage = new SaveImage(this);
			saveImage.save();
		}
		// Appelle la class AboutFrame contenant des informations sur
		// l'application
		if (e.getSource().equals(aboutFrame)) {
			AboutFrame aboutFrame = new AboutFrame();
			aboutFrame.setVisible(true);
		}

		/* Menu Attendre */
		if (e.getSource().equals(attendre)) {

			try {

				InetAddress thisIp = InetAddress.getLocalHost();
				socketReception = new ServerSocket(4184, 1, thisIp);
				int input = JOptionPane
						.showOptionDialog(
								null,
								"L'attente d'une invitation est en cours.\nOk : Pour valider.\nCancel : Pour annuler.",
								"Attente d'une invitation",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null,
								null);
				if (input == JOptionPane.CANCEL_OPTION
						|| input == JOptionPane.NO_OPTION) {
					socketReception.close();
				} else {
					disableMenusandItems();

					Reception rrReception = new Reception(this,
							socketReception.accept());
					Thread threadReception = new Thread(rrReception);
					threadReception.start();
					JOptionPane.showMessageDialog(null, "Connexion réussie");
				}
			} catch (SocketException e1) {
				System.out.println("le ServerSocket est fermer.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		/* Changer lacouleurs d'un point */
		if (e.getSource().equals(red)) {
			mcController.setPointerColor(Color.red);
		}

		if (e.getSource().equals(green)) {
			mcController.setPointerColor(Color.green);
		}
		if (e.getSource().equals(blue)) {
			mcController.setPointerColor(Color.blue);
		}

		if (e.getSource().equals(yellow)) {
			mcController.setPointerColor(Color.yellow);
		}

		if (e.getSource().equals(orange)) {
			mcController.setPointerColor(Color.orange);
		}

		if (e.getSource().equals(pink)) {
			mcController.setPointerColor(Color.pink);
		}

		if (e.getSource().equals(black)) {
			mcController.setPointerColor(Color.black);
		}

		if (e.getSource().equals(white)) {
			mcController.setPointerColor(Color.white);
		}
		/* Changer la forme d'un point */
		if (e.getSource().equals(circle)) {
			mcController.setPointerType("CIRCLE");
		}
		if (e.getSource().equals(square)) {
			mcController.setPointerType("SQUARE");
		}
		/* fermeture de la fenetre */
		if (e.getSource().equals(quitter)) {
			final int reponse = JOptionPane.showConfirmDialog(this,
					"Voulez vous vraiment quittez l'aplication?");
			if (reponse == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		/* Nouveau fichier */
		if (e.getSource().equals(nouveau)) {
			final int reponse = JOptionPane.showConfirmDialog(this,
					"Etes vous sur de vouloir faire un nouveau dessin ?");
			if (reponse == JOptionPane.YES_OPTION) {
				mcController.erase();
			}
		}
	}

	/**
	 * Desactive l'ensemble du menu lorsqu'un utilisateur accepte une invitation
	 * */
	public void disableMenusandItems() {
		inviter.setEnabled(false);
		invitation.setEnabled(false);
		plugin.setEnabled(false);
		nouveau.setEnabled(false);
		checkForUsers.setEnabled(false);
	}

	/**
	 * Desactive le bouton de plugin après le chargement d'un plugin
	 * */
	public void disablePlugin() {
		plugin.setEnabled(false);
	}

	public List<InetAddress> getIpClients() {
		return ipClients;
	}

	public void setIpClients(List<InetAddress> ipClients) {
		this.ipClients = ipClients;
	}

	@Override
	public void menuCanceled(MenuEvent e) {

	}

	@Override
	public void menuDeselected(MenuEvent e) {

	}

	@Override
	public void menuSelected(MenuEvent e) {
		inviter.removeAll();

		try {
			InetAddress thisIp = InetAddress.getLocalHost();
			System.out.println(thisIp);

			inviter.setEnabled(true);
			for (final InetAddress ipInvite : ipClients) {

				if (!thisIp.equals(ipInvite)) {

					final JMenuItem utilisateur = new JMenuItem("adresse : "
							+ ipInvite.toString());
					utilisateur.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							boolean b = getDrawPanel().setIpRecepteur(ipInvite);
							System.out.println(b);
							if (b == false) {
								JOptionPane
										.showMessageDialog(new Frame(),
												"Cet utilisateur n'attend pas d'invitation");
							} else {
								JOptionPane.showMessageDialog(new Frame(),
										"Connexion réussie");
								inviter.setEnabled(false);
								invitation.setEnabled(false);
								checkForUsers.setEnabled(false);

								getDrawPanel().setRecepteur(false);
							}
						}
					});
					inviter.add(utilisateur);
				}

			}

		} catch (UnknownHostException e2) {

			e2.printStackTrace();
		}

	}
}
