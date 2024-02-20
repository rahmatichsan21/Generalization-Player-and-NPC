import java.util.Scanner;
import java.util.Random;

// Kelas dasar Character
class Character {
    private String name;
    private int health;
    private final int maxHealth = 100;
    private final int minHealth = 0;
    private boolean defeated;
    public Character(String name, int health) {
        this.name = name;
        setHealth(health);
        defeated = false;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < minHealth) {
            this.health = minHealth;
        } else if (health > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage");

        if (health <= 0 && !defeated) {
        	defeated = true;
            System.out.println(name + " has been defeated");
        }
    }
    
    public void respawn() {
    	health = 50;
    	defeated = false;
    	System.out.println(getName() + " Has respawned with " + getHealth() + "Health");
    }
    
    public boolean isDefeated() {
    	return defeated;
    }
    
    public void setDefeated() {
    	this.defeated = defeated;
    }
    public void attack(Character character) {
        System.out.println(name + " attacks " + character.getName());
        character.takeDamage(10); // Example damage amount
    }
}

// Kelas Player sebagai turunan dari Character
class Player extends Character {
    private int experience;

    public Player(String name, int health, int experience) {
        super(name, health);
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void levelUp() {
        System.out.println(getName() + " levels up!");
        // Additional logic for leveling up
    }
}

// Kelas NonPlayerCharacter sebagai turunan dari Character
class NonPlayerCharacter extends Character {
    public NonPlayerCharacter(String name, int health) {
        super(name, health);
    }
}

// Kelas Enemy sebagai turunan dari NonPlayerCharacter
class Enemy extends NonPlayerCharacter {
    public Enemy(String name, int health) {
        super(name, health);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (getHealth() <= 0) {
        	
            Player player = Main.getPlayerInstance();
            if (player != null) {
                player.setExperience(player.getExperience() + 10);
                System.out.println(player.getName() + " gains 10 experience");
            }
        }
    }
}

// Kelas Ally sebagai turunan dari NonPlayerCharacter
class Ally extends NonPlayerCharacter {
    public Ally(String name, int health) {
        super(name, health);
    }

    public void heal(Character character) {
    	if (getHealth() > 0 ) {
        System.out.println(getName() + " heals " + character.getName());
        character.setHealth(character.getHealth() + 20); // Example healing amount
        }
    	
    	else {
    		System.out.println("Dia");
    	}
    }
}

// Kelas Main
public class Main {
    private static Player player;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        player = new Player("Ichsan", 100, 0);
        Enemy enemy = new Enemy("Goblin", 50);
        Ally ally = new Ally("Healer", 70);

        int choice;
        do {
            System.out.println(player.getName() + "'s Health: " + player.getHealth());
            System.out.println(player.getName() + "'s Experience: " + player.getExperience());
            System.out.println(enemy.getName() + "'s Health: " + enemy.getHealth());
            System.out.println(ally.getName() + "'s Health: " + ally.getHealth());
            System.out.println("\n1. Serang musuh");
            System.out.println("2. Dapatkan healing");
            System.out.println("0. Keluar");
            System.out.print("\nPilih aksi: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Random random = new Random();
                    int chance = random.nextInt(2) + 1;
                    player.attack(enemy);
                    if (chance == 1) {
                        enemy.attack(player);
                    } else {
                        enemy.attack(ally);
                    }
                    break;
                case 2:
                    ally.heal(player);
                    break;
                case 0:
                    System.out.println("Keluar dari permainan");
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
                    break;
            }

        } while (choice != 0);

        scanner.close();
    }

    public static Player getPlayerInstance() {
        return player;
    }
}
