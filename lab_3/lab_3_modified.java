import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
@SuppressWarnings("unchecked")

class Graph{
    ArrayList<Integer> adj[];
    Monster[] monsters = new Monster[20];

    Graph(){
        adj = new ArrayList[20];
        for(int i=0; i<=19; i++){
            adj[i] = new ArrayList<Integer>();
        }
        Random random = new Random();
        for(int i=0; i<=16; i++){
            int num = random.nextInt(3);
            if(num == 0){
                monsters[i] = new Monster(1, 100, "Goblin");
            }
            if(num == 1){
                monsters[i] = new Monster(2, 150, "Zombie");
            }
            if(num == 2){
                monsters[i] = new Monster(3, 200, "Fiend");
            }
        }
        int lion = random.nextInt(1);
        if(lion == 0){
            monsters[18] = new lionfang();
            int num = random.nextInt(4);
            if(num == 0){
                monsters[17] = new Monster(1, 100, "Goblin");
            }
            if(num == 1){
                monsters[17] = new Monster(2, 150, "Zombie");
            }
            if(num == 2){
                monsters[17] = new Monster(3, 200, "Fiend");
            }
        }
        else{
            monsters[17] = new lionfang();
            int num = random.nextInt(4);
            if(num == 0){
                monsters[18] = new Monster(1, 100, "Goblin");
            }
            if(num == 1){
                monsters[18] = new Monster(2, 150, "Zombie");
            }
            if(num == 2){
                monsters[18] = new Monster(3, 200, "Fiend");
            }
        }

        adj[1].add(2);
        adj[1].add(3);
        adj[1].add(4);

        adj[2].add(1);
        adj[2].add(3);
        adj[2].add(4);
        adj[2].add(6);

        adj[3].add(1);
        adj[3].add(2);
        adj[3].add(4);
        adj[3].add(7);

        adj[4].add(1);
        adj[4].add(3);
        adj[4].add(8);
        adj[4].add(9);

        adj[5].add(2);
        adj[5].add(6);
        adj[5].add(10);
        adj[5].add(11);

        adj[6].add(2);
        adj[6].add(5);
        adj[6].add(7);
        adj[6].add(10);
        adj[6].add(12);

        adj[7].add(3);
        adj[7].add(6);
        adj[7].add(8);
        adj[7].add(13);

        adj[8].add(4);
        adj[8].add(7);
        adj[8].add(9);
        adj[8].add(14);
        adj[8].add(16);

        adj[9].add(4);
        adj[9].add(8);
        adj[9].add(15);
        adj[9].add(16);

        adj[10].add(5);
        adj[10].add(6);
        adj[10].add(11);
        adj[10].add(17);

        adj[11].add(5);
        adj[11].add(10);
        adj[11].add(12);
        adj[11].add(17);

        adj[12].add(6);
        adj[12].add(11);
        adj[12].add(13);
        adj[12].add(17);

        adj[13].add(7);
        adj[13].add(12);
        adj[13].add(14);
        adj[13].add(17);
        adj[13].add(18);

        adj[14].add(8);
        adj[14].add(13);
        adj[14].add(15);
        adj[14].add(18);

        adj[15].add(9);
        adj[15].add(14);
        adj[15].add(16);
        adj[15].add(18);

        adj[16].add(8);
        adj[16].add(9);
        adj[16].add(15);
        adj[16].add(18);

        adj[17].add(10);
        adj[17].add(11);
        adj[17].add(12);
        adj[17].add(13);

        adj[18].add(13);
        adj[18].add(14);
        adj[18].add(15);
        adj[18].add(16);
    }
}

class Game{
    private String username;
    private Hero hero;
    private Graph graph = new Graph();
    private int location = 1;
    private int prev_location = 0;
    Game(String username, Hero hero){
        this.username = username;
        this.hero = hero;
    }

    String get_name(){
        return username;
    }

    Hero get_hero(){
        return hero;
    }

    void set_hero(Hero hero){
        this.hero = hero;
    }

