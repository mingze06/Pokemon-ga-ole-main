public class WaterPokemon extends Pokemon {
    
    public WaterPokemon(String name, int level) {
        super(name, "Water", level, 90 + (level * 12), 60 + (level * 4), 60 + (level * 4), 50 + (level * 3));
    }
    
    @Override
    protected void initializeMoves() {
        moves.add(new WaterMove("Bubble", 20, 95, 20));
        moves.add(new WaterMove("Water Gun", 40, 90, 15));
        moves.add(new WaterMove("Aqua Jet", 60, 85, 10));
        moves.add(new WaterMove("Hydro Pump", 110, 70, 5));
    }
    
    @Override
    public void gainExperience(int exp) {
        super.gainExperience(exp);
        // Water Pokemon gain extra HP when leveling up
        if (exp > 0) {
            maxHP += 5;
            currentHP = maxHP;
        }
    }
} 