import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.ArrayList;

public class Controller {

    private final View v;
    private int crtOp = 0;

    public Controller(View v) {
        // Constructorul Controller adauga listeners pentru butoane
        this.v = v;

        v.addOperatieComplexaListener(new AdunareListener());
        v.addScadereListener(new ScadereListener());
        v.addInmultireListener(new InmultireListener());
        v.addImpartireListener(new ImpartireListener());
        v.addDerivareListener(new DerivareListener());
        v.addIntegrareListener(new IntegrareListener());
        v.addOkListener(new OkListener());
    }

    // Urmatoarele clase implementeaza comportamente in fuctie de operatie

    class AdunareListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            v.getFieldResult().setText("");
            v.getFieldRest().setText("");
            v.getFieldPolinom1().setEditable(true);
            v.getFieldPolinom2().setEditable(true);
            v.getLabelResult().setText("P(x) + Q(x) = ");
            crtOp = 1;
        }
    }

    class ScadereListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            v.getFieldResult().setText("");
            v.getFieldRest().setText("");
            v.getFieldPolinom1().setEditable(true);
            v.getFieldPolinom2().setEditable(true);
            v.getLabelResult().setText("P(x) - Q(x) = ");
            crtOp = 2;
        }
    }

    class InmultireListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            v.getFieldResult().setText("");
            v.getFieldRest().setText("");
            v.getFieldPolinom1().setEditable(true);
            v.getFieldPolinom2().setEditable(true);
            v.getLabelResult().setText("P(x) * Q(x) = ");
            crtOp = 3;
        }
    }

    class ImpartireListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            v.getFieldResult().setText("");
            v.getFieldRest().setText("");
            v.getFieldPolinom1().setEditable(true);
            v.getFieldPolinom2().setEditable(true);
            v.getLabelResult().setText("P(x) : Q(x) = ");
            crtOp = 4;
        }
    }

    class DerivareListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            v.getFieldResult().setText("");
            v.getFieldRest().setText("");
            v.getFieldPolinom1().setEditable(true);
            v.getFieldPolinom2().setEditable(false);
            v.getLabelResult().setText("P(x) d/dx =");
            v.getFieldPolinom2().setText("");
            v.setFieldToColor(v.getFieldPolinom2(), UIManager.getColor("TextField.inactiveBackground"));
            crtOp = 5;
        }
    }

    class IntegrareListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            v.getFieldResult().setText("");
            v.getFieldRest().setText("");
            v.getFieldPolinom1().setEditable(true);
            v.getFieldPolinom2().setEditable(false);
            v.getLabelResult().setText("∫ P(x) dx = ");
            v.getFieldPolinom2().setText("");
            v.setFieldToColor(v.getFieldPolinom2(), UIManager.getColor("TextField.inactiveBackground"));
            crtOp = 6;
        }
    }

    class OkListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // partea de initializare
            Polinom p1 = new Polinom();
            Polinom p2 = new Polinom();
            String field1 = v.getFieldPolinom1().getText();
            String field2 = v.getFieldPolinom2().getText();

            // partea initiala de validare a polinoamelor
            if(crtOp == 0) {
                v.showMessage("Eroare: Selectati o operatie!");
            } else if(crtOp >= 1 && crtOp <= 4) {
                if (field1.equals("") && field2.equals("")) {
                    v.setFieldToColor(v.getFieldPolinom1(), Color.RED);
                    v.setFieldToColor(v.getFieldPolinom2(), Color.RED);
                    v.getFieldResult().setText("");
                    v.getFieldRest().setText("");
                    v.showMessage("Eroare: Introduceti polinoame nevide!");
                } else if (field1.equals("")) {
                    v.setFieldToColor(v.getFieldPolinom1(), Color.RED);
                    v.setFieldToColor(v.getFieldPolinom2(), Color.white);
                    v.getFieldResult().setText("");
                    v.getFieldRest().setText("");
                    v.showMessage("Eroare: Introduceti P(x) nevid!");
                } else if(field2.equals("")){
                    v.setFieldToColor(v.getFieldPolinom1(), Color.white);
                    v.setFieldToColor(v.getFieldPolinom2(), Color.RED);
                    v.getFieldResult().setText("");
                    v.getFieldRest().setText("");
                    v.showMessage("Eroare: Introduceti Q(x) nevid!");
                } else {
                    boolean parse1 = p1.parseString(field1);
                    boolean parse2 = p2.parseString(field2);

                    if(!parse1 && !parse2) {
                        v.setFieldToColor(v.getFieldPolinom1(), Color.RED);
                        v.setFieldToColor(v.getFieldPolinom2(), Color.RED);
                        v.getFieldResult().setText("");
                        v.getFieldRest().setText("");
                        v.showMessage("Eroare: Polinoame invalide!");
                    } else if(!parse1) {
                        v.setFieldToColor(v.getFieldPolinom1(), Color.RED);
                        v.getFieldResult().setText("");
                        v.getFieldRest().setText("");
                        v.showMessage("Eroare: Polinomul P(x) este invalid!");
                    } else if(!parse2) {
                        v.setFieldToColor(v.getFieldPolinom2(), Color.RED);
                        v.getFieldResult().setText("");
                        v.getFieldRest().setText("");
                        v.showMessage("Eroare: Polinomul Q(x) este invalid!");
                    } else {
                        v.setFieldToColor(v.getFieldPolinom1(), Color.WHITE);
                        v.setFieldToColor(v.getFieldPolinom2(), Color.WHITE);
                        p1.descSort();
                        p2.descSort();
                        switch (crtOp) {
                            case 1: // adunare
                                v.getFieldResult().setText(p1.addition(p2).toString());
                                break;
                            case 2: // scadere
                                v.getFieldResult().setText(p1.subtraction(p2).toString());
                                break;
                            case 3: // inmultire
                                v.getFieldResult().setText(p1.multiplication(p2).toString());
                                break;
                            case 4: // impartire
                                try {
                                    ArrayList<Polinom> r = p1.division(p2);
                                    v.getFieldResult().setText(r.get(0).toString());
                                    v.getFieldRest().setText(r.get(1).toString());
                                } catch(ArithmeticException exception) {
                                    v.setFieldToColor(v.getFieldPolinom1(), Color.white);
                                    v.setFieldToColor(v.getFieldPolinom2(), Color.RED);
                                    v.showMessage("Eroare: " + exception.getMessage());
                                }
                                break;
                        }
                    }
                }
            } else { // derivare si integrare
                if(field1.equals("")) {
                    v.setFieldToColor(v.getFieldPolinom1(), Color.RED);
                    v.showMessage("Eroare: Polinomul P(x) este vid!");
                } else {
                    boolean parse1 = p1.parseString(field1);
                    if(!parse1) {
                        v.setFieldToColor(v.getFieldPolinom1(), Color.RED);
                        v.showMessage("Eroare: Polinomul P(x) este invalid!");
                    } else {
                        v.setFieldToColor(v.getFieldPolinom1(), Color.WHITE);
                        v.getFieldResult().setText(crtOp == 5? p1.derivative().toString() : p1.integration().toString() + " + C");
                    }
                }
            }
        }
    }
}
