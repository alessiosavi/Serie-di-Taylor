
package serietaylor;

import java.awt.*;
import javax.swing.JPanel;

public class Grafico extends JPanel{
    
    //altezza e larghezza della canvas
    private int w,h;
    //attributo che indica quale funzione disegnare
    //-1=nessuna; 0=sin; 1=cos; 2=tan; 3=ln; 4=e^x;
    private int funzione;
    //bordo della canvas
    private int bordo;
    //valori massimi visualizzabili sugli assi
    private double xMin,xMax;
    private double yMin,yMax;
    //valore di un singolo pixel sugli assi
    private double unitaX,unitaY;
    //variabile che indica se calclare o no
    private boolean taylor;
    //variabili per il calcolo di taylor
    private int ordine;
    private double x0;
    
    public Grafico(){
        //valori di default dei valori massimi degli assi
        xMax=2.5f;
        xMin=-2.5f;
        yMax=1f;
        yMin=-1f;
        //inizializzo a -1 la funzione, cioè a nessuna
        funzione=-1;
        taylor = false;
        //inizializzo il bordo
        bordo=50;
    }
    
    @Override
    public void paint(Graphics g){
        
        h=this.getHeight();
        w=this.getWidth();
        unitaX=(w-bordo*2)/(xMax-xMin);
        unitaY=(h-bordo*2)/(yMax-yMin);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        drawAssi(g);
        g.setColor(Color.BLACK);
        g.drawLine((int)(bordo-xMin*unitaX),0,(int)(bordo-xMin*unitaX),h);
        g.drawLine(0,(int)(bordo+yMax*unitaY),w,(int)(bordo+yMax*unitaY));
        
        //disegno della funzione
        switch(funzione){
            case 0:
                drawSin(g);
                break;
            case 1:
                drawCos(g);
                break;
            case 2:
                drawTan(g);
                break;
            case 3:
                drawLn(g);
                break;
            case 4:
                drawEesp(g);
        }
        
        //disegno taylor
        if(taylor && funzione >=0 && funzione <=4){
            g.setColor(Color.RED);
            drawTaylor(g);
        }
    }
    
    //metodo per disegnare gli assi in base alla funzione
    private void drawAssi(Graphics g){
        g.drawLine(0,bordo+(int)(yMax*unitaY),w,bordo+(int)(yMax*unitaY));
        g.drawLine(bordo-(int)(xMin*unitaX),0,bordo-(int)(xMin*unitaX),h);
        for(Double i=yMax;i>=yMin;i-=(yMax-yMin)/9){
            g.drawLine(bordo-(int)(xMin*unitaX)-5,bordo+(int)((yMax-yMin-(i-yMin))*unitaY),bordo-(int)(xMin*unitaX)+5,bordo+(int)((yMax-yMin-(i-yMin))*unitaY));
        }
        for(int i=bordo;i<=w-bordo;i+=(w-bordo*2)/9){
            g.drawLine(i,bordo+(int)(yMax*unitaY)-5,i,bordo+(int)(yMax*unitaY)+5);
        }
        g.setFont(new Font("sansserif", Font.BOLD, 12));
        g.setColor(Color.BLUE);
        g.fillRect(w-w/16, h/32, 7, 7);
        g.drawString("f(x)", w-3*w/64, h/32+7);
        g.setColor(Color.RED);
        g.fillRect(w-w/16, h/16, 7, 7);
        g.drawString("Taylor", w-3*w/64, h/16+7);
        g.setColor(Color.GREEN);
        g.fillRect(w-w/16, 3*h/32, 7, 7);
        g.drawString("resto", w-3*w/64, 3*h/32+7);
    }
    
