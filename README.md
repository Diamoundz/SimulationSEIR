# Introduction to SEIR Simulation Model

Made by - Elijah Orhan-Cartelli and Jerome Engelbrecht -

If you wish to run the project just run (make a backup of the csv files as they will be erased) :
compile_and_run.bat (Windows)
compile_and_run.sh (Linux)

Used PRNG : Mersene-Twister (Seed : new int[]{0x123, 0x234, 0x345, 0x456})

We also recommend enabling the GUI in the Main class for a nicer vizualisation.

The SEIR model is a widely-used epidemiological model designed to simulate the spread of infectious diseases within a population. It is based on compartmentalization, dividing the population into four distinct compartments:

- **Susceptible (S)**: Individuals who are susceptible to the disease and can become infected if they come into contact with infectious individuals.

- **Exposed (E)**: Individuals who have been exposed to the disease but are not yet infectious. This represents the latent period during which the individual is infected but not yet showing symptoms or infectious.

- **Infectious (I)**: Individuals who are infected and can spread the disease to susceptible individuals.

- **Recovered (R)**: Individuals who have recovered from the disease and are assumed to have acquired immunity, at least temporarily. They are not susceptible to reinfection.

The SEIR model tracks the flow of individuals between these compartments over time through a system of ordinary differential equations (ODEs). It considers parameters such as the transmission rate, the incubation period, and the recovery rate to model the dynamics of disease spread.

SEIR models are widely used in epidemiology and public health research to study the dynamics of infectious diseases such as influenza, measles, and COVID-19. By adjusting parameters and initial conditions, SEIR simulations can provide insights into the potential trajectory of an epidemic and inform public health interventions.



## Organization of Files and Packages <a id="organization"></a>

Our project directory is structured as follows:

````{verbatim}
.
├── build/
│   └── com/
│       ├── main/
│       │   ├── CSVGenerator.class
│       │   ├── Grid.class
│       │   ├── Keyboard.class
│       │   ├── Main.class
│       │   ├── MersenneTwister.class
│       │   ├── Subject.class
│       │   ├── Utils.class
│       │   └── Vector2.class
│       └── visual/
│           ├── Interface.class
│           └── ProgressBarWindow.class
├── data/
├── jupyter notebook/
├── py/
│   └── Graphics.py
├── README.md
└── src/
    └── com/
        ├── main/
        │   ├── CSVGenerator.java
        │   ├── Grid.java
        │   ├── Keyboard.java
        │   ├── Main.java
        │   ├── MersenneTwister.java
        │   ├── Subject.java
        │   ├── Utils.java
        │   └── Vector2.java
        └── visual/
            └── Interface.java
````

In this structure:

- The `build/` directory contains compiled Java `.class` files organized under `com.main` and `com.visual` packages.
- The `src/` directory contains the source Java files organized similarly under `com.main` and `com.visual`.
- Other directories such as `data/`, `jupyter notebook/`, and `py/` appear to contain various data, Jupyter Notebook files, and Python scripts, respectively.
- There is a `README.md` file providing information about the project.

This organization separates source files (`src/`) from compiled files (`build/`), and classes are structured within appropriate packages to maintain modularity and facilitate ease of development and maintenance.