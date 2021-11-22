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
        handler.handle(BN);
        for(int i =0; i < BN.nodesInNetwork.size();i++)
        {
            BN.nodesInNetwork.get(i).PrintCPT();
        }
        ArrayList<String> given = new ArrayList<String>();
        given.add("J=T");
        Factor f1 = new Factor(BN.nodesInNetwork.get(3),given,1);
        f1.printFactor();
        given = new ArrayList<String>();
        given.add("M=T");
        Factor f2 = new Factor(BN.nodesInNetwork.get(4),given,2);
        f2.printFactor();

//        VariableElimination VE = new VariableElimination(BN,"P(B=T|J=T,M=T) A-E",);
//        VE.join(f1,f2).printFactor();
       // System.out.println(handler.handle(BN));
    }
}
