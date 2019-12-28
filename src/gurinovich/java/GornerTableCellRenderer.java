package gurinovich.java;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer
{
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JCheckBox Check = new JCheckBox();

    private String Find = null;
    private boolean FindPalindromes = false;

    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();

    public GornerTableCellRenderer()
    {
        formatter.setMaximumFractionDigits(5);

        formatter.setGroupingUsed(false);

        DecimalFormatSymbols Symbol = formatter.getDecimalFormatSymbols();
        Symbol.setDecimalSeparator('.');

        formatter.setDecimalFormatSymbols(Symbol);

        panel.add(label);
    }

    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int row, int col)
    {
        panel.remove(Check);
        panel.remove(Check);
        String text = formatter.format(o);
        label.setText(text);

        if (col == 4)
        {
            Check.setEnabled(false);
            if (Double.parseDouble(o.toString()) == 1)
                Check.setSelected(true);
            else
                Check.setSelected(false);
            label.setText("");
            panel.add(Check);
            return panel;
        }

        boolean palindrome = false;

        if (FindPalindromes)
        {
            String pal = text.replaceAll("\\.|-", "");
            int len = pal.length() / 2;
            if (len == 0)
                palindrome = true;
            else
            {
                int coincidences = 0;

                for (int i = 0; i < len; i++)
                    if (pal.charAt(i) == pal.charAt(pal.length() - 1 - i))
                        coincidences++;

                if (coincidences == len)
                    palindrome = true;
            }
        }

        if (col != 0 && Find != null && Find.equals(text))
        {
            System.out.println("Find: " + Find + " text: " + text + " row: " + row + " col: " + col);
            if (palindrome)
                panel.setBackground(Color.GREEN);
            else
            {
             //   panel.removeAll();
                Check.setEnabled(false);
                Check.setSelected(true);
                panel.add(Check);
                panel.setBackground(Color.BLUE);
            }

        }
        else if (palindrome)
            panel.setBackground(Color.YELLOW);
        else
            panel.setBackground(Color.WHITE);

        return panel;
    }

    public void setFind(String find)
    {
        this.Find = find;
    }

    public void setFindPalindromes(boolean a)
    {
        this.FindPalindromes = a;
    }
}
