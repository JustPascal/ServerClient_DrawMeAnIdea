package packClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.aboutframe.AboutFrame;
import com.pluginloader.PluginLoader;
import com.tools.MainViewWindowListener;
import com.tools.SaveImage;

public class MainView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(MainView.class.getName());

	// LE MENU
	private final JMenuBar menuBar = new JMenuBar();

	private JMenu fichier = new JMenu("Fichier");

	private JMenu edition = new JMenu("Edition");

	private JMenu checkForUsers = new JMenu("Check Users");

	private JMenu inviter = new JMenu("Utilisateurs disponible");

	private JMenu forme = new JMenu("Forme du pointeur");

	private JMenu couleur = new JMenu("Couleur du pointeur");

	/* Menu et menu Item du plugin */

	private JMenu plugin = new JMenu("Plugin");

	JMenuItem chargerPlugin = new JMenuItem("Charger un Plugin");

	/* -- -- -- */

	JMenu help = new JMenu("?");

	private JMenu invitation = new JMenu("Invitation");

	JMenuItem aboutFrame = new JMenuItem("A propos de nous");

	JMenuItem nouveau = new JMenuItem("Effacer");

	JMenuItem sauvegarde = new JMenuItem("Save");

	JMenuItem quitter = new JMenuItem("Quitter");

	JMenuItem checkUsers = new JMenuItem("recent users");

	JMenuItem rond = new JMenuItem("Circle");

	static JMenuItem carre = new JMenuItem("Square");

	static JMenuItem bleu = new JMenuItem("Blue");

	JMenuItem rouge = new JMenuItem("Red");

	static JMenuItem vert = new JMenuItem("Green");

	// LA BARRE D'OUTILS
	JToolBar toolBar = new JToolBar();

	static JButton square = new JButton(new ImageIcon("src/carre.png"));

	JButton circle = new JButton(new ImageIcon("src/rond.png"));

	JButton red = new JButton();

	static JButton green = new JButton();

	static JButton blue = new JButton();

	// Notre zone de dessin
	public MainController mcController = null;

	private boolean isLaunched;

	public List<InetAddress> ipClients = new ArrayList<InetAddress>();

	public ServerSocket socketReception = null;

	private JMenuItem attendre = new JMenuItem("Attendre une Invitation");

	public List<InetAddress> getIpClients() {
		return ipClients;
	}

	public void setIpClients(List<InetAddress> ipClients) {
		this.ipClients = ipClients;
	}

	// Constructeur
	public MainView() {

		logger.info("[BEGIN] MainView()");

		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (Exception e) {
			e.getMessage();
		}
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// On initialise le menu
		this.initMenu();
		// Idem pour la barre d'outils
		this.initToolBar();
		// On positionne notre zone de dessin
		this.getContentPane().add(getDrawPanel(), BorderLayout.CENTER);
		this.setVisible(true);
		/* WindowListenner */
		MainViewWindowListener mainViewWindowListenner = new MainViewWindowListener(
				this);
		this.addWindowListener(mainViewWindowListenner);
		/*				*/
		logger.info("[END] MainView()");
	}

	// Initialise le menu
	private void initMenu() {
		red.setBackground(Color.RED);
		blue.setBackground(Color.BLUE);
		green.setBackground(Color.GREEN);
		nouveau.addActionListener(this);
		nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_DOWN_MASK));

		quitter.addActionListener(this);
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				KeyEvent.CTRL_DOWN_MASK));

		sauvegarde.addActionListener(this);
		fichier.add(nouveau);
		fichier.addSeparator();
		fichier.add(sauvegarde);
		fichier.addSeparator();
		fichier.add(quitter);
		fichier.setMnemonic('F');

		carre.addActionListener(this);
		rond.addActionListener(this);
		forme.add(rond);
		forme.add(carre);

		rouge.addActionListener(this);
		vert.addActionListener(this);
		bleu.addActionListener(this);

		inviter.setEnabled(false);
		inviter.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				inviter.removeAll();

				try {
					InetAddress thisIp = InetAddress.getLocalHost();
					System.out.println(thisIp);

					inviter.setEnabled(true);
					for (final InetAddress ipInvite : ipClients) {

						if (!thisIp.equals(ipInvite)) {

							final JButton button = new JButton("adresse : "
									+ ipInvite.toString());
							button.addActionListener(new ActionListener() {
								@SuppressWarnings("deprecation")
								@Override
								public void actionPerformed(ActionEvent e) {

									boolean b = getDrawPanel().setIpRecepteur(
											ipInvite);
									System.out.println(b);
									if (b == false) {
										JOptionPane
												.showMessageDialog(new Frame(),
														"Cet utilisateur n'attend pas d'invitation");
									} else {
										JOptionPane.showMessageDialog(
												new Frame(),
												"Connexion réussie");
										inviter.setEnabled(false);
										invitation.setEnabled(false);

										getDrawPanel().setRecepteur(false);
									}
								}
							});
							inviter.add(button);
						}

					}

				} catch (UnknownHostException e2) {

					e2.printStackTrace();
				}

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});

		couleur.add(rouge);
		couleur.add(vert);
		couleur.add(bleu);

		edition.setMnemonic('E');
		edition.add(forme);
		edition.addSeparator();
		edition.add(couleur);

		/* check users */
		checkUsers.addActionListener(this);
		checkForUsers.add(checkUsers);
		/* */
		menuBar.add(fichier);
		menuBar.add(checkForUsers);
		menuBar.add(edition);
		menuBar.add(inviter);

		/* Plugin */
		chargerPlugin.addActionListener(this);
		plugin.add(chargerPlugin);
		menuBar.add(plugin);
		/*	*/
		aboutFrame.addActionListener(this);
		help.add(aboutFrame);
		/* Inviter */
		attendre.addActionListener(this);
		invitation.add(attendre);
		menuBar.add(invitation);
		/*	*/
		menuBar.add(help);

		this.setJMenuBar(menuBar);
	}

	// Initialise la barre d'outils
	private void initToolBar() {

		square.addActionListener(this);
		circle.addActionListener(this);
		red.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);

		toolBar.add(square);
		toolBar.add(circle);
		toolBar.addSeparator();
		toolBar.add(red);
		toolBar.add(blue);
		toolBar.add(green);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
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

		if (e.getSource().equals(sauvegarde)) {
			SaveImage saveImage = new SaveImage(this);
			saveImage.save();
		}

		if (e.getSource().equals(aboutFrame)) {
			AboutFrame aboutFrame = new AboutFrame();
			aboutFrame.setVisible(true);
		}

		/* Menu Attendre */
		if (e.getSource().equals(attendre)) {
			// JOptionPane.showMessageDialog(this,
			// "Attendre invitation button.");
			disableMenusandItems();
			try {

				InetAddress thisIp = InetAddress.getLocalHost();
				System.out.println(thisIp);

				socketReception = new ServerSocket(4184, 1, thisIp);

				int input = JOptionPane
						.showOptionDialog(
								null,
								"L'attente d'une invitation est en cours.\nOk : Pour valider.\nCancel : Pour annuler.",
								"Attente d'une invitation",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null,
								null);
				if (input == JOptionPane.CANCEL_OPTION) {
					socketReception.close();
				}

				Socket socketR = socketReception.accept();
				JOptionPane.showMessageDialog(new Frame(), "Connexion réussie");

				Reception rrReception = new Reception(MainView.this, socketR);
				new Thread(rrReception).start();
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
		if (e.getSource().equals(rond) || e.getSource().equals(circle)) {
			mcController.setPointerType("CIRCLE");
		}
		if (e.getSource().equals(carre) || e.getSource().equals(square)) {
			mcController.setPointerType("SQUARE");
			;
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
		edition.setEnabled(false);
		nouveau.setEnabled(false);
		checkForUsers.setEnabled(false);
	}

	public void disablePlugin() {
		plugin.setEnabled(false);
	}
}
