package com.resuadam.burguerbuilderlistview.core;

public class BurguerConfigurator {
    public static final String DELIMITER = ", ";
    public static final double BASE_COST = 3.0;

    public BurguerConfigurator()
    {
        this.selected = new boolean[ INGREDIENTS.length ];

        assert INGREDIENTS.length != COSTS.length:
                "all arrays should have the same length";
    }

    /** Calculates the cost for the burguer
     * @return A real with the total cost.
     */
    public double calculateCost()
    {
        double toret = BASE_COST;

        for(int i = 0; i < this.selected.length; ++i) {
            if ( this.selected[ i ] ) {
                toret += COSTS[ i ];
            }
        }

        return toret;
    }

    /** Returns the nth global element given by selectedPos in the vector of vector of selections */
    public int getGlobalPosOfSelected(int selectedPos)
    {
        int toret = 0;

        for(int i = 0; i < this.selected.length; ++i) {
            if ( this.selected[ i ] ) {
                --selectedPos;
                if ( selectedPos < 0 ) {
                    toret = i;
                    break;
                }
            }
        }

        return toret;
    }

    /** Returns the live boolean vector for ingredient selection */
    public boolean[] getSelected()
    {
        return this.selected;
    }

    @Override
    public String toString()
    {
        String delimiter = "";
        StringBuilder toret = new StringBuilder();

        for(int i = 0; i < this.selected.length; ++i) {
            if ( this.selected[ i ] ) {
                toret.append( delimiter );
                toret.append( INGREDIENTS[ i ] );
                delimiter = DELIMITER;
            }
        }

        return toret.toString();
    }

    /** Returns selected ingredients as a string.
     *  @returns A single string with the whole contents.*/
    public String toList()
    {
        return this.toListWith( DELIMITER );
    }

    /** Returns selected ingredients as a string.
     *  @param newDelimiter The delimiter to use to end each line.
     *  @returns A single string with the whole contents.*/
    public String toListWith(String newDelimiter)
    {
        return this.toString().replace( DELIMITER, newDelimiter );
    }

    /** Represents all available (selectable) ingredients */
    public static String[] INGREDIENTS = new String[] {
            "Lechuga",
            "Tomate",
            "Queso",
            "York",
            "Cebolla"
    };

    /** Parallel array to ingredients, representing their costs */
    public static double[] COSTS = new double[] {
            0.1,
            0.5,
            1,
            0.75,
            0.1
    };

    /** Represents all fixed, mandatory ingredients */
    public static String[] FIXED_INGREDIENTS = new String[] {
            "Pan",
            "Carne"
    };

    /** Parallel array to fixed ingredients, representing their costs */
    public static double[] FIXED_COSTS = new double[] {
            1,
            2
    };

    private boolean[] selected;
}