package com.shorturl.ui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.shorturl.service.DecodeUrl;
import com.shorturl.service.DeleteUrl;
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
        mainFrame.setLayout(new GridLayout(1,1));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(2,1));

        CardLayout cardLayout=new CardLayout();
        JPanel mainPanel=new JPanel(cardLayout);
        
        JPanel shortPanel=new JPanel(new GridLayout(2,3));
        JPanel decodePanel=new JPanel(new GridLayout(2,3));
        JPanel deletePanel=new JPanel(new GridLayout(2,3));
        JPanel homePanel=new JPanel();
        JPanel navigationBarPanel=new JPanel(new FlowLayout(1,10,10));

        JLabel shortLabel=new JLabel("Enter URL to shorten: ");
        JLabel decodeLabel=new JLabel("Enter shortURL to decode: ");
        JLabel decodeOutputLabel=new JLabel();
        JLabel shortOutputLabel=new JLabel();
        JLabel deleteLabel=new JLabel("Enter original url to delete: ");
        JLabel deleteOutputLabel=new JLabel();

        JTextField shortInput=new JTextField();
        JTextField decodeInput=new JTextField();
        JTextField deleteInput=new JTextField();

        JButton shortButton=new JButton("Encode");
        JButton decodeButton=new JButton("Decode");
        JButton deleteButton=new JButton("Delete");
        JButton homePageButton=new JButton("Home");
        JButton deletePageButton=new JButton("Delete");
        JButton encodePageButton=new JButton("Encode");
        JButton decodePageButton=new JButton("Decode");

        String expireListOptions[]={"Select Expire Time","Never","1-day","3-days","7-days"};
        JList<String> expireList=new JList<>(expireListOptions);

        shortPanel.add(shortLabel);
        shortPanel.add(shortInput);
        shortPanel.add(expireList);
        shortPanel.add(shortButton);
        shortPanel.add(shortOutputLabel);

        decodePanel.add(decodeLabel);
        decodePanel.add(decodeInput);
        decodePanel.add(decodeButton);
        decodePanel.add(decodeOutputLabel);

        deletePanel.add(deleteLabel);
        deletePanel.add(deleteInput);
        deletePanel.add(deleteButton);
        deletePanel.add(deleteOutputLabel);

        navigationBarPanel.add(homePageButton);
        navigationBarPanel.add(encodePageButton);
        navigationBarPanel.add(decodePageButton);
        navigationBarPanel.add(deletePageButton);

        mainPanel.add(homePanel,"HOME");
        mainPanel.add(shortPanel,"ENCODE");
        mainPanel.add(decodePanel,"DECODE");
        mainPanel.add(deletePanel,"DELETE");

        mainFrame.add(navigationBarPanel);
        mainFrame.add(mainPanel);

        shortButton.addActionListener(e->{
            String originalUrl=shortInput.getText().trim();
            String expireTime=expireList.getSelectedValue();
            if (expireTime==null || expireTime.equals("Select Expire Time"))
            {
                expireTime=expireListOptions[1];
            }
            EncodeUrl encUrl=new EncodeUrl(originalUrl,expireTime);
            String encodedUrl=encUrl.generateEncodedUrl();
            shortOutputLabel.setText(encodedUrl);
        });
        decodeButton.addActionListener(e->{
            String encodedUrl=decodeInput.getText().trim();
            DecodeUrl decUrl=new DecodeUrl(encodedUrl);
            String originalUrl=decUrl.getOriginalUrl();
            decodeOutputLabel.setText(originalUrl);
        });
        deleteButton.addActionListener(e->{
            String originalUrl=deleteInput.getText().trim();
            DeleteUrl delUrl=new DeleteUrl(originalUrl);
            String deletionMsg=delUrl.deleteUrl();
            deleteOutputLabel.setText(deletionMsg);
        });

        //Copy Feature code
        JButton shortCopyButton=new JButton("Copy");
        JButton decodeCopyButton=new JButton("Copy");
        shortPanel.add(shortCopyButton);
        decodePanel.add(decodeCopyButton);

        shortCopyButton.addActionListener(e->{
            String encUrl=shortOutputLabel.getText();
            StringSelection selection=new StringSelection(encUrl);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(null,"URL Copied Successfully");
        });
        decodeCopyButton.addActionListener(e->{
            String oriUrl=decodeOutputLabel.getText();
            StringSelection selection=new StringSelection(oriUrl);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            JOptionPane.showMessageDialog(null,"URL Copied Successfully");
        });

        //Navigation Buttons Events

        homePageButton.addActionListener(e->{
            cardLayout.show(mainPanel, "HOME");
        });
        encodePageButton.addActionListener(e->{
            cardLayout.show(mainPanel, "ENCODE");
        });
        decodePageButton.addActionListener(e->{
            cardLayout.show(mainPanel, "DECODE");
        });
        deletePageButton.addActionListener(e->{
            cardLayout.show(mainPanel, "DELETE");
        });
        mainFrame.setVisible(true);
    }   
}
