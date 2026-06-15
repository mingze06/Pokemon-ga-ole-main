import java.util.*;

public class PokemonDatabase {
    private List<Pokemon> availablePokemon;
    private Random random;
    
    public PokemonDatabase() {
        random = new Random();
        availablePokemon = new ArrayList<>();
        initializePokemon();
    }
    
    private void initializePokemon() {
        // Fire Pokemon
        availablePokemon.add(new FirePokemon("Charmander", 5));
        availablePokemon.add(new FirePokemon("Vulpix", 6));
        availablePokemon.add(new FirePokemon("Growlithe", 7));
        availablePokemon.add(new FirePokemon("Ponyta", 8));
        availablePokemon.add(new FirePokemon("Magmar", 10));
        
        // Water Pokemon
        availablePokemon.add(new WaterPokemon("Squirtle", 5));
        availablePokemon.add(new WaterPokemon("Psyduck", 6));
        availablePokemon.add(new WaterPokemon("Poliwag", 7));
        availablePokemon.add(new WaterPokemon("Staryu", 8));
        availablePokemon.add(new WaterPokemon("Seel", 9));
        
        // Grass Pokemon
        availablePokemon.add(new GrassPokemon("Bulbasaur", 5));
        availablePokemon.add(new GrassPokemon("Oddish", 6));
        availablePokemon.add(new GrassPokemon("Bellsprout", 7));
        availablePokemon.add(new GrassPokemon("Exeggcute", 8));
        availablePokemon.add(new GrassPokemon("Tangela", 9));
        
        // Electric Pokemon
        availablePokemon.add(new ElectricPokemon("Pikachu", 5));
        availablePokemon.add(new ElectricPokemon("Voltorb", 6));
        availablePokemon.add(new ElectricPokemon("Magnemite", 7));
        availablePokemon.add(new ElectricPokemon("Electabuzz", 10));
        availablePokemon.add(new ElectricPokemon("Jolteon", 12));
    }
    
    public Pokemon getRandomPokemon() {
        int index = random.nextInt(availablePokemon.size());
        Pokemon original = availablePokemon.get(index);
        
        // Create a copy with random level variation
        int levelVariation = random.nextInt(5) - 2; // -2 to +2
        int newLevel = Math.max(1, original.getLevel() + levelVariation);
        
        // Create a new instance of the same type
        String pokemonName = original.getName();
        String pokemonType = original.getType();
        
        Pokemon newPokemon;
        switch (pokemonType) {
            case "Fire":
                newPokemon = new FirePokemon(pokemonName, newLevel);
                break;
            case "Water":
                newPokemon = new WaterPokemon(pokemonName, newLevel);
                break;
            case "Grass":
                newPokemon = new GrassPokemon(pokemonName, newLevel);
                break;
            case "Electric":
                newPokemon = new ElectricPokemon(pokemonName, newLevel);
                break;
            default:
                newPokemon = new FirePokemon(pokemonName, newLevel);
        }
        
        return newPokemon;
    }
    
    public Pokemon getRarePokemon() {
        // 10% chance to get a rare Pokemon (higher level)
        if (random.nextDouble() < 0.1) {
            Pokemon original = getRandomPokemon();
            int rareLevel = original.getLevel() + 5;
            String pokemonName = original.getName();
            String pokemonType = original.getType();
            
            Pokemon rarePokemon;
            switch (pokemonType) {
                case "Fire":
                    rarePokemon = new FirePokemon(pokemonName, rareLevel);
                    break;
                case "Water":
                    rarePokemon = new WaterPokemon(pokemonName, rareLevel);
                    break;
                case "Grass":
                    rarePokemon = new GrassPokemon(pokemonName, rareLevel);
                    break;
                case "Electric":
                    rarePokemon = new ElectricPokemon(pokemonName, rareLevel);
                    break;
                default:
                    rarePokemon = new FirePokemon(pokemonName, rareLevel);
            }
            
            System.out.println("A rare " + rarePokemon.getName() + " appeared!");
            return rarePokemon;
        }
        
        return getRandomPokemon();
    }
    
    public List<Pokemon> getAllPokemon() {
        return new ArrayList<>(availablePokemon);
    }
} 