    int getLocation(){
        return location;
    }

    void setLocation(int location){
        prev_location = this.location;
        this.location = location;
    }

    int getPrev_location(){
        return prev_location;
    }

    ArrayList<Integer> get_adj(){
        return graph.adj[location];
    }

    Monster getMonster(){
        return graph.monsters[location];
    }

    void hint(){
        boolean noHint = true;
        if(location == 1){
            for(int element: graph.adj[location]){
                if(graph.monsters[element].get_level() == 1){
                    System.out.println("Hint: Go to location " + element);
                    noHint = false;
                    break;
                }
            }
        }
        if(location == 2 || location == 3 || location == 4){
            boolean flag = false;
            for(int element: graph.adj[location]){
                if(graph.monsters[element].get_level() == 1){
                    System.out.println("Hint: Go to location " + element);
                    flag = true;
                    noHint = false;
                    break;
                }
            }
            if(!flag){
                for(int element: graph.adj[location]){
                    if(graph.monsters[element].get_level() == 2){
                        System.out.println("Hint: Go to location " + element);
                        noHint = false;
                        break;
                    }
                }
            }
        }
        if(location == 5 || location == 6 || location == 7 || location == 8 || location == 9){
            boolean flag = false;
            for(int element: graph.adj[location]){
                if(graph.monsters[element].get_level() == 1){
                    System.out.println("Hint: Go to location " + element);
                    flag = true;
                    noHint = false;
                    break;
                }
            }
            if(!flag){
                for(int element: graph.adj[location]){
                    if(graph.monsters[element].get_level() == 2){
                        System.out.println("Hint: Go to location " + element);
                        flag = true;
                        noHint = false;
                        break;
                    }
                }
            }
            if(!flag){
                for(int element: graph.adj[location]){
                    if(graph.monsters[element].get_level() == 3){
                        System.out.println("Hint: Go to location " + element);
                        noHint = false;
                        break;
                    }
                }
            }
        }
        if(location == 10 || location == 11 || location == 12 || location == 13 || location == 14 || location == 15){
            boolean flag = false;
            for(int element: graph.adj[location]){
                if(graph.monsters[element].get_level() == 3){
                    flag = true;
                    System.out.println("Hint: Go to location " + element);
                    noHint = false;
                    break;
                }
            }
            if(!flag){
                for(int element: graph.adj[location]){
                    if(graph.monsters[element].get_level() == 2){
                        System.out.println("Hint: Go to location " + element);
                        flag = true;
                        noHint = false;
                        break;
                    }
                }
            }
            if(!flag){
                for(int element: graph.adj[location]){
                    if(graph.monsters[element].get_level() == 3){
                        System.out.println("Hint: Go to location " + element);
                        noHint = false;
                        break;
                    }
                }
            }
        }
        if(noHint){
            System.out.println("No hint");
        }
    }
}

class Type{
//    superclass for hero and monster
    private int level;
    private double hp;
    private double default_hp;

    Type(int level, double hp){
        this.level = level;
        this.hp = hp;
        default_hp = hp;
    }

    int get_level(){
        return level;
    }

    double get_hp(){
        return hp;
    }

    void set_hp(double hp){
        this.hp = hp;
    }

    void set_level(int level){
        this.level = level;
    }

    double getDefault_hp(){
        return default_hp;
    }

    void setDefault_hp(double default_hp){
        this.default_hp = default_hp;
    }
}

abstract class Hero extends Type{
    private double xp = 0;
    private double attackAttribute;
    private double defenseAttribute;
    private int move;
    private boolean defense = false;
    private boolean special = false;
    private double special_attack;
    private double special_defense;
    private int special_moves = -1;

    Hero(int level, double hp, double attackAttribute, double defenseAttribute){
        super(level, hp);
        this.attackAttribute = attackAttribute;
        this.defenseAttribute = defenseAttribute;
        special_attack = attackAttribute;
        special_defense = defenseAttribute;
    }

    void set_xp(int xp){
        this.xp += xp;
    }

