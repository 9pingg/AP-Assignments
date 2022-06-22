import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

class Player {
    private final String name;
    private int position;
    private int points;
    private int roll;
    // true if game is started
    // false if game not started
    private boolean gameStart = false;
    // true if game ends.
    // false if game is still going on.
    private boolean gameEnd = false;
    public Player(String name){
        this.name = name;
        this.points = 0;
        this.position = -1;
    }
    //GETTERS
    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getPoints() {
        return points;
    }

    public int getRoll() {
        return roll;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    //SETTERS
    public void setPoints(int points) {
        this.points = points;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setGameEnd(boolean game) {
        this.gameEnd = game;
    }
    public void setGameStart(boolean gameStart)
    {
        this.gameStart = gameStart;
    }
}
final class Dice
{
    private final Random randomNumber;
    public Dice() {
        this.randomNumber = new Random();
    }
    public int rollDice() {
        return this.randomNumber.nextInt(2) + 1;
    }
}
abstract class Floor{
    private int position;
    private int points;
    private String type;
    public Floor(int position, int points){
        this.points = points;
        this.position = position;
    }
    abstract public void displayMessage(Player p);

    protected int getPoints() {
        return points;
    }

    protected int getPosition() {
        return position;
    }

    protected String getType() {
        return type;
    }
}
class NormalSnake extends Floor{
    public NormalSnake(int position, int points) {
        super(position, points);
    }
    @Override
    public void displayMessage(Player p) {
        System.out.println(p.getName() +" has reached normal snake floor.");
    }
}

class KingCobra extends Floor{

    public KingCobra(int position, int points) {
        super(position, points);
    }
    @Override
    public void displayMessage(Player p) {
        System.out.println(p.getName() + " has reached king cobra.");
    }
}
class EmptyFloor extends Floor{

    public EmptyFloor(int position, int points) {
        super(position, points);
    }

    @Override
    public void displayMessage(Player p) {
        System.out.println(p.getName() + " has reached an empty floor.");

    }
}
class LadderFloor extends Floor{

    public LadderFloor(int position, int points) {
        super(position, points);
    }

    @Override
    public void displayMessage(Player p) {
        System.out.println(p.getName() + " has reached a ladder floor.");
    }
}
class ElevatorFloor extends Floor{

    public ElevatorFloor(int position, int points) {
        super(position, points);
    }

    @Override
    public void displayMessage(Player p) {
        System.out.println(p.getName() + " has reached the elevator floor.");
    }
}
class Game {
    private ArrayList<Player> listOfPlayers;
    private Dice dice;
    private int noOfPlayers;
    public Game(){
        this.listOfPlayers = new ArrayList<Player>();
        this.dice = new Dice();
    }
    public void setPosition(Floor floor, Player player){
        player.setPosition(floor.getPosition());
    }
    public void setPoints(Floor floor, Player player){
        player.setPoints(player.getPoints() + floor.getPoints());
    }
    public void findFloor(Player player){
        if(player.getPosition() == 2){
            Floor floor = new ElevatorFloor(10, 4);
            this.setPoints(floor, player);
            this.setPosition(floor, player);
            floor.displayMessage(player);
            System.out.println("Total Points " + player.getPoints());
            this.emptyFloorAfter(player);
        }
        else if(player.getPosition() == 5){
            Floor floor = new NormalSnake(1, -2);
            this.setPoints(floor, player);
            this.setPosition(floor, player);
            floor.displayMessage(player);
            System.out.println("Total Points " + player.getPoints());
            this.emptyFloorAfter(player);
        }
        else if(player.getPosition() == 8){
            Floor floor = new LadderFloor(12, 2);
            this.setPoints(floor, player);
            this.setPosition(floor, player);
            floor.displayMessage(player);
            System.out.println("Total Points " + player.getPoints());
            this.emptyFloorAfter(player);
        }
        else if(player.getPosition() == 11){
            Floor floor = new KingCobra(3, -4);
            this.setPoints(floor, player);
            this.setPosition(floor, player);
            floor.displayMessage(player);
            System.out.println("Total Points " + player.getPoints());
            this.emptyFloorAfter(player);
        }
        else{
            Floor floor = new EmptyFloor(0, 1);
            this.setPoints(floor, player);
            floor.displayMessage(player);
            System.out.println("Total Points " + player.getPoints());
        }


    }
    public void emptyFloorAfter(Player player){
        System.out.println("Player position Floor-" + player.getPosition());
        Floor floor = new EmptyFloor(0, 1);
        this.setPoints(floor, player);
        floor.displayMessage(player);
        System.out.println("Total Points " + player.getPoints());
    }
    public void addListOfPlayers(Player p){
        this.getListOfPlayers().add(p);
    }

    public ArrayList<Player> getListOfPlayers() {
        return this.listOfPlayers;
    }

    public Dice getDice() {
        return dice;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }
    public void showResults(){
        ArrayList<Player> temp = new ArrayList<Player>(listOfPlayers);
        Collections.copy(temp, listOfPlayers);
        temp.sort(new SortByPoints());
        Collections.reverse(temp);
        System.out.println("SCOREBOARD: ");
        int cnt = 1;
        for(Player p : temp){
            if(cnt != 1 && temp.get(temp.indexOf(p) -1).getPoints() == p.getPoints()){
                System.out.println((cnt-1) + ") name:" + p.getName() +" with points " + p.getPoints());
                continue;
            }
            System.out.println(cnt + ") name:" + p.getName() +" with points " + p.getPoints());
            cnt++;
        }

    }
}
class SortByPoints implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return o1.getPoints() - o2.getPoints();
    }
}
