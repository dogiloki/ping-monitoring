package com.dogiloki.pingmonitoring.infraestructure.ui;

import com.dogiloki.multitaks.database.record.RecordList;
import com.dogiloki.multitaks.datastructure.tree.TreeNodeWrapper;
import com.dogiloki.multitaks.persistent.ExecutionObserver;
import com.dogiloki.pingmonitoring.application.dto.Ping;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author dogi_
 */

public final class MainForm extends javax.swing.JFrame{

    private final Map<String,PingMonitoringPanel> list_ping_monitoring=new HashMap<>();
    private Ping selected_ping=null;
    
    public MainForm(){
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MainForm.MAXIMIZED_BOTH); 
        this.loadPings();
        this.pings_tree.setPreferredSize(new Dimension(300,this.pings_tree.getHeight()));
    }
    
    public void loadPings(){
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("Direccionamientos");
        DefaultTreeModel model=new DefaultTreeModel(root);
        this.pings_tree.setModel(model);
        this.selected_ping=null;
        RecordList<Ping> pings=new Ping().all().orderByAsc("name");
        Ping ping;
        while((ping=pings.next())!=null){
            DefaultMutableTreeNode node=new TreeNodeWrapper(ping,ping.toName());
            root.add(node);
        }
        model.reload();
    }
    
    public void recalculateDesktop(){
        int total=this.list_ping_monitoring.size();
        int rows=(int)Math.ceil(Math.sqrt(total));
        int cols=rows;
        GridLayout gl=((GridLayout)this.ping_monitoring_desktop.getLayout());
        if(rows<=0){
            rows=1;
        }
        if(cols<=0){
            cols=1;
        }
        gl.setRows(rows);
        gl.setColumns(cols);
        this.ping_monitoring_desktop.updateUI();
    }
    
    public void removePingMonitoring(PingMonitoringPanel ping_monitoring){
        this.list_ping_monitoring.remove(ping_monitoring.getIp().getAddress());
        this.ping_monitoring_desktop.remove(ping_monitoring);
        this.recalculateDesktop();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pings_popup = new javax.swing.JPopupMenu();
        start_ping_minitoring_btn = new javax.swing.JMenuItem();
        delete_ping_monitoring_btn = new javax.swing.JMenuItem();
        jSplitPane2 = new javax.swing.JSplitPane();
        ping_monitoring_desktop = new javax.swing.JDesktopPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        pings_tree = new javax.swing.JTree();
        new_ping_btn = new javax.swing.JButton();
        start_ping_monitoring_btn = new javax.swing.JButton();

        start_ping_minitoring_btn.setText("Iniciar Ping");
        start_ping_minitoring_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_ping_minitoring_btnActionPerformed(evt);
            }
        });
        pings_popup.add(start_ping_minitoring_btn);

        delete_ping_monitoring_btn.setText("Eliminar");
        delete_ping_monitoring_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_ping_monitoring_btnActionPerformed(evt);
            }
        });
        pings_popup.add(delete_ping_monitoring_btn);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ping_monitoring_desktop.setLayout(new java.awt.GridLayout(1, 0));
        jSplitPane2.setRightComponent(ping_monitoring_desktop);

        pings_tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pings_treeMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(pings_tree);

        jSplitPane2.setLeftComponent(jScrollPane1);

        new_ping_btn.setText("Nuevo direccionamiento");
        new_ping_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_ping_btnActionPerformed(evt);
            }
        });

        start_ping_monitoring_btn.setText("Iniciar todo los pings");
        start_ping_monitoring_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_ping_monitoring_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(new_ping_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                        .addComponent(start_ping_monitoring_btn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(new_ping_btn)
                    .addComponent(start_ping_monitoring_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void new_ping_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_ping_btnActionPerformed
        PingDialog ping_dialog=new PingDialog(this,true,null);
        ping_dialog.setTitle("Nuevo direccionamimento");
        ping_dialog.setVisible(true);
        this.loadPings();
    }//GEN-LAST:event_new_ping_btnActionPerformed

    private void start_ping_monitoring_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_ping_monitoring_btnActionPerformed
        RecordList<Ping> pings=new Ping().all().orderByAsc("name");
        Ping ping;
        while((ping=pings.next())!=null){
            if(this.list_ping_monitoring.containsKey(ping.ip)) continue;
            PingMonitoringPanel panel=new PingMonitoringPanel(ping);
            panel.setOnDestroy(()->{
                removePingMonitoring(panel);
            });
            this.list_ping_monitoring.put(ping.ip,panel);
            this.ping_monitoring_desktop.add(panel);
        }
        this.recalculateDesktop();
    }//GEN-LAST:event_start_ping_monitoring_btnActionPerformed

    private void pings_treeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pings_treeMousePressed
        TreePath path=this.pings_tree.getPathForLocation(evt.getX(),evt.getY());
        if(path==null) return;
        DefaultMutableTreeNode node=(DefaultMutableTreeNode)path.getLastPathComponent();
        DefaultMutableTreeNode parent=(DefaultMutableTreeNode)node.getParent();
        this.pings_tree.setSelectionPath(path);
        if(SwingUtilities.isRightMouseButton(evt)){
            if(parent!=null && node.isLeaf()){
                this.pings_popup.show(this.pings_tree,evt.getX(),evt.getY());
                this.selected_ping=(Ping)node.getUserObject();
            }
        }
    }//GEN-LAST:event_pings_treeMousePressed

    private void start_ping_minitoring_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_ping_minitoring_btnActionPerformed
        if(this.selected_ping==null || this.list_ping_monitoring.containsKey(this.selected_ping.ip)) return;
        PingMonitoringPanel panel=new PingMonitoringPanel(this.selected_ping);
        panel.setOnDestroy(()->{
            removePingMonitoring(panel);
        });
        this.list_ping_monitoring.put(this.selected_ping.ip,panel);
        this.ping_monitoring_desktop.add(panel);
        this.recalculateDesktop();
    }//GEN-LAST:event_start_ping_minitoring_btnActionPerformed

    private void delete_ping_monitoring_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_ping_monitoring_btnActionPerformed
        if(this.selected_ping==null) return;
        this.selected_ping.delete();
        this.loadPings();
    }//GEN-LAST:event_delete_ping_monitoring_btnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ExecutionObserver.shutdown();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem delete_ping_monitoring_btn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JButton new_ping_btn;
    private javax.swing.JDesktopPane ping_monitoring_desktop;
    private javax.swing.JPopupMenu pings_popup;
    private javax.swing.JTree pings_tree;
    private javax.swing.JMenuItem start_ping_minitoring_btn;
    private javax.swing.JButton start_ping_monitoring_btn;
    // End of variables declaration//GEN-END:variables
}
