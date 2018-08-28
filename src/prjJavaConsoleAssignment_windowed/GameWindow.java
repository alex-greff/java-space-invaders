package prjJavaConsoleAssignment_windowed;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/*
 * TODO: re-add the name prompt (use a text box) 
 * 
 * 
 * 
 */

public class GameWindow {

	private JFrame frame;
	
	private static JLabel screen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the application.
	 */
	public GameWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 450, 635);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblScreen = new JLabel("screen");
		lblScreen.setForeground(Color.CYAN);
		lblScreen.setFont(new Font("Consolas", Font.PLAIN, 11));
		frame.getContentPane().add(lblScreen, BorderLayout.NORTH);
		
		
		screen = lblScreen;
		
		
		frame.addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	            //System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
	            
	            try {
					InterfaceKeyPress(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	        }
	    });
		
		new Thread(new Runnable() { //Enemy spawner
		    public void run() {
		    	try {
					SpaceInvaders.StartGame();
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		}).start();
		
		
		//input.setVisible(false);
	}
		
	
	
	public static void PrintScreen (String text) {
		//text = text.replaceAll(" ", "&nbsp");
		
		String printText = "";
		
		for (int i = 0; i < text.length(); i++) {
			char c_before = '-';
			if (i > 0) {
				c_before = text.charAt(i-1);
			}
			
			char c = text.charAt(i);
			
			String printChar = c + "";
			
			if (c == ' ' && c_before == ' ') {
				printChar = "&nbsp";
			}
			
			printText += printChar;
		}
		
		if (screen != null) {
			screen.setText("<html>" + printText + "</html>");
		}
		else {
			System.out.println("No screen");
		}
	
	}
	
	private long last_timestamp = 0;
	private long minimum_time_delay = 100;
	
	public void InterfaceKeyPress (KeyEvent e) throws IOException {
		long timestamp = System.currentTimeMillis();
		

		if ((timestamp - last_timestamp) >= minimum_time_delay) {
		
			if (e.getKeyCode() == KeyEvent.VK_W) {
				SpaceInvaders.KeyPress("w");
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				SpaceInvaders.KeyPress("a");
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				SpaceInvaders.KeyPress("s");
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				SpaceInvaders.KeyPress("d");
			}
			
			last_timestamp = System.currentTimeMillis();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			SpaceInvaders.KeyPress("space");
		}
		if (e.getKeyCode() == KeyEvent.VK_1) {
			SpaceInvaders.KeyPress("1");
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			SpaceInvaders.KeyPress("2");
		}
		if (e.getKeyCode() == KeyEvent.VK_3) {
			SpaceInvaders.KeyPress("3");
		}
		if (e.getKeyCode() == KeyEvent.VK_4) {
			SpaceInvaders.KeyPress("4");
		}

		//System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
	}
	
}
