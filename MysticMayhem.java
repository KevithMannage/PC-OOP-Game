import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

//marker interfaces
interface Highlander{}
interface Marshlander{}
interface Sunchild{}
interface Mystic{}

class InputManager {
    private static Scanner scanner = new Scanner(System.in);

    public static int integerInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static String stringInput() {
        return scanner.nextLine().trim();
    }

}

class FileManager {
	public static void savePlayerMap(Map<Integer,Player> players) {
        //writing the serialized player objects to the file
        try{
            FileOutputStream filestream = new FileOutputStream("PlayerMap.ser");
            ObjectOutputStream os = new ObjectOutputStream(filestream);
            os.writeObject(players);
            os.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

	@SuppressWarnings("unchecked")
	public static Map<Integer, Player> loadPlayerMap() {
		Map<Integer,Player> players_got_from_file = new HashMap<Integer,Player>();
        try{
            FileInputStream filestream2 = new FileInputStream("PlayerMap.ser");
            ObjectInputStream os2 = new ObjectInputStream(filestream2);
            players_got_from_file = (Map<Integer,Player>)os2.readObject();
            os2.close();
        }
        catch(Exception e){
        	return players_got_from_file;
        }
		return players_got_from_file;
	}
	public static void saveNameMap(Map<String,Integer> usernames) {
        //writing the serialized player objects to the file
        try{
            FileOutputStream filestream = new FileOutputStream("usernames.ser");
            ObjectOutputStream os = new ObjectOutputStream(filestream);
            os.writeObject(usernames);
            os.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

	@SuppressWarnings("unchecked")
	public static Map<String,Integer> loadNameMap() {
		Map<String,Integer> players_got_from_file = new HashMap<String,Integer>();
        try{
            FileInputStream filestream2 = new FileInputStream("usernames.ser");
            ObjectInputStream os2 = new ObjectInputStream(filestream2);
            players_got_from_file = (Map<String,Integer>)os2.readObject();
            os2.close();
        }
        catch(Exception e){
        	return players_got_from_file;
        }
		return players_got_from_file;
	}
	public static void saveNumOfPlayers(int n) {
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("number.dat"))) {
	        dos.writeInt(n);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public static int retrieveNumOfPlayers() {
		int numOfPlayers = 0;
        try (DataInputStream dis = new DataInputStream(new FileInputStream("number.dat"))) {
            numOfPlayers = dis.readInt();
        } catch (IOException e) {
        	return numOfPlayers;
        }
        return numOfPlayers;
	}
}
abstract class Equipment implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//all attributes are private
    //all attributes are read only once created
    private String name;
    private int price;
    private int attackModifier;
    private int defenceModifier;
    private int healthModifier;
    private int speedModifier;

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getAttackModifier() {
        return attackModifier;
    }
    public int getDefenceModifier() {
        return defenceModifier;
    }
    public int getHealthModifier() {
        return healthModifier;
    }
    public int getSpeedModifier() {
        return speedModifier;
    }

    public Equipment(String name, int price, int attackModifier, int defenceModifier, int healthModifier, int speedModifier) {
        this.name = name;
        this.price = price;
        this.attackModifier = attackModifier;
        this.defenceModifier = defenceModifier;
        this.healthModifier = healthModifier;
        this.speedModifier = speedModifier;
    }
}

abstract class Armour extends Equipment {
	private static final long serialVersionUID = 1L;

	// Armour has 0 attack modifier
    public Armour(String name, int price, int defenceModifier,int HealthModifier, int speedModifier) {
        super(name, price, 0, defenceModifier,HealthModifier, speedModifier);
    }
}

abstract class Artefact extends Equipment {
	private static final long serialVersionUID = 1L;

	public Artefact(String name, int price, int attackModifier, int defenceModifier, int healthModifier, int speedModifier) {
        super(name, price, attackModifier, defenceModifier, healthModifier, speedModifier);
    }
}


abstract class Archer extends Character {
	private static final long serialVersionUID = 1L;

	public Archer(String name, int price, int attack, int defence, int health, int speed) {
        super(name, price, attack, defence, health, speed);
    }

    @Override
    public String getCatagory() {
        return "Archer";
    }
}

abstract class Knight extends Character {
	private static final long serialVersionUID = 1L;

	public Knight(String name, int price, int attack, int defence, int health, int speed) {
        super(name, price, attack, defence, health, speed);
    }

    @Override
    public String getCatagory() {
        return "Knight";
    }
}

abstract class Mage extends Character {
	private static final long serialVersionUID = 1L;

	public Mage(String name, int price, int attack, int defence, int health, int speed) {
        super(name, price, attack, defence, health, speed);
    }

    @Override
    public String getCatagory() {
        return "Mage";
    }
}

abstract class Healer extends Character {
	private static final long serialVersionUID = 1L;

	public Healer(String name, int price, int attack, int defence, int health, int speed) {
        super(name, price, attack, defence, health, speed);
    }

    @Override
    public String getCatagory() {
        return "Healer";
    }

    public void attack(Player targetPlayer, Character lowestHealthCharacter) {
    }
}

abstract class MythicalCreature extends Character {
	private static final long serialVersionUID = 1L;

	public MythicalCreature(String name, int price, int attack, int defence, int health, int speed) {
        super(name, price, attack, defence, health, speed);
    }

    @Override
    public String getCatagory() {
        return "Mythical Creature";
    }
}


abstract class Character implements Serializable  {
	private static final long serialVersionUID = 1L;
    private String name;
    private int originalPrice;
    private int price;
    private int attack;
    private int defence;
	private double health;
    private int speed;
    private Armour armour;
    private Artefact artifact;
    private double currentHealth;
    private boolean bonusTurn = false;
    private boolean autoHeal = false;

    public Character(String name, int price, int attack, int defence, double health, int speed) {
        this.name = name;
        this.originalPrice = price;
        this.price = price;
        this.attack = attack;
        this.defence = defence;
        this.health = health;
        this.speed = speed;
        this.currentHealth = health;
    }

    public abstract String getCatagory();
    public void equipArmour(Armour armour) {
        this.armour = armour;
        this.defence += armour.getDefenceModifier();
        this.speed += armour.getSpeedModifier();
        this.health += armour.getHealthModifier();
    }

    public void equipArtefact(Artefact artifact) {
        this.artifact = artifact;
        this.attack += artifact.getAttackModifier();
        this.defence += artifact.getDefenceModifier();
        this.health += artifact.getHealthModifier();
        this.speed += artifact.getSpeedModifier();
    }
    
    public String getName() {
		return name;
	}
	public int getPrice() {
		return price;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Armour getArmour() {
		return armour;
	}

	public Artefact getArtefact() {
		return artifact;
	}
	public void addEquipmentPrize() {
		this.price = this.originalPrice;
		if(!(armour == null)) {
			this.price += (int)Math.round(0.2*armour.getPrice());
		}
		if(!(artifact == null)) {
			this.price += (int)Math.round(0.2*artifact.getPrice());
		}
	}
    @Override
    public String toString() {
        return  getCatagory() + "::  name : " + name +
                ", price : " + price +
                ", attack : " + attack +
                ", defence : " + defence +
                ", health : " + health +
                ", speed : " + speed +
                ", armour : " + armour +
                ", artifact : " + artifact;
    }

    public void addTerrainEffect(String battleGround) {
        switch (battleGround) {
            case "Hillcrest":
                if (this instanceof Highlander) {
                    System.out.println(this.getName()+" : Attack and Defence increased by 1, Bonus Turn enabled.");
                    attack += 1;
                    defence += 1;
                    bonusTurn = true;
                }
                if (this instanceof Marshlander || this instanceof Sunchild) {
                    System.out.println(this.getName()+" : Speed decreased by 1.");
                    speed -= 1;
                }
                break;
            case "Marshland":
                if (this instanceof Marshlander) {
                    System.out.println(this.getName()+" : Defence increased by 2.");
                    defence += 2;
                }
                if (this instanceof Sunchild) {
                    System.out.println(this.getName()+" : Attack decreased by 1.");
                    attack -= 1;
                }
                if (this instanceof Mystic) {
                    System.out.println(this.getName()+" : Speed decreased by 1.");
                    speed -= 1;
                }
                break;
            case "Desert":
                if (this instanceof Marshlander) {
                    System.out.println(this.getName()+" : Health decreased by 1.");
                    health -= 1;
                    currentHealth -= 1;
                }
                if (this instanceof Sunchild) {
                    System.out.println(this.getName()+" : Attack increased by 1.");
                    attack += 1;
                }
                break;
            case "Arcane":
                if (this instanceof Highlander || this instanceof Marshlander) {
                    System.out.println(this.getName()+" : Speed and Defence decreased by 1.");
                    speed -= 1;
                    defence -= 1;
                }
                if (this instanceof Mystic) {
                    System.out.println(this.getName()+" : Attack increased by 2, Auto Heal enabled.");
                    attack += 2;
                    autoHeal = true;
                }
                break;
                // Additional cases for other terrains
            default:
                System.out.println("No terrain effects for: " + battleGround);
                break;
        }
    }
	public void revertTerrainEffect(String battleGround) {
		switch (battleGround) {
        case "Hillcrest":
        	if(this instanceof Highlander) {
        		attack -= 1;
        		defence -= 1;
        		bonusTurn = false;
        	}
        	if(this instanceof Marshlander) {
        		speed += 1;
        	}
        	if(this instanceof Sunchild) {
        		speed += 1;
        	}
        	break;
        case "Marshland":
        	if(this instanceof Marshlander) {
        		defence -= 2;
        	}
        	if(this instanceof Sunchild) {
        		attack += 1;
        	}
        	if(this instanceof Mystic) {
        		speed += 1;
        	}
        	break;
        case "Desert":
        	if(this instanceof Marshlander) {
        		health += 1;
        		currentHealth += 1;
        	}
        	if(this instanceof Sunchild) {
        		attack -= 1;
        	}
        	break;
        case "Arcane":
        	if(this instanceof Highlander) {
        		speed += 1;
        		defence += 1;
        	}
        	if(this instanceof Marshlander) {
        		speed += 1;
        		defence += 1;
        	}
        	if(this instanceof Mystic) {
        		attack -= 2;
        		autoHeal = false;
        	}
        	break;
        	// Additional cases for other terrains
        default:
            System.out.println("No terrain effects for: " + battleGround);
            break;
            }
	}

	public void resetHealth() {
		this.currentHealth = health;
	}

	public double getCurrentHealth() {
		return currentHealth;
	}

	public void takeDamage(double damage) {
		this.currentHealth -= damage;
		if (!isAlive()) {
			System.out.println(name+" died!");
		}
	}
	public void heal(double hp) {
		this.currentHealth += hp;
		if (this.currentHealth > health) {
			this.currentHealth = health;
		}
	}
	
	public boolean isAlive() {
		return this.currentHealth > 0;
	}
	public boolean hasBonusTurn() {
		return bonusTurn;
	}
	public boolean hasAutoHeal() {
		return autoHeal;
	}
}

class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	public static int numOfPlayers = 0;
	// name, goldCoins are read and write
	// username, userID, homeground are read only
    private String name;
	private String username;
    private int userID;
    private int xpLevel;
	private int goldCoins;
    private Map<String, Character> army;
    private String homeground;
    
    public Map<String, Character> getArmy() {
		return army;
	}
	public void addCharacter(Character newchar) {
    	//adds a given character replacing any current character of the sub class
    	army.put(newchar.getCatagory(), newchar);
    }
    public Player(String name, String username, String homeground) {
        this.name = name;
        this.username = username;
        this.homeground = homeground;
        this.userID = numOfPlayers;
        this.xpLevel = 0;
        this.goldCoins = 500;//initial 500 gold
        this.army = new HashMap<>();
        numOfPlayers ++;
    }

    public Player(String name, String username, String homeground, int xp, int gc) {
    	this.name = name;
        this.username = username;
        this.homeground = homeground;
        this.userID = numOfPlayers;
        this.xpLevel = xp;
        this.goldCoins = gc;
        this.army = new HashMap<>();
        numOfPlayers ++;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public int getUserID() {
		return userID;
	}
	public int getGoldCoins() {
		return goldCoins;
	}
    public void recieveGoldCoins(int coins) {
        this.goldCoins += coins;
        System.out.println(username + " recieved" + coins +" coins.");
    }
    public void spendGoldCoins(int coins) {
        this.goldCoins -= coins;
        System.out.println(username + " spent " + coins +" coins.");
    }
    public void addXp() {
        this.xpLevel++;
        System.out.println(username + "'s xp level upgraded");
    }
    public int getXpLevel() {
        return this.xpLevel;
    }
    public String getHomeground() {
		return homeground;
	}
	public void displayArmy() {
        System.out.println(name + "'s Army:");
        for (Character character : army.values()) {
            System.out.println(character);
        }
    }
	public void displayStats() {
		    System.out.println("Name    : " + name);
		    System.out.println("XP Level: " + xpLevel);
		    System.out.print("Army    : ");
		    for(Character c: army.values()) {
		    	System.out.print(c.getName()+"--");
		    }
		    System.out.println("\n");
	}
	public Character leastHealthCharacter() {
	    if (army.isEmpty()) {
	        // If the army is empty, return null or handle the case appropriately
	        return null;
	    }

	    Character leastHealthChar = null;
	    double minHealth = Integer.MAX_VALUE;

	    for (Character character : army.values()) {
	        double curhealth = character.getCurrentHealth();
	        if (curhealth < minHealth && character.isAlive()) {
	            minHealth = curhealth;
	            leastHealthChar = character;
	        }
	    }

	    return leastHealthChar;
	}
}


//Archer sub classes
class Shooter extends Archer implements Highlander {
 private static final long serialVersionUID = 1L;
 public Shooter() {super("Shooter", 80, 11, 4, 6, 9);}}

class Ranger extends Archer implements Highlander {
 private static final long serialVersionUID = 1L;
 public Ranger() { super("Ranger", 115, 14, 5, 8, 10); }}

class Sunfire extends Archer implements Sunchild {
 private static final long serialVersionUID = 1L;
 public Sunfire() { super("Sunfire", 160, 15, 5, 7, 14); }}

class Zing extends Archer implements Sunchild {
 private static final long serialVersionUID = 1L;
 public Zing() { super("Zing", 200, 16, 9, 11, 14); }}

class Saggitarius extends Archer implements Mystic {
 private static final long serialVersionUID = 1L;
 public Saggitarius() { super("Saggitarius", 230, 18, 7, 12, 17); }}

//Knight sub classes
class Squire extends Knight implements Marshlander {
 private static final long serialVersionUID = 1L;
 public Squire() { super("Squire", 85, 8, 9, 7, 8); }}

class Cavalier extends Knight implements Highlander {
 private static final long serialVersionUID = 1L;
 public Cavalier() {super("Cavalier", 110, 10, 12, 7, 10);}}

class Templar extends Knight implements Sunchild {
 private static final long serialVersionUID = 1L;
 public Templar() { super("Templar", 155, 14, 16, 12, 12);}}

class Zoro extends Knight implements Highlander {
 private static final long serialVersionUID = 1L;
 public Zoro() { super("Zoro", 180, 17, 16, 13, 14);}}

class Swiftblade extends Knight implements Marshlander {
 private static final long serialVersionUID = 1L;
 public Swiftblade() {super("Swiftblade", 250, 18, 20, 17, 13);}}

//Mage sub classes
class Warlock extends Mage implements Marshlander {
 private static final long serialVersionUID = 1L;
 public Warlock() {super("Warlock", 100, 12, 7, 10, 12);}}

class Illusionist extends Mage implements Mystic {
 private static final long serialVersionUID = 1L;
 public Illusionist() {super("Illusionist", 120, 13, 8, 12, 4);}}

class Enchanter extends Mage implements Highlander {
 private static final long serialVersionUID = 1L;
 public Enchanter() {super("Enchanter", 160, 16, 10, 13, 16);}}

class Conjurer extends Mage implements Highlander {
 private static final long serialVersionUID = 1L;
 public Conjurer() {super("Conjurer", 195, 18, 15, 14, 12);}}

class Eldritch extends Mage implements Mystic {
 private static final long serialVersionUID = 1L;
 public Eldritch() {super("Eldritch", 270, 19, 17, 18, 14);}}

//Healer sub classes
class Soother extends Healer implements Sunchild {
 private static final long serialVersionUID = 1L;
 public Soother() {super("Soother", 95, 10, 8, 9, 6);}}

class Medic extends Healer implements Highlander {
 private static final long serialVersionUID = 1L;
 public Medic() {super("Medic", 125, 12, 9, 10, 7);}}

class Alchemist extends Healer implements Marshlander {
 private static final long serialVersionUID = 1L;
 public Alchemist() {super("Alchemist", 150, 13, 13, 13, 13);}}

class Saint extends Healer implements Mystic {
 private static final long serialVersionUID = 1L;
 public Saint() {super("Saint", 200, 16, 14, 17, 9);}}

class Lightbringer extends Healer implements Sunchild {
 private static final long serialVersionUID = 1L;
 public Lightbringer() {super("Lightbringer", 260, 17, 15, 19, 12);}}

//MythicalCreature sub classes
class Dragon extends MythicalCreature implements Sunchild {
 private static final long serialVersionUID = 1L;
 public Dragon() {super("Dragon", 120, 12, 14, 15, 8);}}

class Basilisk extends MythicalCreature implements Marshlander {
 private static final long serialVersionUID = 1L;
 public Basilisk() {super("Basilisk", 165, 15, 11, 10, 12);}}

class Hydra extends MythicalCreature implements Marshlander {
 private static final long serialVersionUID = 1L;
 public Hydra() {super("Hydra", 205, 12, 16, 15, 11);}}

class Phoenix extends MythicalCreature implements Sunchild {
 private static final long serialVersionUID = 1L;
 public Phoenix() {super("Phoenix", 275, 17, 13, 17, 19);}}

class Pegasus extends MythicalCreature implements Mystic {
 private static final long serialVersionUID = 1L;
 public Pegasus() {super("Pegasus", 340, 14, 18, 20, 20);}}

//Armour sub classes
class Chainmail extends Armour { 
	private static final long serialVersionUID = 1L; 
	public Chainmail() { super("Chainmail", 70, 1, 0, -1); } }
class Regalia extends Armour { 
	private static final long serialVersionUID = 1L; 
	public Regalia() { super("Regalia", 105, 1, 0, 0); } }
class Fleece extends Armour { 
	private static final long serialVersionUID = 1L; 
	public Fleece() { super("Fleece", 150, 2, 1, -1); } }

//Artefact sub classes
class Amulet extends Artefact { 
	private static final long serialVersionUID = 1L; 
	public Amulet() { super("Amulet", 200, 1, -1, 1, 1); } }
class Excalibur extends Artefact { 
	private static final long serialVersionUID = 1L; 
	public Excalibur() { super("Excalibur", 150, 2, 0, 0, 0); } }
class Crystal extends Artefact { 
	private static final long serialVersionUID = 1L; 
	public Crystal() { super("Crystal", 210, 2, 1, -1, -1); } }

//main game
public class MysticMayhem{
    public static Map<Integer,Player> players = null;
    public static Map<String,Integer> usernames = null;
    public static Map<Integer, String> Category = Map.of(0, "Archer",1, "Knight",2, "Mage",3, "Healer",4, "Mythical Creature");
    public static int currentPlayerID = -1; // -1 Indicates no players logged in
    
    //Select log in/ Create new
    public static void mainMenu() {
        while (true) {
            System.out.println("1)Login");
            System.out.println("2)Create a profile");
            System.out.println("3)Exit");
            System.out.println("\nPlease select a menu option");

            int com = InputManager.integerInput();
            if (com == 1) {
                Login();
            } else if (com == 2) {
                CreateProfile();
            } else if (com == 3) {//exit
                break;
            } else {
                System.out.println("Invalid option");
                continue;
            }
        }
    }
    //Creates a profile and logs in
    public static void CreateProfile() {
        System.out.print("Enter your name: ");
        String name = InputManager.stringInput();
        String username;
        while(true) {
            System.out.print("Enter your username: ");
            username = InputManager.stringInput();
            if (!usernames.containsKey(username)) {
                break;
            }
            System.out.println("This username already exists. Use a different username");
        }
        //Enter homeground
        String homeground = null;
        while (homeground == null) {
            System.out.println("Select your homeground: ");
            System.out.println("1) Hillcrest");
            System.out.println("2) Marshland");
            System.out.println("3) Desert ");
            System.out.println("4) Arcane ");

            int com = InputManager.integerInput();
            switch (com) {
                case 1:
                    homeground = "Hillcrest";
                    break;
                case 2:
                    homeground = "Marshland";
                    break;
                case 3:
                    homeground = "Desert";
                    break;
                case 4:
                    homeground = "Arcane";
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }

        //Create player object
        Player player = new Player(name, username,homeground);
        players.put(player.getUserID(), player);
        usernames.put(player.getUsername(),player.getUserID());

        while (true) {
            //Buying first army
            System.out.println("Create your army to battle");
            System.out.println("You have "+ player.getGoldCoins() +" Gold coins.");
            displayCharacterPrizes();
            System.out.println("\nYou must buy a single character from each catagory.");

            int id;
            int Gold_Coin_on_hand=player.getGoldCoins();

            System.out.println("Gold coins on hand : "+Gold_Coin_on_hand+"\n");
            System.out.println("Archer (0-4) 0: Shooter  1:Ranger 2:Sunfire  3:Zing  4:Saggitarius (0 is recommended): ");
            id=InputManager.integerInput();
            if(id<0 || id>4){
                System.out.println("Invalid Selection , Select Again"+"\n");
                continue;
            }
            Character archer = createChar(id);
            Gold_Coin_on_hand =Gold_Coin_on_hand-archer.getPrice();

            System.out.println("Gold coins on hand : "+Gold_Coin_on_hand+"\n");
            System.out.println("Knight(5-9)  5:Sqire  6:Cavalier 7:Templar  8:Zoro  9:Swiftblade (5 is recommended) ");
            id=InputManager.integerInput();
            if(id<5 || id>9){
                System.out.println("Invalid Selection , Select Again\n");
                continue;
            }
            Character knight = createChar(id);
            Gold_Coin_on_hand =Gold_Coin_on_hand-knight.getPrice();

            System.out.println("Gold coins on hand : "+Gold_Coin_on_hand+"\n");
            System.out.println("Mage(10-14)  10:Warlock  11:Illusionist  12:Enchanter  13:Conjurer  14:Eldritch (10 is recommended)");
            id=InputManager.integerInput();
            if(id<10 || id>14){
                System.out.println("Invalid Selection , Select Again\n");
                continue;
            }
            Character mage = createChar(id);
            Gold_Coin_on_hand =Gold_Coin_on_hand-mage.getPrice();

            System.out.println("Gold coins on hand : "+Gold_Coin_on_hand+"\n");
            System.out.println("Healer(15-19) 15:Soother  16:Medic  17:Alchemist  18:Saint  19:Lightbringer (15 is recommended)");
            id=InputManager.integerInput();
            if(id<15 || id>19){
                System.out.println("Invalid Selection , Select Again\n");
                continue;
            }
            Character healer = createChar(id);
            Gold_Coin_on_hand =Gold_Coin_on_hand-healer.getPrice();
            if(Gold_Coin_on_hand<120){
                System.out.println("You Have No Enough Money To Progress, Please Select Again\n");
                continue;
            }
            System.out.println("Gold coins on hand : "+Gold_Coin_on_hand+"\n");
            System.out.println("MythicalCreature(20-24) 20:Dragon  21:Basillisk  22:Hydra  23:Phoenix  24:pegasus (20 is recommended)");
            id=InputManager.integerInput();
            if(id<20 || id>24){
                System.out.println("Invalid Selection , Select Again\n");
                continue;
            }
            Character mythicalCreature = createChar(id);
            Gold_Coin_on_hand =Gold_Coin_on_hand-mythicalCreature.getPrice();
            if ( Gold_Coin_on_hand<0) {
                System.out.println("Not enough money for this army, Please select again\n");
                continue;
            }

            //Buy the army
            player.addCharacter(archer);
            player.addCharacter(knight);
            player.addCharacter(mage);
            player.addCharacter(healer);
            player.addCharacter(mythicalCreature);
            player.spendGoldCoins(player.getGoldCoins()- Gold_Coin_on_hand);
            break;
        }
        //Log in
        currentPlayerID = player.getUserID();
        //Save
        FileManager.saveNameMap(usernames);
        FileManager.savePlayerMap(players);
        BattleMenu();
    }
    //Log in using the user name
    public static void Login() {
        if (Player.numOfPlayers == 0) {
            System.out.println("No Players are registered. Please create a new profile first\n");
            return;
        }
        String username;
        while(true) {
            System.out.println("Registered usernames:\n");
            
	            for(int i=0;i<Player.numOfPlayers;i++) {
	                System.out.println(players.get(i).getUsername());
	            }
            
            System.out.print("\nEnter your username: ");
            username = InputManager.stringInput();
            if (usernames.containsKey(username)) {
                break;
            }
            System.out.println("Couldn't find a username called \""+username+"\"");
        }
        currentPlayerID = usernames.get(username);
        BattleMenu();
    }
    
    //Select to pass/ battle/ upgrade Army or change name
    public static void BattleMenu() {
        while (true) {
        	System.out.println("\nYour State:- Gold coins: "+players.get(currentPlayerID).getGoldCoins()+
        	"|| XP level: "+players.get(currentPlayerID).getXpLevel());
            int currentOpID = randOpID();
            if (currentOpID == -1) {
                System.out.println("No Opponents to show\n");
            }
            else {
            	System.out.println("Random Opponent:");
                players.get(currentOpID).displayStats();
            }
            System.out.println("1) Pass");
            System.out.println("2) Battle");
            System.out.println("3) Upgrade Army");
            System.out.println("4) Log out");
            System.out.println("5) Change Player Name"); // New menu option
            System.out.println("\nPlease select a menu option");

            int com = InputManager.integerInput();
            if (com == 1) {
                continue;
            } else if (com == 2) {
                Battle(players.get(currentPlayerID), players.get(currentOpID));
                continue;
            } else if (com == 3) {
                ArmyUpgradeMenu();
                continue;
            } else if (com == 4) {
                // Log out
                currentPlayerID = -1;
                break;
            } else if (com == 5) {
                changePlayerName();
                continue;
            } else {
                System.out.println("Invalid option");
                continue;
            }
        }
    }
    //Generate random opponent ID
	public static int randOpID() {
	    if (Player.numOfPlayers <= 1) {
	        // If there is only one player, return null
	        return -1;
	    }
	
	    Random random = new Random();
	    int randomID;
	
	    do {
	        randomID = random.nextInt(Player.numOfPlayers);
	    } while (randomID == currentPlayerID);
	
	    return randomID;
	}
	//Select to Buy new characters or equipment
    private static void ArmyUpgradeMenu() {
        while (true) {
            players.get(currentPlayerID).displayArmy();
            System.out.println("1)Buy New Character");
            System.out.println("2)Buy New Equipment");
            System.out.println("3)Back to Battle Menu");
            System.out.println("\nPlease select a menu option");

            int com = InputManager.integerInput();
            if (com == 1) {
                CharacterShop();
                continue;
            } else if (com == 2) {
                EquipmentShop();
                continue;
            } else if (com == 3) {
                break;
            } else {
                // Default case, handle unexpected values of command
                System.out.println("Invalid option");
                continue;
            }
        }
    }
    //Select and buy characters
    private static void CharacterShop() {
        Player p = players.get(currentPlayerID);
        while (true) {
            p.displayArmy();
            displayCharacterPrizes();
            System.out.println("\nEnter the character ID to buy (or press Enter to exit): ");
            String userInput = InputManager.stringInput();

            if (userInput.isEmpty()) {
            	FileManager.saveNameMap(usernames);
                FileManager.savePlayerMap(players);
                // Exit the shop
                break;
            }
            try {
                int payBack;
                Character newC;
                int characterID = Integer.parseInt(userInput);
                if (characterID < 0 || characterID > 24) {
                    System.out.println("Invalid character ID.");
                    continue;
                }
                newC = createChar(characterID);
                String catagory = newC.getCatagory();
                Character currentC = p.getArmy().get(catagory);
                payBack = (int)Math.round(currentC.getPrice()*0.9);
                if (newC.getPrice()>p.getGoldCoins() + payBack) {
                    System.out.println("Not enough money.");
                    continue;
                }
                //Sell old char
                p.recieveGoldCoins(payBack);
                //Buy new char
                p.spendGoldCoins(newC.getPrice());
                p.addCharacter(newC);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid character ID or press Enter to exit.");
                continue;
            }
        }
    }
    //Select and buy equipment
    private static void EquipmentShop() {
        Player p = players.get(currentPlayerID);
        displayEquipementPrizes();
        while (true) {
            System.out.println("\nEnter the Equipment ID to buy (or press Enter to exit)"
            		+ " \n: Chainmail(0), Regalia(1), Fleece(2), Excalibur(3),Amulet(4), Crystal(5)");
            String userInput = InputManager.stringInput();
            if (userInput.isEmpty()) {
            	FileManager.saveNameMap(usernames);
                FileManager.savePlayerMap(players);
                // Exit the shop
                break;
            }
            try {
                int eqipID = Integer.parseInt(userInput);
                if (eqipID < 0 || eqipID > 5) {
                    System.out.println("Invalid equipment ID.");
                    continue;
                }
                Equipment newE = createEqu(eqipID);
                if (newE.getPrice() > p.getGoldCoins()) {
                    System.out.println("Not enough money.");
                    continue;
                }
                players.get(currentPlayerID).spendGoldCoins(newE.getPrice());
                while (true) {
                    p.displayArmy();
                    System.out.println("\nWhich character to give the equipment: ");
                    System.out.println("1) Archer");
                    System.out.println("2) Knight");
                    System.out.println("3) Mage");
                    System.out.println("4) Healer");
                    System.out.println("5) Mythical Creature");
                    int com = InputManager.integerInput();
                    if (1<=com && com<=5) {
                        if (eqipID<=2) {
                            p.getArmy().get(Category.get(com-1)).equipArmour((Armour)newE);
                        }
                        else {
                            p.getArmy().get(Category.get(com-1)).equipArtefact((Artefact)newE);
                        }
                        p.getArmy().get(Category.get(com-1)).addEquipmentPrize();
                        break;
                    }
                    else {
                        System.out.println("Invalid option");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid character ID or press Enter to exit.");
            }
        }
    }
   
    //Creates a character given an ID
    private static Character createChar(int i) {
        Character c =null;
        switch(i) {
            case 0:
                c = new Shooter();
                break;
            case 1:
                c = new Ranger();
                break;
            case 2:
                c = new Sunfire();
                break;
            case 3:
                c = new Zing();
                break;
            case 4:
                c = new Saggitarius();
                break;
            case 5:
                c = new Squire();
                break;
            case 6:
                c = new Cavalier();
                break;
            case 7:
                c = new Templar();
                break;
            case 8:
                c = new Zoro();
                break;
            case 9:
                c = new Swiftblade();
                break;
            case 10:
                c = new Warlock();
                break;
            case 11:
                c = new Illusionist();
                break;
            case 12:
                c = new Enchanter();
                break;
            case 13:
                c = new Conjurer();
                break;
            case 14:
                c = new Eldritch();
                break;
            case 15:
                c = new Soother();
                break;
            case 16:
                c = new Medic();
                break;
            case 17:
                c = new Alchemist();
                break;
            case 18:
                c = new Saint();
                break;
            case 19:
                c = new Lightbringer();
                break;
            case 20:
                c = new Dragon();
                break;
            case 21:
                c = new Basilisk();
                break;
            case 22:
                c = new Hydra();
                break;
            case 23:
                c = new Phoenix();
                break;
            case 24:
                c = new Pegasus();
                break;
        }
        return c;
    }
    //Creates an equipment given an ID
    private static Equipment createEqu(int eqipID) {
        switch (eqipID) {
            case 0:
                return new Chainmail();
            case 1:
            	return new Regalia();
            case 2:
            	return new Fleece();
            case 3:
                return new Amulet();
            case 4:
                return new Excalibur();
            case 5:
                return new Crystal();
            default:
                throw new IllegalArgumentException("Invalid equipment ID: " + eqipID);
        }
    }
    //displays all characters initial prize
    private static void displayCharacterPrizes() {
            // Print headers
            String[] headers = {"Archers", "Knights", "Mages", "Healers", "Mythical Creatures"};

            String[][] details_archers = {
                    {"ID:0 \t","Name: Shooter","Price: 80 gc", "Attack: 11    ", "Defence: 4    ", "Health: 6     ", "Speed: 9      "},
                    {"ID:1 \t","Name: Ranger","Price: 115 gc", "Attack: 14  ", "Defence: 5   ", "Health: 8    ", "Speed: 10      "},
                    {"ID:2 \t","Name: Sunfire","Price: 160 gc", "Attack: 15    ", "Defence: 5    ", "Health: 7   ", "Speed: 14    "},
                    {"ID:3 \t","Name: Zing","Price: 200 gc", "Attack: 16", "Defence: 9", "Health: 11", "Speed: 14"},
                    {"ID:4 \t","Name: Saggitarius", "Price: 230 gc", "Attack: 18", "Defence: 7", "Health: 12", "Speed: 17"},
            };
            String[][] details_knights = {
                    {"ID:5 \t","Name: Squire", "Price: 85 gc", "Attack: 8    ", "Defence: 9    ", "Health: 7    ", "Speed: 8    "},
                    {"ID:6 \t","Name: Cavalier", "Price: 110 gc ", "Attack: 10     ", "Defence: 12    ", "Health: 7    ", "Speed: 10     "},
                    {"ID:7 \t","Name: Templar", "Price: 155 gc", "Attack: 14    ", "Defence: 16    ", "Health: 12    ", "Speed: 12    "},
                    {"ID:8 \t","Name: Zoro", "Price: 180 gc", "Attack: 17", "Defence: 16", "Health: 13", "Speed: 14"},
                    {"ID:9 \t","Name: Swiftblade", "Price: 250 gc", "Attack: 18", "Defence: 20", "Health: 17", "Speed: 13"}
            };
            String[][] details_mages = {
                    {"ID:10\t","Name: Warlock", "Price: 100 gc", "Attack: 12    ", "Defence: 7    ", "Health: 10    ", "Speed: 12    "},
                    {"ID:11\t\t","Name: Illusionist", "Price: 120 gc    ", "Attack: 13         ", "Defence: 8       ", "Health: 12         ", "Speed: 14        "},
                    {"ID:12\t","Name: Enchanter", "Price: 160 gc", "Attack: 16     ", "Defence: 10    ", "Health: 13    ", "Speed: 16    "},
                    {"ID:13\t","Name: Conjurer", "Price: 195 gc", "Attack: 18", "Defence: 15", "Health: 14", "Speed: 12"},
                    {"ID:14\t","Name: Eldritch", "Price: 270 gc", "Attack: 19", "Defence: 17", "Health: 18", "Speed: 14"},
            };
            String[][] details_healers = {
                    {"ID:15\t","Name: Soother", "Price: 95 gc", "Attack: 10    ", "Defence: 8    ", "Health: 9    ", "Speed: 6    "},
                    {"ID:16\t","Name: Medic    ", "Price: 125 gc", "Attack: 12     ", "Defence: 9    ", "Health: 10     ", "Speed: 7    "},
                    {"ID:17\t","Name: Alchemist", "Price: 150 gc", "Attack: 13    ", "Defence: 13    ", "Health: 13    ", "Speed: 13    "},
                    {"ID:18\t","Name: Saint", "Price: 200 gc", "Attack: 16", "Defence: 14", "Health: 17", "Speed: 9"},
                    {"ID:19\t","Name: Lightbringer", "Price: 260 gc", "Attack: 17", "Defence: 15", "Health: 19", "Speed: 12"},
            };
            String[][] details_mythicalCreatures = {
                    {"ID:20\t","Name: Dragon", "Price: 120 gc", "Attack: 12    ", "Defence: 14 ", "Health: 15    ", "Speed: 8    "},
                    {"ID:21\t","Name: Basilisk", "Price: 165 gc", "Attack: 15    ", "Defence: 11    ", "Health: 10    ", "Speed: 12    "},
                    {"ID:22\t","Name: Hydra    ", "Price: 205 gc", "Attack: 12    ", "Defence: 16    ", "Health: 15    ", "Speed: 11    "},
                    {"ID:23\t","Name: Phoenix", "Price: 275 gc", "Attack: 17", "Defence: 13", "Health: 17", "Speed: 19"},
                    {"ID:24\t","Name: Pegasus", "Price: 340 gc", "Attack: 14", "Defence: 18", "Health: 20", "Speed: 20"}
            };

            for (String header : headers) {
                System.out.println();
                System.out.println(header);
                System.out.println();
                if(header=="Archers") {
                    for (int i = 0; i <details_archers [0].length; i++) {
                        for (String[] instructionRow : details_archers) {
                            System.out.print(instructionRow[i] + "\t\t");
                        }
                        System.out.println();
                    }
                }
                else if (header=="Knights"){
                    for (int i = 0; i <details_knights[0].length; i++) {
                        for (String[] instructionRow : details_knights) {
                            System.out.print(instructionRow[i] + "\t\t");
                        }
                        System.out.println();
                    }
                }
                else if (header=="Mages"){
                    for (int i = 0; i <details_mages[0].length; i++) {
                        for (String[] instructionRow : details_mages) {
                            System.out.print(instructionRow[i] + "\t\t");
                        }
                        System.out.println();
                    }
                }
                else if(header=="Healers"){
                    for (int i = 0; i <details_healers[0].length; i++) {
                        for (String[] instructionRow : details_healers) {
                            System.out.print(instructionRow[i] + "\t\t");
                        }
                        System.out.println();
                    }
                }
                else{
                    for (int i = 0; i <details_mythicalCreatures[0].length; i++) {
                        for (String[] instructionRow : details_mythicalCreatures) {
                            System.out.print(instructionRow[i] + "\t\t");
                        }
                        System.out.println();
                    }

                }

            }
}
    //displays all equipments initial prize
    private static void displayEquipementPrizes() {
    	// Print headers
        String[] headers = {"Armours", "Artefacts"};

        String[][] details_armours = {
                {"Name: Chainmail\t", "Price: 70 gc\t", "Attack: no change", "Defence: +1\t", "Health: no change", "Speed: -1\t"},
                {"Name: Regalia\t", "Price: 105 gc\t", "Attack: no change", "Defence: +1\t", "Health: no change", "Speed: no change"},
                {"Name: Fleece", "Price: 150 gc", "Attack: no change", "Defence: +2", "Health: +1", "Speed: -1"},
        };
        String[][] details_artefacts = {
                {"Name: Amulet", "Price: 200 gc", "Attack: +1", "Defence: -1", "Health: +1", "Speed: +1"},
                {"Name: Excalibur\t", "Price: 150 gc\t", "Attack: +2\t", "Defence: no change", "Health: no change", "Speed: no change"},
                {"Name: Crystal", "Price: 210 gc", "Attack: +2", "Defence: +1", "Health: -1", "Speed: -1"},
        };

        for (String header : headers) {
            System.out.println();
            System.out.println(header);
            System.out.println();
            if(header=="Armours") {
                for (int i = 0; i <details_armours [0].length; i++) {
                    for (String[] instructionRow : details_armours) {
                        System.out.print(instructionRow[i] + "\t\t");
                    }
                    System.out.println();
                }
            }
            else{
                for (int i = 0; i <details_artefacts[0].length; i++) {
                    for (String[] instructionRow : details_artefacts) {
                        System.out.print(instructionRow[i] + "\t\t");
                    }
                    System.out.println();
                }

            }

        }

    }
    //change current player's name
    private static void changePlayerName() {
        System.out.println("Enter the new player name:");
        String newName = InputManager.stringInput();
        players.get(currentPlayerID).setName(newName);
        System.out.println("Player name changed to: " + newName);
    }
    
    //Battle selected opponent
    private static void Battle(Player player, Player opponent) {
        if (opponent == null) {
            System.out.println("No opponent to battle");
            return;
        }
        //Healer < Mage < Mythical Creature < Knight < Archer
        String[] speedPriority = {"Healer", "Mage", "Mythical Creature", "Knight", "Archer"};
        //Mage < Knight< Archer < Mythical Creature < Healer
        String[] defencePriority = {"Mage", "Knight", "Archer", "Mythical Creature", "Healer"};
        String battleGround = opponent.getHomeground();
        System.out.println(player.getName()+" vs "+ opponent.getName());
        System.out.println("Location : "+ battleGround);
        for (Character c: player.getArmy().values()) {
            c.addTerrainEffect(battleGround);
        }
        for (Character c: opponent.getArmy().values()) {
            c.addTerrainEffect(battleGround);
        }
        // Retrieve and sort the player's army based on speed and defence
        Character[] spSortedPlayerArmy = new Character[5];
        Character[] defSortedPlayerArmy = new Character[5];

        sort(player, speedPriority, defencePriority, spSortedPlayerArmy, defSortedPlayerArmy);

        // Retrieve and sort the opponent's army based on speed and defence
        Character[] spSortedOpArmy = new Character[5];
        Character[] defSortedOpArmy = new Character[5];
        sort(opponent, speedPriority, defencePriority, spSortedOpArmy, defSortedOpArmy);

        player.displayArmy();
        opponent.displayArmy();
        
        //fight
        int plAttacker = 0;
        int opAttacker = 0;
        int deffender;
        boolean draw = true;
        for(int turn = 1; turn <= 10; turn++) {
            Character playerAttacker = null;
            if (spSortedPlayerArmy[plAttacker].isAlive()) {
            	playerAttacker = spSortedPlayerArmy[plAttacker];
            }
            else {
            	boolean alldead = true;
            	for(int i=0;i<4;i++) {
            		plAttacker = nextInd(plAttacker);
            		if (spSortedPlayerArmy[plAttacker].isAlive()) {
            			playerAttacker = spSortedPlayerArmy[plAttacker];
            			alldead = false;
            			break;
            		}
            	}
            	if (alldead){
            		award(player,opponent);
            		System.out.println("You Lose!");
            		draw = false;
            		break;
            	}
            }
            System.out.println("\nTurn " + turn+ " : " + player.getName());
            if (playerAttacker instanceof Healer) { 
            	System.out.println(player.getName()+"'s "+playerAttacker.getName()+" Heals-> "+player.getName()+"'s "+player.leastHealthCharacter().getName());
            	performAttack(playerAttacker, player.leastHealthCharacter(),false,playerAttacker.hasAutoHeal());
            }
            else{
            	// find best alive deffender
            	deffender = 0;
                for(int i = 0; i<5; i++) {
                	if (defSortedOpArmy[deffender].isAlive()) {
                		break;
                	}
                	deffender +=1;
                }
                Character opponentDefender = defSortedOpArmy[deffender];
                System.out.println(player.getName()+"'s "+playerAttacker.getName()+" attacks-> "+opponent.getName()+"'s "+opponentDefender.getName());
            	performAttack(playerAttacker, opponentDefender,false,playerAttacker.hasAutoHeal());
            }
            if(playerAttacker.hasBonusTurn()) {
            	System.out.println("\nBonus Turn : " + playerAttacker.getName());
            	if (playerAttacker instanceof Healer) { 
                	System.out.println(player.getName()+"'s "+playerAttacker.getName()+" Heals-> "+player.getName()+"'s "+player.leastHealthCharacter().getName());
                	performAttack(playerAttacker, player.leastHealthCharacter(),true,playerAttacker.hasAutoHeal());
                }
            	else {
	            	deffender = 0;
	                for(int i = 0; i<5; i++) {
	                	if (defSortedOpArmy[deffender].isAlive()) {
	                		break;
	                	}
	                	deffender +=1;
	                }
	                Character opponentDefender = defSortedOpArmy[deffender];
	                System.out.println(player.getName()+"'s "+playerAttacker.getName()+" attacks-> "+opponent.getName()+"'s "+opponentDefender.getName());
	            	performAttack(playerAttacker, opponentDefender,true,playerAttacker.hasAutoHeal());
            	}
            }
            if(playerAttacker.hasAutoHeal()) {
            	double hp = playerAttacker.getCurrentHealth()*0.1;
            	System.out.println(String.format("Healed %.1f of %s's health.",hp,playerAttacker.getName()));
            	playerAttacker.heal(hp);
            }
            Character opponentAttacker = spSortedOpArmy[opAttacker];
            
            // Opponent attacks player
            if (spSortedOpArmy[opAttacker].isAlive()) {
            	opponentAttacker = spSortedOpArmy[opAttacker];
            }
            else {
            	boolean alldead = true;
            	for(int i=0;i<4;i++) {
            		opAttacker = nextInd(opAttacker);
            		if (spSortedOpArmy[opAttacker].isAlive()) {
            			opponentAttacker = spSortedOpArmy[opAttacker];
            			alldead = false;
            			break;
            		}
            	}
            	if (alldead){
            		System.out.println("You Won!");
            		award(opponent,player);
            		draw = false;
            		break;
            	}
            }
            System.out.println("\nTurn "+turn+ " : " + opponent.getName());
            
            if (opponentAttacker instanceof Healer) { 
            	System.out.println(opponent.getName()+"'s "+opponentAttacker.getName()+" Heals-> "+opponent.getName()+"'s "+opponent.leastHealthCharacter().getName());
            	performAttack(opponentAttacker, opponent.leastHealthCharacter(),false,playerAttacker.hasAutoHeal());
            }
            else{
            	// find best alive defender
            	deffender = 0;
                for(int i = 0; i<5; i++) {
                	if (defSortedPlayerArmy[deffender].isAlive()) {
                		break;
                	}
                	deffender +=1;
                }
                Character playerDefender = defSortedPlayerArmy[deffender];
                System.out.println(opponent.getName()+"'s "+opponentAttacker.getName()+" attacks-> "+player.getName()+"'s "+playerDefender.getName());
                performAttack(opponentAttacker, playerDefender,false,playerAttacker.hasAutoHeal());
            }
            if(opponentAttacker.hasBonusTurn()) {
            	System.out.println("\nBonus Turn : " + opponent.getName());
            	if (opponentAttacker instanceof Healer) { 
                	System.out.println(opponent.getName()+"'s "+opponentAttacker.getName()+" Heals-> "+opponent.getName()+"'s "+opponent.leastHealthCharacter().getName());
                	performAttack(opponentAttacker, opponent.leastHealthCharacter(),true,playerAttacker.hasAutoHeal());
                }
                else{
                	// find best alive defender
                	deffender = 0;
                    for(int i = 0; i<5; i++) {
                    	if (defSortedPlayerArmy[deffender].isAlive()) {
                    		break;
                    	}
                    	deffender +=1;
                    }
                    Character playerDefender = defSortedPlayerArmy[deffender];
                    System.out.println(opponent.getName()+"'s "+opponentAttacker.getName()+" attacks-> "+player.getName()+"'s "+playerDefender.getName());
                    performAttack(opponentAttacker, playerDefender,true,playerAttacker.hasAutoHeal());
                }
            }
            if(opponentAttacker.hasAutoHeal()) {
            	double hp = opponentAttacker.getCurrentHealth()*0.1;
            	opponentAttacker.heal(hp);
            	System.out.println(String.format("Healed %.1f of %s's health.",hp,opponentAttacker.getName()));
            }
            plAttacker = nextInd(plAttacker);
            opAttacker = nextInd(opAttacker);
            
        }
        if (draw){
        	System.out.println("\nBattle ended in a draw.");
        }
        // Restore characters to their state before the battle
        restoreCharacters(player, opponent, battleGround);
        FileManager.saveNameMap(usernames);
        FileManager.savePlayerMap(players);
    }
    private static void award(Player looser, Player winner) {
    	int transfer = (int)Math.round(looser.getGoldCoins()*0.1);
		winner.recieveGoldCoins(transfer);
		looser.spendGoldCoins(transfer);
		winner.addXp();
	}
	private static int nextInd(int plAttacker) {
        // Increment the current index
        int nextIndex = plAttacker + 1;

        // If the next index is less than 5, return it; otherwise, wrap around to 0
        return (nextIndex < 5) ? nextIndex : 0;
    }

	//single attack
    private static void performAttack(Character attacker, Character target,boolean bonus,boolean autoHeal) {
        if (attacker == null) {
            System.out.println("Error: Attacker is null");
            return;
        }

        // Perform the attack based on character type
        if (attacker instanceof Healer) {
            // Healer's attack: Increase the health of the lowest health character in their own army
        	if (bonus) {//20% attack
            	System.out.println(String.format("Heal power : %.1f",0.2 * attacker.getAttack()));
	            target.heal(0.2*attacker.getAttack());
	            System.out.println(String.format("Healed %s health\t: %.1f",target.getName(),target.getCurrentHealth()));
        	}
        	else {
        		target.heal(0.1*attacker.getAttack());
	            System.out.println(String.format("Healed %s health\t: %.1f",target.getName(),target.getCurrentHealth()));
        	}
        }
        else {
            // Other characters' attack: Target the opposing team's character with the lowest defense
            double damage = 0.5 * attacker.getAttack() - 0.1 * target.getDefence();
            if (bonus) {//+20% attack
            	System.out.println(String.format("Attack power : %.1f",0.2 * attacker.getAttack()));
            	damage = 0.5 * 0.2 * attacker.getAttack() - 0.1 * target.getDefence();
            }
            target.takeDamage(damage);
        
	        System.out.println(String.format("Attacker %s health\t: %.1f",attacker.getName(),attacker.getCurrentHealth()));
	        if (target.isAlive()) {
	        	System.out.println(String.format("Defender %s health\t: %.1f",target.getName(),target.getCurrentHealth()));
	        }
        }
        if (autoHeal) {
    		attacker.heal(0.2*attacker.getAttack());
    		System.out.println(String.format("Attacker healed himself with heal power : %.1f",0.2 * attacker.getAttack()));
    		System.out.println(String.format("Healed %s health\t: %.1f",attacker.getName(),attacker.getCurrentHealth()));
    	}
    }
    //take characters to their original status
    private static void restoreCharacters(Player player, Player opponent, String battleGround) {
        // Revert terrain effects and reset health
        for (Character c : player.getArmy().values()) {
            c.revertTerrainEffect(battleGround);
            c.resetHealth();
        }
        for (Character c : opponent.getArmy().values()) {
            c.revertTerrainEffect(battleGround);
            c.resetHealth();
        }

        // Reset characters' health to their initial values

    }
    //sort a given player's army based on it's defence and speed
    private static void sort(Player player, String[] sPriority, String[] dPriority,
                             Character[] spSortedPlayerArmy, Character[] defSortedPlayerArmy) {
    	String[] defencePriority = sPriority.clone();
        String[] speedPriority = dPriority.clone();
        for (int i = 0; i < 5; i++) {
            int minDefence = Integer.MAX_VALUE;
            int minDefenceCategoryId = 0;
            int maxSpeed = 0;
            int maxSpeedCategoryId = 0;
            
            
			// Find the category with the maximum speed that hasn't been added yet
            for (int j = 0; j < 5; j++) {
                if (speedPriority[j] == null) { // Already added
                    continue;
                }

                int currentSpeed = player.getArmy().get(speedPriority[j]).getSpeed();
                if (maxSpeed < currentSpeed) {
                    maxSpeed = currentSpeed;
                    maxSpeedCategoryId = j;
                }
            }

			// Find the category with the minimum defence that hasn't been added yet
            for (int j = 0; j < 5; j++) {
                if (defencePriority[j] == null) { // Already added
                    continue;
                }

                int currentDefence = player.getArmy().get(defencePriority[j]).getDefence();
                if (minDefence > currentDefence) {
                    minDefence = currentDefence;
                    minDefenceCategoryId = j;
                }
            }

            // Add the character with the maximum speed and defence to the sorted arrays
            spSortedPlayerArmy[i] = player.getArmy().get(speedPriority[maxSpeedCategoryId]);
            defSortedPlayerArmy[i] = player.getArmy().get(defencePriority[minDefenceCategoryId]);

            // Mark these categories as added
            speedPriority[maxSpeedCategoryId] = null;
            defencePriority[minDefenceCategoryId] = null;
        }
    }
    private static void addSystemPlayer() {
		if (usernames.containsKey("whitewolf")) {
			return;
		}
		else {
			Player player = new Player("GeraltofRivia", "whitewolf", "Marshland", 32, 215);
		    // Create characters with specific types and equipment
			Armour chainmail = new Chainmail();
		    Character archer = new Ranger();
		    archer.equipArmour(chainmail);
		    archer.addEquipmentPrize();
		    Character knight = new Squire();
		    Character mage = new Warlock();
		    Artefact amulet = new Amulet();
		    Character healer = new Medic();
		    healer.equipArtefact(amulet);
		    healer.addEquipmentPrize();
		    Character mythicalCreature = new Dragon();
		    // Add characters to the player
		    player.addCharacter(archer);
		    player.addCharacter(knight);
		    player.addCharacter(mage);
		    player.addCharacter(healer);
		    player.addCharacter(mythicalCreature);

		    // Add the player to the maps
		    players.put(player.getUserID(), player);
		    usernames.put(player.getUsername(), player.getUserID());
		}
	}
	public static void main(String[] args) {
	    System.out.println("----------------\n| Mystic Mayhem |\n----------------\n");
		players = FileManager.loadPlayerMap();
		usernames = FileManager.loadNameMap();
		//Adjust numberOfplayers
		Player.numOfPlayers += players.keySet().size();
	    addSystemPlayer();
	    mainMenu();
	}
}