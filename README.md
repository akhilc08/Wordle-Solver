# Wordle-Solver
This project implements a Wordle-solving algorithm in Java that combines data structures with probability and information theory to efficiently crack the puzzle. The solver analyzes possible guesses, calculates the information gain from each option, and selects the move that maximizes the chance of solving in fewer attempts.

Highlights

- Developed with ArrayList, HashMap, and Lists for efficient word storage and lookup.

- Uses search optimization and entropy-based probability calculations to narrow down guesses.

- Tested on all 2,315 official Wordle solutions, averaging 3.6 guesses per word.

- Includes simulation files for evaluating performance across the full dataset.

Explanation of files: 

- solver.java – Implements the core Wordle solving logic, including probability calculations and information-theoretic scoring for optimal guesses.

- simulator.java – Runs simulations of the solver against Wordle puzzles, measuring efficiency and guess counts.

- fullsimulate.java – Executes large-scale testing across all 2,315 official Wordle solutions, producing aggregate performance results.
