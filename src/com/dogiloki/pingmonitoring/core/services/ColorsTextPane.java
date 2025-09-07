package com.dogiloki.pingmonitoring.core.services;

import com.dogiloki.multitaks.Function;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author dogi_
 */

public class ColorsTextPane extends JTextPane{
    
    public StyleContext context;
    public StyledDocument doc;
    public Style style;
    private Integer line=null;
    private Color color=null;
    private Integer default_line=0;
    private Color default_color=Color.WHITE;
    private int max_lines=500;
    
    private boolean has_time=false;
    
    public ColorsTextPane(){
        super();
        this.context=new StyleContext();
        this.doc=this.getStyledDocument();
        this.style=this.context.addStyle("colored",null);
        this.setEditable(false);
        this.setBackground(Color.BLACK);
    }
    
    public ColorsTextPane defaultLine(Integer line){
        this.default_line=line;
        return this;
    }
    
    public ColorsTextPane defaultColor(Color color){
        this.default_color=color;
        return this;
    }
    
    public ColorsTextPane line(Integer line){
        this.line=line;
        return this;
    }
    
    public ColorsTextPane color(Color color){
        this.color=color;
        return this;
    }
    
    public Integer getLine(){
        return Function.set(this.line,this.default_line);
    }
    
    public Color getColor(){
        return Function.set(this.color,this.default_color);
    }
    
    public boolean info(String msg){
        this.color(Color.WHITE);
        return this.insert(msg);
    }
    
    public boolean success(String msg){
        this.color(Color.GREEN);
        return this.insert(msg);
    }
    
    public boolean error(String msg){
        this.color(Color.RED);
        return this.insert(msg);
    }
    
    public boolean warning(String msg){
        this.color(Color.YELLOW);
        return this.insert(msg);
    }
    
    public boolean insert(String msg){
        return this._insert(msg);
    }
    
    public boolean insert(String msg, Color color){
        this.color(color);
        return this._insert(msg);
    }
    
    public boolean insertLine(Integer line, String msg){
        this.line(line);
        return this._insert(msg);
    }
    
    public boolean insertLine(Integer line, String msg, Color color){
        this.line(line);
        this.color(color);
        return this._insert(msg);
    }
    
    public boolean hasTime(boolean arg){
        return this._hasTime(arg);
    }
    
    public boolean hasTime(){
        return this._hasTime(true);
    }
    
    public boolean _hasTime(boolean arg){
        return this.has_time=arg;
    }
    
    public String time(){
        return Function.getDateTime();
    }
    
    private boolean _insert(String msg){
        if(this.hasTime()){
            msg="["+this.time()+"] "+msg;
        }
        StyleConstants.setForeground(this.style,this.getColor());
        try{
            this.doc.insertString(this.doc.getLength(),msg+"\n",this.style);
            this.trimLines();
            this.setCaretPosition(this.doc.getLength());
            this.line(null);
            this.color(null);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    private void trimLines() throws BadLocationException{
        int lines=this.getDocument().getDefaultRootElement().getElementCount();
        if(lines>this.max_lines){
            Element first_line=this.doc.getDefaultRootElement().getElement(0);
            this.doc.remove(0,first_line.getEndOffset());
        }
    }
    
}

