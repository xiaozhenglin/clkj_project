package com.changlan.point.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class LineMonitorCountEntity {
	@Id
	private Integer ROW_ID;
	
	private Integer line_total = 0;
	private Integer point_total = 0;
	private Integer operation_total = 0;		
	
	@Transient
	private Integer cpu_total = 0;
	@Transient	
	private Long totalMemory = 0L ;
	@Transient
	private Long maxMemory= 0L ;
	@Transient	
	private Long freeMemory = 0L;


	public void setSystemVar() {
		
		Runtime runtime = Runtime.getRuntime();
        
		this.cpu_total =  runtime.availableProcessors();
		this.freeMemory = runtime.freeMemory()/1024/1024;   //单位:MB
		this.maxMemory = runtime.maxMemory()/1024/1024;     //单位:MB
		this.totalMemory = runtime.totalMemory()/1024/1024;  //单位 MB

	}
	
			
	public Integer getOperation_total() {
		return operation_total;
	}

	public void setOperation_total(Integer operation_total) {
		this.operation_total = operation_total;
	}




	public Integer getROW_ID() {
		return ROW_ID;
	}


	public void setROW_ID(Integer rOW_ID) {
		ROW_ID = rOW_ID;
	}


	public Integer getLine_total() {
		return line_total;
	}


	public void setLine_total(Integer line_total) {
		this.line_total = line_total;
	}


	public Integer getPoint_total() {
		return point_total;
	}


	public void setPoint_total(Integer point_total) {
		this.point_total = point_total;
	}


	public Integer getCpu_total() {
		return cpu_total;
	}


	public void setCpu_total(Integer cpu_total) {
		this.cpu_total = cpu_total;
	}


	public Long getTotalMemory() {
		return totalMemory;
	}


	public void setTotalMemory(Long totalMemory) {
		this.totalMemory = totalMemory;
	}


	public Long getMaxMemory() {
		return maxMemory;
	}


	public void setMaxMemory(Long maxMemory) {
		this.maxMemory = maxMemory;
	}


	public Long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(Long freeMemory) {
		this.freeMemory = freeMemory;
	}
				
}
