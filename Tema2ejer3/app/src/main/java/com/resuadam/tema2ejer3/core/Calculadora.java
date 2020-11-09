package com.resuadam.tema2ejer3.core;

public class Calculadora {
    public enum OperadoBinario { Add, Sub, Mul, Div, Ave };
    public enum OperadorUnario { Neg, Sqr, Sqrt, Fac };

    public Calculadora(String op1, String op2)
    {
        this( Double.parseDouble( op1 ), Double.parseDouble( op2 ) );
    }

    public Calculadora(double op1, double op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    public double calcula(OperadorUnario opr)
    {
        double toret = this.getOp1();

        switch (opr) {
            case Neg:
                toret *= -1;
                break;
            case Sqr:
                toret *= toret;
                break;
            case Sqrt:
                toret = Math.sqrt( toret );
                break;
            case Fac:
                int i, res = 1;
                for(i = 1; i <= toret; i++) {
                    res = res * i;
                }
                toret = res;
                break;
        }

        return toret;
    }

    public double calcula(OperadoBinario opr)
    {
        double toret = 0;

        switch (opr) {
            case Add:
                toret = this.getOp1() + this.getOp2();
                break;
            case Sub:
                toret = this.getOp1() - this.getOp2();
                break;
            case Mul:
                toret = this.getOp1() * this.getOp2();
                break;
            case Div:
                toret = this.getOp1() / this.getOp2();
                break;
            case Ave:
                toret = (this.getOp1() + this.getOp2())/2;
                break;
        }

        return toret;
    }

    public double getOp1() {
        return this.op1;
    }

    public double getOp2() {
        return this.op2;
    }

    private double op1;
    private double op2;
}
