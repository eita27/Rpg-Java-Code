import java.util.*;

class Character {
    String name;
    String job;
    int health;
    int damage;
    int stamina;

    Character(String name, String job, int health, int damage, int stamina) {
        this.name = name;
        this.job = job;
        this.health = health;
        this.damage = damage;
        this.stamina = stamina;
    }

    boolean isAlive() {
        return health > 0;
    }

    void gainStamina() {
        stamina += 5;
    }

    void lightAttack(Character enemy) {
        int cost = 5;

        if (stamina >= cost) {
            stamina -= cost;
            enemy.health -= damage;
            System.out.println(name + " used LIGHT ATTACK and dealt " + damage + " damage!");
        } else {
            System.out.println(name + " tried to attack but doesn't have enough stamina!");
        }
    }

    void heavyAttack(Character enemy) {
        int cost = 10;

        if (stamina >= cost) {
            stamina -= cost;
            int heavyDamage = damage * 2;
            enemy.health -= heavyDamage;
            System.out.println(name + " used HEAVY ATTACK and dealt " + heavyDamage + " damage!");
        } else {
            System.out.println(name + " tried heavy attack but doesn't have enough stamina!");
        }
    }
}

class Archer extends Character {
    int arrows;

    Archer(String name) {
        super(name, "Archer", 90, 15, 10);
        arrows = 10;
    }

    @Override
    void lightAttack(Character enemy) {
        if (arrows <= 0) {
            System.out.println("No arrows left!");
            return;
        }
        super.lightAttack(enemy);
        arrows--;
        System.out.println("Arrows left: " + arrows);
    }

    @Override
    void heavyAttack(Character enemy) {
        if (arrows < 2) {
            System.out.println("Not enough arrows!");
            return;
        }
        super.heavyAttack(enemy);
        arrows -= 2;
        System.out.println("Arrows left: " + arrows);
    }
}

class Warrior extends Character {
    int staminaBonus;

    Warrior(String name) {
        super(name, "Warrior", 120, 18, 15);
        staminaBonus = 5;
    }
}

class Mage extends Character {
    int mana;

    Mage(String name) {
        super(name, "Mage", 80, 20, 10);
        mana = 20;
    }

    @Override
    void lightAttack(Character enemy) {
        if (mana < 3) {
            System.out.println("Not enough mana!");
            return;
        }
        mana -= 3;
        super.lightAttack(enemy);
        System.out.println("Mana left: " + mana);
    }

    @Override
    void heavyAttack(Character enemy) {
        if (mana < 6) {
            System.out.println("Not enough mana!");
            return;
        }
        mana -= 6;
        super.heavyAttack(enemy);
        System.out.println("Mana left: " + mana);
    }
}

class Enemy extends Character {

    Enemy(String name, int health, int damage) {
        super(name, "Enemy", health, damage, 10);
    }

    void enemyAttack(Character player) {
        Random rand = new Random();

        if (rand.nextBoolean()) {
            lightAttack(player);
        } else {
            heavyAttack(player);
        }
    }
}

public class Main {

    static Scanner input = new Scanner(System.in);

    public static Character chooseClass() {

        System.out.println("Choose your class:");
        System.out.println("1. Archer");
        System.out.println("2. Warrior");
        System.out.println("3. Mage");

        int choice = input.nextInt();

        System.out.print("Enter your name: ");
        String name = input.next();

        if (choice == 1) return new Archer(name);
        if (choice == 2) return new Warrior(name);
        return new Mage(name);
    }

    public static Enemy[] createEnemies() {

        Enemy[] enemies = new Enemy[5];

        enemies[0] = new Enemy("Goblin", 40, 8);
        enemies[1] = new Enemy("Orc", 60, 10);
        enemies[2] = new Enemy("Dark Knight", 80, 12);
        enemies[3] = new Enemy("Dragonling", 100, 14);
        enemies[4] = new Enemy("DEMON KING (BOSS)", 150, 18);

        return enemies;
    }

    public static void battle(Character player, Enemy enemy) {

        System.out.println("\nA wild " + enemy.name + " appeared!");

        while (player.isAlive() && enemy.isAlive()) {

            player.gainStamina();
            enemy.gainStamina();

            System.out.println("\n" + player.name + " HP: " + player.health + " | Stamina: " + player.stamina);
            System.out.println(enemy.name + " HP: " + enemy.health);

            System.out.println("1. Light Attack (5 stamina)");
            System.out.println("2. Heavy Attack (10 stamina)");

            int action = input.nextInt();

            if (action == 1) {
                player.lightAttack(enemy);
            } else {
                player.heavyAttack(enemy);
            }

            if (enemy.isAlive()) {
                enemy.enemyAttack(player);
            }
        }

        if (!player.isAlive()) {
            System.out.println("You were defeated...");
        } else {
            System.out.println("Enemy defeated!");
        }
    }

    public static void main(String[] args) {

        boolean playing = true;

        while (playing) {

            Character player = chooseClass();
            Enemy[] enemies = createEnemies();

            for (Enemy enemy : enemies) {

                battle(player, enemy);

                if (!player.isAlive()) break;
            }

            if (player.isAlive()) {
                System.out.println("\nYOU DEFEATED ALL ENEMIES!");
            }

            System.out.println("\n1. Restart Game");
            System.out.println("2. Exit");

            int choice = input.nextInt();

            if (choice != 1) {
                playing = false;
            }
        }

        System.out.println("Game closed.");
    }
}
