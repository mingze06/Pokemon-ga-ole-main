public class GrassPokemon extends Pokemon {
    
    public GrassPokemon(String name, int level) {
        super(name, "Grass", level, 85 + (level * 11), 65 + (level * 4), 55 + (level * 3), 65 + (level * 4));
    }
    
    @Override
    protected void initializeMoves() {
        moves.add(new GrassMove("Vine Whip", 35, 90, 15));
        moves.add(new GrassMove("Razor Leaf", 55, 85, 10));
        moves.add(new GrassMove("Solar Beam", 120, 60, 5));
        moves.add(new GrassMove("Giga Drain", 75, 80, 8));
    }
    
    @Override
    public void gainExperience(int exp) {
        super.gainExperience(exp);
        // Grass Pokemon gain extra defense when leveling up
        if (exp > 0) {
            defense += 3;
        }
    }
} 