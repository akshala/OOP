import java.util.*;

class Game{
    private int total_tiles;
    private HashMap<Integer, Type> map = new HashMap<>();
    private int num_snakes;
    private int num_vultures;
    private int num_crickets;
    private int num_trampolines;
    private int num_white;
    private int snake_throw;
    private int vulture_throw;
    private int cricket_throw;
    private int trampoline_throw;
    private int location = 1;
    private Player player;
    private Dice dice = new Dice();

    Game(int total_tiles){
        this.total_tiles = total_tiles;
        Random random = new Random();
        num_snakes = 1 + random.nextInt(total_tiles/10);
        num_vultures = 1 + random.nextInt(total_tiles/10);
        num_crickets = 1 + random.nextInt(total_tiles/10);
        num_trampolines = 1 + random.nextInt(total_tiles/10);
        num_white = total_tiles - (num_snakes + num_vultures + num_crickets + num_trampolines);
        snake_throw = 1 + random.nextInt(10);
        vulture_throw = 1 + random.nextInt(10);
        cricket_throw = 1 + random.nextInt(10);
        trampoline_throw = 1 + random.nextInt(10);
    }

    int getNum_snakes(){
        return num_snakes;
    }

    int getNum_vultures(){
        return num_vultures;
    }

    int getNum_crickets(){
        return num_crickets;
    }

    int getNum_trampolines(){
        return num_trampolines;
    }

    int getSnake_throw(){
        return snake_throw;
    }

    int getVulture_throw(){
        return vulture_throw;
    }

    int getCricket_throw(){
        return cricket_throw;
    }

    int getTrampoline_throw(){
        return trampoline_throw;
    }

    void setPlayer_name(String player_name){
        this.player = new Player(player_name);
    }

    String getPlayer_name(){
        return player.getPlayer_name();
    }

    void set_tiles(){
//        initialises all tiles in the game as white
        for(int i=2; i<total_tiles; i++){
            Type white = new White();
            map.put(i, white);
        }
        ArrayList<Integer> invalid_pos_trampoline = new ArrayList<Integer>();
        Random random = new Random();
        int num_set_snakes = 0;
        int num_set_vultures = 0;
        int num_set_crickets = 0;
        int num_set_trampolines = 0;
        while(num_set_snakes < num_snakes){
//            setting snake tiles
            int num = 5 + random.nextInt(total_tiles - 5);
            if(map.get(num) instanceof White){
                Type snake = new Snake(snake_throw);
                map.put(num, snake);
                num_set_snakes++;
                if(num - snake_throw > 0){
                    invalid_pos_trampoline.add(num - snake_throw);
                    invalid_pos_trampoline.add(num);
                }
            }
        }
        while(num_set_vultures < num_vultures){
//            setting vulture tiles
            int num = 5 + random.nextInt(total_tiles - 5);
            if(map.get(num) instanceof White){
                Type vulture = new Vulture(vulture_throw);
                map.put(num, vulture);
                num_set_vultures++;
                if(num - vulture_throw > 0){
                    invalid_pos_trampoline.add(num - vulture_throw);
                    invalid_pos_trampoline.add(num);
                }
            }
        }
        while(num_set_crickets < num_crickets){
//            setting cricket tiles
            int num = 5 + random.nextInt(total_tiles - 5);
            if(map.get(num) instanceof White){
                Type cricket = new Cricket(cricket_throw);
                map.put(num, cricket);
                num_set_crickets++;
                if(num - cricket_throw > 0){
                    invalid_pos_trampoline.add(num - cricket_throw);
                    invalid_pos_trampoline.add(num);
                }
            }
        }
        while(num_set_trampolines < num_trampolines){
//            setting trampoline tiles
            int num = 5 + random.nextInt(total_tiles - 5);
            if((map.get(num) instanceof White) && !invalid_pos_trampoline.contains(num + trampoline_throw)){
                Type trampoline = new Trampoline(trampoline_throw);
                map.put(num, trampoline);
                num_set_trampolines++;
            }
        }
    }

    class GameWinnerException extends Exception{
        GameWinnerException(){
            super("Game Winner Exception");
        }
    }

    private void win() throws GameWinnerException{
        throw new GameWinnerException();
    }

