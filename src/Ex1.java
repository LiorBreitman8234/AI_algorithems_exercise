import java.util.ArrayList;

public class Ex1 {

    public static void main(String[] args) {


        BayesianNetwork BN = new BayesianNetwork();
        String txtfile = "src/input.txt";
        queriesHandler handler = fileHandler.readTXT(txtfile);
        System.out.println(handler.filename);
        for(int i = 0; i < handler.queries.size(); i++)
        {
            System.out.println(handler.queries.get(i));
        }
        BN.buildNetwork(handler.filename);
        ArrayList<String> answers = handler.handle(BN);
        fileHandler.writeToTxt(answers);
        for (String answer : answers) {
            System.out.println(answer);
        }
    }
}
