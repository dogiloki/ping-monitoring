package com.dogiloki.pingmonitoring.application.dto;

import com.dogiloki.multitaks.database.ModelDB;
import com.dogiloki.multitaks.database.annotations.Collect;
import com.google.gson.annotations.Expose;

/**
 *
 * @author dogi_
 */

@Collect(src="ping_log")
public class PingLog extends ModelDB{
    
    @Expose
    public String ip;
    @Expose
    public String line;
    @Expose
    public String datetime;
    @Expose
    public String date;
    @Expose
    public String time;
    @Expose
    public String bytes;
    @Expose
    public String ping_time;
    @Expose
    public String ttl;
    
    public PingLog(){
        
    }
    
}
