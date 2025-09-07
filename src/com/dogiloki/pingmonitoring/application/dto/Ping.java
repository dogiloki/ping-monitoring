package com.dogiloki.pingmonitoring.application.dto;

import com.dogiloki.multitaks.Function;
import com.dogiloki.multitaks.database.ModelDB;
import com.dogiloki.multitaks.database.annotations.Collect;
import com.dogiloki.multitaks.dataformat.annotations.FieldFormat;
import com.dogiloki.multitaks.dataformat.enums.TypeFieldFormat;
import com.dogiloki.multitaks.network.IP;
import com.google.gson.annotations.Expose;
import java.awt.Color;

/**
 *
 * @author dogi_
 */

@Collect(src="ping")
public class Ping extends ModelDB{
    
    @Expose @FieldFormat(label="Nombre",type=TypeFieldFormat.TEXT)
    public String name;
    @Expose @FieldFormat(label="Dirección IP",type=TypeFieldFormat.TEXT)
    public String ip;
    @Expose
    public String background=Function.toColorString(Color.BLACK);
    @Expose
    public String foreground=Function.toColorString(Color.WHITE);
    @Expose
    public boolean color_errors=true;
    
    public Ping(){
        
    }
    
    public IP toIp(){
        return new IP(this.ip);
    }
    
    public String toName(){
        return this.name+" ("+this.ip+")";
    }
    
}
