public class Monom implements Comparable<Monom>{

    Double coef;
    Integer grad;

    public Monom(double coef, int grad) {
        this.coef = coef;
        this.grad = grad;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    public int getGrad() {
        return grad;
    }

    public void setGrad(Integer grad) {
        this.grad = grad;
    }

    public Monom divide(Monom m) {
        return new Monom(this.getCoef()/m.getCoef(), this.getGrad()-m.getGrad());
    }

    @Override
    public String toString() {
        String s = "";

        if(coef == 0) {
            return s;
        }

        // rotunjim coeficientul la doua zecimale
        coef = Math.round(coef * 100.0) / 100.0;

        // caz 1, in care nu apare variabila x in monom (grad == 0)
        if(grad == 0) {
            if(coef > 0) {
                return "+" + coef;
            } else {
                return coef + "";
            }
        }

        // cazul 2, in care apare variabila x in monom (grad == 1)
        if(grad == 1) {
            if(coef > 1) {
                return "+" + coef + "x";
            } else if(coef == 1) {
                return "+x";
            } else if(coef == -1) {
                return "-x";
            } else {
                return coef + "x";
            }
        }

        // cazul 3, in care apare exponent la x (grad > 1)
        if(coef > 0) {
            if(coef == 1) {
                s = "+x^" + grad;
            } else {
                s = "+" + coef + "x^" + grad;
            }
        } else {
            if(coef == -1) {
                s = "-x^" + grad;
            } else {
                s = coef + "x^" + grad;
            }
        }

        return s;
    }

    @Override
    public int compareTo(Monom m) {
        return m.getGrad() - this.getGrad();
    }
}
