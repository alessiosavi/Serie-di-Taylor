
package serietaylor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

public class Finestra extends JFrame implements WindowListener,ActionListener {
    
    private GridBagConstraints container;
    
    //pannello per il disegno della funzione
    private JPanel funzPanel;
    private JLabel labFunz;
    private JList listaFunz;
    private DefaultListModel listModel;
    private JButton disegna;
    
    //pannello per il calcolo di Taylor
    private JPanel taylPanel;
    private JLabel labX0;
    private JTextField x0;
    private JLabel labOrdine;
    private JTextField ordine;
    private JButton calcTaylor;
    private JButton cancella;
    
    //pannello per il dimensionamento degli assi
    private JPanel assiPanel;
    private JLabel labAssi;
    private JLabel labXmax;
    private JTextField xMax;
    private JLabel labXmin;
    private JTextField xMin;
    private JLabel labYmax;
    private JTextField yMax;
    private JLabel labYmin;
    private JTextField yMin;
    
    //grafico
    Grafico grafico;
    
    public Finestra(int x, int y){
        
        this.setSize(x, y);
        this.setTitle("SERIE DI TAYLOR");
        this.setLayout(new GridBagLayout());
        this.addWindowListener(this);
        
        container = new GridBagConstraints();
        
        //posiziono il pannello e i componenti per il disegno delle funzioni
        funzPanel = new JPanel(new GridBagLayout());
        funzPanel.setBackground(Color.LIGHT_GRAY);
        funzPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        container.gridx = 0;
        container.gridy = 0;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.weightx = 0.1;
        container.weighty = 0.1;
        container.fill = GridBagConstraints.BOTH;
        this.add(funzPanel, container);
        labFunz = new JLabel("Funzioni");
        labFunz.setFont(new Font("sansserif", Font.BOLD, 20));
        container.gridx = 0;
        container.gridy = 1;
        container.gridwidth = 2;
        container.gridheight = 1;
        container.weightx = 0.1;
        container.weighty = 0.1;
        container.anchor = GridBagConstraints.LAST_LINE_START;
        container.fill = GridBagConstraints.NONE;
        container.insets = new Insets(0, 20, 0, 0);
        funzPanel.add(labFunz, container);
        listaFunz = new JList();
        listaFunz.setBorder(new BevelBorder(BevelBorder.LOWERED));
        listaFunz.setFont(new Font("sansserif", Font.BOLD, 16));
        listModel = new DefaultListModel();
        listModel.addElement("  seno sin");
        listModel.addElement("  coseno cos");
        listModel.addElement("  tangente tan");
        listModel.addElement("  logaritmo ln");
        listModel.addElement("  esponenziale e^x");
        listaFunz.setModel(listModel);
        container.gridx = 0;
        container.gridy = 2;
        container.gridwidth = 3;
        container.gridheight = 4;
        container.weightx = 1.0;
        container.weighty = 1.0;
        container.fill = GridBagConstraints.BOTH;
        container.anchor = GridBagConstraints.CENTER;
        container.insets = new Insets(10, 10, 10, 10);
        funzPanel.add(listaFunz, container);
        disegna = new JButton("DISEGNA");
        disegna.addActionListener(this);
        container.gridx = 3;
        container.gridy = 3;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.weightx = 0.2;
        container.weighty = 0.2;
        container.fill = GridBagConstraints.NONE;
        container.anchor = GridBagConstraints.LINE_START;
        container.insets = new Insets(0, 0, 0, 0);
        container.ipadx = 5;
        container.ipady = 5;
        funzPanel.add(disegna, container);
        
        //posiziono il pannello e i componenti per il calcolo di taylor
        taylPanel = new JPanel(new GridBagLayout());
        taylPanel.setBackground(Color.LIGHT_GRAY);
        taylPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        container.gridx = 0;
        container.gridy = 1;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.ipadx = 0;
        container.ipady = 0;
        container.weightx = 0.1;
        container.weighty = 0.1;
        container.anchor = GridBagConstraints.CENTER;
        container.fill = GridBagConstraints.BOTH;
        this.add(taylPanel, container);
        labX0 = new JLabel("X0: ");
        labX0.setFont(new Font("sansserif", Font.BOLD, 14));
        container.gridx = 1;
        container.gridy = 0;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.weightx = 0.1;
        container.anchor = GridBagConstraints.LAST_LINE_END;
        container.fill = GridBagConstraints.NONE;
        taylPanel.add(labX0, container);
        x0 = new JTextField();
        container.gridx = 2;
        container.gridy = 0;
        container.ipady = 5;
        container.insets = new Insets(0, 5, 0, 40);
        container.fill = GridBagConstraints.HORIZONTAL;
        container.anchor = GridBagConstraints.LAST_LINE_START;
        taylPanel.add(x0, container);
        labOrdine = new JLabel("Ordine del polinomio: ");
        labOrdine.setFont(new Font("sansserif", Font.BOLD, 14));
        container.gridx = 0;
        container.gridy = 1;
        container.gridwidth = 2;
        container.gridheight = 1;
        container.ipady = 0;
        container.insets = new Insets(0, 0, 0, 0);
        container.fill = GridBagConstraints.NONE;
        container.anchor = GridBagConstraints.LINE_END;
        taylPanel.add(labOrdine, container);
        ordine = new JTextField();
        container.gridx = 2;
        container.gridy = 1;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.weightx = 1.0;
        container.ipady = 5;
        container.insets = new Insets(0, 5, 0, 40);
        container.fill = GridBagConstraints.HORIZONTAL;
        container.anchor = GridBagConstraints.LINE_START;
        taylPanel.add(ordine, container);
        calcTaylor = new JButton("CALCOLA TAYLOR");
        calcTaylor.addActionListener(this);
        container.gridx = 0;
        container.gridy = 2;
        container.gridwidth = 3;
        container.gridheight = 1;
        container.weightx = 1.0;
        container.insets = new Insets(0, 0, 0, 0);
        container.fill = GridBagConstraints.NONE;
        container.anchor = GridBagConstraints.PAGE_START;
        taylPanel.add(calcTaylor, container);
        cancella = new JButton("CANC. GRAFICI");
        cancella.addActionListener(this);
        container.gridx = 0;
        container.gridy = 3;
        taylPanel.add(cancella, container);
        
        
        //posiziono il pannello e i compnenti per il dimensionamento degli assi
        assiPanel = new JPanel(new GridBagLayout());
        assiPanel.setBackground(Color.LIGHT_GRAY);
        assiPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        container.gridx = 0;
        container.gridy = 2;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.weightx = 0.1;
        container.weighty = 0.1;
        container.fill = GridBagConstraints.BOTH;
        container.anchor = GridBagConstraints.CENTER;
        this.add(assiPanel, container);
        labAssi = new JLabel("Dimensione assi");
        labAssi.setFont(new Font("sansserif", Font.BOLD, 20));
        container.gridx = 0;
        container.gridy = 0;
        container.gridwidth = 4;
        container.gridheight = 1;
        container.weightx = 0.1;
        container.weighty = 0.1;
        container.ipady = 0;
        container.insets = new Insets(0, 20, 0, 0);
        container.fill = GridBagConstraints.NONE;
        container.anchor = GridBagConstraints.LAST_LINE_START;
        assiPanel.add(labAssi, container);
        labXmax = new JLabel("X Max: ");
        labXmax.setFont(new Font("sansserif", Font.BOLD, 13));
        container.gridx = 0;
        container.gridy = 1;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.weightx = 0.2;
        container.insets = new Insets(0, 0, 0, 0);
        container.anchor = GridBagConstraints.LINE_END;
        assiPanel.add(labXmax, container);
        xMax = new JTextField();
        xMax.setText("2.5");
        xMax.addActionListener(this);
        container.gridx = 1;
        container.gridy = 1;
        container.ipady = 5;
        container.insets = new Insets(0, 5, 0, 20);
        container.fill = GridBagConstraints.HORIZONTAL;
        container.anchor = GridBagConstraints.LINE_START;
        assiPanel.add(xMax, container);
        labXmin = new JLabel("X Min: ");
        labXmin.setFont(new Font("sansserif", Font.BOLD, 13));
        container.gridx = 2;
        container.gridy = 1;
        container.ipady = 0;
        container.insets = new Insets(0, 0, 0, 0);
        container.fill = GridBagConstraints.NONE;
        container.anchor = GridBagConstraints.LINE_END;
        assiPanel.add(labXmin, container);
        xMin = new JTextField();
        xMin.setText("-2.5");
        xMin.addActionListener(this);
        container.gridx = 3;
        container.gridy = 1;
        container.ipady = 5;
        container.insets = new Insets(0, 5, 0, 20);
        container.fill = GridBagConstraints.HORIZONTAL;
        container.anchor = GridBagConstraints.LINE_START;
        assiPanel.add(xMin, container);
        labYmax = new JLabel("Y Max: ");
        labYmax.setFont(new Font("sansserif", Font.BOLD, 13));
        container.gridx = 0;
        container.gridy = 2;
        container.gridwidth = 1;
        container.gridheight = 1;
        container.ipady = 0;
        container.insets = new Insets(0, 0, 0, 0);
        container.fill = GridBagConstraints.NONE;
        container.anchor = GridBagConstraints.FIRST_LINE_END;
        assiPanel.add(labYmax, container);
        yMax = new JTextField();
        yMax.setText("1.0");
        yMax.addActionListener(this);
        container.gridx = 1;
        container.gridy = 2;
        container.ipady = 5;
        container.insets = new Insets(0, 5, 0, 20);
        container.fill = GridBagConstraints.HORIZONTAL;
        container.anchor = GridBagConstraints.FIRST_LINE_START;
        assiPanel.add(yMax, container);
        labYmin = new JLabel("Y Min: ");
        labYmin.setFont(new Font("sansserif", Font.BOLD, 13));
        container.gridx = 2;
        container.gridy = 2;
        container.ipady = 0;
        container.insets = new Insets(0, 0, 0, 0);
        container.fill = GridBagConstraints.NONE;
        container.anchor = GridBagConstraints.FIRST_LINE_END;
        assiPanel.add(labYmin, container);
        yMin = new JTextField();
        yMin.setText("-1.0");
        yMin.addActionListener(this);
        container.gridx = 3;
        container.gridy = 2;
        container.ipady = 5;
        container.insets = new Insets(0, 5, 0, 20);
        container.fill = GridBagConstraints.HORIZONTAL;
        container.anchor = GridBagConstraints.FIRST_LINE_START;
        assiPanel.add(yMin, container);
        
        //posiziono il Grafico
        grafico = new Grafico();
        container.gridx = 1;
        container.gridy = 0;
        container.gridwidth = 3;
        container.gridheight = 3;
        container.weightx = 1.0;
        container.weighty = 1.0;
        container.insets = new Insets(10, 10, 10, 10);
        container.fill = GridBagConstraints.BOTH;
        container.anchor = GridBagConstraints.CENTER;
        this.add(grafico, container);
        
        this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //disegno della funzione
        if(e.getSource() == disegna){
            grafico.setFunzione(listaFunz.getSelectedIndex());
            grafico.repaint();
        }
        
        //richiesta di calcolo di taylor
        if(e.getSource() == calcTaylor){
            int ordine = Integer.parseInt(this.ordine.getText().trim());
            
            if(ordine > 0){
                double x0 = Double.parseDouble(this.x0.getText().trim());
                double rangeX = Math.abs(grafico.getxMax())+Math.abs(grafico.getxMin());
                xMin.setText(""+(x0-rangeX/2));
                xMax.setText(""+(x0+rangeX/2));
                grafico.setxMin(x0-rangeX/2);
                grafico.setxMax(x0+rangeX/2);
                grafico.setOrdine(ordine);
                grafico.setX0(x0);
                grafico.setTaylor(true);
                grafico.repaint();
            }
            else{
                if(ordine <= 0){
                    this.ordine.setText("-n.v.-");
                }
            }
        }
        
        //cancellazione dei grafici
        if(e.getSource() == cancella){
            grafico.setFunzione(-1);
            grafico.setTaylor(false);
            grafico.repaint();
        }
        
        //aree di testo per le dimensioni degli assi
        if(e.getSource() == xMax){
            grafico.setxMax(Float.parseFloat(xMax.getText()));
            grafico.repaint();
        }
        if(e.getSource() == xMin){
            grafico.setxMin(Float.parseFloat(xMin.getText()));
            grafico.repaint();
        }
        if(e.getSource() == yMax){
            grafico.setyMax(Float.parseFloat(yMax.getText()));
            grafico.repaint();
        }
        if(e.getSource() == yMin){
            grafico.setyMin(Float.parseFloat(yMin.getText()));
            grafico.repaint();
        }
        
    }
    
}
