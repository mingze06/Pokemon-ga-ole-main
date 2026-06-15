import java.util.*;

public class PokemonGaOleGame {
    private Scanner scanner;
    private Player player;
    private PokemonDatabase pokemonDB;
    private BattleSystem battleSystem;
    private CatchSystem catchSystem;
    private boolean gameRunning;
    
    public PokemonGaOleGame() {
        scanner = new Scanner(System.in);
        pokemonDB = new PokemonDatabase();
        battleSystem = new BattleSystem();
        catchSystem = new CatchSystem();
        gameRunning = true;
    }
    
    public void start() {
        System.out.println("=== POKEMON GA-OLE GAME ===");
        System.out.println("Welcome to the Pokémon Ga-Ole Arcade Game!");
        
        initializePlayer();
        
        while (gameRunning) {
            displayMainMenu();
            int choice = getPlayerChoice();
            processMainMenuChoice(choice);
        }
        
        System.out.println("Thanks for playing Pokémon Ga-Ole!");
        scanner.close();
    }
    
    private void initializePlayer() {
        System.out.print("Enter your trainer name: ");
        String name = scanner.nextLine();
        player = new Player(name);
        System.out.println("Welcome, " + name + "! Let's catch some Pokémon!");
    }
    
    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Battle Mode");
        System.out.println("2. Catch Mode");
        System.out.println("3. View Collection");
        System.out.println("4. View Stats");
        System.out.println("5. Shop");
        System.out.println("6. Heal All Pokémon");
        System.out.println("7. Exit Game");
        System.out.println("8. Save Collection");
        System.out.println("9. Load Collection");
        System.out.print("Choose an option: ");
    }
    
    private int getPlayerChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number!");
            scanner.next();
        }
        return scanner.nextInt();
    }
    
    private void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                battleMode();
                break;
            case 2:
                catchMode();
                break;
            case 3:
                viewCollection();
                break;
            case 4:
                viewStats();
                break;
            case 5:
                shop();
                break;
            case 6:
                healAllPokemon();
                break;
            case 7:
                gameRunning = false;
                break;
            case 8:
                player.saveCollection("player_collection.txt");
                break;
            case 9:
                player.loadCollection("player_collection.txt");
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
        scanner.nextLine(); // Consume the newline
    }
    
    private void battleMode() {
        System.out.println("\n=== BATTLE MODE ===");
        if (player.getCollection().isEmpty()) {
            System.out.println("You need to catch some Pokémon first!");
            return;
        }
        
        Pokemon wildPokemon = pokemonDB.getRandomPokemon();
        System.out.println("A wild " + wildPokemon.getName() + " appeared!");
        
        Pokemon playerPokemon = selectPokemonForBattle();
        if (playerPokemon != null) {
            battleSystem.startBattle(playerPokemon, wildPokemon, player);
        }
    }
    
    private void catchMode() {
        System.out.println("\n=== CATCH MODE ===");
        
        // 15% chance for rare Pokemon
        Pokemon wildPokemon;
        if (Math.random() < 0.15) {
            wildPokemon = pokemonDB.getRarePokemon();
            catchSystem.attemptCatchWithRarePokemon(wildPokemon, player);
        } else {
            wildPokemon = pokemonDB.getRandomPokemon();
            System.out.println("A wild " + wildPokemon.getName() + " appeared!");
            catchSystem.attemptCatch(wildPokemon, player);
        }
    }
    
    private Pokemon selectPokemonForBattle() {
        System.out.println("Select a Pokémon for battle:");
        List<Pokemon> collection = player.getCollection();
        
        for (int i = 0; i < collection.size(); i++) {
            Pokemon pokemon = collection.get(i);
            System.out.println((i + 1) + ". " + pokemon.getName() + " (HP: " + pokemon.getCurrentHP() + "/" + pokemon.getMaxHP() + ")");
        }
        
        System.out.print("Enter your choice (0 to cancel): ");
        int choice = getPlayerChoice();
        
        if (choice == 0) {
            return null;
        }
        
        if (choice > 0 && choice <= collection.size()) {
            return collection.get(choice - 1);
        } else {
            System.out.println("Invalid choice!");
            return null;
        }
    }
    
    private void viewCollection() {
        System.out.println("\n=== YOUR COLLECTION ===");
        List<Pokemon> collection = player.getCollection();
        
        if (collection.isEmpty()) {
            System.out.println("You haven't caught any Pokémon yet!");
            return;
        }
        
        for (int i = 0; i < collection.size(); i++) {
            Pokemon pokemon = collection.get(i);
            System.out.println((i + 1) + ". " + pokemon.getName() + " (Level " + pokemon.getLevel() + ")");
            System.out.println("   Type: " + pokemon.getType());
            System.out.println("   HP: " + pokemon.getCurrentHP() + "/" + pokemon.getMaxHP());
            System.out.println("   Attack: " + pokemon.getAttack());
            System.out.println("   Defense: " + pokemon.getDefense());
            System.out.println();
        }
    }
    
    private void viewStats() {
        System.out.println("\n=== PLAYER STATS ===");
        System.out.println("Trainer: " + player.getName());
        System.out.println("Pokémon Caught: " + player.getCollection().size());
        System.out.println("Battles Won: " + player.getBattlesWon());
        System.out.println("Total Experience: " + player.getTotalExperience());
        System.out.println("Coins: " + player.getCoins());
        System.out.println("Poké Balls: " + player.getPokeballs());
        System.out.println("Great Balls: " + player.getGreatBalls());
        System.out.println("Ultra Balls: " + player.getUltraBalls());
    }
    
    private void shop() {
        System.out.println("\n=== POKEMON SHOP ===");
        System.out.println("Your coins: " + player.getCoins());
        System.out.println("1. Buy Poké Ball (50 coins)");
        System.out.println("2. Buy Great Ball (100 coins)");
        System.out.println("3. Buy Ultra Ball (200 coins)");
        System.out.println("4. Buy Experience Booster (150 coins)");
        System.out.println("5. Back to Main Menu");
        System.out.print("Choose an option: ");
        
        int choice = getPlayerChoice();
        
        switch (choice) {
            case 1:
                if (player.getCoins() >= 50) {
                    player.spendCoins(50);
                    player.addPokeballs(1);
                    System.out.println("You bought a Poké Ball!");
                } else {
                    System.out.println("Not enough coins!");
                }
                break;
            case 2:
                if (player.getCoins() >= 100) {
                    player.spendCoins(100);
                    player.addGreatBalls(1);
                    System.out.println("You bought a Great Ball!");
                } else {
                    System.out.println("Not enough coins!");
                }
                break;
            case 3:
                if (player.getCoins() >= 200) {
                    player.spendCoins(200);
                    player.addUltraBalls(1);
                    System.out.println("You bought an Ultra Ball!");
                } else {
                    System.out.println("Not enough coins!");
                }
                break;
            case 4:
                if (player.getCoins() >= 150) {
                    player.spendCoins(150);
                    System.out.println("You bought an Experience Booster!");
                    // Give bonus experience to all Pokemon
                    for (Pokemon pokemon : player.getCollection()) {
                        pokemon.gainExperience(100);
                    }
                } else {
                    System.out.println("Not enough coins!");
                }
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private void healAllPokemon() {
        if (player.getCollection().isEmpty()) {
            System.out.println("You don't have any Pokémon to heal!");
            return;
        }
        
        player.healAllPokemon();
        System.out.println("All your Pokémon have been healed to full health!");
    }
} 