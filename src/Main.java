public class Main {

    public static void main(String[] args) {
        BayesianNetwork BN = new BayesianNetwork();
        String filename = "src/alarm_net.xml";
        String txtfile = "src/input.txt";
        inputQueries input = ReadFiles.readTXT(txtfile);
        System.out.println(input.filename);
        for(int i =0; i < input.BayesBallQueries.size();i++)
        {
            System.out.println(input.BayesBallQueries.get(i));
        }
        for(int i =0; i < input.probabilityQueries.size();i++)
        {
            System.out.println(input.probabilityQueries.get(i));
        }
//        BN.buildNetwork(filename);
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