    //metodo per il disegno delle funzioni trigonometriche
    private void drawSin(Graphics g){
        int risultato;
        g.setColor(Color.BLUE);
        for(double i=xMin;i<=xMax;i+=0.002){
            risultato=(int) (bordo+((yMax-yMin)-((Math.sin(i*Math.PI)-yMin)))*unitaY);
            if(risultato>=bordo&&risultato<=h-bordo){
                g.fillOval(bordo+(int)((xMax-xMin-(xMax-i))*unitaX),risultato,2,2);
            }
        }
        g.setColor(Color.BLACK);
    }
    
    private void drawCos(Graphics g){
        int risultato;
        g.setColor(Color.BLUE);
        for(double i=xMin;i<=xMax;i+=0.002){
            risultato=(int)(bordo+((yMax-yMin)-(Math.cos(i*Math.PI)-yMin))*unitaY);
            if(risultato>=bordo&&risultato<=h-bordo){
                g.fillOval(bordo+(int)((xMax-xMin-(xMax-i))*unitaX),risultato,2,2);
            }
        }
        g.setColor(Color.BLACK);
    }
    
    private void drawTan(Graphics g){
        int risultato;
        g.setColor(Color.BLUE);
        for(double i=xMin;i<=xMax;i+=0.002){
            risultato=(int)(bordo+((yMax-yMin)-(Math.tan(i*Math.PI)-yMin))*unitaY);
            if(risultato>=bordo&&risultato<=h-bordo){
                g.fillOval(bordo+(int)((xMax-xMin-(xMax-i))*unitaX),risultato,2,2);
            }
        }
        g.setColor(Color.BLACK);
    }
    
    //metodi per il disegno delle funzioni di e
    private void drawLn(Graphics g){
        int risultato=bordo;
        g.setColor(Color.BLUE);
        for(double i=0.01f;i<=xMax&&risultato>=bordo;i+=0.002){
            risultato=(int)(bordo+((yMax-yMin)-(Math.log(i)-yMin))*unitaY);
            if(risultato<=h-bordo){
                g.fillOval(bordo+(int)((xMax-xMin-(xMax-i))*unitaX),risultato,2,2);
            }
        }
        g.setColor(Color.BLACK);
    }
    
    private void drawEesp(Graphics g){
        int risultato=bordo;
        g.setColor(Color.BLUE);
        for(double i=xMin;i<=xMax&&risultato>=bordo;i+=0.002){
            risultato=(int)(bordo+((yMax-yMin)-(Math.exp(i)-yMin))*unitaY);
            g.fillOval(bordo+(int)((xMax-xMin-(xMax-i))*unitaX),risultato,2,2);
        }
    }
    
    //metodo per disegnare le derivate
    private double calcolaDeriv(int gradoDer, double x){
        //risultato della derivata
        double risDeriv;
        //variabili di appoggio per il calcolo
        double funz1,funz2,combinazione;
        double h=0.01;
        //contesto grafico su cui disegnare
        Graphics g=this.getGraphics();
        
        if(funzione<2){
            gradoDer=gradoDer%4;
        }
        risDeriv=0;
        for(int j=0; j<=gradoDer; j++){
            combinazione=1;
            if(j!=0){
                for(double k=j;k>0;k--){
                    combinazione*=(gradoDer-(j-k))/k;
                }
            }
            funz1=0;
            funz2=0;
            switch(funzione){
                case 0:
                    funz1+=Math.sin(x*Math.PI+(gradoDer-j)*h);
                    funz2+=Math.sin(x*Math.PI-j*h);
                    break;
                case 1:
                    funz1+=Math.cos(x*Math.PI+(gradoDer-j)*h);
                    funz2+=Math.cos(x*Math.PI-j*h);
                    break;
                case 2:
                    funz1+=Math.tan(x*Math.PI+(gradoDer-j)*h);
                    funz2+=Math.tan(x*Math.PI-j*h);
                    break;
                case 3:
                    funz1+=Math.log(x+(gradoDer-j)*h);
                    funz2+=Math.log(x-j*h);
                    break;
                case 4:
                    funz1+=Math.exp(x+(gradoDer-j)*h);
                    funz2+=Math.exp(x-j*h);
            }
            risDeriv+=Math.pow(-1,j)*combinazione*(1f/2)*(funz1+funz2);
        }
        risDeriv*=(1f/Math.pow(h,gradoDer));
        return risDeriv;
    }
    
