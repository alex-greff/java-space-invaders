# Text-Based Java Game
A simple text-based retro arcade-like game coded entirely from scratch.

## Getting Started
### Playing the Game

* Download and extract [SpaceInvadersAlexG.zip](SpaceInvadersAlexG.zip).
* Run the jar file and enjoy.

### Opening the Project
* This repository can be imported into an any Java supported IDE such as Eclipse or IntelliJ.

## Game Objectives
The goal of the game is to survive and shoot down as many enemies as possible in order to get the best score.

## Game Mechanics
* **Health Pack** - Shooting/colliding with one of these replenishes 1 health-point to yourself.
### Enemies
There are three classes of enemies each will varying amounts of health. Shooting an enemy deals one health point of damage to it. Colliding with an enemy results in a net health loss to your ship of its remaining health before impact. So colliding with an enemy with a remaining 3 health would result in your ship loosing 3 health-points.

The following enemy classes are:

* **Scout** - 1 hp.
* **Cruiser** - 2 hp.
* **Dreadnought** - 5 hp.

## Controls
* **W** - Move up.
* **A** - Move left.
* **S** - Move down.
* **D** - Move right.
* **Space** - Fire bullet.
