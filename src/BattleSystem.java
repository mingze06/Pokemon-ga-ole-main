import java.util.*;

public class BattleSystem {
    private Scanner scanner;
    
    public BattleSystem() {
        scanner = new Scanner(System.in);
    }
    
    public void startBattle(Pokemon playerPokemon, Pokemon wildPokemon, Player player) {
        System.out.println("\n=== BATTLE START ===");
        System.out.println("Your " + playerPokemon.getName() + " vs Wild " + wildPokemon.getName());
        
        // Reset Pokemon HP for battle
        playerPokemon.heal(playerPokemon.getMaxHP());
        wildPokemon.heal(wildPokemon.getMaxHP());
        
        Move lastPlayerMove = null;
        Move lastWildMove = null;
        boolean playerRushCombo = false;
        boolean wildRushCombo = false;
        
        while (!playerPokemon.isFainted() && !wildPokemon.isFainted()) {
            displayBattleStatus(playerPokemon, wildPokemon);
            
            // Player's turn
            Move playerMove = selectPlayerMove(playerPokemon);
            if (playerMove != null) {
                playerRushCombo = (lastPlayerMove != null && playerMove.getName().equals(lastPlayerMove.getName()));
                if (playerRushCombo) {
                    System.out.println("RUSH COMBO! " + playerPokemon.getName() + " used " + playerMove.getName() + " consecutively for extra damage!");
                    playerPokemon.useMoveWithMultiplier(playerMove, wildPokemon, 1.5);
                } else {
                    playerPokemon.useMove(playerMove, wildPokemon);
                }
                lastPlayerMove = playerMove;
            }
            
            if (wildPokemon.isFainted()) {
                System.out.println("Wild " + wildPokemon.getName() + " fainted!");
                break;
            }
            
            // Wild Pokemon's turn
            Move wildMove = wildPokemon.selectMove();
            if (wildMove != null) {
                wildRushCombo = (lastWildMove != null && wildMove.getName().equals(lastWildMove.getName()));
                if (wildRushCombo) {
                    System.out.println("RUSH COMBO! Wild " + wildPokemon.getName() + " used " + wildMove.getName() + " consecutively for extra damage!");
                    wildPokemon.useMoveWithMultiplier(wildMove, playerPokemon, 1.5);
                } else {
                    wildPokemon.useMove(wildMove, playerPokemon);
                }
                lastWildMove = wildMove;
            }
            
            if (playerPokemon.isFainted()) {
                System.out.println("Your " + playerPokemon.getName() + " fainted!");
                break;
            }
            
            System.out.println(); // Add spacing between turns
        }
        
        // Battle result
        if (playerPokemon.isFainted()) {
            System.out.println("You lost the battle!");
            player.loseBattle();
        } else {
            System.out.println("You won the battle!");
            player.winBattle();
            
            // Gain experience
            int expGained = wildPokemon.getLevel() * 10;
            playerPokemon.gainExperience(expGained);
            System.out.println(playerPokemon.getName() + " gained " + expGained + " experience!");
        }
    }
    
    private void displayBattleStatus(Pokemon playerPokemon, Pokemon wildPokemon) {
        System.out.println("\n--- Battle Status ---");
        System.out.println("Your " + playerPokemon.getName() + ": HP " + playerPokemon.getCurrentHP() + "/" + playerPokemon.getMaxHP() + " " + getHealthBar(playerPokemon) );
        System.out.println("Wild " + wildPokemon.getName() + ": HP " + wildPokemon.getCurrentHP() + "/" + wildPokemon.getMaxHP() + " " + getHealthBar(wildPokemon) );
    }

    // Add a health bar display for Pokémon
    private String getHealthBar(Pokemon pokemon) {
        int barLength = 10;
        double hpPercent = (double) pokemon.getCurrentHP() / pokemon.getMaxHP();
        int filled = (int) Math.round(hpPercent * barLength);
        StringBuilder bar = new StringBuilder("<");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("█");
            } else {
                bar.append("▒");
            }
        }
        bar.append(">");
        return bar.toString();
    }
    
    private Move selectPlayerMove(Pokemon playerPokemon) {
        System.out.println("\nYour moves:");
        List<Move> moves = playerPokemon.getMoves();
        
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            String ppStatus = move.hasPP() ? "(" + move.getCurrentPP() + "/" + move.getMaxPP() + ")" : "(No PP)";
            System.out.println((i + 1) + ". " + move.getName() + " " + ppStatus);
        }
        
        System.out.print("Select a move (1-" + moves.size() + "): ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number!");
            scanner.next();
        }
        
        int choice = scanner.nextInt();
        
        if (choice > 0 && choice <= moves.size()) {
            Move selectedMove = moves.get(choice - 1);
            if (selectedMove.hasPP()) {
                return selectedMove;
            } else {
                System.out.println("This move has no PP left!");
                return selectPlayerMove(playerPokemon);
            }
        } else {
            System.out.println("Invalid choice!");
            return selectPlayerMove(playerPokemon);
        }
    }
    
    public void autoBattle(Pokemon playerPokemon, Pokemon wildPokemon, Player player) {
        System.out.println("\n=== AUTO BATTLE ===");
        System.out.println("Your " + playerPokemon.getName() + " vs Wild " + wildPokemon.getName());
        
        // Reset Pokemon HP for battle
        playerPokemon.heal(playerPokemon.getMaxHP());
        wildPokemon.heal(wildPokemon.getMaxHP());
        
        while (!playerPokemon.isFainted() && !wildPokemon.isFainted()) {
            // Player's turn (auto-select strongest move)
            Move playerMove = getStrongestMove(playerPokemon);
            if (playerMove != null) {
                playerPokemon.useMove(playerMove, wildPokemon);
            }
            
            if (wildPokemon.isFainted()) {
                System.out.println("Wild " + wildPokemon.getName() + " fainted!");
                break;
            }
            
            // Wild Pokemon's turn
            Move wildMove = wildPokemon.selectMove();
            if (wildMove != null) {
                wildPokemon.useMove(wildMove, playerPokemon);
            }
            
            if (playerPokemon.isFainted()) {
                System.out.println("Your " + playerPokemon.getName() + " fainted!");
                break;
            }
        }
        
        // Battle result
        if (playerPokemon.isFainted()) {
            System.out.println("You lost the battle!");
            player.loseBattle();
        } else {
            System.out.println("You won the battle!");
            player.winBattle();
            
            // Gain experience
            int expGained = wildPokemon.getLevel() * 10;
            playerPokemon.gainExperience(expGained);
            System.out.println(playerPokemon.getName() + " gained " + expGained + " experience!");
        }
    }
    
    private Move getStrongestMove(Pokemon pokemon) {
        List<Move> moves = pokemon.getMoves();
        Move strongest = null;
        
        for (Move move : moves) {
            if (move.hasPP() && (strongest == null || move.getPower() > strongest.getPower())) {
                strongest = move;
            }
        }
        
        return strongest;
    }
} 