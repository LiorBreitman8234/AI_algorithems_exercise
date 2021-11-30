import java.util.Arrays;

/**
 * this class will help me move the information from the xml file to the function that will build my network
 */
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

    public String[] getOutcomes() {
        return outcomes;
    }

}
