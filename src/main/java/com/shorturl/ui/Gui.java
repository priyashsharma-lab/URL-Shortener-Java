package com.shorturl.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.shorturl.service.DecodeUrl;
import com.shorturl.service.EncodeUrl;

public class Gui 
{
    private final JFrame mainFrame;
    public Gui()
    {
        mainFrame=new JFrame("shortURL");
    } 
    public void loadGui()
    {
        mainFrame.setSize(500,500);
        mainFrame.setLayout(new GridLayout(2,1));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel shortPanel=new JPanel(new GridLayout(2,2));
        JPanel decodePanel=new JPanel(new GridLayout(2,2));

        JLabel shortLabel=new JLabel("Enter URL to shorten: ");
        JLabel decodeLabel=new JLabel("Enter shortURL to decode: ");
        JLabel decodeOutputLabel=new JLabel();
        JLabel shortOutputLabel=new JLabel();

        JTextField shortInput=new JTextField();
        JTextField decodeInput=new JTextField();

        JButton shortButton=new JButton("Encode");
        JButton decodeButton=new JButton("Decode");

        shortPanel.add(shortLabel);
        shortPanel.add(shortInput);
        shortPanel.add(shortButton);
        shortPanel.add(shortOutputLabel);

        decodePanel.add(decodeLabel);
        decodePanel.add(decodeInput);
        decodePanel.add(decodeButton);
        decodePanel.add(decodeOutputLabel);

        mainFrame.add(shortPanel);
        mainFrame.add(decodePanel);

        shortButton.addActionListener(e->{
            String originalUrl=shortInput.getText().trim();
            EncodeUrl encUrl=new EncodeUrl(originalUrl);
            String encodedUrl=encUrl.generateEncodedUrl();
            shortOutputLabel.setText(encodedUrl);
        });

        decodeButton.addActionListener(e->{
            String encodedUrl=decodeInput.getText().trim();
            DecodeUrl decUrl=new DecodeUrl(encodedUrl);
            String originalUrl=decUrl.getOriginalUrl();
            decodeOutputLabel.setText(originalUrl);
        });
        mainFrame.setVisible(true);
    }   
}