    void setSpecial_attack(double special_attack){
        this.special_attack += special_attack;
    }

    double getSpecial_attack(){
        return special_attack;
    }

    void setSpecial_defense(double special_defense){
        this.special_defense += special_defense;
    }

    double getSpecial_defense(){
        return special_defense;
    }

    void setSpecial_moves(int special_moves){
        this.special_moves = special_moves;
    }

    int getSpecial_moves(){
        return special_moves;
    }

    void nextLevel(){
        if(xp == 20){
//            after increase of 20 xp at each level there is level up
            this.set_level(this.get_level() + 1);
            if(get_level() == 2){
                this.set_hp(150);
                this.setSpecial_hp(150);
                this.setDefault_hp(150);
            }
            if(get_level() == 3){
                this.set_hp(200);
                this.setSpecial_hp(200);
                this.setDefault_hp(200);
            }
            if(get_level() == 4){
                this.set_hp(250);
                this.setSpecial_hp(250);
                this.setDefault_hp(250);
            }
            xp = 0;
            attackAttribute++;
            defenseAttribute++;
        }
    }

    private double special_hp = this.get_hp();

    void setSpecial_hp(double special_hp){
        this.special_hp = special_hp;
    }

    double getSpecial_hp(){
        return special_hp;
    }

    boolean getSpecial(){
        return special;
    }

    void setSpecial(boolean special){
        this.special = special;
    }

    boolean getDefense(){
        return defense;
    }

    void setDefense(boolean defense){
        this.defense = defense;
    }

    double getDefenseAttribute(){
        return defenseAttribute;
    }

    void setDefenseAttribute(double defense){
        defenseAttribute = defense;
    }

    double getAttackAttribute(){
        return attackAttribute;
    }

    void setAttackAttribute(double attack){
        attackAttribute = attack;
    }

    int getMove(){
        return move;
    }

    void setMove(int move){
        this.move = move;
    }
    abstract String get_name();
    abstract void attack(Monster monster);
    abstract void defense();
    abstract void special(Monster monster);
}
class Warrior extends Hero{
    private final String name = "Warrior";
    String get_name(){
        return name;
    }

    Warrior(int level, double hp){
        super(level, hp, 10, 3);
    }
    void attack(Monster monster){
        if(this.getSpecial_moves() >= 0){
            monster.set_hp(monster.get_hp() - this.getSpecial_attack());
        }
        else{
            monster.set_hp(monster.get_hp() - this.getAttackAttribute());
        }
        monster.setSpecial_hp(monster.get_hp());
    }

    void defense(){
        this.setDefense(true);
    }

    void special(Monster monster){
        this.setSpecial(true);
        this.setSpecial_moves(3);
        this.setSpecial_attack(5);
        this.setSpecial_defense(5);
        System.out.println("For next 3 moves attack attribute and defense attribute increased by 5");
    }
}

class Thief extends Hero{
    private final String name = "Thief";
    String get_name(){
        return name;
    }
    Thief(int level, double hp){
        super(level, hp, 6, 4);
    }
    void attack(Monster monster){
        monster.set_hp(monster.get_hp() - this.getAttackAttribute());
        monster.setSpecial_hp(monster.get_hp());
    }
    void defense(){
        this.setDefense(true);
    }
    void special(Monster monster){
        this.setSpecial(true);
        this.setSpecial_moves(3);
        System.out.println("You have stolen " + 0.3 * monster.get_hp() + " Hp from the monster!");
        this.set_hp(this.get_hp() + 0.3 * monster.get_hp());
        if(this.get_hp() > 100){
            this.set_hp(100);
        }
        monster.set_hp(0.7 * monster.get_hp());
    }
}

