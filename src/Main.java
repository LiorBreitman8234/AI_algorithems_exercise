import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {


        BayesianNetwork BN = new BayesianNetwork();
        String txtfile = "src/input.txt";
        queriesHandler handler = ReadFiles.readTXT(txtfile);
        System.out.println(handler.filename);
        for(int i = 0; i < handler.queries.size(); i++)
        {
            System.out.println(handler.queries.get(i));
        }
        BN.buildNetwork(handler.filename);
//        BN.nodesInNetwork.get(3).PrintCPT();
        for(int i =0; i < BN.nodesInNetwork.size();i++)
        {
            BN.nodesInNetwork.get(i).PrintCPT();
        }
        ArrayList<String> given = new ArrayList<String>();
        given.add("B=T");
        given.add("E=T");
        Factor f = new Factor(BN.nodesInNetwork.get(2),given,1);
        System.out.println(f.FactorOf);
//        System.out.println(handler.handle(BN));
    }
}
