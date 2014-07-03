package packClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
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

import com.pluginloader.PluginLoader;

public class MainView extends JFrame implements WindowListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(MainView.class.getName());

	// LE MENU
	private final JMenuBar menuBar = new JMenuBar();

	JMenu fichier = new JMenu("Fichier");

	JMenu edition = new JMenu("Edition");

	JMenu inviter = new JMenu("Utilisateurs disponible");

	JMenu forme = new JMenu("Forme du pointeur");

	JMenu couleur = new JMenu("Couleur du pointeur");

	/* Menu et menu Item du plugin */

	JMenu plugin = new JMenu("Plugin");

	JMenuItem chargerPlugin = new JMenuItem("Charger un Plugin");

	/* -- -- -- */

	JMenuItem nouveau = new JMenuItem("Effacer");

	JMenuItem quitter = new JMenuItem("Quitter");

	JMenuItem rond = new JMenuItem("Rond");

	static JMenuItem carre = new JMenuItem("Carré");

	static JMenuItem bleu = new JMenuItem("Bleu");

	JMenuItem rouge = new JMenuItem("Rouge");

	static JMenuItem vert = new JMenuItem("Vert");

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

	public List<InetAddress> ipClients = null;

	public ServerSocket socketReception = null;
	public JButton bouton2 = new JButton("Attendre une Invitation");

	public List<InetAddress> getIpClients() {
		return ipClients;
	}

	public void setIpClients(List<InetAddress> ipClients) {
		this.ipClients = ipClients;
	}

	// Constructeur
	public MainView() {
		logger.info("[BEGIN] MainView()");

		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// On initialise le menu
		this.initMenu();
		// Idem pour la barre d'outils
		this.initToolBar();
		// On positionne notre zone de dessin
		this.getContentPane().add(getDrawPanel(), BorderLayout.CENTER);
		this.setVisible(true);

		logger.info("[END] MainView()");
	}

	// Initialise le menu
	private void initMenu() {
		red.setBackground(Color.RED);
		blue.setBackground(Color.BLUE);
		green.setBackground(Color.GREEN);
		nouveau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				mcController.erase();

			}
		});

		nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));

		quitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));

		fichier.add(nouveau);
		fichier.addSeparator();
		fichier.add(quitter);
		fichier.setMnemonic('F');

		carre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchForme(e);
			}
		});
		rond.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchForme(e);
			}
		});
		forme.add(rond);
		forme.add(carre);

		rouge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchCouleur(e);
			}
		});
		vert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchCouleur(e);
			}
		});
		bleu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchCouleur(e);
			}
		});

		inviter.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				inviter.removeAll();

				try {
					InetAddress thisIp = InetAddress.getLocalHost();
					System.out.println(thisIp);

					if (ipClients.size() != 0) {
						for (final InetAddress ipInvite : ipClients) {

							if (!thisIp.equals(ipInvite)) {

								final JButton button = new JButton("adresse n° " + ipInvite.toString());
								button.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {

										boolean b = getDrawPanel().setIpRecepteur(ipInvite);
										System.out.println(b);
										if (b == false) {
											JOptionPane.showMessageDialog(new Frame(), "Cet utilisateur n'attend pas d'invitation");
										} else {
											JOptionPane.showMessageDialog(new Frame(), "Connexion réussie");
											// menuBar.remove(inviter);

											inviter.setEnabled(false);
											bouton2.setEnabled(false);

											getDrawPanel().setRecepteur(false);
										}
									}
								});
								inviter.add(button);
							}

						}
					} else {
						System.out.println("liste vide");
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

		// final JButton bouton2 = new JButton("Attendre une Invitation");
		bouton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					InetAddress thisIp = InetAddress.getLocalHost();
					System.out.println(thisIp);

					socketReception = new ServerSocket(4184, 1, thisIp);

					int input = JOptionPane.showOptionDialog(null,
							"L'attente d'une invitation est en cours.\nOk : Pour valider.\nCancel : Pour annuler.",
							"Attente d'une invitation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if (input == JOptionPane.CANCEL_OPTION) {
						socketReception.close();
					}

					@SuppressWarnings("resource")
					Socket socketR = new Socket();
					socketR = socketReception.accept();
					JOptionPane.showMessageDialog(new Frame(), "Connexion réussie");

					inviter.setEnabled(false);
					bouton2.setEnabled(false);
					plugin.setEnabled(false);
					edition.setEnabled(false);
					nouveau.setEnabled(false);

					Reception rrReception = new Reception(MainView.this, socketR);
					new Thread(rrReception).start();
				} catch (SocketException e1) {
					System.out.println("le ServerSocket est refermé");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		couleur.add(rouge);
		couleur.add(vert);
		couleur.add(bleu);

		edition.setMnemonic('E');
		edition.add(forme);
		edition.addSeparator();
		edition.add(couleur);

		menuBar.add(fichier);
		menuBar.add(edition);
		menuBar.add(inviter);

		/* Plugin */
		chargerPlugin.addActionListener(this);
		plugin.add(chargerPlugin);
		menuBar.add(plugin);

		menuBar.add(bouton2);

		this.setJMenuBar(menuBar);
	}

	// Initialise la barre d'outils
	private void initToolBar() {

		square.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchForme(e);
			}
		});
		circle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchForme(e);
			}
		});
		red.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchCouleur(e);
			}
		});
		green.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchCouleur(e);
			}
		});
		blue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcController.switchCouleur(e);
			}
		});

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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.setLaunched(false);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

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
			// JOptionPane.showMessageDialog(this, "Charger Plugin");
			new PluginLoader(this);
		}
	}

}
