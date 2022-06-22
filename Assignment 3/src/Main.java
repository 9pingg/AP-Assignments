import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = new Game();
        System.out.println("Enter the number of Players: ");
        int noOfPlayers = sc.nextInt();
        game.setNoOfPlayers(noOfPlayers);
        sc.nextLine();
        while(noOfPlayers != 0) {
            System.out.println("Enter player No. " + (game.getNoOfPlayers() - noOfPlayers) +" name and hit enter");
            String playerName = sc.nextLine();
            Player player = new Player(playerName);
            game.addListOfPlayers(player);
            System.out.println("The game setup is ready");
            while (!player.isGameEnd()) {
                if (player.getPosition() >= 13) {
                    player.setGameEnd(true);
                    //TODO game end printing
                    System.out.println("******************************************\n");
                    System.out.println("Total Points: " + player.getPoints());
                    System.out.println("Game Over");
                    System.out.println("\n******************************************\n\n");
                    break;
                }
                System.out.println("\nHit enter to roll the dice");
                sc.nextLine();
                player.setRoll(game.getDice().rollDice());
                System.out.println("Dice gave " + player.getRoll());
                if ((player.getPosition() + player.getRoll()) > 13) {
                    System.out.println("Player cannot move");
                    continue;
                }
                if (player.getRoll() == 2 && !player.isGameStart()) {
                    System.out.println("Game cannot start until you get 1");
                } else {
                    player.setGameStart(true);
                    player.setPosition(player.getPosition() + player.getRoll());
                    System.out.println("Player position Floor-" + player.getPosition());
                    game.findFloor(player);
                }

            }
            noOfPlayers --;
        }
        game.showResults();
    }
}
