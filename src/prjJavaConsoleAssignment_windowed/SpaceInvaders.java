/* Alex Greff
 * 18/10/2016
 * Space Invaders
 * My java file with all of
 * the Space Invaders game
 */

package prjJavaConsoleAssignment_windowed;
import java.util.*;
import java.io.*;

public class SpaceInvaders {
	
	static int size = 25; //Size of the screen
	static String[][] screen; //The screen
	static Player player; //The player
	static Enemy[] enemies; //Array of enemies
	static Object[] bullets; //Array of bullets
	static Object[] healthPacks; //Array of health packs
	
	public static String name = "unknown"; //The player's default name
	static int score = 0, bulletAmt = 0; 
	public static boolean inGame = false, inSecondaryMenu = false, inMenu = false, runningUpdate = false, gameOver = false;
	
	static Scanner read = new Scanner(System.in); //Reader
	
	//Initialization variables
	static final int INIT_BULLET_AMOUNT = 10, INIT_HEALTH_PACK_AMOUNT = 3, ENEMY_COUNT = 20,  INIT_HEALTH = 3, BULLET_MAX_AMOUNT = 3;
	
	//Timer variables
	static final long ENEMY_SPAWNER = 1000, HEALTH_PACK_SPAWNER = 30000, ENEMY_SPEED = 300, BULLET_SPEED = 200, HEALTH_PACK_SPEED = 500, BULLET_RESPAWN_TIME = 1500; 
	
	static final boolean USING_ECLIPSE = true; //SPECIFIES THE TYPE OF CONSOLE BEING USED (DEVELOPER OPTION)
	
	
	public static String GameState = "";
	
	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("Running game");
		
		/*clearScreen(); //Clear the screen
		splashScreen(); //Show splash screen
		
		nameEnter(); //Prompt user for their name
		
		mainMenu(); //Show the main menu*/
	}
	
	public static void KeyPress (String key) throws IOException {
		if (inMenu) {
			if (key.equals("1")) {
				game();
			}
			else if (key.equals("2")) {
				information();
			}
			else if (key.equals("3")) {
				System.exit(0); //Close the game
			}
		}
		
		if (inSecondaryMenu) {
			if (key.equals("1")) {
				mainMenu(); //Load menu
			}
		}
		
		if (inGame && !gameOver) {
			if (key.equals("w")) {
				movePlayer(0,-1); //Move one space up
			}
			if (key.equals("s")) {
				movePlayer(0,1); //Move one space down
			}
			if (key.equals("d")) {
				movePlayer(1,0); //Move 1 space right
			}
			if (key.equals("a")) {
				movePlayer(-1,0); //Move 1 space left
			}
			
			if (key.equals("space")) {
				fireBullet(); //Fire a bullet
			}
			
			update(); //Update
		}
		else if (inGame && gameOver) { //If the game is over
			if (key.equals("1")){
				restart(); //Restart game
			}
			else if (key.equals("2")){
				mainMenu(); //Load main menu
			}
			
			update(); //Update
		}
		
		
	}
	
