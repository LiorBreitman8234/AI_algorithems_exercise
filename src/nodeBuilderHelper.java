import java.util.Arrays;

public class nodeBuilderHelper {

    String name;
    String[] given;
    String[] outcomes;
    String values;

    public nodeBuilderHelper(String event, String[] given, String[] outcomes, String values)
    {
        this.name = event;
        this.given = Arrays.copyOf(given, given.length);
        this.outcomes = Arrays.copyOf(outcomes, outcomes.length);
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getGiven() {
        return given;
    }

    public void setGiven(String[] given) {
        this.given =  Arrays.copyOf(given, given.length);
    }

    public String[] getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(String[] outcomes) {
        this.outcomes = Arrays.copyOf(outcomes, outcomes.length);
    }
}
