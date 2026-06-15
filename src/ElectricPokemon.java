public class ElectricPokemon extends Pokemon {
    
    public ElectricPokemon(String name, int level) {
        super(name, "Electric", level, 75 + (level * 9), 75 + (level * 5), 50 + (level * 3), 80 + (level * 5));
    }
    
    @Override
    protected void initializeMoves() {
        moves.add(new ElectricMove("Thunder Shock", 40, 90, 15));
        moves.add(new ElectricMove("Thunderbolt", 90, 85, 10));
        moves.add(new ElectricMove("Thunder", 110, 70, 5));
        moves.add(new ElectricMove("Volt Tackle", 120, 60, 5));
    }
    
    @Override
    public void gainExperience(int exp) {
        super.gainExperience(exp);
        // Electric Pokemon gain extra speed when leveling up
        if (exp > 0) {
            speed += 4;
        }
    }
} 