class Mage extends Hero{
    private final String name = "Mage";
    String get_name(){
        return name;
    }
    Mage(int level, double hp){
        super(level, hp, 5, 5);
    }
    void attack(Monster monster){
        // if(monster.getSpecialMoves() >= 0){
        //     monster.set_hp(monster.getSpecial_hp() - this.getAttackAttribute());
        // }
        // else{
            monster.set_hp(monster.get_hp() - this.getAttackAttribute());
        // }
        monster.setSpecial_hp(monster.get_hp());
    }
    void defense(){
        this.setDefense(true);
    }
    void special(Monster monster){
        this.setSpecial(true);
        this.setSpecial_moves(3);
        monster.setSpecial_hp(0.95 * monster.get_hp());
        monster.set_hp(0.95* monster.get_hp());
    }
}

class Healer extends Hero{
    private final String name = "Healer";
    String get_name(){
        return name;
    }
    Healer(int level, double hp){
        super(level, hp, 4, 8);
    }
    void attack(Monster monster){
        monster.set_hp(monster.get_hp() - this.getAttackAttribute());
        monster.setSpecial_hp(monster.get_hp());
    }
    void defense(){
        this.setDefense(true);
    }
    void special(Monster monster){
        this.setSpecial(true);
        this.setSpecial_moves(3);
        this.setSpecial_hp(1.05 * this.get_hp());
        this.set_hp(1.05 * this.get_hp());
    }
}

class Monster extends Type{
    private final String name;
    Monster(int level, double hp, String name){
        super(level, hp);
        this.name = name;
    }

    String getName(){
        return name;
    }

    private int specialMoves = 0;
    private double special_hp = this.get_hp();

    void setSpecial_hp(double special_hp){
        this.special_hp = special_hp;
    }

    double getSpecial_hp(){
        return special_hp;
    }

    void setSpecialMoves(int specialMoves){
        this.specialMoves = specialMoves;
    }

    double getSpecialMoves(){
        return specialMoves;
    }

    public double attack(Hero hero){
//        normal attack reduce by 0-1/4 of monster hp
        Random random = new Random();
        double rand = (random.nextGaussian() * 0.0625) + 0.125;
        while(rand > 0.25 || rand < 0){
            rand = (random.nextGaussian() * 0.0625) + 0.125;
        }
        double factor = rand * this.get_hp();
        if(hero.getSpecial_moves() >= 0){
            factor = rand * this.getSpecial_hp();
        }
        if(hero.getDefense()){
//            hero defense
            factor -= hero.getDefenseAttribute();
        }
        if(factor < 0){
            factor = 0;
        }
//        nextGaussian() gives [-1,1] we need [0, 1/4]
        if(hero.getSpecial_moves() >= 0 && (hero.get_name().equals("Mage") || hero.get_name().equals("Healer"))){
            hero.set_hp(hero.getSpecial_hp() - factor);
        }
        else{
            hero.set_hp(hero.get_hp() - factor);
        }
        hero.setDefense(false);
        hero.setSpecial_hp(hero.get_hp());
        return factor;
    }
}

