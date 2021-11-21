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
//        System.out.println(handler.handle(BN));
    }
}
