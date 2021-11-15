import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {


        BayesianNetwork BN = new BayesianNetwork();
        String txtfile = "src/input2.txt";
        queriesHandler handler = ReadFiles.readTXT(txtfile);
        System.out.println(handler.filename);
        for(int i = 0; i < handler.queries.size(); i++)
        {
            System.out.println(handler.queries.get(i));
        }
        BN.buildNetwork(handler.filename);
        System.out.println(handler.handle(BN));
//        ArrayList<String> answers = handler.handle(BN);
//        System.out.println(answers.toString());
//        for(int i = 0; i < BN.nodesInNetwork.size();i++)
//        {
//            System.out.println(BN.nodesInNetwork.get(i).toString());
//        }
//        ArrayList<String> given = new ArrayList<String>();
//        given.add("J");
//        BayesBall BB = new BayesBall("B","E",given,BN);
//        boolean flag = BB.bayesBallTraversal(BB.source,null);
        //System.out.println(BB.bayesBallTraversal(BB.source,null));
    }
}
