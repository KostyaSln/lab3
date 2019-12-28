package gurinovich.java;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel
{
    private double[] Coefficients;
    private double XFrom, XTo, XStep;

    public GornerTableModel(double from, double to, double step, double[] coeff)
    {
        XFrom = from;
        XTo = to;
        XStep = step;
        Coefficients = coeff;
    }

    public double getXFrom()
    {
        return XFrom;
    }
    public double getXTo()
    {
        return XTo;
    }
    public double getXStep()
    {
        return XStep;
    }


    @Override
    public int getColumnCount()
    {
        return 5;
    }

    @Override
    public int getRowCount()
    {
        return Double.valueOf(Math.ceil( ( XTo - XFrom ) / XStep )).intValue() + 1;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        double X = XFrom + XStep * row;
        switch (col)
        {
            case 0:
                return X;
            case 1:
                int ln = Coefficients.length;
                double rez = 0;
                for (int i = 0; i < ln - 1; i++)
                    rez = (rez + Coefficients[i]) * X;
                rez += Coefficients[ln - 1];
                return rez;
            case 2:
                int lnf = Coefficients.length;
                float rezf = 0;
                for (int i = 0; i < lnf - 1; i++)
                    rezf = (rezf + (float)Coefficients[i]) * (float)X;
                rezf += Coefficients[lnf - 1];
                return rezf;
            case 3:
                return Double.parseDouble(getValueAt(row, 2).toString()) - Double.parseDouble(getValueAt(row, 1).toString());
            case 4:
                if (Math.abs(Double.parseDouble(getValueAt(row, 3).toString())) > 1000)
                {
                    return 1;
                }
                else
                    return 0;

        }
        return null;
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0:
                return "X";
            case 1:
                return "F(X)";
            case 2:
                return "(float) F(X)";
            case 3:
                return "(float) F(X) - F(X)";
            default:
                return ">1000";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return Double.class;
    }

}
