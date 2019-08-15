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

import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;
import com.changlan.common.util.SpringUtil;
import com.changlan.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="DEVICEDATA")
public class DeviceData implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;	
	@Column(name="CHANNELSETTINGS_ID")
	private Integer channelSettings_id; //一个设备 其中的一个通道主键id
	@Column(name="AMPLITUDE")
	private Float amplitude; //幅值
	@Column(name="FREQUENCY")
	private Integer frequency; //频次
	@Column(name="ENERGY")
	private Integer energy; //总能量
	@Column(name="ALARM_AMPLITUDE")
	private Integer alarm_amplitude; //幅值报警
	@Column(name="ALARM_AMPLITUDE_FREQUENCY")
	private Integer alarm_amplitude_frequency; //报警幅值频次  
	@Column(name="CREATETIME")
	private Date createtime; //创建时间
	@Column(name="PHASE")
	private Float phase; // 相位
	@Column(name="POINT_ID")
	private Integer pointId;
	@Column(name="PHASE_NO")
	private String phase_no;
	@Column(name="RECORD_ID")
	private String record_id;
	@Transient	
	private String pointName;//余数
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
	
	public void setPointName() {
		ICrudService iCrudService = SpringUtil.getICrudService(); 
		if(this.pointId!=null) {
			Object object = iCrudService.get(this.pointId, TblPointsEntity.class, true);
			if(object!=null) {
				TblPointsEntity entity = (TblPointsEntity)object;
				this.pointName= entity.getPointName();
			}
		}
	}
	
			
	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getPhase_no() {
		return phase_no;
	}

	public void setPhase_no(String phase_no) {
		this.phase_no = phase_no;
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

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	
	
}
