package jeu.ihm;
import javax.swing.*;
import java.awt.event.*;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class PanelAccueil extends JPanel implements ActionListener
{
    private JButton         btnRegle ;
    private JButton         btnSolo  ;
    private JButton         btnDuo   ;
    private JButton         btnMulti ;

    private Image           imgFond  ;
    private FramePrincipale frame    ;

    public PanelAccueil(FramePrincipale frame)
    {
        this.frame = frame;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.btnSolo  = new JButton("Jouer en Solo" );
        this.btnDuo   = new JButton("Jouer en Duo"  );
        this.btnMulti = new JButton("Jouer en Multi" );
        this.btnRegle = new JButton("Règle"          );

        this.imgFond = getToolkit().getImage("../jeu/ihm/images/ecran_d_acceuil.png");

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtn.setOpaque(false);

        panelBtn.add(this.btnSolo );
        panelBtn.add(this.btnDuo  );
        panelBtn.add(this.btnMulti);
        panelBtn.add(this.btnRegle);

        this.add(panelBtn, gbc);

        this.btnSolo .addActionListener(this);
        this.btnDuo  .addActionListener(this);
        this.btnMulti.addActionListener(this);
        this.btnRegle.addActionListener(this);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(this.imgFond, 0, 0, getWidth(), getHeight(), this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnSolo )
            this.frame.switchPanel("chargement");    // solo passe par le chargement

        if (e.getSource() == this.btnDuo  )
            this.frame.switchPanel("chargementDuo"); // duo passe par le chargement duo

        if (e.getSource() == this.btnMulti)
            this.frame.switchPanel("multi");

        if (e.getSource() == this.btnRegle)
            this.frame.switchPanel("regle");
    }
}