    void play() throws CricketBiteException, SnakeBiteException, VultureBiteException, TrampolineException {
        int roll = 1;
        int total_snakeBites = 0;
        int total_vultureBites = 0;
        int total_cricketBites = 0;
        int total_trampolines = 0;
        boolean flag_cage = true;
        boolean flag_forward = true;
        while(location != total_tiles){
            int die = 0;
            if(flag_forward || location == 1){
                die = dice.dieThrow();
                System.out.print("[Roll-" + roll + "]: " + this.player.getPlayer_name() + " rolled " + die + " at Tile-" + location + ", ");
                roll++;
            }
            if(flag_cage){
                if(die == 6){
                    System.out.println("You are out of the cage! You get a free roll.");
                    flag_cage = false;
                    continue;
                }
                else{
                    System.out.print("OOPs you need 6 to start.");
                }
            }
            else{
                if((die + location) > total_tiles){
                    System.out.println("landed on tile " + location);
                }
                else if(die + location == total_tiles){
                    location = die + location;
                    try{
                        this.win();
                    }
                    catch(GameWinnerException e){
                        System.out.println("landed on tile " + location);
                        System.out.println("         " + player.getPlayer_name() + " wins the race in " + (roll-1) + " moves");
                        System.out.println("Total snake bites = " + total_snakeBites);
                        System.out.println("Total vulture bites = " + total_vultureBites);
                        System.out.println("Total cricket bites = " + total_cricketBites);
                        System.out.println("Total trampolines = " + total_trampolines);
                    }
                    location += die;
                    break;
                }
                else{
                    if(flag_forward || location == 1){
                        location += die;
                        System.out.println("landed on tile " + location);
                    }
                }
                System.out.println("         Trying to shake the tile " + location);
                Type tile = map.get(location);
                String tile_name = tile.getName();
                int move = 0;
                try{
                    tile.bite();
                }
                catch (SnakeBiteException e) {
                    System.out.println("         Hiss..I am a snake. You go back " + snake_throw + " tiles");
                    move = -1 * snake_throw;
                    total_snakeBites++;
                    flag_forward = false;
                }
                catch (VultureBiteException e) {
                    System.out.println("         Yapping..I am a vulture. You go back " + vulture_throw + " tiles");
                    move = -1 * vulture_throw;
                    total_vultureBites++;
                    flag_forward = false;
                }
                catch (CricketBiteException e) {
                    System.out.println("         Chirp..I am a cricket. You go back " + cricket_throw + " tiles");
                    move = -1 * cricket_throw;
                    total_cricketBites++;
                    flag_forward = false;
                }
                catch (TrampolineException e) {
                    System.out.println("         PingPong..I am a trampoline. You advance " + trampoline_throw + " tiles");
                    move = trampoline_throw;
                    total_trampolines++;
                    flag_forward = false;
                }
                catch (WhiteTileException e){
                    System.out.println("         I am a white tile.");
                    flag_forward = true;
                }
                finally{
                    location += move;
                    if(location < 1){
                        location = 1;
                        System.out.println("         " + player.getPlayer_name() + " moved to tile-" + location + ", as it can't go back further.");
                        flag_cage = true;
                    }
                    else if(location >= total_tiles){
                        try{
                            this.win();
                        }
                        catch(GameWinnerException e){
                            System.out.println("       " + player.getPlayer_name() + " wins the race in " + (roll-1) + " moves");
                            System.out.println("Total snake bites = " + total_snakeBites);
                            System.out.println("Total vulture bites = " + total_vultureBites);
                            System.out.println("Total cricket bites = " + total_cricketBites);
                            System.out.println("Total trampolines = " + total_trampolines);
                            break;
                        }
                    }
                    else{
                        System.out.print("         " + player.getPlayer_name() + " moved to tile-" + location);
                    }
                }

            }
            System.out.println();
        }
    }
}

class Player{
    private String player_name;
    Player(String player_name){
        this.player_name = player_name;
    }

    String getPlayer_name(){
        return player_name;
    }
}

class Dice{
    int dieThrow(){
//        die throw
        Random random = new Random();
        int num = 1 + random.nextInt(6);
        return num;
    }
}

abstract class Type{
    private int obj_throw;
    Type(int obj_throw){
        this.obj_throw = obj_throw;
    }

    int get_throw(){
        return obj_throw;
    }

    abstract String getName();
    abstract void bite() throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineException, WhiteTileException;
}

