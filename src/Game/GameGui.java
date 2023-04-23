/**
* The GameGui class creates a graphical user interface for the Dungeon Game.
* It extends the JFrame class and includes a title, a play button, a quit button,
* and panels to hold them.
*/

package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameGui extends JFrame {

	// Declare instance variables
	JLabel title;
	JButton play;
	JButton quit;
	JPanel panel;
	JPanel buttonPanel;
	JPanel titlePanel;

	/**
	 * Constructs a new GameGui object. It initializes the title, play button, quit
	 * button, and the panels that hold them. It also sets up the layout of the GUI,
	 * sets the font and color of the title, and adds listeners to the play and quit
	 * buttons.
	 */
	public GameGui() {
		// Initialize the GUI components
		title = new JLabel("Dungeon Game");
		play = new JButton("Start Game");
		quit = new JButton("Quit");
		panel = new JPanel();
		panel.setBackground(Color.decode("#B2AC88"));
		panel.setLayout(new BorderLayout());

		titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		panel.add(titlePanel, BorderLayout.NORTH);

		// Set up the title panel
		title.setFont(new Font("Times New Roman", Font.BOLD, 58));
		title.setForeground(Color.WHITE);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		title.setBorder(border);
		titlePanel.add(title);
		titlePanel.add(Box.createRigidArea(new Dimension(0, 100)));
		titlePanel.setBackground(Color.decode("#B2AC88"));
		title.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		// Set up the button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		panel.add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.add(play);
		play.setPreferredSize(new Dimension(50, 50));
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		buttonPanel.setBackground(Color.decode("#B2AC88"));
		play.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		buttonPanel.add(quit);
		quit.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		add(panel);

		// Set up the JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
		setVisible(true);

		JFrame gameGUI = this;
		
		// Add listeners to the play and quit buttons
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Close the GUI
				setVisible(false);
				dispose();
			}
		});

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Start the game and close the GUI
				new DungeonGame(gameGUI);
				setVisible(false);
				dispose();
			}
		});
	}

	/**
	 * The main method creates a new GameGui object, which launches the GUI.
	 * 
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		new GameGui();
	}
}
