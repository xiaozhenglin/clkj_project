/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.0
 */
package com.changlan.common.entity;
import javax.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.changlan.common.util.StringUtil;

/**
 * TBL_COMMAND_PROTOCOL
 * 
 * @author bianj
 * @version 1.0.0 2019-02-23
 */
@Entity
@Table(name = "TBL_COMMAND_PROTOCOL")
public class TblCommandProtocolEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8278186165296210019L;

    /** protocolId */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROTOCOL_ID", unique = true )
    private Integer protocolId;

    /** dataType */
    @Column(name = "DATA_TYPE"   )
    private String dataType;

    /** beginByte */
    @Column(name = "BEGIN_BYTE"   )
    private Integer beginByte;

    /** endByte */
    @Column(name = "DATA_BYTE"   )
    private Integer dataByte;  // 4表示一个数据占4个位

    /** binaryValue */
    @Column(name = "BINARY_VALUE"    )
    private Integer binaryValue;

    /** dataExplain */
    @Column(name = "DATA_EXPLAIN"  )
    private String dataExplain;

    /** remark */
    @Column(name = "REMARK"   )
    private String remark;

    /** neddCanculate */
    @Column(name = "NEDD_CANCULATE"    )
    private Integer neddCanculate;

    /** canculateRule */
    @Column(name = "CANCULATE_RULE"   )
    private String canculateRule;

    /** COMMANDCatagoryId */
    @Column(name = "COMMAND_CATAGORY_ID"  )
    private Integer commandCatagoryId;

    /** canculateResultExplain */
    @Column(name = "CANCULATE_RESULT_EXPLAIN"  )
    private String canculateResultExplain;
    
    @Column(name = "POINT_ID" )
    private Integer pointId;
    
    @Column(name = "INDICATOR_ID"  )
    private Integer indicatorId;
    
    @Column(name = "ADDRESS_CODE"  )
    private Integer addressCode;
    
    
	public Integer getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(Integer protocolId) {
		this.protocolId = protocolId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getBeginByte() {
		return beginByte;
	}

	public void setBeginByte(Integer beginByte) {
		this.beginByte = beginByte;
	}

	public Integer getDataByte() {
		return dataByte;
	}

	public void setDataByte(Integer dataByte) {
		this.dataByte = dataByte;
	}

	public Integer getBinaryValue() {
		return binaryValue;
	}

	public void setBinaryValue(Integer binaryValue) {
		this.binaryValue = binaryValue;
	}

	public String getDataExplain() {
		return dataExplain;
	}

	public void setDataExplain(String dataExplain) {
		this.dataExplain = dataExplain;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getNeddCanculate() {
		return neddCanculate;
	}

	public void setNeddCanculate(Integer neddCanculate) {
		this.neddCanculate = neddCanculate;
	}

	public String getCanculateRule() {
		return canculateRule;
	}

	public void setCanculateRule(String canculateRule) {
		this.canculateRule = canculateRule;
	}

	public Integer getCommandCatagoryId() {
		return commandCatagoryId;
	}

	public void setCommandCatagoryId(Integer commandCatagoryId) {
		this.commandCatagoryId = commandCatagoryId;
	}

	public String getCanculateResultExplain() {
		return canculateResultExplain;
	}

	public void setCanculateResultExplain(String canculateResultExplain) {
		this.canculateResultExplain = canculateResultExplain;
	}

//	public List<String> getCodePositions() {
//		List<String> list= new ArrayList<String>();
//		//7-1,11-2
//		List<String> stringToList = StringUtil.stringToList(this.channelIndicator, ","); 
//		for(String str : stringToList) {
////			7-1
//			String[] split = str.split("-"); 
//			list.add(split[0]);
//		}
//		return list;
//	}

	//第二种线圈状态时使用
//	public List<Integer> getIndicators() {
//		List<Integer> list= new ArrayList<Integer>();
//		//7-1,11-2
//		List<String> stringToList = StringUtil.stringToList(this.channelIndicator, ","); 
//		for(String str : stringToList) {
////			7-1
//			String[] split = str.split("-"); 
//			list.add(Integer.parseInt(split[1]));
//		}
//		return list;
//	}

	public Integer getIndicatorId() {
		return indicatorId;
	}

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}

	public void setIndicatorId(Integer indicatorId) {
		this.indicatorId = indicatorId;
	}

	public Integer getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(Integer addressCode) {
		this.addressCode = addressCode;
	}

  

}