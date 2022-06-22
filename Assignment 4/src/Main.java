import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] positions = {"first", "Second", "third", "fourth", "fifth"};
        System.out.println("Hit enter to initialize the game");
        sc.nextLine();
        Game game = new Game();
        System.out.println("\nEnter the number of Players: ");
        int noOfPlayers = sc.nextInt();
        game.setNoOfPlayers(noOfPlayers);
        sc.nextLine();

        while(noOfPlayers != 0) {
            System.out.println("Enter player No. " + (game.getNoOfPlayers() - noOfPlayers) +" name and hit enter");
            String playerName = sc.nextLine();
            Player player = new Player(playerName);
            game.addListOfPlayers(player);
            int counter = 0;
            System.out.println("The game setup is ready");
            while (!player.isGameEnd() && counter != 5) {
                System.out.println("\nHit enter for your " + positions[counter] + " hop");
                sc.nextLine();
                try{
                    player.setRoll(game.getDice().rollDice());
                    System.out.println(player.getRoll());
                    if(player.getRoll() > 20){
                        throw new PastAllTilesException("You are too energetic and zoomed past all the tiles. Muddy Puddle Splash!");
                    }
                    System.out.println("You landed on tile " + player.getRoll());
                    SoftToy st = game.findSoftToy(player.getRoll());
                    if(!st.isDirectPrize()){
                        boolean check = true;
                        while(check) {
                            System.out.println("Question answer round. Integer or String?");
                            String ans = sc.nextLine();
                            try {
                                if (ans.toLowerCase().equals("integer")) {
                                    check = false;
                                    int a = game.generateRandomInteger();
                                    int b = game.generateRandomInteger();
                                    Calculator<Integer> calculator = new Calculator<Integer>(a, b);
                                    System.out.println("Calculate the result of " + a + " divided by " + b + " (integer division) : ");
                                    int res = -1;
                                    try {
                                        res = sc.nextInt();
                                    }
                                    catch (InputMismatchException e){
                                        System.out.println("Integer expected.(Wrong Input)");
                                    }
                                    sc.nextLine();
                                    if (res == calculator.divide()) {
                                        System.out.println("Correct answer");
                                        System.out.println("You won a" + st.getName() + " soft toy");
                                        player.addSoftToy(st.clone());
                                    } else {
                                        System.out.println("Incorrect answer\n" + "You did not win any soft toy");
                                    }
                                } else if (ans.toLowerCase().equals("string")) {
                                    check = false;
                                    String a = game.generateRandomString(4);
                                    String b = game.generateRandomString(4);
                                    System.out.println("Calculate the concatenation of strings " + a + " and " + b);
                                    Calculator<String> calculator = new Calculator<String>(a, b);
                                    String c = "00000000";
                                    try {
                                        c = sc.nextLine();
                                    }
                                    catch(InputMismatchException e){
                                        System.out.println("String expected.(Wrong Input");
                                    }
                                    if (c.equals(calculator.add())) {
                                        System.out.println("Correct answer");
                                        System.out.println("You won a" + st.getName() + " soft toy");
                                        player.addSoftToy(st.clone());
                                    } else {
                                        System.out.println("Incorrect answer\n" + "You did not win any soft toy");
                                    }
                                } else {
                                    throw new NoChoiceSelectedException("select either integer or string");
                                }
                            } catch (NoChoiceSelectedException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    else{
                        System.out.println("You won a" + st.getName()  +" soft toy");
                        player.addSoftToy(st.clone());
                    }

                }catch(PastAllTilesException e){
                    System.out.println(e.getMessage());
                }finally {
                    counter++;
                }

            }
            System.out.println();
            player.listAllSoftToys();
            noOfPlayers --;
        }

    }
}

