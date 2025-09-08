package com.dogiloki.pingmonitoring.infraestructure.ui;

import com.dogiloki.multitaks.Function;
import com.dogiloki.multitaks.network.IP;
import com.dogiloki.multitaks.persistent.ExecutionObserver;
import com.dogiloki.pingmonitoring.application.dto.Ping;
import com.dogiloki.pingmonitoring.application.dto.PingLog;
import com.dogiloki.pingmonitoring.core.services.ColorsTextPane;
import com.dogiloki.pingmonitoring.infraestructure.util.ClosablePanel;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author dogi_
 */

public class PingMonitoringPanel extends ClosablePanel{
    
    private final Ping ping;
    private final ExecutionObserver executor=new ExecutionObserver().useThread();
    private int delay=1;
    private Runnable on_destroy;
    private JPopupMenu menu=new JPopupMenu();
    private JMenuItem item_logs=new JMenuItem("Registros");
    private JMenuItem item_background=new JMenuItem("Color de fondo");
    private JMenuItem item_foreground=new JMenuItem("Color de texto");
    private JCheckBox item_color_errors=new JCheckBox("Color de errores");
    
    public PingMonitoringPanel(Ping ping){
        super(ping.toName());
        this.initComponents();
        this.ping=ping;
        this.item_color_errors.setSelected(ping.color_errors);
        try{
            ColorsTextPane text_pane=new ColorsTextPane();
            text_pane.hasTime();
            this.executor.command("ping -t "+this.ping.ip);
            this.executor.start((line,posi)->{
                text_pane.setBackground(Function.fromColorString(ping.background));
                text_pane.color(Function.fromColorString(ping.foreground));
                if(line.contains("bytes=")){
                    PingLog ping_log=new PingLog();
                    ping_log.ip=ping.ip;
                    ping_log.line=line;
                    ping_log.datetime=text_pane.time();
                    String[] date_parts=ping_log.datetime.split(" ");
                    ping_log.date=date_parts[0];
                    ping_log.time=date_parts[1];
                    String[] parts=Function.getSafeSplit(line.split(":"),1).split(" ");
                    for(String part:parts){
                        if(part.startsWith("bytes=")){
                            ping_log.bytes=part.split("=")[1];
                        }else if(part.startsWith("time=") || part.startsWith("tiempo=")){
                            ping_log.ping_time=part.split("=")[1];
                        }else if(part.startsWith("TTL=")){
                            ping_log.ttl=part.split("=")[1];
                        }
                    }
                    text_pane.insert(Function.set(Function.getSafeSplit(line.split(":"),1),line)+"\n");
                    ping_log.save();
                }else if(!line.contains("byte") && !line.equals("")){
                    PingLog ping_log=new PingLog();
                    ping_log.ip=ping.ip;
                    ping_log.line=line;
                    ping_log.datetime=text_pane.time();
                    String[] date_parts=ping_log.datetime.split(" ");
                    ping_log.date=date_parts[0];
                    ping_log.time=date_parts[1];
                    if(line.contains(":")){
                        text_pane.insert(line+"\n");
                    }else{
                        if(ping.color_errors){
                            text_pane.error(line+"\n");
                        }else{
                            text_pane.insert(line+"\n");
                        }
                    }
                    ping_log.save();
                }
            });
            JPanel panel=new JPanel();
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            JScrollPane scroll=new JScrollPane(text_pane);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            panel.add(scroll);
            this.setBody(panel);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void initComponents(){
        // Agregar opciones al menu
        this.menu.add(this.item_logs);
        this.menu.add(this.item_background);
        this.menu.add(this.item_foreground);
        this.menu.add(this.item_color_errors);
        // Aregar evento de click derecho para mostrar el menu
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                showPopup(evt);
            }
            
            private void showPopup(MouseEvent evt){
                if(SwingUtilities.isRightMouseButton(evt)){
                    menu.show(evt.getComponent(),evt.getX(),evt.getY());
                }
            }
        });
        this.item_logs.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                JOptionPane.showMessageDialog(null,"Proximamente....");
            }
        });
        this.item_background.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                Color color=JColorChooser.showDialog(evt.getComponent(),"Color de fondo",Function.fromColorString(ping.background));
                ping.background=Function.toColorString(color);
                ping.save();
            }
        });
        this.item_foreground.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent evt){
                Color color=JColorChooser.showDialog(evt.getComponent(),"Color de texto",Function.fromColorString(ping.foreground));
                ping.foreground=Function.toColorString(color);
                ping.save();
            }
        });
        this.item_color_errors.addItemListener((evt)->{
            ping.color_errors=evt.getStateChange()==ItemEvent.SELECTED;
            ping.save();
        });
    }
    
    public void setOnDestroy(Runnable action){
        this.on_destroy=action;
    }
    
    public PingMonitoringPanel delay(int value){
        this.delay=value;
        return this;
    }
    
    public int delay(){
        return this.delay;
    }
    
    public IP getIp(){
        return this.ping.toIp();
    }
    
    public ExecutionObserver getExecutor(){
        return this.executor;
    }
    
    public void destroy(){
        this.executor.cancel();
        this.on_destroy.run();
    }
    
    @Override
    public void onClose(){
        this.destroy();
    }
        
}

