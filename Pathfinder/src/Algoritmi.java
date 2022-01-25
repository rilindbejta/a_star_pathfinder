
import java.util.PriorityQueue;
import javax.swing.Timer;


public class Algoritmi  {

    PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
    Timer koheMatesi;


    public void A_Star(Node fillimi, Node fundi){
        fillimi.G = 0;
        fillimi.H = fillimi.ktheDistancen(fundi);
        fillimi.F = fillimi.G + fillimi.H;
        priorityQueue.add(fillimi);

        koheMatesi = new Timer(10, e -> {

            if(!priorityQueue.isEmpty()){
                Node nyjaAktuale = priorityQueue.poll();

                if(nyjaAktuale.equals(fundi)){
                    Visualizimi.isUserActionEnabled = false;
                    kthimi(nyjaAktuale);
                    koheMatesi.stop();
                }

                if(!nyjaAktuale.eshteVizituar){
                    nyjaAktuale.eshteVizituar = true;
                    for(int i = 0; i< nyjaAktuale.skajet.size(); i++){
                        Node nyjaSkajore = nyjaAktuale.skajet.get(i);
                            if(!nyjaSkajore.eshteVizituar && !nyjaSkajore.eshtePengese){
                                double cmimiRi;
                                    if(nyjaAktuale.eshteDiagonale(nyjaSkajore)){
                                        cmimiRi = nyjaAktuale.G + 1.4;
                                    }else{
                                        cmimiRi = nyjaAktuale.G + 1;
                                    }
                                    nyjaSkajore.H = nyjaSkajore.ktheDistancen(fundi);
                                    double totaliRi = cmimiRi + nyjaSkajore.H;
                                        if(totaliRi < nyjaSkajore.F){
                                            nyjaSkajore.G = cmimiRi;
                                            nyjaSkajore.F = nyjaSkajore.G + nyjaSkajore.H;
                                            nyjaSkajore.Prindi = nyjaAktuale;
                                            priorityQueue.add(nyjaSkajore);
                                            nyjaSkajore.listaHapur = true;
                                        }
                            }
                    }
                    Visualizimi.getinstanca().repaint();
                }
            }
        });
        koheMatesi.start();
    }


    private void kthimi(Node fundi){
        Node nyjaAktuale = fundi.Prindi;
        while(!nyjaAktuale.eshteFillimi){
            nyjaAktuale.neRruge = true;
            nyjaAktuale = nyjaAktuale.Prindi;
        }
    }

}
