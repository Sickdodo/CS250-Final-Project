import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;

public class MatchingGame extends JFrame {
	private List<Integer> numberPairs;
	private JButton[] cards;
	private JButton firstCard = null;
	private JButton secondCard = null;
	private int pairsMatched = 0;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MatchingGame frame = new MatchingGame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MatchingGame() {
		setTitle("Card Matching Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		numberPairs = generateNumberPairs();
		
		List<Integer> randomNumbers = new ArrayList<>(numberPairs);
		Collections.shuffle(randomNumbers);
		
		setBounds(100, 100, 450, 800);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 4, 0, 0));
		
		cards = new JButton[16];
		
		for (int i = 0; i < 16; i++) {
			cards[i] = new JButton("");
			cards[i].setPreferredSize(new Dimension(100, 200));
			panel.add(cards[i]);
			
			final int index = i;
			cards[i].addActionListener(e -> {
				if (firstCard == null) {
					firstCard = cards[index];
					firstCard.setText(String.valueOf(numberPairs.get(index)));
				} else if (firstCard != null && secondCard == null) {
					secondCard = cards[index];
					secondCard.setText(String.valueOf(numberPairs.get(index)));
					
					if (numberPairs.get(cardsToIndex(firstCard)) == numberPairs.get(cardsToIndex(secondCard))) {
						pairsMatched++;
						firstCard.setEnabled(false);
						secondCard.setEnabled(false);
						
						if (pairsMatched == 8) {
							
						}
						
						firstCard = null;
						secondCard = null;
					} else {
						
						new Thread(() -> {
							try {
								Thread.sleep(1000);
								SwingUtilities.invokeLater(() -> {
									firstCard.setText("");
									secondCard.setText("");
									firstCard = null;
									secondCard = null;
								});
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}).start();
					}
				}
			});
		}
		
	}
	
	private int cardsToIndex(JButton card) {
		for (int i = 0; i < cards.length; i++) {
			if(cards[i] == card) {
				return i;
			}
		}
		return -1;
	}
	
	private List<Integer> generateNumberPairs() {
		List<Integer> numberPairs = new ArrayList<>();
		for (int i = 1; i <=8; i++) {
			numberPairs.add(i);
			numberPairs.add(i);
		}
		Collections.shuffle(numberPairs);
		return numberPairs;
	}

}
