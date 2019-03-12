package com.changlan.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.changlan.command.pojo.CommandProtolDetail;
import com.changlan.command.pojo.CommandRecordDetail;
import com.changlan.common.action.BaseController;
import com.changlan.common.entity.TblCommandProtocolEntity;
import com.changlan.common.entity.TblIndicatorValueEntity;
import com.changlan.common.entity.TblPoinDataEntity;
import com.changlan.common.entity.TblPointsEntity;
import com.changlan.common.service.ICrudService;

public class AnalysisDataUtil {
	
    private static Logger log = LoggerFactory.getLogger(AnalysisDataUtil.class);
	
    public static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");    
	
    
	/**
	 * @param 需要解析的指令内容
	 * @param 解析协议
	 * @return 解析得到一个个按顺序排列的指标的值
	 * 
	 */
	public static List<BigDecimal> getData(String backContent,TblCommandProtocolEntity protocol) {
		//根据类别进行处理
		Integer commandCatagoryId = protocol.getCommandCatagoryId(); 
		switch (commandCatagoryId) {
		case 1:
			return firstAnalysis(backContent,protocol);
		case 2:
			return secondAnalysis(backContent,protocol);
		case 3:
			//第三种类别 为发送指令不需要解析
			break;
		case 4:
			return secondAnalysis(backContent,protocol);
		default:
			break;
		}
		return null;
	}

	private static List<BigDecimal> firstAnalysis(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		String canculateRule = protocol.getCanculateRule(); 	//计算规则 
		int binaryValue = protocol.getBinaryValue(); //转为 多少进制
		Integer beginByte = protocol.getBeginByte(); //开始位置
		//解析数据
		//下标值为位置数减去1   6
		Integer begin = beginByte-1;
		if(begin<backContent.length()) {
			//0769  6-10
			String channelValue = backContent.substring(begin, begin+4); 
			//1897
			String decimalConvert = StringUtil.decimalConvert(channelValue, 16, binaryValue, null); 
			//1897
			int value = new BigDecimal(decimalConvert).intValue(); 
			//9.26
			Object ca = canculate(value,canculateRule); 
			BigDecimal canculate = new BigDecimal(ca.toString()); 
			list.add(canculate);
		}
		return list;
	}
	
	private static List<BigDecimal> secondAnalysis(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		Integer beginByte = protocol.getBeginByte(); //开始位置
		int binaryValue = protocol.getBinaryValue(); //转为 多少进制 
		Integer dataByte = protocol.getDataByte(); //转为进制后一个数据的位置  5
		//解析数据
		//下标值为位置数减去1   6
		Integer begin = beginByte-1;
		if(begin<backContent.length()) {
			//05  
			String channelValue = backContent.substring(begin, begin+2); 
			//00000101
			String decimalConvert = StringUtil.decimalConvert(channelValue, 16, binaryValue, 8); 
			String value = decimalConvert.substring(dataByte-1,dataByte-1+1);
			//9.26
			BigDecimal canculate = new BigDecimal(value); 
			list.add(canculate);
		}
		return list;
	}
	

	private static  Object canculate(int value, String str)  { 
		//(%d*20)/4095
		String format = String.format(str, value); 
		Object eval;
		try {
			eval = jse.eval(format);
			return  eval;
		} catch (ScriptException e) {
			e.printStackTrace();
			log.info("计算错误"+e.getMessage()); 
		}
		return null;
	}
	
}
