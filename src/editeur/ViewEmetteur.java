package editeur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

//CTRL + SHIFT + O pour générer les imports 
public class ViewEmetteur extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// LE MENU

	Logger logger = Logger.getLogger(ViewEmetteur.class.getName());

	private final JMenuBar menuBar = new JMenuBar();
	JMenu fichier = new JMenu("Fichier"), edition = new JMenu("Edition"),
			forme = new JMenu("Forme du pointeur"), couleur = new JMenu(
					"Couleur du pointeur");

	JMenuItem nouveau = new JMenuItem("Effacer");
	JMenuItem quitter = new JMenuItem("Quitter");
	JMenuItem rond = new JMenuItem("Rond");
	static JMenuItem carre = new JMenuItem("Carré");
	static JMenuItem bleu = new JMenuItem("Bleu");
	JMenuItem rouge = new JMenuItem("Rouge");
	static JMenuItem vert = new JMenuItem("Vert");

	// LA BARRE D'OUTILS
	JToolBar toolBar = new JToolBar();

	static JButton square = new JButton(new ImageIcon("images/carré.jpg"));
	JButton circle = new JButton(new ImageIcon("images/rond.jpg"));
	JButton red = new JButton(new ImageIcon("images/rouge.jpg"));
	static JButton green = new JButton(new ImageIcon("images/vert.jpg"));
	static JButton blue = new JButton(new ImageIcon("images/bleu.jpg"));

	// LES ÉCOUTEURS
	private final FormeListener fListener = new FormeListener();
	private final CouleurListener cListener = new CouleurListener();

	// Notre zone de dessin
	public DrawPanelEmetteur drawPanel = null;

	private boolean isLaunched;

	public ViewEmetteur() {
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
		nouveau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawPanel.erase();
			}
		});

		nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_DOWN_MASK));

		quitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				KeyEvent.CTRL_DOWN_MASK));

		fichier.add(nouveau);
		fichier.addSeparator();
		fichier.add(quitter);
		fichier.setMnemonic('F');

		carre.addActionListener(fListener);
		rond.addActionListener(fListener);
		forme.add(rond);
		forme.add(carre);

		rouge.addActionListener(cListener);
		vert.addActionListener(cListener);
		bleu.addActionListener(cListener);
		couleur.add(rouge);
		couleur.add(vert);
		couleur.add(bleu);

		edition.setMnemonic('E');
		edition.add(forme);
		edition.addSeparator();
		edition.add(couleur);

		menuBar.add(fichier);
		menuBar.add(edition);

		this.setJMenuBar(menuBar);
	}

	// Initialise la barre d'outils
	private void initToolBar() {

		square.addActionListener(fListener);
		circle.addActionListener(fListener);
		red.addActionListener(cListener);
		green.addActionListener(cListener);
		blue.addActionListener(cListener);

		toolBar.add(square);
		toolBar.add(circle);

		toolBar.addSeparator();
		toolBar.add(red);
		toolBar.add(blue);
		toolBar.add(green);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public DrawPanelEmetteur getDrawPanel() {
		if (drawPanel == null)
			drawPanel = new DrawPanelEmetteur();
		return drawPanel;
	}

	public void setDrawPanel(DrawPanelEmetteur drawPanel) {
		this.drawPanel = drawPanel;
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

}