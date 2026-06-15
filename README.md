# Pokémon Ga-Ole

A Java console-based arcade-style Pokémon game built for an Object-Oriented Programming project. Players can catch Pokémon, battle wild Pokémon, manage a collection, buy capture items, and save/load progress.

## Features

- Interactive command-line gameplay
- Battle Mode with move selection and combo bonuses
- Catch Mode with Poké Balls, Great Balls, and Ultra Balls
- Rare Pokémon encounters
- Pokémon collection and player stats
- In-game shop for buying capture items and experience boosters
- Heal all Pokémon feature
- Save and load collection from `player_collection.txt`

## Project Structure

- `src/Main.java` - Program entry point
- `src/PokemonGaOleGame.java` - Core game loop and menu system
- `src/Player.java` - Player data, collection management, inventory, economy
- `src/BattleSystem.java` - Battle flow and attack logic
- `src/CatchSystem.java` - Catching mechanics and ball probabilities
- `src/PokemonDatabase.java` - Available wild Pokémon and rare encounters
- `src/Pokemon.java` - Base Pokémon class
- `src/Move.java` - Base move class
- `src/FirePokemon.java`, `WaterPokemon.java`, `GrassPokemon.java`, `ElectricPokemon.java` - Elemental Pokémon types
- `src/FireMove.java`, `WaterMove.java`, `GrassMove.java`, `ElectricMove.java` - Elemental move implementations

## Gameplay

1. Run the game.
2. Enter your trainer name.
3. Use the main menu to choose between:
   - Battle Mode
   - Catch Mode
   - View Collection
   - View Stats
   - Shop
   - Heal All Pokémon
   - Save and Load Collection
   - Exit Game

## How to Run

From the repository root, compile and run using Java:

```bash
javac src/*.java
java -cp src Main
```

If your environment uses `openjdk` or a different Java installation, make sure `javac` and `java` are on your PATH.

## Save/Load

The game uses `player_collection.txt` to save and load your Pokémon collection. Save your progress from the in-game menu and load it later to continue.

## Notes

- The game is designed for console input and output.
- Pokémon levels, types, and move sets are defined in the source files.
- The game does not require external libraries.

## Author

Group 26 — Object-Oriented Programming Semester 3
# Pokemon-ga-ole-main
