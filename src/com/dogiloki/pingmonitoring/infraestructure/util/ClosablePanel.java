package com.dogiloki.pingmonitoring.infraestructure.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author dogi_
 */

public class ClosablePanel extends JPanel{
    
    protected JPanel header;
    protected JPanel body;
    
    public ClosablePanel(String title){
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        
        this.header=new JPanel(new BorderLayout());
        this.header.setBackground(Color.LIGHT_GRAY);
        
        JLabel text_title=new JLabel(title);
        text_title.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        JButton btn_close=new JButton("X");
        btn_close.setMargin(new Insets(2,6,2,6));
        btn_close.addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent evt){
                onClose();
            }
        });
        
        this.header.add(text_title,BorderLayout.WEST);
        this.header.add(btn_close,BorderLayout.EAST);
        
        this.add(this.header,BorderLayout.NORTH);
    }
    
    public void setTitle(String text){
        ((JLabel)this.header.getComponent(0)).setText(text);
    }
    
    public void setBody(JPanel body){
        this.body=body;
        this.add(body,BorderLayout.CENTER);
        this.updateUI();
    }
    
    public void onClose(){
        
    }
    
}

