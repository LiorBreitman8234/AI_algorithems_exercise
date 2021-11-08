import java.util.Arrays;

public class MyPair {

    String name;
    String[] given;

    public MyPair(String event, String[] given)
    {
        this.name = event;
        this.given = Arrays.copyOf(given, given.length);
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
        this.given =  Arrays.copyOf(given, given.length);;
    }
}