class lionfang extends Monster{
    lionfang(){
        super(4, 250, "Lionfang");
    }
    @Override
    public double attack(Hero hero){
        double opponent_hp = hero.get_hp();
//        special lionfang attack half hp
        Random random = new Random();
        boolean special = random.nextInt(10)==0;
//        probability of 1/10
        if(special){
            hero.set_hp(0.5 * opponent_hp);
            return 0.5 * opponent_hp;
        }
        else{
//        normal attack reduce by 0-1/4 of monster hp
            double rand = (random.nextGaussian() * 0.0625) + 0.125;
//            4 standard deviations so * 0.25/4 and mean = 0.25/2
            while(rand > 0.25 || rand < 0){
                rand = (random.nextGaussian() * 0.0625) + 0.125;
            }
            double factor = rand * this.get_hp();
            if(hero.getSpecial_moves() >= 0){
                factor = rand * this.getSpecial_hp();
            }
            if(hero.getDefense()){
//            hero defense
                factor -= hero.getDefenseAttribute();
            }
            if(factor < 0){
                factor = 0;
            }
//        nextGaussian() gives [-1,1] we need [0, 1/4]
            if(hero.getSpecial_moves() >= 0){
                hero.set_hp(hero.getSpecial_hp() - factor);
            }
            else{
                hero.set_hp(hero.get_hp() - factor);
            }
            hero.setDefense(false);
            return factor;
        }
    }
}
public class lab_3_modified {
    public static void main(String[] args){
        ArrayList<Game> games = new ArrayList<Game>();
        while(true) {
            System.out.println("Welcome to ArchLegends");
            System.out.println("Choose your option");
            System.out.println("1) New User");
            System.out.println("2) Existing User");
            System.out.println("3) Exit");
            Scanner scan = new Scanner(System.in);
            int option = scan.nextInt();
            if(option == 1){
//            new user
                scan.nextLine();
                System.out.println("Enter Username");
                String username = scan.nextLine();
                System.out.println("Choose a Hero");
                System.out.println("1) Warrior");
                System.out.println("2) Thief");
                System.out.println("3) Mage");
                System.out.println("4) Healer");
                int heroOption = scan.nextInt();
                Hero hero = new Warrior(1, 100);
                if(heroOption == 1){
                    hero = new Warrior(1, 100);
                }
                if(heroOption == 2){
                    hero = new Thief(1, 100);
                }
                if(heroOption == 3){
                    hero = new Mage(1, 100);
                }
                if(heroOption == 4){
                    hero = new Healer(1, 100);
                }
                Game game = new Game(username, hero);
                games.add(game);
                System.out.println("User Creation done. Username: " + game.get_name() + " Hero type: " + game.get_hero().get_name() + " Log in to play the game . Exiting");
            }
            if(option == 2){
//            existing user
                scan.nextLine();
                System.out.println("Enter Username");
                String username = scan.nextLine();
                boolean userFlag = false;
                for(Game g: games){
                    if(g.get_name().equals(username)){
                        System.out.println("User Found... logging in");
                        userFlag = true;
                        System.out.println("Welcome " + username);
                        while(true){
                            if(g.getLocation() == 0){
                                System.out.println("You are at the starting location. Choose path:");
                            }
                            else{
                                System.out.println("You are at " + g.getLocation() + " location. Choose path:");
                            }
                            ArrayList<Integer> neighbours = g.get_adj();
                            int i = 1;
                            HashMap<Integer, Integer> map = new HashMap<>();
                            for(int neighbour: neighbours){
                                if(neighbour == g.getPrev_location()){
                                    System.out.println(i + ") Go back");
                                    map.put(i, neighbour);
                                }
                                else{
                                    System.out.println(i + ") Go to Location " + neighbour);
                                    map.put(i, neighbour);
                                }
                                i++;
                            }
                            g.hint();
                            System.out.println("Enter -1 to exit");
                            int locationOption = scan.nextInt();
                            if(locationOption == -1){
                                break;
                            }
                            else {
                                int location = map.get(locationOption);
                                g.setLocation(location);
                                System.out.println("Moving to location " + location);
                                Monster monster = g.getMonster();
                                System.out.println("Fight Started. Your fighting a level " + monster.get_level() + " Monster");
                                Hero hero = g.get_hero();
                                boolean flag = false;
                                while (true) {
                                    System.out.println("Choose move");
                                    System.out.println("1) Attack");
                                    System.out.println("2) Defense");
                                    if (hero.getMove() >= 4 && !flag) {
                                        System.out.println("3) Special Attack");
                                    }
                                    int heroMove = scan.nextInt();
                                    if(hero.getSpecial_moves() >= 0 && (hero.get_name().equals("Healer") || hero.get_name().equals("Mage"))){
                                        System.out.println(hero.getSpecial_moves());
                                        hero.special(monster);
                                    }
                                    if (heroMove == 1) {
                                        System.out.println("You choose to attack");
                                        hero.attack(monster);
                                        if (hero.getSpecial_moves() >= 0) {
                                            System.out.println("You attacked and inflicted " + hero.getSpecial_attack() + " damage to the monster.");
                                        } else {
                                            System.out.println("You attacked and inflicted " + hero.getAttackAttribute() + " damage to the monster.");
                                        }
                                    }
                                    if (heroMove == 2) {
                                        System.out.println("You choose to defend");
                                        hero.defense();
                                        if (hero.getSpecial_moves() >= 0) {
                                            System.out.println("Monster attack reduced by " + hero.getSpecial_defense());
                                        } else {
                                            System.out.println("Monster attack reduced by " + hero.getDefenseAttribute());
                                        }
                                    }
                                    if (heroMove == 3) {
                                        System.out.println("Special power activated");
                                        System.out.println("Performing special attack");
                                        if(hero.get_name().equals("Healer")){
                                            System.out.println("For next 3 moves hp would be increased by " + 0.05*hero.get_hp());
                                        }
                                        if(hero.get_name().equals("Mage")){
                                            System.out.println("For next 3 moves monster hp would be decreased by " + (0.05 * monster.get_hp()));
                                        }
                                        hero.special(monster);
                                        flag = true;
                                    }
                                    hero.setMove(hero.getMove() + 1);
                                    hero.setSpecial_moves(hero.getSpecial_moves() - 1);
                                    if (flag && hero.getSpecial_moves() == -1) {
                                        flag = false;
                                    }
                                    if (hero.getSpecial_moves() >= 0 && (hero.get_name().equals("Mage") || hero.get_name().equals("Healer"))) {
                                        System.out.println("Your Hp: " + hero.getSpecial_hp() + "/" + hero.getDefault_hp() + " Monster's Hp: " + monster.getSpecial_hp() + "/" + monster.getDefault_hp());
                                    } else {
                                        System.out.println("Your Hp: " + hero.get_hp() + "/" + hero.getDefault_hp() + " Monster's Hp: " + monster.get_hp() + "/" + monster.getDefault_hp());
                                    }
                                    if (monster.get_hp() <= 0) {
                                        if(monster.getName().equals("Lionfang")){
                                            System.out.println("Game won!");
                                            break;
                                        }
                                        System.out.println("Monster killed");
                                        System.out.println("20 XP awarded");
                                        hero.set_xp(20);
                                        hero.nextLevel();
                                        hero.setMove(0);
                                        monster.set_hp(monster.getDefault_hp());
                                        System.out.println("Level Up: level: " + hero.get_level());
                                        System.out.println("Fight won proceed to the next location.");
                                        break;
                                    } else if (hero.get_hp() <= 0) {
                                        System.out.println("You lost!");
                                        System.out.println("Back to start location");
                                        g.setLocation(0);
                                        hero.setMove(0);
                                        hero.set_hp(100);
                                        hero.setSpecial_hp(100);
                                        break;
                                    } else {
                                        System.out.println("Monster attack!");
                                        double reduce = monster.attack(hero);
                                        System.out.println("The monster attacked and inflicted " + reduce + " damage to you.");
                                        if (hero.getSpecial_moves() >= 0 && (hero.get_name().equals("Mage") || hero.get_name().equals("Healer"))) {
                                            System.out.println("Your Hp: " + hero.getSpecial_hp() + "/" + hero.getDefault_hp() + " Monster's Hp: " + monster.getSpecial_hp() + "/" + monster.getDefault_hp());
                                        } else {
                                            System.out.println("Your Hp: " + hero.get_hp() + "/" + hero.getDefault_hp() + " Monster's Hp: " + monster.get_hp() + "/" + monster.getDefault_hp());
                                        }
                                        if (hero.get_hp() <= 0) {
                                            System.out.println("You lost!");
                                            System.out.println("Back to start location");
                                            g.setLocation(0);
                                            monster.set_hp(monster.getDefault_hp());
                                            hero.setMove(0);
                                            hero.set_hp(100);
                                            hero.setSpecial_hp(100);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!userFlag){
                    System.out.println("User not found");
                }
            }
            
            if(option == 3){
//            exit
                System.exit(0);
            }
        }
    }
}
