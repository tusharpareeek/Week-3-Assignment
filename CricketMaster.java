/**
* Week 3 Assignment Task: Program of a console app to count the runs of a cricket match
* 
* @author Tushar Pareek
* @since  17/09/2024
*/
import java.util.Scanner;
import java.lang.Thread;
public class CricketMaster {
    private static final int MAX_TEAMS = 12; 
    private static final int MAX_PLAYERS = 90; 
    private static String[][] teams = new String[MAX_TEAMS][MAX_PLAYERS + 1]; 
    private static int teamCount = 0;
    
        
        private static void printWelcome() throws InterruptedException{
            System.out.println("\n\n\t\t\t\t\t\t\t" + CricketMasterConstant.welcome);
            Thread.sleep(5000,20);
            System.out.print("\033[H\033[2J");  
            System.out.println("\n\n" + CricketMasterConstant.decoration);
            System.out.println("\n\n\n\t\t\t\t\t\t\t" + CricketMasterConstant.welcome2);
            System.out.println("\n\t\t\t\t\t\t\tLOADING...");
            System.out.println("\n\n" + CricketMasterConstant.decoration);
            Thread.sleep(3000,200);
            System.out.print("\033[H\033[2J");    
        }

    public static void main(String[] args) throws InterruptedException {
        printWelcome();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n");
            System.out.println("Cricket Score Counter");
            System.out.println("1. Add Team");
            System.out.println("2. Display Teams");
            System.out.println("3. Start Match");
            System.out.println("4. Rules of game");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addTeam(scanner);
                    break;
                case 2:
                    displayTeams();
                    break;
                case 3:
                    startMatch(scanner);
                    break;
                case 4:
                    rules();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
               
                default:
                    System.out.println(CricketMasterConstant.invalid);
            }
        } while (choice != 5);
    }

    private static void addTeam(Scanner scanner) {
        if (teamCount >= MAX_TEAMS) {
            System.out.println(CricketMasterConstant.maxTeamREached);
            return;
        }

        System.out.print("Enter team name: ");
        String teamName = scanner.nextLine();
        teams[teamCount][0] = teamName;

        for (int i = 1; i <= MAX_PLAYERS; i++) {
            System.out.print("Enter player " + i + " name (or type 'done' to finish): ");
            String playerName = scanner.nextLine();
            if (playerName.equalsIgnoreCase("done")) {
                break;
            }
            teams[teamCount][i] = playerName;
        }

        teamCount = teamCount + 1;
        System.out.println("Team added successfully.");
    }

    private static void displayTeams() {
        if (teamCount == 0) {
            System.out.println("No teams to display.");
            return;
        }

        System.out.println("Teams:");
        for (int i = 0; i < teamCount; i++) {
            System.out.print((i + 1) + ". " + teams[i][0] + " - Players: ");
            for (int j = 1; j <= MAX_PLAYERS && teams[i][j] != null; j++) {
                System.out.print(teams[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void startMatch(Scanner scanner) throws  NumberFormatException{
        if (teamCount < 2) {
            System.out.println("Not enough teams to start a match.");
            return;
        }

        System.out.println("Select two teams to play the match:");
        for (int i = 0; i < teamCount; i++) {
            System.out.println((i + 1) + ". " + teams[i][0]);
        }

        System.out.print("Enter the number of the first team: ");
        int team1Index = scanner.nextInt() - 1;
        scanner.nextLine();
        System.out.print("Enter the number of the second team: ");
        int team2Index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (team1Index < 0 || team1Index >= teamCount || team2Index < 0 || team2Index >= teamCount || team1Index == team2Index) {
            System.out.println("Invalid team selection.");
            return;
        }

        System.out.print("Enter the number of overs: ");
        int overs = scanner.nextInt();
        scanner.nextLine();

        int[] scores = new int[2];
        int[] wickets = new int[2];
        String[][] battingOrder = new String[2][MAX_PLAYERS];
        for (int i = 0; i < 2; i++) {
            System.arraycopy(teams[i == 0 ? team1Index : team2Index], 1, battingOrder[i], 0, MAX_PLAYERS);
        }

        for (int inning = 0; inning < 2; inning++) {
            System.out.println("Inning " + (inning + 1) + " for team " + teams[inning == 0 ? team1Index : team2Index][0]);

            int balls = 0;
            int totalScore = 0;
            int totalWickets = 0;
            int strikeIndex = 0;
            int nonStrikeIndex = 1;

            while (balls < overs * 6 && totalWickets < 10) {
                System.out.println("Ball " + (balls + 1) + ":");
                System.out.println("Current score: " + totalScore + "/" + totalWickets);
                System.out.print("Enter runs scored: \nEnter 'no ball' or 'wide' or 'out' as required: \n");
                String runs = scanner.nextLine();
                

                
                if (runs.equalsIgnoreCase("wide")) {
                    totalScore++;
                    System.out.println("Wide ball.. One run to the batting team.");
                    continue;
                    
                }
                 // Check for wicket
                 if (runs.equalsIgnoreCase("out")) {
                    totalWickets++;
                    balls++;
                    System.out.println("Wicket! Player out.");
                    strikeIndex++;
                    if (totalWickets < 10) {
                        System.out.println("New batsman: " + battingOrder[inning][strikeIndex]);
                    }
                }
                
                if (runs.equalsIgnoreCase("no ball")) {
                    System.out.println("\nno ball.\n");
                    System.out.println("\nOne run to the batting team.");
                    totalScore++;
                    System.out.println("\nEnter runs scored in no ball:");
                    int noBallRuns = scanner.nextInt();
                    scanner.nextLine();
                    totalScore += noBallRuns;
                    continue;
                    
                }
                if(nonInteger(runs)){
                    System.out.println("\n\nInvalid runs entered.");
                    continue;
                }
                
                int num = Integer.parseInt(runs);
                
                
                if (num < 0 || num == 5 || num >  6) {
                    System.out.println("Invalid runs. Try again.");
                    continue;
                }

                totalScore += num;
                balls++;

                if (num % 2 != 0) {
                    int temp = strikeIndex;
                    strikeIndex = nonStrikeIndex;
                    nonStrikeIndex = temp;
                    System.out.println("\nPlayer On-Strike: " + battingOrder[inning][strikeIndex]);
                System.out.println("\nPlayer Non-Strike: " + battingOrder[inning][nonStrikeIndex]);
                }

               
                
            }

            scores[inning] = totalScore;
            wickets[inning] = totalWickets;
        }

        System.out.println("Match Summary:");
        System.out.println("Team " + teams[team1Index][0] + ": " + scores[0] + "/" + wickets[0]);
        System.out.println("Team " + teams[team2Index][0] + ": " + scores[1] + "/" + wickets[1]);

        if (scores[0] > scores[1]) {
            System.out.println("Team " + teams[team1Index][0] + " wins!");
        } else if (scores[0] < scores[1]) {
            System.out.println("Team " + teams[team2Index][0] + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
    private static boolean nonInteger(String input){
        try{
            Integer.parseInt(input);
            return false;
        }
        catch(NumberFormatException e){
            return true;
        }
    }
    private static void rules(){
        System.out.println("\n\n" + CricketMasterConstant.rules);
                        System.out.println();
                        return;
    }
}
