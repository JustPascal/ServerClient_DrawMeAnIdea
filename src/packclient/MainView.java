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

	private JMenuItem bleu;

	private JMenuItem rouge;

	private JMenuItem vert;

	private JMenuItem attendre;

	/* Tool Bar */

	private JToolBar toolBar = new JToolBar();

	private JButton save;

	private JButton square;

	private JButton circle;

	private JButton red;

	private JButton green;

	private JButton blue;

	/* Zone de dessin */
	public MainController mcController = null;

	private boolean isLaunched;

	public List<InetAddress> ipClients = new ArrayList<InetAddress>();

	public ServerSocket socketReception = null;

	public List<InetAddress> getIpClients() {
		return ipClients;
	}

	public void setIpClients(List<InetAddress> ipClients) {
		this.ipClients = ipClients;
	}

	// Constructeur
	public MainView() {

		logger.info("[BEGIN] MainView");

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
		MainViewWindowListener mainViewWindowListenner = new MainViewWindowListener(
				this);
		addWindowListener(mainViewWindowListenner);
		setVisible(true);
		logger.info("[END] MainView");
	}

	// Initialise le menu
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

		red.setBackground(Color.RED);
		blue.setBackground(Color.BLUE);
		green.setBackground(Color.GREEN);

		red.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);

		toolBar.add(save);
		toolBar.addSeparator();
		toolBar.add(square);
		toolBar.add(circle);
		toolBar.addSeparator();
		toolBar.add(red);
		toolBar.add(blue);
		toolBar.add(green);

		getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public MainController getDrawPanel() {
		if (mcController == null)
			mcController = new MainController();
		return mcController;
	}

	public void setMainController(MainController mcController) {
		this.mcController = mcController;
	}

	public boolean isLaunched() {
		return isLaunched;
	}

	public void setLaunched(boolean isLaunched) {
		this.isLaunched = isLaunched;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(chargerPlugin)) {
			new PluginLoader(this);
		}

		if (e.getSource().equals(checkUsers)) {

			final boolean hasOneClient = ipClients.size() == 1;
			inviter.setEnabled(!hasOneClient);
		}

		if (e.getSource().equals(sauvegarde) || e.getSource().equals(save)) {
			SaveImage saveImage = new SaveImage(this);
			saveImage.save();
		}

		if (e.getSource().equals(aboutFrame)) {
			AboutFrame aboutFrame = new AboutFrame();
			aboutFrame.setVisible(true);
		}

		/* Menu Attendre */
		if (e.getSource().equals(attendre)) {

			try {

				InetAddress thisIp = InetAddress.getLocalHost();
				System.out.println(thisIp);

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
					Thread treception = new Thread(rrReception);
					treception.start();
					JOptionPane.showMessageDialog(null, "Connexion réussie");
				}
			} catch (SocketException e1) {
				System.out.println("le ServerSocket est fermer.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		/* Couleur */
		if (e.getSource().equals(rouge) || e.getSource().equals(red)) {
			mcController.setPointerColor(Color.red);
		}

		if (e.getSource().equals(vert) || e.getSource().equals(green)) {
			mcController.setPointerColor(Color.green);
		}

		if (e.getSource().equals(bleu) || e.getSource().equals(blue)) {
			mcController.setPointerColor(Color.blue);
		}
		/* Forme */
		if (e.getSource().equals(circle)) {
			mcController.setPointerType("CIRCLE");
		}
		if (e.getSource().equals(square)) {
			mcController.setPointerType("SQUARE");
		}
		/* fermeture de la fenetre */
		if (e.getSource().equals(quitter)) {
			JOptionPane.showMessageDialog(this, "Vous quittez l'aplication");
			System.exit(0);
		}
		/* Nouveau fichier */
		if (e.getSource().equals(nouveau)) {
			JOptionPane.showMessageDialog(this,
					"Vous allez effacer tout pour recommencer.");
			mcController.erase();
		}
	}

	public void disableMenusandItems() {
		inviter.setEnabled(false);
		invitation.setEnabled(false);
		plugin.setEnabled(false);
		nouveau.setEnabled(false);
		checkForUsers.setEnabled(false);
	}

	public void disablePlugin() {
		plugin.setEnabled(false);
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
