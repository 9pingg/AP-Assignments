import java.util.ArrayList;
import java.util.Random;

class Bucket{
    private ArrayList<SoftToy> listOfToys;
    Bucket(){
        this.listOfToys = new ArrayList<SoftToy>();
    }

    public ArrayList<SoftToy> getListOfToys() {
        return listOfToys;
    }
}
class SoftToy implements Cloneable{
    private String name;
    private int tileNumber;
    private boolean directPrize;
    SoftToy(String name, int tileNumber){
        this.name = name;
        this.tileNumber = tileNumber;
        this.setDirectPrize();
    }
    @Override
    public SoftToy clone(){
        try{
            SoftToy copy = (SoftToy) super.clone();
            return copy;
        }catch(CloneNotSupportedException e){
            System.out.println("Couldn't clone the class.");
            return null;
        }
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectPrize() {
        return directPrize;
    }


    public void setDirectPrize() {
        if(tileNumber % 2 == 0){
            this.directPrize = true;
        }
        else {
            this.directPrize = false;
        }
    }

}
class Player{
    private String name;
    private Bucket bucket;
    private boolean gameEnd = false;
    private int roll;
    Player(String name){
        this.name = name;
        this.bucket = new Bucket();
    }
    public void addSoftToy(SoftToy st){
        getBucket().getListOfToys().add(st);
    }
    public void listAllSoftToys(){
        System.out.println("Soft Toys won by you are: ");
        if(bucket.getListOfToys().size() == 0){
            System.out.println();
        }
        for(SoftToy st: bucket.getListOfToys()){
            System.out.print(st.getName() + ", ");
        }
    }

    public boolean isGameEnd() {
        return gameEnd;
    }
    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getRoll() {
        return roll;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public String getName() {
        return name;
    }
}
class Game{
    private ArrayList<SoftToy> listOfSoftToys;
    private Dice dice;
    private int noOfPlayers;
    private ArrayList<Player> listOfPlayers;
    private String[] nameToys = new String[20];
    Game(){
        this.listOfPlayers = new ArrayList<Player>();
        this.listOfSoftToys = new ArrayList<>();
        this.dice = new Dice();
        this.initializeGame();
    }
    public String generateRandomString(int length){
        String charactersUsed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(charactersUsed.charAt(random.nextInt(charactersUsed.length())));
        }
        return stringBuilder.toString();
    }
    public void initializeGame(){
        for(int i = 1; i <= 20 ; i++){
            nameToys[i-1] = generateRandomString(6);
            this.getListOfSoftToys().add(new SoftToy(this.nameToys[i-1], i));
        }
    }


    public ArrayList<SoftToy> getListOfSoftToys() {
        return listOfSoftToys;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }
    public void addListOfPlayers(Player p){
        this.getListOfPlayers().add(p);
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public Dice getDice() {
        return dice;
    }
    public SoftToy findSoftToy(int tileno){
        for(SoftToy st: listOfSoftToys){
            if(st.getTileNumber() == tileno){
                return st;
            }
        }
        return null;
    }
    public int generateRandomInteger(){
        Random r = new Random();
        return r.nextInt(1000)+1;
    }
}
final class Dice
{
    private final Random randomNumber;
    public Dice() {
        this.randomNumber = new Random();
    }
    public int rollDice() {
        return this.randomNumber.nextInt(25) + 1;
    }
}

class Calculator <T>  {
    private T a;
    private T b;
    Calculator(T a, T b){
        this.a = a;
        this.b = b;
    }
    public T divide(){
        try {
            if (a instanceof Integer && b instanceof Integer) {
                return (T) (Integer) ((Integer) a / (Integer) b);
            } else {
                throw new InputtMismatchException("only integer allowed");
            }
        }catch(InputtMismatchException e){
            System.out.println(e.getMessage());
        }
        return (T) (Integer)Integer.MAX_VALUE;
    }
    public T add() {
        try {
            if (a instanceof String && b instanceof String) {
                return (T) (String) ((String) a + (String) b);
            } else {
                throw new InputtMismatchException("only String allowed");
            }
        }catch(InputtMismatchException e){
            System.out.println(e.getMessage());
        }
        String CONST_VALUE ="00000000";
        return (T) CONST_VALUE;
    }
}
class NoChoiceSelectedException extends RuntimeException{
    NoChoiceSelectedException(String msg){
        super(msg);
    }
}
class InputtMismatchException extends RuntimeException{
    InputtMismatchException(String msg){
        super(msg);
    }
}
class PastAllTilesException extends RuntimeException{
    PastAllTilesException(String msg){
        super(msg);
    }
}
