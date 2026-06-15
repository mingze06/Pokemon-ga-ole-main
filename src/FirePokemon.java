public class FirePokemon extends Pokemon {
    
    public FirePokemon(String name, int level) {
        super(name, "Fire", level, 80 + (level * 10), 70 + (level * 5), 50 + (level * 3), 60 + (level * 4));
    }
    
    @Override
    protected void initializeMoves() {
        moves.add(new FireMove("Ember", 40, 90, 15));
        moves.add(new FireMove("Fire Spin", 35, 85, 10));
        moves.add(new FireMove("Flame Burst", 70, 80, 8));
        moves.add(new FireMove("Inferno", 100, 50, 5));
    }
    
    @Override
    public void gainExperience(int exp) {
        super.gainExperience(exp);
        // Fire Pokemon gain extra attack when leveling up
        if (exp > 0) {
            attack += 2;
        }
    }
} 