import java.util.*;

public class CatchSystem {
    private Scanner scanner;
    private Random random;
    
    public CatchSystem() {
        scanner = new Scanner(System.in);
        random = new Random();
    }
    
    public void attemptCatch(Pokemon wildPokemon, Player player) {
        System.out.println("Level: " + wildPokemon.getLevel());
        System.out.println("Type: " + wildPokemon.getType());
        
        while (!wildPokemon.isFainted()) {
            displayCatchOptions(player);
            int choice = getPlayerChoice();
            
            switch (choice) {
                case 1:
                    throwPokeball(wildPokemon, player);
                    break;
                case 2:
                    throwGreatBall(wildPokemon, player);
                    break;
                case 3:
                    throwUltraBall(wildPokemon, player);
                    break;
                case 4:
                    battleToWeaken(wildPokemon, player);
                    break;
                case 5:
                    System.out.println("You ran away!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
            
            if (player.getCollection().contains(wildPokemon)) {
                return; // Pokemon was caught
            }
        }
        
        System.out.println("The wild " + wildPokemon.getName() + " fainted!");
    }
    
    private void displayCatchOptions(Player player) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Throw Poké Ball (" + player.getPokeballs() + " available)");
        System.out.println("2. Throw Great Ball (" + player.getGreatBalls() + " available)");
        System.out.println("3. Throw Ultra Ball (" + player.getUltraBalls() + " available)");
        System.out.println("4. Battle to weaken");
        System.out.println("5. Run away");
        System.out.print("Choose an option: ");
    }
    
    private int getPlayerChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number!");
            scanner.next();
        }
        return scanner.nextInt();
    }
    
    private void throwPokeball(Pokemon wildPokemon, Player player) {
        if (player.getPokeballs() <= 0) {
            System.out.println("You don't have any Poké Balls!");
            return;
        }
        
        player.usePokeball();
        System.out.println("You threw a Poké Ball!");
        
        // 100% catch rate for first Pokemon
        double catchRate = player.hasCaughtFirstPokemon() ? calculateCatchRate(wildPokemon, 1.0) : 1.0;
        if (random.nextDouble() < catchRate) {
            player.addPokemon(wildPokemon);
        } else {
            System.out.println("The Poké Ball broke free!");
        }
    }
    
    private void throwGreatBall(Pokemon wildPokemon, Player player) {
        if (player.getGreatBalls() <= 0) {
            System.out.println("You don't have any Great Balls!");
            return;
        }
        
        player.useGreatBall();
        System.out.println("You threw a Great Ball!");
        
        double catchRate = calculateCatchRate(wildPokemon, 1.5);
        if (random.nextDouble() < catchRate) {
            player.addPokemon(wildPokemon);
        } else {
            System.out.println("The Great Ball broke free!");
        }
    }
    
    private void throwUltraBall(Pokemon wildPokemon, Player player) {
        if (player.getUltraBalls() <= 0) {
            System.out.println("You don't have any Ultra Balls!");
            return;
        }
        
        player.useUltraBall();
        System.out.println("You threw an Ultra Ball!");
        
        double catchRate = calculateCatchRate(wildPokemon, 2.0);
        if (random.nextDouble() < catchRate) {
            player.addPokemon(wildPokemon);
        } else {
            System.out.println("The Ultra Ball broke free!");
        }
    }
    
    private void battleToWeaken(Pokemon wildPokemon, Player player) {
        if (player.getCollection().isEmpty()) {
            System.out.println("You need to have at least one Pokémon to battle!");
            return;
        }
        
        System.out.println("Select a Pokémon to battle with:");
        List<Pokemon> collection = player.getCollection();
        
        for (int i = 0; i < collection.size(); i++) {
            Pokemon pokemon = collection.get(i);
            System.out.println((i + 1) + ". " + pokemon.getName() + " (HP: " + pokemon.getCurrentHP() + "/" + pokemon.getMaxHP() + ")");
        }
        
        System.out.print("Enter your choice: ");
        int choice = getPlayerChoice();
        
        if (choice > 0 && choice <= collection.size()) {
            Pokemon playerPokemon = collection.get(choice - 1);
            
            // Quick battle to weaken the wild Pokemon
            System.out.println("Your " + playerPokemon.getName() + " attacks " + wildPokemon.getName() + "!");
            
            // Calculate damage based on player Pokemon's attack
            int damage = (playerPokemon.getAttack() * 2) / wildPokemon.getDefense();
            wildPokemon.takeDamage(damage);
            
            System.out.println(wildPokemon.getName() + " took " + damage + " damage!");
            System.out.println(wildPokemon.getName() + " HP: " + wildPokemon.getCurrentHP() + "/" + wildPokemon.getMaxHP());
            
            if (wildPokemon.isFainted()) {
                System.out.println("The wild " + wildPokemon.getName() + " fainted!");
            }
        } else {
            System.out.println("Invalid choice!");
        }
    }
    
    private double calculateCatchRate(Pokemon wildPokemon, double ballMultiplier) {
        // Base catch rate based on Pokemon level and HP
        double baseRate = 0.3;
        
        // Lower catch rate for higher level Pokemon
        double levelFactor = Math.max(0.1, 1.0 - (wildPokemon.getLevel() * 0.02));
        
        // Lower catch rate for Pokemon with more HP
        double hpFactor = (double) wildPokemon.getCurrentHP() / wildPokemon.getMaxHP();
        
        // Apply ball multiplier
        double finalRate = baseRate * levelFactor * (1.0 - hpFactor) * ballMultiplier;
        
        return Math.min(0.95, Math.max(0.05, finalRate));
    }
    
    public void attemptCatchWithRarePokemon(Pokemon wildPokemon, Player player) {
        System.out.println("A rare " + wildPokemon.getName() + " appeared!");
        System.out.println("This is a rare encounter! Catch rate is reduced.");
        
        // Rare Pokemon have lower catch rates
        wildPokemon.takeDamage(wildPokemon.getMaxHP() / 4); // Start with some damage
        
        attemptCatch(wildPokemon, player);
    }
} 