package xyz.quazaros.epfragments73.config;

public class ModConfig {
    public double ruined_chance = 0.33;
    public double withered_chance = 1;
    public double golden_chance = 0.1;
    public double gusty_chance = 0.1;
    public double ancient_chance = 0.2;
    public double dark_chance = 0.1;
    public double wealthy_chance = 1;
    public double sandy_chance = 0.25;

    public void validate() {
        ruined_chance = check(ruined_chance);
        withered_chance = check(withered_chance);
        golden_chance = check(golden_chance);
        gusty_chance = check(gusty_chance);
        ancient_chance = check(ancient_chance);
        dark_chance = check(dark_chance);
        wealthy_chance = check(wealthy_chance);
        sandy_chance = check(sandy_chance);
    }

    private double check(double value) {
        if (value > 1) {
            return 1;
        } else if (value < 0) {
            return 0;
        } else {
            return value;
        }
    }
}