//	public static void InitializeGame () throws InterruptedException, IOException {
//		
//	}
	
	public static void StartGame () throws InterruptedException, IOException {
		initializeVariables(); //Initialize the variables to their default value at the start of each game
		
		splashScreen(); //Show splash screen
		
		//nameEnter(); //Prompt user for their name
		
		mainMenu();
	}
	
	public static void PrintScreen (String text) {
		GameWindow.PrintScreen(text);
	}
	
	static void initializeVariables(){
		//Reset variables
		score = 0;
		bulletAmt = BULLET_MAX_AMOUNT; //Give player all their bullets initially
		gameOver = false;
		
		screen = new String[size][size*2]; //Make screen string
		
		player = new Player(INIT_HEALTH, size-2, size-1); //Make player
		
		
		bullets = new Object[INIT_BULLET_AMOUNT]; //Make pool of bullets based off initial bullet amount
		for (int i = 0; i < INIT_BULLET_AMOUNT; i++){
			bullets[i] = new Object(1, ObjectType.BULLET); //Create each bullet
		}
		
		healthPacks = new Object[INIT_HEALTH_PACK_AMOUNT]; //Make pool of health packed based off initial bullet amount
		for (int i = 0; i < INIT_HEALTH_PACK_AMOUNT; i++){
			healthPacks[i] = new Object(1, ObjectType.HEALTH_PACK); //Create each pack
		}
		
		enemies = new Enemy[ENEMY_COUNT];
		for (int i = 0; i < ENEMY_COUNT; i++){
				if (i%2 == 1)
					enemies[i] = new Enemy(EnemyType.MEDIUM); //Create medium enemy
				else if (i%3 == 1)
					enemies[i] = new Enemy(EnemyType.LARGE); //Create large enemy
				else 
					enemies[i] = new Enemy(EnemyType.SMALL); //Create small enemy
		}
	}
	
	static void restart() throws IOException { //Restarts the game
		initializeVariables(); //Initialize variables
		game(); //Start game
	}
	
	static void splashScreen() throws InterruptedException, IOException { //Cool little splash screen
		int iterations = 5; //Number of times the animation repeats
		int delay = 200; //The delay between frames
		int c = 0; //The counter
		
		String display = ""; // The display string
		
		//Simple 2-frame animation
		while (c < iterations){ //Exit when c is greater than or equal to the iterations amount
			display = "+=+=+=+=+=+=+=+=+=+=+" + newLine() +
					  "+=+=Space Invaders+=+" + newLine() +
					  "+=+=+=By Alex G=+=+=+" + newLine() +
					  "+=+=+=+=+=+=+=+=+=+=+";
			PrintScreen (display);
			
			/* System.out.println("+=+=+=+=+=+=+=+=+=+=+");
			System.out.println("+=+=Space Invaders+=+");
			System.out.println("+=+=+=By Alex G=+=+=+");
			System.out.println("+=+=+=+=+=+=+=+=+=+=+"); */
			
			Thread.sleep(delay); //Delay
			clearScreen(); //Clear the screen
			
			display = "=+=+=+=+=+=+=+=+=+=+=" + newLine() + 
					"=+=+Space Invaders=+=" + newLine() +
					"=+=+=+By Alex G+=+=+=" + newLine() +
					"=+=+=+=+=+=+=+=+=+=+=";
			PrintScreen (display);
			
			/* System.out.println("=+=+=+=+=+=+=+=+=+=+=");
			System.out.println("=+=+Space Invaders=+=");
			System.out.println("=+=+=+By Alex G+=+=+=");
			System.out.println("=+=+=+=+=+=+=+=+=+=+="); */
			
			Thread.sleep(delay); //Delay
			clearScreen(); //Clear the screen
			
			c++; //Increment timer
		}
	}
	
	static void mainMenu() throws IOException { //Displays the main menu
		//Set variables
		int selection = 0;
		inSecondaryMenu = false;
		inMenu = true;
		inGame = false;
		
		String display = ""; 
		
		//while (true) { //Reprint screen
			clearScreen(); //Clear screen
			
			//Display menu
			/*System.out.println("Welcome, " + name + "!");
			System.out.println();
			System.out.println("Main Menu");
			System.out.println("1. Play");
			System.out.println("2. Highscores");
			System.out.println("3. Information");
			System.out.println("4. Exit");*/
			
			/*display = "Welcome, " + name + "!" + newLine() + newLine() +
					  "Main Menu" + newLine() + 
					  "1. Play" + newLine() +
					  "2. Information" + newLine() +
					  "3. Exit";*/
			
			display = "Welcome!" + newLine() + newLine() +
					  "Main Menu" + newLine() + 
					  "1. Play" + newLine() +
					  "2. Information" + newLine() +
					  "3. Exit";
			
			PrintScreen (display);
			
			/*try {
				selection = read.nextInt(); //Read user's input
				
				if (selection == 1) { 
					game(); //Load game
					break; //Exit loop
				}
				else if (selection == 2) {
					highscore(); //Load highscore screen
					break; //Exit loop
				}
				else if (selection == 3) {
					information(); //Load information screen
					break; //Exit loop
				}
				else if (selection == 4) {
					clearScreen(); //Clear screen
					System.exit(0); //Close the game
				}
			} catch (Exception e) { //If the user inputs something invalid
				read.next(); //Go to the next input
			}
		}*/
	}
	
	//Highscore Screen
	static void highscore() throws IOException{
		//Reset variables
		inSecondaryMenu = true; 
		inMenu = false;
		final int DISPLAY_TOP = 10; //The number is placements shown
		
		BufferedReader br = new BufferedReader(new FileReader("scores.csv"));
		
		String line = ""; //Initialize the line variable
		
		//Do an initial run-through to calculate the size needed for the array (could've just used a list)
		int size = 0; //Make size variable
		do {
			line = br.readLine(); //Read the score line
			
			if (line != null) { //If line isn't blank
				size++; //Increment size
			}
		} while (line != null); //Exit loop if line is blank
		
		
		String[] lineInfo = null; //Initialize an object
		String[][] scoreList = new String[size][2]; //Make array of player name and their score
		int r = 0;
		
		br.close(); //Close reader
		br = new BufferedReader(new FileReader("scores.csv")); //Reinitialize reader (so it starts at the top again)
		
		do {
			line = br.readLine(); //Reads the line
			
			if (line != null) {
				lineInfo = line.split(","); //Splits the string when it reaches the given parameter string (like explode in php)
				
				scoreList[r][0] = lineInfo[0]; //Store name into 2d array
				scoreList[r][1] = lineInfo[1]; //Store score into 2d array
			}
			r++; //Increment counter
		} while (line != null); //Exit when line is empty
		
		//br.close();
		
		//Sort the scores from greatest to least
		int n = scoreList.length;
		int tempScore = 0;
		String tempName = "";
		
		
		//Bubble sorting
		for (int i = 0; i < n; i++){
			for (int j = 1; j < (n-i); j++){
				int num1 = 0;
				int num2 = 0;
				
				try { //Temporarily convert the two scores that we wish to compare to ints   
					num1 = Integer.parseInt(scoreList[j-1][1]);
					num2 = Integer.parseInt(scoreList[j][1]);
				} catch (NumberFormatException e){} //Catch if it can't convert
				
				if (num1 < num2) {
					//Sort the scores
					tempScore = num1;
					scoreList[j-1][1] = Integer.toString(num2); 
					scoreList[j][1] = Integer.toString(tempScore);
					
					//Sort the names
					tempName = scoreList[j-1][0];
					scoreList[j-1][0] = scoreList[j][0];
					scoreList[j][0] = tempName;
				}
			}
		}
		
		br.close(); //Close reader
		
		//while (true) { //Reprint the screen every time the user miss-inputs (removes the previous input) 
			clearScreen();
			
			String display = "";
			
			// System.out.println("Top Scores");
			display = "Top Scores" + newLine();
			
			//Display the top scores
			for (int i = 0; i < DISPLAY_TOP; i++) {
				if (i > (scoreList.length - 1)) //If i will be out of bounds in the array
					break;
				if (scoreList[i][0] == null) //If the slot is empty
					break;
					
				//System.out.println("#" + (i+1) + " " + scoreList[i][0] + " " + scoreList[i][1]); //Display score
				display = "#" + (i+1) + " " + scoreList[i][0] + " " + scoreList[i][1] + newLine();
			}
			
			//Generate the user input to go back to the main menu
			//System.out.println(); //New Line
			//System.out.println("1. Back");
			
			display = newLine() + "1. Back";
			
			PrintScreen (display);
			
		/*	try {
				int input = 0;
					
				input = read.nextInt(); //Read line
				if (input == 1) {
					mainMenu(); //Load menu
					break; //Stop while loop
				}
			} catch (Exception e) { //If the user enters an invalid option that throws an error
				read.next(); //Go to the next input
			}
		}*/
	}
	
	//Information Screen
	static void information() throws IOException{
		inSecondaryMenu = true;
		inMenu = false;
		
		//while (true) { //Reprint the screen
			clearScreen();
			
			String display = "";
			
			display = "Information" + newLine() + newLine();
			display += "W: move ship up" + newLine();
			display += "A: move ship left" + newLine();
			display += "S: move ship down" + newLine();
			display += "D: move ship right" + newLine();
			display += "Space: fire bullet" + newLine() + newLine();
			display += "Enemies can be destroyed by either shooting "
					+ newLine() +
					" or colliding into them." + newLine();
			display += "The enemy will be destroyed upon impact "
					+newLine()+
					" but the remainder of its health will be "
					+newLine()+
					" deducted from yours." + newLine();
			display += "Bullets will regenerate over time "
					+newLine()+
					" so use them sparingly." + newLine() + newLine();
			
			/*System.out.println("Information:");
			System.out.println();
			System.out.println("W: move ship up");
			System.out.println("A: move ship left");
			System.out.println("S: move ship down");
			System.out.println("D: move ship right");
			System.out.println("Space: fire bullet");
			System.out.println("Press ENTER after making an input");
			System.out.println();
			System.out.println("Enemies can be destroyed by either shooting "
								+newLine()+
								" or colliding into them.");
			System.out.println("The enemy will be destroyed upon impact "
								+newLine()+
								" but the remainder of its health will be "
								+newLine()+
								" deducted from yours.");
			System.out.println("Bullets will regenerate over time "
								+newLine()+
								" so use them sparingly.");*/
			display += "Your Ship:" + newLine();
			display += "  | " + newLine();
			display += "/|\\" + newLine() + newLine() + newLine();
			display += "Enemies:" + newLine() + newLine();
			display += "Scout: " + enemies[0].SMALL_ENEMY_HEALTH + " hp" + newLine();
			display += "[=]" + newLine() + newLine();
			display += "Cruiser: " + enemies[0].MEDIUM_ENEMY_HEALTH + " hp" + newLine();
			display += "\\=|=/" + newLine();
			display += "  [=]" + newLine() + newLine();
			display += "Dreadnought: " + enemies[0].LARGE_ENEMY_HEALTH + " hp" + newLine();
			display += "\\[=U=|=U=]/" + newLine();
			display += "  \\-[=|=]-/" + newLine();
			display += "     [=]" + newLine() + newLine() + newLine();
			display += "Health Pack:" + newLine();
			display += "+" + newLine() + newLine();
			display += "1. Back" + newLine();
			
			
			
			/*System.out.println();
			System.out.println("Your Ship:");
			System.out.println(" | ");
			System.out.println("/|\\");
			
			System.out.println();
			System.out.println();
			
			System.out.println("Enemies:");
			System.out.println();
			System.out.println("Scout: " + enemies[0].SMALL_ENEMY_HEALTH + " hp");
			System.out.println("[=]");
			System.out.println();
			System.out.println("Cruiser: " + enemies[0].MEDIUM_ENEMY_HEALTH + " hp");
			System.out.println("\\=|=/");
			System.out.println(" [=]");
			System.out.println();
			System.out.println("Dreadnought: " + enemies[0].LARGE_ENEMY_HEALTH + " hp");
			System.out.println("\\[=U=|=U=]/");
			System.out.println(" \\-[=|=]-/");
			System.out.println("    [=]");
			
			System.out.println();
			System.out.println();
			
			System.out.println("Health Pack:");
			System.out.println("+");
			
			System.out.println(); //New Line
			System.out.println("1. Back");*/
			
			PrintScreen (display);
			
			//Generate the user input to go back to the main menu
			/*try {
				int input = 0;
					
				input = read.nextInt();
				if (input == 1) {
					mainMenu();
					break; //Stop while loop
				}
			} catch (Exception e) { //If it throws an error
				read.next(); //Go to the next input
			}
		}*/
	}
		
	//Game Screen
	static void game() throws IOException{
		initializeVariables();
		inGame = true; //Set state to in game
		inSecondaryMenu = false;
		inMenu = false;
		gameOver = false;
		
		String selection = ""; //User's input string
				
		update();
		
		new Thread(new Runnable() { //Enemy spawner
		    public void run() {
		    	while (true){
		    		if (gameOver) //If game is over
		    			break; //Stop loop
		    		
		    		try {
						Thread.sleep(ENEMY_SPAWNER); //Spawn enemy every ENEMY_SPAWNER seconds
					} catch (InterruptedException e) {}
		    		
		    		Enemy enemy = getAvailableEnemy(); //Get an available enemy
		    		if (enemy != null) //If there is an available enemy
		    			spawnEnemy(enemy); //Spawn it
		    		
		    		try {
						update(); //Update screen
					} catch (IOException e) {}
		    	}
		    }
		}).start();
		
		new Thread(new Runnable() { //Bullet respawn timer
		    public void run() {
		    	while (true){
		    		if (gameOver) //If game is over
		    			break; //Stop loop
		    		
		    		try {
						Thread.sleep(BULLET_RESPAWN_TIME); //Give player bullet every BULLET_RESPAWN_TIME seconds
					} catch (InterruptedException e) {}
		    		
		    		addBulletToPool(1); //Add a bullet back to the pool
		    		
		    		try {
						update(); //Update
					} catch (IOException e) {}
		    	}
		    }
		}).start();
		
		new Thread(new Runnable() { //Health pack spawner
		    public void run() {
		    	while (true){
		    		if (gameOver) //If game is over
		    			break; //Stop loop
		    		
		    		try {
						Thread.sleep(HEALTH_PACK_SPAWNER); //Spawn health pack every HEALTH_PACK_SPAWNER seconds
					} catch (InterruptedException e) {}
		    		
		    		Object pack = getAvailableObject(healthPacks); //Get an available pack
		    		if (pack != null) //If there is an available pack
		    			spawnHealthPack(pack); //Spawn it
		    		try {
						update();
					} catch (IOException e) {}
		    	}
		    }
		}).start();
		
		/*while (true) { //User's movement inputs
			selection = read.nextLine(); //Read input			
			
			//I'm using contains to make the input system a little more foolproof
			//since sometimes you enter duplicates especially since the input is always disappearing due to updates
			try {
				if (!gameOver) {
					if (selection.contains("w")) {
						movePlayer(0,-1); //Move one space up
					}
					if (selection.contains("s")) {
						movePlayer(0,1); //Move one space down
					}
					if (selection.contains("d")) {
						movePlayer(1,0); //Move 1 space right
					}
					if (selection.contains("a")) {
						movePlayer(-1,0); //Move 1 space left
					}
					
					if (selection.contains(" ")) {
						fireBullet(); //Fire a bullet
					}
				}
				else { //If the game is over
					if (selection.contains("1")){
						restart(); //Restart game
						break;
					}
					else if (selection.contains("2")){
						mainMenu(); //Load main menu
						break;
					}
				}
			} catch (Exception e) { //If it throws an error
				read.next(); //Go to the next input
			}
			
			update(); //Update
			
			if (!inGame) //If not in game
				break; //Stop loop
		}*/
	}
	
	static void addBulletToPool(int amt){ //Adds/removes a bullet from the queue
		if ((bulletAmt + amt) > BULLET_MAX_AMOUNT) //If it goes above the max
			bulletAmt = BULLET_MAX_AMOUNT; //Set bullet pool to max size
		else if ((bulletAmt + amt) < 0) //If it goes below zero
			bulletAmt = 0; //Set bullet pool to min size
		else 
			bulletAmt += amt; //Add amount to the queued bullet count 
	}
	
	//Checks the IDE / what OS is being used and returns the appropriate new-line character
	static String newLine() { 
		return "<br>";
		
		/*
		if (USING_ECLIPSE) //If using the Eclipse IDE
			return "\r";
		else {
			//This stuff doesn't work with the console inside eclipse
			final String os = System.getProperty("os.name");
	        if (os.contains("Windows"))
				return "\n";
			else //Linux, Mac, Unix, etc
				return "\n";
		}*/
	}
	
	//Updates the screen 
	static void update() throws IOException{
		/* A giant string of the whole screen which is constructed throughout this function 
		 * and then displayed at the end.
		   This reduces a bunch of print calls which causes annoying graphical impurities. */ 
		
		if (runningUpdate || inSecondaryMenu || inMenu) //If the update is already running or the game is over/another menu is open
			return; //Do nothing
		
		String display = ""; //The screen to display
		
		runningUpdate = true; //Run the update (causes other calls to update to be ignored it it's already updating the screen)
			
		clearScreen(); //Remove the last frame from on-screen
			
		display += newLine() + "Score: " + score +  " Health: " + player.health + " Bullets: " + bulletAmt + newLine();
		
		for (int i = 0; i < size*2; i++) {
			//Top Border
			display += "=";
		}
		display += newLine();
			
		checkCollisions(); //Check for collisions
			
		initializeScreen(" "); //Make the screen blank
			
		for (int y = 0; y < screen.length; y++) { //Draw objects (player, enemies, bullets, etc) on screen space
			for (int x = 0; x < screen[y].length; x++){
				if (!gameOver) { //Make sure game isn't over
					//Display the player's character
					//Looks like
					//  | 
					// /|\
					
					if (player.x == x && player.y == (y+1))
						screen [y][x] = "|"; //Nose
					if (player.x == x && player.y == y) 
						screen [y][x] = "|"; //Body
					if (player.x == (x-1) && player.y == y)
						screen [y][x] = "\\"; //Right wing
					if (player.x == (x+1) && player.y == y)
						screen [y][x] = "/"; //Left wing
					
					
					//Display the enemy
					for (int i = 0; i < enemies.length; i++){ //Iterate through the enemies
						if (!enemies[i].dead) { //If not dead
							String displayData = enemies[i].getDisplayData(x, y); //Get the display data of the enemy
							if (displayData != null) { //If a part of the enemy ship is in that location
								screen[y][x] = displayData; //Add it to the screen
							}
						}
					}
					
					//Display bullets
					for (int i = 0; i < bullets.length; i++) { //Iterate through the bullets
						if (bullets[i].active){ //If bullet is being used
							if (bullets[i].x == x && bullets[i].y == y) {
								screen[y][x] = "*"; //The bullet
							}
						}
					}
						
						
					//Display health packs
					for (int i=0; i < healthPacks.length; i++) {
						if (healthPacks[i].active) { //If health pack is on screen
							if (healthPacks[i].x == x && healthPacks[i].y == y) {
								screen[y][x] = "+"; //The health pack
							}
						}
					}
				}
					
				display += screen[y][x]; //Add the screen to the display string
				}
			
				display += "|"; //Right side border
				display += newLine(); //New line
			}
			
			for (int i = 0; i < size*2; i++) {
				//Bottom border
				display += "=";
			} 
			
			display += newLine();
			
			if (gameOver){ //Display death text
				display += newLine() + "GAMEOVER!"
						+ newLine() + "1. Retry"
						+ newLine() + "2. Menu"
						+ newLine();
				
			}
			
			display += "Enter Inputs:" + newLine(); //Enter input prompt
			
			PrintScreen (display); // Print the screen
			// System.out.print(display); //Display the entire screen
			
			runningUpdate = false; //Done running update
		}
	
	static void checkCollisions () throws IOException{
		if (gameOver) //If the game is over
			return; //Do nothing
		
		int[][] pLoc = player.getHitLocations(); //Get the player's hit locations
		
		for (int i = 0; i < enemies.length; i++){ //Iterate through each enemy
			boolean enemyCollide = false;
			if (!enemies[i].dead){ //If current enemy isn't dead
				int[][] eLoc = enemies[i].getHitLocations(); //Get the enemies hit locations
				
				if (eLoc != null) { //If the ship doesn't exist					
					for (int j = 0; j < eLoc.length; j++) {
						//Check for collisions between the player and an enemy
						for (int k = 0; k < pLoc.length; k++){
							if (pLoc[k][0] == eLoc[j][0] && pLoc[k][1] == eLoc[j][1]) { //If they overlap
								if (!enemyCollide) { //If the player has already collided with the current enemy
									enemyCollide = true; //Make sure the player only gets hit once by the same enemy
									player.takeDamage(enemies[i].health); //Take the damage of what the enemy's remaining health is //TODO: useful point
									enemies[i].dead = true; //Kill the enemy
									score += enemies[i].reward; //Add to the score
									
									if (player.dead) {//If the player died from that
										gameOver = true; //Set the game to done
										saveScore(); //Save the score to the file
										//update();
									}
								}
							}
						}
						
						//Check for collisions between a bullet and an enemy
						for (int l = 0; l < bullets.length; l++) { 
							int[] bLoc = bullets[l].getHitLocation(); //The the bullet's hit locations
	 
							if (bLoc[0] == eLoc[j][0] && bLoc[1] == eLoc[j][1]) { //If they overlap
								if (bullets[l].active) {
									bullets[l].active = false; //Deactivate bullet
									enemies[i].takeDamage(bullets[l].amount); //The enemy takes the damage amount of that bullet
									
									if (enemies[i].dead) //If the enemy died from that
										score += enemies[i].reward; //Add to the score
								}
							}
						}
					}
				}
			}
		}
		
		//Check for health pack collisions
		for (int i = 0; i < healthPacks.length; i++) { //Iterate through health packs
			int[] hLoc = healthPacks[i].getHitLocation();
			
			if (healthPacks[i].active) { //If the health pack is active
				for (int j = 0; j < pLoc.length; j++){ //Iterate through each player ship position
					if (hLoc[0] == pLoc[j][0] && hLoc[1] == pLoc[j][1]) { //If it collides
						healthPacks[i].active = false; //Deactivate health pack
						
						player.takeDamage(-healthPacks[i].amount); //Add health to the player
					}
				}
				
				
				for (int k = 0; k < bullets.length; k++) { //Iterate through each bullet
					int[] bLoc = bullets[k].getHitLocation();
					
					if (bullets[k].active) { //If the bullet is active
						if (hLoc[0] == bLoc[0] && hLoc[1] == bLoc[1]) { //If it collides
							bullets[k].active = false; //Deactivate bullet
							
							healthPacks[i].active = false; //Deactivate health pack
							
							player.takeDamage(-healthPacks[i].amount); //Add health to the player
						}
					}
				}
			}
		}
		//update(); //Update the frame
	}
	
	static void saveScore() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("scores.csv", true)); //Get the file
		
		String text = name + "," + score + "\r"; //Make an entry 
		bw.write(text); //Write to file
		
		bw.close(); //Close writer
	}
	
	static Enemy getAvailableEnemy(){ //Returns an available enemy (one that isn't on-screen and active)
		Enemy answer = null; //Default to null
		
		for (int i = 0; i < enemies.length; i++){
			if (enemies[i].dead){ //If enemy is dead
				answer = enemies[i]; //Use enemy
				break; //Exit for loop
			}
		}
		
		return answer; //Return the answer
	}
	
	static void spawnEnemy(Enemy enemy){ //Spawns an enemy
		Random r = new Random(); //Initialize random

		enemy.resetHealth(); //Reset health
		
		enemy.dead = false; //Set the enemy to alive
		
		int min = 0;
		int max = size * 2;
		
		// Set the spawning constraints so the enemies do not spawn partially off-screen
		// In hindsight, this is better than what I had before (spawning the enemies partially clipped off-screen)
		// But I'm mainly doing it to fix a bug that I can't figure out for the life of me
		// (I think it has something to do with the JLabel that represents the game screen)
		// TODO: this is bad coding style, fix so it accesses the enemy for the width info (atm these were worked out thru trial and error)
		if (enemy.type == EnemyType.SMALL) {
			min = 2;
			max -= 2;
		}
		else if (enemy.type == EnemyType.MEDIUM) {
			min = 3;
			max -= 3;
		}
		else if (enemy.type == EnemyType.LARGE) {
			min = 6;
			max -= 6;
		}
		
		
		int xPos = r.nextInt((max - min) + 1) + min;
		
		//int xPos = r.nextInt(size*2); //Random between 0 and size*2
		
		enemy.x = xPos; //Set to enemy's x 
		enemy.y = 0; //Set y to zero (top of the screen)
		
		new Thread(new Runnable() { //Move enemy
		    public void run() {
		    	while (true){ 
		    		try {
						Thread.sleep(ENEMY_SPEED); //Enemy speed
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		
		    		
		    		enemy.y++; //Move down one
		    		if (enemy.y > size || enemy.dead) { //If it hits the bottom of the screen or is dead
		    			enemy.dead = true; //Set to dead
		    			break; //Stop the loop
		    		}
		    		
		    		try {
						update();
					} catch (IOException e) {}
		    		
		    		if (gameOver) //If game is over
		    			break; //Stop loop
		    	}
		    }
		}).start();
	}
	
	static void spawnHealthPack (Object pack) {
		Random r = new Random(); //Initialize random

		pack.active = true; //Set the pack to active
		
		int xPos = r.nextInt(size*2); //Random between 0 and size*2
		
		pack.x = xPos; //Set to enemy's x 
		pack.y = 0; //Set y to zero (top of the screen)
		
		new Thread(new Runnable() { //Move enemy
		    public void run() {
		    	while (true){ 
		    		try {
						Thread.sleep(HEALTH_PACK_SPEED); //Enemy speed
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		
		    		
		    		pack.y++; //Move down one
		    		if (pack.y > size || !pack.active) { //If it hits the bottom of the screen or is dead
		    			pack.active = false; //Set to dead
		    			break; //Stop the loop
		    		}
		    		
		    		try {
						update();
					} catch (IOException e) {}
		    		
		    		if (gameOver) //If game is over
		    			break; //Stop loop
		    	}
		    }
		}).start();
	}
	
	static void movePlayer(int xAmt, int yAmt){ //Moves the player by an amount
		//Check if player is in bounds
		//if ((player.x+xAmt) < 0)
		if ((player.x+xAmt) < 2)
			player.x = 2; //Keep player from moving out of bounds
		else if ((player.x+xAmt) >= size*2)
			player.x = (size*2)-1; //Keep player from moving out of bounds 
		else 
			player.x += xAmt; //Move the player horizontally 
		
		//On the y-axis
		if ((player.y+yAmt) < 1)
			player.y = 1;
		else if ((player.y+yAmt) >= size)
			player.y = size - 1;
		else
			player.y += yAmt; //Move the player horizontally
		
	}
	
	static void fireBullet() throws IOException { //Fires a bullet
		Object bullet = getAvailableObject(bullets); //Gets an available bullet
		
		if (bullet != null && bulletAmt > 0) { //If bullet exists and the bullet amount is greater than zero
			bullet.active = true; //Set to alive
			
			//Spawn in front of the player
			bullet.x = player.x; 
			bullet.y = player.y - 2;
			
			addBulletToPool(-1); //Remove bullet from the pool 
			
			update(); //Update screen
			
			new Thread(new Runnable() { //Move bullet
			    public void run() {
			    	while (true){ 
			    		try {
							Thread.sleep(BULLET_SPEED); //Bullet speed
						} catch (InterruptedException e) {}
			    		
			    		bullet.y--; //Move up one
			    		if (bullet.y < 0 ) { //If it hits the bottom of the screen or is not active
			    			bullet.active = false; //Set to non-active
			    			break; //Stop the loop
			    		}
			    		
			    		try {
							update();
						} catch (IOException e) {}
			    		
			    		if (gameOver) //If game is over
			    			break; //Stop loop
			    	}
			    }
			}).start();
		}
	}
	
	static Object getAvailableObject(Object[] arr) { //Returns an available object of from the array passed through as a parameter 
		Object answer = null; //Default to null 
		
		for (int i = 0; i < arr.length; i++){
			if (!arr[i].active) { //If bullet isn't active
				answer = arr[i]; //Set to the bullet at index i
				break; //Exit for loop
			}
		}
		
		return answer; //Return the object
	}

	static void initializeScreen(String character){ //Initializes playing field with a character
		for (int y = 0; y < screen.length; y++) {
			for (int x = 0; x < screen[y].length; x++){
				screen [y][x] = character;
				//System.out.print(screen[y][x]);
			}
		}
	}
	
	public static void clearScreen() { //Clears the console
		// DOnt need to do anything because its windowed now
		//GameWindow.PrintScreen(""); // Just set the screen to blank
		
		/*
		 if (USING_ECLIPSE) //If using the Eclipse IDE
			//It's more of a workaround since it only simulates a "clear" screen
			for (int i = 0; i < 50; ++i) System.out.println(); //Add 50 \n
		else {
			//This stuff doesn't work with the console inside eclipse only in an actual console
			final String os = System.getProperty("os.name");
	        if (os.contains("Windows"))
				try {
					new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				} catch (InterruptedException e) {} catch (IOException e) {}
			else
				try {
					Runtime.getRuntime().exec("clear");
				} catch (IOException e) {}
		}
		*/
	}
}


class Player { //The player's class
	//Variables
	int health = 1;
	boolean dead = false;
	
	int x = 0, y = 0; //Position cooridnates
	
	Player (int h, int xPos, int yPos){ //Constructor
		health = h; //Initial health
		
		//Initial position
		x = xPos;
		y = yPos;
	}
	
	int[][] getHitLocations (){ //Returns a 2D array of all the coordinates the player can currently get hit at
		int[][] answer = new int[4][2]; //Create a 4 by 2 array
		
		answer[0][0] = x;
		answer[0][1] = y;
		
		answer[1][0] = x;
		answer[1][1] = y-1;
		
		answer[2][0] = x-1;
		answer[2][1] = y;
		
		answer[3][0] = x+1;
		answer[3][1] = y;
		
		return answer; //Return hit locations
	}
	
	void takeDamage (int amt){ //Take/receive health
		health -= amt; //Remove health
		
		if (health <= 0) { //If health is zero of below
			dead = true; //Set to dead
			health = 0; //Set health to zero
		}
	}
}

enum ObjectType { //Possible behavior types of the object class
	BULLET, HEALTH_PACK
}

class Object {
	//Variables
	int amount = 1;
	boolean active = false;
	
	ObjectType behaviorType; //The object behavior type (bullet or health pack)
	
	int x = 0, y = 0;
	
	Object (int amt, ObjectType type){ //Constructor
		amount = amt; //Set amount
		
		behaviorType = type; //Set the behavior type
	}
	
	int[] getHitLocation(){ //Returns the object's hit locations
		int[] answer = new int[2]; //Create an array
		
		//Set values
		answer[0] = x;
		answer[1] = y;
		
		return answer; //Return locations
	}
}


enum EnemyType { //An enum to for the list of ship sizes
	SMALL, MEDIUM, LARGE 
}

class Enemy { //The enemy class
	final int SMALL_ENEMY_HEALTH = 1, MEDIUM_ENEMY_HEALTH = 2, LARGE_ENEMY_HEALTH = 5; //Enemy health values
	final int SMALL_ENEMY_REWARD = 1, MEDIUM_ENEMY_REWARD = 3, LARGE_ENEMY_REWARD = 10; //Reward values for different classes of enemies
	
	//Variables
	int health = 1;
	boolean dead = true;
	int reward = 1; //The score increase amount you get for killing this ship
	
	public EnemyType type = EnemyType.SMALL; //Default ship size to small
	
	int x = 0, y = 0; //Position coordinates
	
	Enemy (EnemyType t){ //Constructor
		dead = true; //Set to dead (idle)
		type = t; //Set the enemy's type
		
		resetHealth(); //Reset it's health to it's defaults
		
		//Set the reward amount based off the enemy's type
		if (type == EnemyType.SMALL) {
			reward = SMALL_ENEMY_REWARD;
		}
		else if (type == EnemyType.MEDIUM) {
			reward = MEDIUM_ENEMY_REWARD;
		}
		else if (type == EnemyType.LARGE) {
			reward = LARGE_ENEMY_REWARD;
		}
	}
	
	int[][] getHitLocations (){ //Returns a 2D array of all the coordinates the player can currently get hit at
		int[][] answer = null; //Default to null
		
		if (type == EnemyType.SMALL) {
			//Looks like
			// [=]
			
			answer = new int[3][2]; 
			int c = 0; //counter
			answer = new int[3][2]; 
			for (int i=-1; i <= 1; i++) { //First and only row
				answer[c][0] = x + i;
				answer[c][1] = y;
				c++;
			}
		}
		else if (type == EnemyType.MEDIUM) {
			//Looks like
			// \=|=/
			//  [=]
			
			int c = 0; //counter
			answer = new int[8][2];
			for (int i=-2; i <= 2; i++) { //First row
				answer[c][0] = x + i;
				answer[c][1] = y;
				c++;
			}
			for (int i=-1; i <= 1; i++) { //Second row
				answer[c][0] = x + i;
				answer[c][1] = y + 1;
				c++;
			}
		}
		else if (type == EnemyType.LARGE) {
			//Looks like
			// \[=U=|=U=]/
			//  \-[=|=]-/
			//     [=]
			
			int c = 0;
			answer = new int[23][2];
			for (int i=-5; i <= 5; i++) { //First row
				answer[c][0] = x + i;
				answer[c][1] = y;
				c++;
			}
			for (int i=-4; i <= 4; i++) { //Second row
				answer[c][0] = x + i;
				answer[c][1] = y + 1;
				c++;
			}
			for (int i=-1; i <= 1; i++) { //Third row
				answer[c][0] = x + i;
				answer[c][1] = y + 2;
				c++;
			}
		}
		return answer; //Return the hit locations
	}
	
	void takeDamage (int amt){
		health -= amt;
		
		if (health <= 0) {
			dead = true;
			health = 0;
		}
	}
	
	String getDisplayData (int xPos, int yPos) { //Returns the the part of the enemy ship that matches the location given
		if (type == EnemyType.SMALL) {
			//Looks like
			// [=]
			
			if (xPos == x && yPos == y)
				return "="; //Body
			else if (xPos == x-1 && y == yPos)
				return "["; //Left wing
			else if (xPos == x+1 && yPos == y)
				return "]"; //Right wing
		
		}
		else if (type == EnemyType.MEDIUM) {
			//Looks like
			// \=|=/
			//  [=] 
			
			if (xPos == x && yPos == y)
				return "|"; 
			else if ((xPos == x-1 && yPos == y) || (xPos == x+1 && yPos == y) || (xPos == x && yPos == y+1)) //All the "=" characters in the ship model
				return "="; 
			else if (xPos == x+2 && yPos == y)
				return "/";
			else if (xPos == x-2 && yPos == y)
				return "\\";
			else if (xPos == x-1 && yPos == y+1)
				return "[";
			else if (xPos == x+1 && yPos == y+1)
				return "]";
		}
		else if (type == EnemyType.LARGE) {
			//Looks like 
			// \[=U=|=U=]/
			//  \-[=|=]-/
			//     [=]
			
			if ((xPos == x && yPos == y) || (xPos == x && yPos == y+1))
				return "|";
			else if ((xPos == x+1 && yPos == y) || (xPos == x-1 && yPos == y) 
					|| (xPos == x+3 && yPos == y) || (xPos == x-3 && yPos == y)
					|| (xPos == x+1 && yPos == y+1) || (xPos == x-1 && yPos == y+1)
					|| (xPos == x && yPos == y+2)) //The "=" characters
				return "=";
			else if ((xPos == x+4 && yPos == y) || (xPos == x+2 && yPos == y+1) || (xPos == x+1 && yPos == y+2))
				return "]";
			else if ((xPos == x-4 && yPos == y) || (xPos == x-2 && yPos == y+1) || (xPos == x-1 && yPos == y+2))
				return "[";
			else if ((xPos == x-5 && yPos == y) ||(xPos == x-4 && yPos == y+1))
				return "\\";
			else if ((xPos == x+5 && yPos == y) ||(xPos == x+4 && yPos == y+1))
				return "/";
			else if ((xPos == x+2 && yPos == y) || (xPos == x-2 && yPos == y))
				return "U";
			else if ((xPos == x+3 && yPos == y+1) || (xPos == x-3 && yPos == y+1))
				return "-";
		}
		
		return null; //In the case that is is anything else, it'll just return null
	}
	
	void resetHealth () { //Resets the enemy's health
		if (type == EnemyType.SMALL)
			health = SMALL_ENEMY_HEALTH;
		else if (type == EnemyType.MEDIUM)
			health = MEDIUM_ENEMY_HEALTH;
		else if (type == EnemyType.LARGE)
			health = LARGE_ENEMY_HEALTH;
	}
}