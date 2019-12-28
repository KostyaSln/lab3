package gurinovich.java;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class Main extends JFrame
{
    private GornerTableModel data;
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private double XFrom = 0, XTo = 0, XStep = 0;
    private JTable Table;
    private Box TableBox = Box.createHorizontalBox();
    private boolean PalindromesShown = false;
    private JFileChooser fileChooser = null;
    private MyFilter[] Filters = new MyFilter[]{
            new MyFilter("txt"),
            new MyFilter("bin"),
            new MyFilter("csv")
    };

    public Main(double[] Coefficients)
    {
        super("idk how to name it");
        setSize(640, 480);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation( (kit.getScreenSize().width - 640) / 2, (kit.getScreenSize().height - 480) / 2 );

///////////menu

        JMenuBar Menu = new JMenuBar();

        JMenu FileMenu = new JMenu("File");

        FileMenu.setMaximumSize(FileMenu.getPreferredSize());

        JMenuItem Save = new JMenuItem("Save");

        Save.setEnabled(false);

        Save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if (fileChooser == null)
                {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    for (int i = 0; i < Filters.length; i++)
                        fileChooser.addChoosableFileFilter(Filters[i]);

                    fileChooser.setFileFilter(Filters[0]);
                }

                if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION)
                {

                    Saving(data);
                    fileChooser.setSelectedFile(new File(""));
                }
            }
        });

        FileMenu.add(Save);

        Menu.add(FileMenu);

        JMenu TableMenu = new JMenu("Table");

        TableMenu.setEnabled(false);

        TableMenu.setMaximumSize(TableMenu.getPreferredSize());

        JMenuItem Find = new JMenuItem("Find");

        Find.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                renderer.setFind(JOptionPane.showInputDialog(Main.this, "What do yau want to find?", "Find", JOptionPane.QUESTION_MESSAGE));
                repaint();
            }
        });

        TableMenu.add(Find);

        JCheckBoxMenuItem FindPalindromes = new JCheckBoxMenuItem("Show palindromes");

        FindPalindromes.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                renderer.setFindPalindromes(!PalindromesShown);
                PalindromesShown = !PalindromesShown;
                repaint();
            }
        });

        TableMenu.add(FindPalindromes);

        Menu.add(TableMenu);

        JMenuItem About = new JMenuItem("About");
        About.setMaximumSize(About.getPreferredSize());

        About.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                ImageIcon Chester = new ImageIcon("D:/projects/java/lab3/Chester.jpg" );
                JOptionPane.showMessageDialog(Main.this, "Stankevich, group 7\ni don't like photos, so my Chester figurine",  "About", JOptionPane.INFORMATION_MESSAGE, Chester);
            }
        });

        Menu.add(About);

        setJMenuBar(Menu);

///////////input

        Box Input = Box.createHorizontalBox();

        JLabel XFromLabel = new JLabel("X from: ");
        Input.add(XFromLabel);
        JTextField XFromText = new JTextField("0", 10);
        XFromText.setMaximumSize(XFromText.getPreferredSize());
        Input.add(XFromText);

        Input.add(Box.createHorizontalStrut(20));

        JLabel XToLabel = new JLabel("X to: ");
        Input.add(XToLabel);
        JTextField XToText = new JTextField("0", 10);
        XToText.setMaximumSize(XToText.getPreferredSize());
        Input.add(XToText);

        Input.add(Box.createHorizontalStrut(20));

        JLabel XStepLabel = new JLabel("Step: ");
        Input.add(XStepLabel);
        JTextField XStepText = new JTextField("0", 10);
        XStepText.setMaximumSize(XStepText.getPreferredSize());
        Input.add(XStepText);

///////////buttons

        Box Buttons = Box.createHorizontalBox();

        JButton Calc = new JButton("Calculate");

        Calc.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                XFrom = Double.parseDouble(XFromText.getText());
                XTo = Double.parseDouble(XToText.getText());
                XStep = Double.parseDouble(XStepText.getText());

                data = new GornerTableModel(XFrom, XTo, XStep, Coefficients);

                Table = new JTable(data);

                Table.setDefaultRenderer(Double.class, renderer);

                Table.setRowHeight(30);
                TableBox.removeAll();
                TableBox.add(new JScrollPane(Table));

                getContentPane().validate();

                Save.setEnabled(true);
                TableMenu.setEnabled(true);

            }
        });

        Buttons.add(Calc);

        Buttons.add(Box.createHorizontalStrut(20));

        JButton Clear = new JButton("Clear");

        Clear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                XFromText.setText("0");
                XFrom = 0;
                XToText.setText("0");
                XTo = 0;
                XStepText.setText("0");
                XStep = 0;
            }
        });

        Buttons.add(Clear);

///////////end

        Box Up = Box.createVerticalBox();

        Up.add(Box.createVerticalStrut(10));
        Up.add(Input);

        getContentPane().add(Up, BorderLayout.NORTH);

        getContentPane().add(TableBox,  BorderLayout.CENTER);

        Box Down = Box.createVerticalBox();

        Down.add(Buttons);

        Down.add(Box.createVerticalStrut(10));

        getContentPane().add(Down, BorderLayout.SOUTH);

    }

    private void Saving(GornerTableModel data)//txt, bin, csv
    {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols Symbol = formatter.getDecimalFormatSymbols();
        Symbol.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(Symbol);

        try
        {
            PrintStream out;

            switch (fileChooser.getFileFilter().getDescription())
            {
                case "bin":
                    fileChooser.setSelectedFile(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".bin"));
                    out = new PrintStream(fileChooser.getSelectedFile());

                    for (int i = 0; i < data.getRowCount(); i++)
                    {
                        for (int j = 0; j < data.getColumnCount(); j++)
                            out.print(Double.parseDouble(formatter.format(data.getValueAt(i, j))) + " ");

                        out.println();
                    }

                    break;
                case "csv":
                    fileChooser.setSelectedFile(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".csv"));
                    out = new PrintStream(fileChooser.getSelectedFile());

                    for (int i = 0; i < data.getRowCount(); i++)
                    {
                        for (int j = 0; j < data.getColumnCount(); j++)
                        {
                            out.print(Double.parseDouble(formatter.format(data.getValueAt(i, j))));
                            if (j != 3)
                                out.print(", ");
                        }
                        out.println();
                    }

                    break;
                default://txt
                    fileChooser.setSelectedFile(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".txt"));
                    out = new PrintStream(fileChooser.getSelectedFile());

                      for (int i = 0; i < data.getRowCount(); i++)
                      {

                          for (int j = 0; j < data.getColumnCount(); j++)
                          {
                              switch(j)
                              {
                                  case 0:
                                      out.print("X: " + formatter.format(data.getValueAt(i, j)));
                                      break;
                                  case 1:
                                      out.print("F(X): " + formatter.format(data.getValueAt(i, j)));
                                      break;
                                  case 2:
                                      out.print("(float) F(X): " + formatter.format(data.getValueAt(i, j)));
                                      break;
                                  case 3:
                                      out.print("(float) F(X) - F(X): " + formatter.format(data.getValueAt(i, j)));

                              }

                              out.print(" ");

                          }

                          out.println();
                      }
            }

            out.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }

    public static void main(String[] args)
    {
        double[] Coefficients = new double[args.length];
        for (int i = 0;  i < args.length; i++)
            Coefficients[i] = Double.parseDouble(args[i]);

        Main frame = new Main(Coefficients);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
