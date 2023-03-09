import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    // panel principal al ferestrei
    private final JPanel mainPanel = new JPanel();

    // text fields
    private final JTextField fieldPolinom1 = new JTextField(25);
    private final JTextField fieldPolinom2 = new JTextField(25);
    private final JTextField fieldResult = new JTextField(25);
    private final JTextField fieldRest = new JTextField(25);

    // label pentru field-ul rezultat
    private final JLabel labelResult = new JLabel("Selectati operatia");

    // butoane
    private final JButton butonAdunare = new JButton("Adunare +");
    private final JButton butonScadere = new JButton("Scadere -");
    private final JButton butonInmultire = new JButton("Inmultire *");
    private final JButton butonImpartire = new JButton("Impartire :");
    private final JButton butonDerivare = new JButton("Derivare d/dx");
    private final JButton butonIntegrare = new JButton("Integrare ∫ dx");
    private final JButton butonGo = new JButton("Efectueaza operatie");

    public View() {
        // Constructorul View initializeaza fereastra principala
        // se adauga butoane, labels, textfields si se formateaza cu layouts

        mainPanel.removeAll();
        setVisible(true);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panelTitlu = new JPanel();
        panelTitlu.setLayout(new FlowLayout());
        panelTitlu.add(new JLabel("Calculator de polinoame - Marcus Cristian-Viorel 302210"));

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new FlowLayout());
        panelInput.add(new JLabel("Polinomul P(x)"));
        panelInput.add(fieldPolinom1);
        fieldPolinom1.setEditable(false);
        panelInput.add(new JLabel("Polinomul Q(x)"));
        panelInput.add(fieldPolinom2);
        fieldPolinom2.setEditable(false);

        JPanel panelOperatii = new JPanel();
        panelOperatii.setLayout(new FlowLayout());
        panelOperatii.add(butonAdunare);
        panelOperatii.add(butonScadere);
        panelOperatii.add(butonInmultire);
        panelOperatii.add(butonImpartire);
        panelOperatii.add(butonDerivare);
        panelOperatii.add(butonIntegrare);

        JPanel panelGo = new JPanel();
        panelGo.setLayout(new FlowLayout());
        panelGo.add(butonGo);

        JPanel panelResult = new JPanel();
        panelResult.setLayout(new FlowLayout());
        panelResult.add(labelResult);
        panelResult.add(fieldResult);
        panelResult.add(new JLabel("Restul impartirii"));
        panelResult.add(fieldRest);
        fieldResult.setEditable(false);
        fieldRest.setEditable(false);

        mainPanel.add(panelTitlu);
        mainPanel.add(panelInput);
        mainPanel.add(panelOperatii);
        mainPanel.add(panelGo);
        mainPanel.add(panelResult);

        this.add(mainPanel);
        this.setTitle("Calculator de polinoame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(880, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public JTextField getFieldPolinom1() {
        return fieldPolinom1;
    }

    public JTextField getFieldPolinom2() {
        return fieldPolinom2;
    }

    public JTextField getFieldResult() {
        return fieldResult;
    }

    public JTextField getFieldRest() {
        return fieldRest;
    }

    public JLabel getLabelResult() {
        return labelResult;
    }

    public void addOperatieComplexaListener(ActionListener adunare) {
        butonAdunare.addActionListener(adunare);
    }

    public void addScadereListener(ActionListener scadere) {
        butonScadere.addActionListener(scadere);
    }

    public void addInmultireListener(ActionListener inmultire) {
        butonInmultire.addActionListener(inmultire);
    }

    public void addImpartireListener(ActionListener impartire) {
        butonImpartire.addActionListener(impartire);
    }

    public void addDerivareListener(ActionListener derivare) {
        butonDerivare.addActionListener(derivare);
    }

    public void addIntegrareListener(ActionListener integrare) {
        butonIntegrare.addActionListener(integrare);
    }

    public void addOkListener(ActionListener ok) {
        butonGo.addActionListener(ok);
    }

    public void setFieldToColor(JTextField field, Color color) {
        field.setBackground(color);
        if(color == Color.RED) {
            field.setForeground(Color.white);
        } else {
            field.setForeground(Color.black);
        }
    }
}
