import java.util.*;
import java.io.*;

public class Player {
    private String name;
    private List<Pokemon> collection;
    private int battlesWon;
    private int totalExperience;
    private int coins;
    private int pokeballs;
    private int greatBalls;
    private int ultraBalls;
    private boolean hasCaughtFirstPokemon;
    
    public Player(String name) {
        this.name = name;
        this.collection = new ArrayList<>();
        this.battlesWon = 0;
        this.totalExperience = 0;
        this.coins = 100; // Starting coins
        this.pokeballs = 3; // Starting Poké Balls
        this.greatBalls = 0;
        this.ultraBalls = 0;
        this.hasCaughtFirstPokemon = false;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Pokemon> getCollection() {
        return collection;
    }
    
    public int getBattlesWon() {
        return battlesWon;
    }
    
    public int getTotalExperience() {
        return totalExperience;
    }
    
    public int getCoins() {
        return coins;
    }
    
    public int getPokeballs() {
        return pokeballs;
    }
    
    public int getGreatBalls() {
        return greatBalls;
    }
    
    public int getUltraBalls() {
        return ultraBalls;
    }
    
    public boolean hasCaughtFirstPokemon() {
        return hasCaughtFirstPokemon;
    }
    
    public void addPokemon(Pokemon pokemon) {
        collection.add(pokemon);
        if (!hasCaughtFirstPokemon) {
            hasCaughtFirstPokemon = true;
            System.out.println("You caught your first Pokémon, " + pokemon.getName() + "!");
            System.out.println("Congratulations! You're now a Pokémon Trainer!");
        } else {
            System.out.println("You caught " + pokemon.getName() + "!");
        }
    }
    
    public void winBattle() {
        battlesWon++;
        totalExperience += 50;
        coins += 20;
        System.out.println("Battle won! You gained 50 experience and 20 coins!");
    }
    
    public void loseBattle() {
        totalExperience += 10;
        System.out.println("Battle lost! You gained 10 experience for trying!");
    }
    
    public void spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
        } else {
            System.out.println("Not enough coins!");
        }
    }
    
    public void addCoins(int amount) {
        coins += amount;
    }
    
    public boolean usePokeball() {
        if (pokeballs > 0) {
            pokeballs--;
            return true;
        }
        return false;
    }
    
    public boolean useGreatBall() {
        if (greatBalls > 0) {
            greatBalls--;
            return true;
        }
        return false;
    }
    
    public boolean useUltraBall() {
        if (ultraBalls > 0) {
            ultraBalls--;
            return true;
        }
        return false;
    }
    
    public void addPokeballs(int amount) {
        pokeballs += amount;
    }
    
    public void addGreatBalls(int amount) {
        greatBalls += amount;
    }
    
    public void addUltraBalls(int amount) {
        ultraBalls += amount;
    }
    
    public boolean hasPokemon() {
        return !collection.isEmpty();
    }
    
    public void healAllPokemon() {
        for (Pokemon pokemon : collection) {
            pokemon.heal(pokemon.getMaxHP());
            for (Move move : pokemon.getMoves()) {
                move.restorePP();
            }
        }
        System.out.println("All Pokémon have been healed!");
    }
    
    public Pokemon getStrongestPokemon() {
        if (collection.isEmpty()) {
            return null;
        }
        
        Pokemon strongest = collection.get(0);
        for (Pokemon pokemon : collection) {
            if (pokemon.getAttack() > strongest.getAttack()) {
                strongest = pokemon;
            }
        }
        return strongest;
    }

    // Save the player's Pokémon collection to a file
    public void saveCollection(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Pokemon pokemon : collection) {
                writer.print(pokemon.getClass().getSimpleName() + ",");
                writer.print(pokemon.getName() + ",");
                writer.print(pokemon.getLevel() + ",");
                writer.print(pokemon.getCurrentHP() + ",");
                writer.print(pokemon.getAttack() + ",");
                writer.print(pokemon.getDefense() + ",");
                writer.print(pokemon.getSpeed() + ",");
                List<Move> moves = pokemon.getMoves();
                for (int i = 0; i < moves.size(); i++) {
                    Move move = moves.get(i);
                    writer.print(move.getClass().getSimpleName() + ":" + move.getName() + ":" + move.getPower() + ":" + move.getAccuracy() + ":" + move.getMaxPP());
                    if (i < moves.size() - 1) writer.print("|");
                }
                writer.println();
            }
            System.out.println("Pokémon collection saved to " + filename);
        } catch (Exception e) {
            System.out.println("Error saving collection: " + e.getMessage());
        }
    }

    // Load the player's Pokémon collection from a file
    public void loadCollection(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            collection.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 8) continue;
                String className = parts[0];
                String name = parts[1];
                int level = Integer.parseInt(parts[2]);
                int currentHP = Integer.parseInt(parts[3]);
                int attack = Integer.parseInt(parts[4]);
                int defense = Integer.parseInt(parts[5]);
                int speed = Integer.parseInt(parts[6]);
                String movesStr = parts[7];
                Pokemon pokemon = null;
                if (className.equals("FirePokemon")) {
                    pokemon = new FirePokemon(name, level);
                } else if (className.equals("WaterPokemon")) {
                    pokemon = new WaterPokemon(name, level);
                } else if (className.equals("GrassPokemon")) {
                    pokemon = new GrassPokemon(name, level);
                } else if (className.equals("ElectricPokemon")) {
                    pokemon = new ElectricPokemon(name, level);
                }
                if (pokemon != null) {
                    pokemon.heal(currentHP - pokemon.getCurrentHP());
                    // Optionally set attack, defense, speed if needed
                    // Parse moves if you want to customize further
                    collection.add(pokemon);
                }
            }
            System.out.println("Pokémon collection loaded from " + filename);
        } catch (Exception e) {
            System.out.println("Error loading collection: " + e.getMessage());
        }
    }
} 