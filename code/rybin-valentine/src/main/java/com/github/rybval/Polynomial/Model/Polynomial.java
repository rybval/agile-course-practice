package com.github.rybval.Polynomial.Model;

import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Arrays;

public class Polynomial {
    private final Map<Integer, Monomial> monomials;

    Polynomial() {
        monomials = new TreeMap<Integer, Monomial>();
    }

    Polynomial(final Collection<Monomial> monomialsCollection) {
        monomials = new TreeMap<Integer, Monomial>();
        for (Monomial monomialIn : monomialsCollection) {
            int power = monomialIn.getPower();
            Monomial monomialToPut;
            if (monomials.containsKey(power)) {
                monomialToPut = monomials.get(power).add(monomialIn);
            } else {
                monomialToPut = monomialIn;
            }
            monomials.put(power, monomialToPut);
        }
        cleanZeroMonomials();
    }

    Polynomial(final Monomial[] monomials) {
        this(Arrays.asList(monomials));
    }

    public static Polynomial fromString(final String string) {
        String[] monomialStrings = string.replaceAll(" *- *", " -")
                                         .replaceAll(" *\\+ *", " +")
                                         .split(" +");
        Collection<Monomial> monomials = new ArrayList<Monomial>();
        for (String monomialString : monomialStrings) {
            if (!monomialString.isEmpty()) {
                monomials.add(Monomial.fromString(monomialString));
            }
        }
        return new Polynomial(monomials);
    }

    private void cleanZeroMonomials() {
        Collection<Integer> toRemove = new ArrayList<Integer>();
        for (Monomial monomial : monomials.values()) {
            if (monomial.getCoefficient() == 0) {
                toRemove.add(monomial.getPower());
            }
        }
        for (Integer power : toRemove) {
            monomials.remove(power);
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (Monomial monomial : monomials.values()) {
            String monomialStr = monomial.toString();
            if (monomialStr.startsWith("-")) {
                string += " - " + monomialStr.substring("-".length());
            } else {
                string += " + " + monomialStr;
            }
        }
        if (string.startsWith(" + ")) {
            string = string.substring(" + ".length());
        } else {
            string = "-" + string.substring(" - ".length());
        }
        return string;
    }

    @Override
    public int hashCode() {
        return monomials.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Polynomial) {
            Polynomial polynomial = (Polynomial) object;
            return this.monomials.equals(polynomial.monomials);
        } else {
            return false;
        }
    }

    public Polynomial add(final Polynomial another) {
        Collection<Monomial> summMonomials = new ArrayList<Monomial>();
        summMonomials.addAll(this.monomials.values());
        summMonomials.addAll(another.monomials.values());
        return new Polynomial(summMonomials);
    }

    public Polynomial subtract(final Polynomial subtrahend) {
        return this.add(subtrahend.negate());
    }

    public Polynomial multiply(final Monomial multiplier) {
        ArrayList<Monomial> multMonomials = new ArrayList<Monomial>();
        for (Monomial multiplicand : this.monomials.values()) {
            multMonomials.add(multiplicand.multiply(multiplier));
        }
        return new Polynomial(multMonomials);
    }

    public Polynomial multiply(final Polynomial multiplier) {
        Polynomial product = new Polynomial();
        for (Monomial multiplierMonomial : multiplier.monomials.values()) {
            product = product.add(this.multiply(multiplierMonomial));
        }
        return product;
    }

    public Polynomial negate() {
        Collection<Monomial> negatedMonomials = new ArrayList<Monomial>();
        for (Monomial monomial : monomials.values()) {
           negatedMonomials.add(monomial.negate());
        }
        return new Polynomial(negatedMonomials);
    }
}