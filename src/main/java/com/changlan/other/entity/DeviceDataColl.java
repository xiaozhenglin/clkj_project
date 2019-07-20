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
@Table(name="devicedatacoll")
public class DeviceDataColl implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "POINT_DATA_ID", unique = true )
    private Integer pointDataId;	
	private String record_id; //一个设备 其中的一个通道主键id
		

			
	private String phase_no;  //A,B,C相位
	
	@Column(name = "RECORD_TIME", nullable = true)
	private Date recordTime;
	
	@Column(name="POINT_ID")
	private Integer pointId;
	
	@Column(name="COMMAND_RECORD_ID")
	private Integer commond_record_id; //
	
	 /** indicatorId */
    @Column(name = "INDICATOR_ID"    )
    private Integer indicatorId;

    /** categroryId */
    @Column(name = "CATEGORY_ID"    )
    private Integer categroryId; //指标类别id  
    
    /** value */
    @Column(name = "VALUE"   )
    private String value;
    
    /** pointName */
    @Column(name = "POINT_NAME"    )
    private String pointName;
    
    /** pointCatagoryId */
    @Column(name = "POINT_CATAGORY_ID"    )
    private Integer pointCatagoryId;
    
    @Column(name = "PROTOCOL_ID"    )
    private Integer protocolId;

	

	public Integer getPointDataId() {
		return pointDataId;
	}

	public void setPointDataId(Integer pointDataId) {
		this.pointDataId = pointDataId;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getPhase_no() {
		return phase_no;
	}

	public void setPhase_no(String phase_no) {
		this.phase_no = phase_no;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public Integer getCommond_record_id() {
		return commond_record_id;
	}

	public void setCommond_record_id(Integer commond_record_id) {
		this.commond_record_id = commond_record_id;
	}

	public Integer getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}

	public Integer getCategroryId() {
		return categroryId;
	}

	public void setCategroryId(Integer categroryId) {
		this.categroryId = categroryId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public Integer getPointCatagoryId() {
		return pointCatagoryId;
	}

	public void setPointCatagoryId(Integer pointCatagoryId) {
		this.pointCatagoryId = pointCatagoryId;
	}

	public Integer getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Integer protocolId) {
		this.protocolId = protocolId;
	}

	
	
	
    
    
}