    private void drawTaylor(Graphics g){
        
        int fattOrdine;
        double somma;
        int risultato;
        for(double x=xMin; x<=xMax; x+=0.002){
            somma = 0;
            risultato = 0;
            for(int i=0; i<=ordine; i++){
                fattOrdine = 1;
                for(int j=i; j>0; j--){
                    fattOrdine*=j;
                }
                if(funzione < 3){
                    somma+=((Math.pow(x*Math.PI-x0*Math.PI, i)/fattOrdine)*calcolaDeriv(i, x0));
                }
                else{
                    somma+=((Math.pow(x-x0, i)/fattOrdine)*calcolaDeriv(i, x0));
                }
            }
            if(calcolaY(somma)<=h-bordo && calcolaY(somma)>=bordo){
                g.setColor(Color.RED);
                g.fillOval(calcolaX(x), calcolaY(somma), 2, 2);
            }
            //disegno il resto della funzione
            g.setColor(Color.GREEN);
            switch(funzione){
                case 0:
                    risultato = calcolaY(Math.sin(x*Math.PI)-somma);
                    if(risultato>=bordo&&risultato<=h-bordo){
                        g.fillOval(calcolaX(x), risultato, 2, 2);
                    }
                    break;
                case 1:
                    risultato = calcolaY(Math.cos(x*Math.PI)-somma);
                    if(risultato>=bordo&&risultato<=h-bordo){
                        g.fillOval(calcolaX(x), risultato, 2, 2);
                    }
                    break;
                case 2:
                    risultato = calcolaY(Math.tan(x*Math.PI)-somma);
                    if(risultato>=bordo&&risultato<=h-bordo){
                        g.fillOval(calcolaX(x), risultato, 2, 2);
                    }
                    break;
                case 3:
                    if(x > 0){
                        risultato = calcolaY(Math.log(x)-somma);
                        if(risultato>=bordo&&risultato<=h-bordo){
                            g.fillOval(calcolaX(x), risultato, 2, 2);
                        }
                    }
                    break;
                case 4:
                    risultato = calcolaY(Math.exp(x)-somma);
                    if(risultato>=bordo&&risultato<=h-bordo){
                        g.fillOval(calcolaX(x), calcolaY(Math.exp(x)-somma), 2, 2);
                    }
            }
        }
        
    }
    
    //metodo che calcola le coordinate del pixel corrispondenti al valore
    public int calcolaX(double pixelX){
        return bordo+(int)((pixelX-xMin)*unitaX);
    }
    
    public int calcolaY(double pixelY){
        return bordo+(int)((yMax-pixelY)*unitaY);
    }
    
    //metodi per settare e restituire i valori massimi degli assi
    public double getxMin(){
        return xMin;
    }
    
    public double getxMax(){
        return xMax;
    }
    
    public double getyMin(){
        return yMin;
    }
    
    public double getyMax(){
        return yMax;
    }
    
    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    public void setyMin(double yMin) {
        this.yMin = yMin;
    }
    
    public void setyMax(double yMax) {
        this.yMax = yMax;
    }
    
    //metodi per settare e rendere la funzione
    public int getFunzione(){
        return funzione;
    }
    
    public void setFunzione(int funzione){
        this.funzione=funzione;
    }
    
    //metodi per settare gli attributi per il calcolo di taylor
    public void setOrdine(int ordine){
        this.ordine = ordine;
    }
    
    public void setX0(double x0){
        this.x0 = x0;
    }
    
    public void setTaylor(boolean taylor){
        this.taylor = taylor;
    }
}
