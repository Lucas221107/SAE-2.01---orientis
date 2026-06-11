package jeu.ihm ; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameAccueil extends JFrame implements ActionListener
{
    private CardLayout cardLayout;
    private JPanel     panel    ;

    private JButton btnSolo      ;
    private JButton btnMulti     ;
    private JButton btnMultiLocal;
    private JButton btnRegles    ;

    public FrameAccueil()
    {
        this.setTitle("Orientis");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.cardLayout = new CardLayout();
        this.panel      = new JPanel(this.cardLayout);

        // Création des panels
        JPanel panelAccueil    = creerPanelAccueil();
        JPanel panelSolo       = new JPanel(); // à remplir plus tard
        JPanel panelMulti      = new JPanel(); // à remplir plus tard
        JPanel panelMultiLocal = new JPanel(); // à remplir plus tard
        JPanel panelRegles     = new JPanel(); // à remplir plus tard

        // Ajout des panels au CardLayout
        this.panel.add(panelAccueil   , "accueil"   );
        this.panel.add(panelSolo      , "solo"      );
        this.panel.add(panelMulti     , "multi"     );
        this.panel.add(panelMultiLocal, "multilocal");
        this.panel.add(panelRegles    , "regles"    );

        this.cardLayout.show(this.panel, "accueil");
        this.add(this.panel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel creerPanelAccueil()
    {
        JPanel p = new JPanel(new GridLayout(6, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));

        JLabel lblTitre = new JLabel("Orientis", SwingConstants.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 40));

        this.btnSolo       = new JButton("Jouer Solo"               );
        this.btnMulti      = new JButton("Jouer Multi"              );
        this.btnMultiLocal = new JButton("Jouer Multi en Local"     );
        this.btnRegles     = new JButton("Règles"                   );

        this.btnSolo      .addActionListener(this);
        this.btnMulti     .addActionListener(this);
        this.btnMultiLocal.addActionListener(this);
        this.btnRegles    .addActionListener(this);

        p.add(lblTitre          );
        p.add(this.btnSolo      );
        p.add(this.btnMulti     );
        p.add(this.btnMultiLocal);
        p.add(this.btnRegles    );

        return p;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnSolo      ) this.cardLayout.show(this.panel, "solo"      );
        if (e.getSource() == this.btnMulti     ) this.cardLayout.show(this.panel, "multi"     );
        if (e.getSource() == this.btnMultiLocal) this.cardLayout.show(this.panel, "multilocal");
        if (e.getSource() == this.btnRegles    ) this.cardLayout.show(this.panel, "regles"    );
    }

    public static void main(String[] args)
    {
        new FrameAccueil();
    }
}