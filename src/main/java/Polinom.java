import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinom {

    ArrayList<Monom> listaMonoame;

    public Polinom() {
        listaMonoame = new ArrayList<>();
    }

    public boolean parseString(String polynomial) {
        // se sterg spatiile "albe", se potriversc variabilele si simplifica calculele din polinom
        polynomial = polynomial.replaceAll("\\s+", "");
        polynomial = polynomial.replaceAll("X", "x");
        polynomial = polynomial.replaceAll("--", "+");

        // prima validare, in care se verifica aparitia unor caractere neasteptate, altele decat operatori, cifre sau variabila "x"
        for(int i = 0; i < polynomial.length(); i++) {
            if(polynomial.charAt(i) != 'x' && polynomial.charAt(i) != '+' && polynomial.charAt(i) != '-'
                    && polynomial.charAt(i) != '^' && (polynomial.charAt(i) < '0' || polynomial.charAt(i) > '9'))
                return false;
        }

        // pattern-ul si matcher-ul initial, pentru a descompune polinomul in monoame
        Pattern p = Pattern.compile("([+-]?[^-+]+)");
        Matcher m = p.matcher(polynomial);

        int noRepeats = 0;
        while(m.find()) {
            // pattern-ul si matcher-ul secundar, pentru a descompune monoamele in coeficient, variabila si putere
            String monomial = m.group(1);
            Pattern p1 = Pattern.compile("([+-]?[\\d]*)([xX]?)(?:\\^(\\d+))?");
            Matcher m1 = p1.matcher(monomial);

            while (m1.find()) {
                if(noRepeats++ % 2 == 0) {
                    if(m1.group(1).equals("") && m1.group(2).equals("")) {
                        return false;
                    }

                    Double coef;
                    Integer grad;

                    // se extrag componentele monomului curent
                    if(m1.group(1).equals("")) {
                        coef = 1.0;
                    } else if(m1.group(1).equals("-") || m1.group(1).equals("+") && m1.group(1).length() == 1){
                        coef = Double.parseDouble(m1.group(1)+"1");
                    } else if(!m1.group(1).equals("0")){
                        coef = Double.parseDouble(m1.group(1));
                    } else {
                        coef = 0.0;
                    }

                    if(m1.group(3) == null && m1.group(2).equals("x")) {
                        grad = 1;
                    } else if(m1.group(3) != null && m1.group(2).equals("x")) {
                        grad = Integer.parseInt(m1.group(3));
                    } else {
                        grad = 0;
                    }

                    // se adauga monomul curent la polinom
                    addMonom(new Monom(coef, grad));
                }
            }
        }
        return true;
    }

    public void addMonom(Monom m) {
        // metoda care adauga un monom la polinom
        Monom monomAux = isDuplicatePow(m.grad);

        // se asigura ca nu exista un monom cu acelasi grad
        if(monomAux != null) {
            // in caz afirmativ, se aduna coeficientii
            monomAux.setCoef(monomAux.coef + m.coef);

            // se verifica daca s-au adunat 2 monoame opuse, caz in care se va "distruge"
            if(monomAux.getCoef() == 0) {
                listaMonoame.remove(monomAux);
            }
        } else {
            listaMonoame.add(m);
        }
    }

    public void descSort() {
        Collections.sort(listaMonoame);
    }

    public Polinom addition(Polinom p) {
        // operatia de adunare a 2 polinoame
        Polinom r = new Polinom();
        r.copy(this);

        // se aduna monoamele celor 2 polinoame
        for(Monom m: p.getListaMonoame()) {
            r.addMonom(m);
        }

        // si se sorteaza descrescator dupa grad
        r.descSort();
        return r;
    }

    public Polinom subtraction(Polinom p) {
        // operatia de scadere a 2 polinoame
        Polinom r = new Polinom();
        r.copy(this);
        Polinom neg = new Polinom();
        neg.copy(p);

        // se aduna monoamele, cu coeficientii celui de-al doilea polinom negati
        for(Monom m: neg.getListaMonoame()) {
            m.setCoef(-m.getCoef());
            r.addMonom(m);
        }

        // si se sorteaza descrescator dupa grad
        r.descSort();
        return r;
    }

    public Polinom multiplication(Polinom p) {
        // operatia de inmultire a 2 polinoame
        Polinom r = new Polinom();

        // se parcurg ambele polinoame si inmultim coeficientii, respectiv adunam gradele monoamelor
        for(Monom m1: this.getListaMonoame()) {
            for(Monom m2: p.getListaMonoame()) {
                r.addMonom(new Monom(m1.getCoef()*m2.getCoef(), m1.getGrad()+m2.getGrad()));
            }
        }

        // si se sorteaza descrescator dupa grad
        r.descSort();
        return r;
    }

    public ArrayList<Polinom> division(Polinom p) throws ArithmeticException {
        // operatia de impartire a 2 polinoame
        // testam cazul de impartire la polinomul nul (0)
        if(p.toString().equals("0")) {
            throw new ArithmeticException("impartitorul nu poate fi 0!");
        }

        ArrayList<Polinom> r = new ArrayList<>();
        Polinom c = new Polinom();

        // repetam procedura de impartire cat timp gradul deimpartitului >= gradul impartitorului
        while(this.getGrad() >= p.getGrad()) {
            // retinem monoamele de grad maxim, apoi le impartim
            Monom md = this.getMonomAt(0);
            Monom mi = p.getMonomAt(0);
            Monom mc = md.divide(mi);

            // ne folosim de aux1 pentru a aduna la rezultat (catul) rezultatul impartirii curente
            Polinom aux1 = new Polinom();
            aux1.addMonom(mc);
            c.addition(aux1);

            // se scade din deimpartit inmultirea dintre impartitor si rezultatul anterior
            Polinom aux2 = p.multiplication(aux1);
            this.subtraction(aux2);
        }

        // la final, adaugam polinoamele rezultante (catul si restul impartirii) intr-un ArrayList
        r.add(c);
        r.add(this);
        return r;
    }

    public Polinom derivative() {
        // operatia de derivare a unui polinom
        Polinom r = new Polinom();
        // edge case in care polinomul este 0
        if(this.getSize() == 1 && this.getMonomAt(0).getCoef() == 0)
            return r;

        r.copy(this);

        // parcurgem monoamele si inmultim coeficientul cu puterea curenta, respectiv decrementam gradul
        for(Monom m: r.getListaMonoame()) {
            m.setCoef(m.getCoef()*m.getGrad());
            m.setGrad(m.getGrad()-1);
        }

        // si se sorteaza descrescator dupa grad
        r.descSort();
        return r;
    }

    public Polinom integration() {
        // operatia de integrare a unui polinom
        Polinom r = new Polinom();
        // edge case in care polinomul este 0
        if(this.getSize() == 1 && this.getMonomAt(0).getCoef() == 0)
            return r;

        r.copy(this);

        // parcurgem monoamele si incrementam gradul, respectiv impartim coeficientul curent la gradul incrementat
        for(Monom m: r.getListaMonoame()) {
            m.setGrad(m.getGrad() + 1);
            m.setCoef(m.getCoef() * (1.0 / m.getGrad()));
        }

        // si se sorteaza descrescator dupa grad
        r.descSort();
        return r;
    }

    public int getSize() {
        return listaMonoame.size();
    }

    public Monom getMonomAt(int index) {
        // metoda care returneaza monomul la pozitia aflata in index
        if(this.getListaMonoame().size() == 0) {
            return new Monom(0, -1);
        }

        return this.listaMonoame.get(index);
    }

    public Integer getGrad() {
        if(this.getListaMonoame().size() == 0) {
            return 0;
        }

        return this.getMonomAt(0).getGrad();
    }

    public ArrayList<Monom> getListaMonoame() {
        return listaMonoame;
    }

    public Monom isDuplicatePow(Integer pow) {
        // metoda verifica daca exista puterea pow in polinom
        for(Monom m: listaMonoame) {
            if(m.getGrad() == pow) {
                return m;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        String s = "";

        if(listaMonoame.size() == 0) {
            return "0";
        }

        if(listaMonoame.get(0).getCoef() == 0) {
            return "0";
        }

        // eliminam semnul '+' din cel mai din stanga monom
        boolean isFirst = true;
        for(Monom m: listaMonoame) {
            s += m.toString();

            if(isFirst && s.length() > 0 && s.charAt(0) == '+') {
                s = s.substring(1);
            }
            isFirst = false;
        }

        if(s.equals(""))
            return "0";

        // eliminam partea reala a coeficientilor numere intregi ".0"
        s = s.replaceAll("\\.0", "");
        return s;
    }

    public void copy(Polinom vechi) {
        // metoda de clone
        this.listaMonoame = vechi.listaMonoame;
    }
}