package com.changlan.other.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.changlan.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="devicedata")
public class DeviceData implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;	
	private Integer channelSettings_id; //一个设备 其中的一个通道主键id
	private Float amplitude; //幅值
	private Integer frequency; //频次
	private Integer energy; //总能量
	private Integer alarm_amplitude; //幅值报警
	private Integer alarm_amplitude_frequency; //报警幅值频次  
	private Date createtime; //创建时间
	private Float phase; // 相位
	
	@Transient	
	private Float SuperimposedPhase; //叠加相位
	@Transient	
	private Float quotient ; //商数
	@Transient	
	private Float Remainder;//余数
	
	public void setCaculate() {
		BigDecimal fuzhi = new BigDecimal(amplitude); 
		BigDecimal diejiaXiShu = new BigDecimal(1000); 
		BigDecimal xiangwei = new BigDecimal(phase); 
		
		BigDecimal shang = xiangwei.divideToIntegralValue(diejiaXiShu);
		System.out.println("商值"+shang);
		this.quotient = shang.floatValue();
		
		
		BigDecimal yushu = xiangwei.divideAndRemainder(diejiaXiShu)[1];
		System.out.println("余数"+yushu);
		this.Remainder = yushu.floatValue();
		
		BigDecimal diejiaXiangWei = yushu.divide(diejiaXiShu).multiply(new BigDecimal(360));
		System.out.println("叠加相位"+diejiaXiangWei.floatValue());
		this.SuperimposedPhase = diejiaXiangWei.floatValue();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getChannelSettings_id() {
		return channelSettings_id;
	}
	public void setChannelSettings_id(Integer channelSettings_id) {
		this.channelSettings_id = channelSettings_id;
	}
	public Float getAmplitude() {
		return amplitude;
	}
	public void setAmplitude(Float amplitude) {
		this.amplitude = amplitude;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Integer getEnergy() {
		return energy;
	}
	public void setEnergy(Integer energy) {
		this.energy = energy;
	}
	public Integer getAlarm_amplitude() {
		return alarm_amplitude;
	}
	public void setAlarm_amplitude(Integer alarm_amplitude) {
		this.alarm_amplitude = alarm_amplitude;
	}
	public Integer getAlarm_amplitude_frequency() {
		return alarm_amplitude_frequency;
	}
	public void setAlarm_amplitude_frequency(Integer alarm_amplitude_frequency) {
		this.alarm_amplitude_frequency = alarm_amplitude_frequency;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Float getSuperimposedPhase() {
		return SuperimposedPhase;
	}
	public void setSuperimposedPhase(Float superimposedPhase) {
		SuperimposedPhase = superimposedPhase;
	}
	public Float getQuotient() {
		return quotient;
	}
	public void setQuotient(Float quotient) {
		this.quotient = quotient;
	}
	public Float getRemainder() {
		return Remainder;
	}
	public void setRemainder(Float remainder) {
		Remainder = remainder;
	}

	public Float getPhase() {
		return phase;
	}

	public void setPhase(Float phase) {
		this.phase = phase;
	}
	
	
	
}
