import java.util.ArrayList;

public class Node implements Comparable<Node> {

    boolean eshteVizituar, eshteFillimi, eshteFundi, eshtePengese, neRruge, listaHapur;
    double G = 10e5, H = 10e5, F = G + H;
    int rreshti, kolona;

    Node Prindi;
    ArrayList<Node> skajet = new ArrayList<Node>();

    public Node(int r1, int c1) {
        rreshti = r1;
        kolona = c1;
    }

    public double ktheDistancen(Node node) {
        return Math.sqrt((node.rreshti - this.rreshti)*(node.rreshti - this.rreshti) + (node.kolona - this.kolona)*(node.kolona - this.kolona));
    }

    public boolean eshteDiagonale(Node node) {
        if(node.rreshti == this.rreshti - 1 && node.kolona == this.kolona -1)
            return true;
        if(node.rreshti == this.rreshti - 1 && node.kolona == this.kolona +1)
            return true;
        if(node.rreshti == this.rreshti + 1 && node.kolona == this.kolona -1)
            return true;
        if(node.rreshti == this.rreshti + 1 && node.kolona == this.kolona +1)
            return true;
        return false;
    }

    public void merrSkajet(Node[][] nodes) {
        if(rreshti - 1 >= 0 && kolona - 1 >= 0)
            skajet.add(nodes[rreshti-1][kolona-1]);
        if(rreshti - 1 >= 0)
            skajet.add(nodes[rreshti-1][kolona]);
        if(rreshti - 1 >= 0 && kolona + 1 < nodes[0].length)
            skajet.add(nodes[rreshti-1][kolona+1]);
        if(kolona - 1 >= 0)
            skajet.add(nodes[rreshti][kolona-1]);
        if(kolona + 1 < nodes[0].length)
            skajet.add(nodes[rreshti][kolona+1]);
        if(rreshti + 1 < nodes.length && kolona - 1 >= 0)
            skajet.add(nodes[rreshti+1][kolona-1]);
        if(rreshti + 1 < nodes.length)
            skajet.add(nodes[rreshti+1][kolona]);
        if(rreshti + 1 < nodes.length && kolona + 1 < nodes[0].length)
            skajet.add(nodes[rreshti+1][kolona+1]);
    }

    public boolean eshteAferMe(Node node){
        if(rreshti == node.rreshti-1 && kolona == node.kolona-1 ||
                rreshti == node.rreshti-1 && kolona == node.kolona   ||
                rreshti == node.rreshti-1 && kolona == node.kolona+1 ||
                rreshti == node.rreshti   && kolona == node.kolona-1 ||
                rreshti == node.rreshti   && kolona == node.kolona+1 ||
                rreshti == node.rreshti+1 && kolona == node.kolona-1 ||
                rreshti == node.rreshti+1 && kolona == node.kolona   ||
                rreshti == node.rreshti+1 && kolona == node.kolona+1)
            return true;
        return false;
    }

    @Override
    public int compareTo(Node node) {
        if (node.F > this.F)
            return -1;
        if (node.F < this.F)
            return 1;
        return 0;
    }

}