class SnakeBiteException extends Exception{
    SnakeBiteException(){
        super("Snake Bite Exception");
    }
}

class Snake extends Type{
    private static final String name = "Snake";

    Snake(int snake_throw){
        super(snake_throw);
    }

    @Override
    String getName(){
        return name;
    }

    @Override
    void bite() throws SnakeBiteException {
        throw new SnakeBiteException();
    }

}

class VultureBiteException extends Exception{
    VultureBiteException(){
        super("Vulture Bite Exception");
    }
}

class Vulture extends Type{
    private static final String name = "Vulture";

    Vulture(int vulture_throw){
        super(vulture_throw);
    }

    @Override
    String getName(){
        return name;
    }

    @Override
    void bite() throws VultureBiteException{
        throw new VultureBiteException();
    }
}

class CricketBiteException extends Exception{
    CricketBiteException(){
        super("Cricket Bite Exception");
    }
}

class Cricket extends Type{
    private static final String name = "Cricket";

    Cricket(int cricket_throw){
        super(cricket_throw);
    }

    @Override
    String getName(){
        return name;
    }

    @Override
    void bite() throws CricketBiteException{
        throw new CricketBiteException();
    }
}

class WhiteTileException extends Exception{
    WhiteTileException(){
        super("White Tile Exception");
    }
}

class White extends Type{
    private static final String name = "White";

    White(){
        super(0);
    }
    @Override
    String getName(){
        return name;
    }

    @Override
    void bite() throws WhiteTileException{
        throw new WhiteTileException();
    }
}

class TrampolineException extends Exception{
    TrampolineException(){
        super("trampoline Exception");
    }
}

class Trampoline extends Type{
    private static final String name = "Trampoline";

    Trampoline(int trampoline_throw){
        super(trampoline_throw);
    }

    @Override
    String getName(){
        return name;
    }

    @Override
    void bite() throws TrampolineException{
        throw new TrampolineException();
    }
}

class invalidInput extends Exception{
    invalidInput(){
        super("Invalid input");
    }
}

public class lab5 {
    public static void main(String[] args){
        System.out.println("Enter total number of tiles on racetrack(length)");
        Scanner scan = new Scanner(System.in);
        boolean num_taken = false;
        int total_tiles = 0;
        while(!num_taken){
            try{
                total_tiles = scan.nextInt();
                if(total_tiles < 10){
                    throw new invalidInput();
                }
                num_taken = true;
            }
            catch(InputMismatchException | invalidInput e){
                System.out.println("Wrong input. Enter valid input (>=10)");
                scan.nextLine();
            }
        }
        System.out.println("Setting up the racetrack");
        Game game = new Game(total_tiles);
        int num_snakes = game.getNum_snakes();
        int num_crickets = game.getNum_crickets();
        int num_vultures = game.getNum_vultures();
        int num_trampolines = game.getNum_trampolines();
        System.out.println("Danger! There are " + num_snakes + ", " + num_crickets + ", " + num_vultures + " number of snakes, crickets and vultures on your track!");
        int snake_throw = game.getSnake_throw();
        int cricket_throw = game.getCricket_throw();
        int vulture_throw = game.getVulture_throw();
        int trampoline_throw = game.getTrampoline_throw();
        System.out.println("Danger! Each snake, cricket and vulture can throw you back by " + snake_throw + ", " + cricket_throw + ", " + vulture_throw + " number of tiles respectively!");
        System.out.println("Good new! There are " + num_trampolines + " number of trampolines on your track!");
        System.out.println("Good news! Each trampoline can help you advance by " + trampoline_throw + " number of tiles!");
        System.out.println("Enter the player name");
        scan.nextLine();
        String player_name = "";
        boolean name_taken = false;
        while(!name_taken){
            try{
                player_name = scan.nextLine();
                name_taken = true;
            }
            catch(InputMismatchException e){
                System.out.println("Wrong input. Enter valid input");
                scan.nextLine();
            }
        }
        game.setPlayer_name(player_name);
        System.out.println("Starting the game with " + player_name + " at Tile-1");
        System.out.println("Control transferred to the computer to roll the dice for " + player_name);
        System.out.print("Hit enter to start the game");
        scan.nextLine();
        System.out.println("Game started ===============>");
        game.set_tiles();
        try{
            game.play();
        }
        catch(Exception ignored){
        }
    }
}
