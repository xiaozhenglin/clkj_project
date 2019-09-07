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
			//模拟量输入信号采集
			return firstAnalysis(backContent,protocol);
		case 2:
			//数字量输入信号采集
			return secondAnalysis(backContent,protocol);
		case 3:
			//数字量输出控制指令（单个）
			break;
		case 4:
			//数字量输出状态采集
			return secondAnalysis(backContent,protocol);
		case 5: 
			//温度采集
			return temperature(backContent,protocol);
		case 6:   //获取局放频次
			return partialDischarge(backContent,protocol);
		case 8: 
			//菲博泰温度采集
			return getTemperature(backContent,protocol);
		case 9: 
			//获取局放频次,能量,幅值全采集
			return PartDischargeAnalysis(backContent,protocol);
		case 10: 
			//
			return WaterHeigthAnalysis(backContent,protocol);
		default:
			break;
		}
		return null;
	}

	private static List<BigDecimal> PartDischargeAnalysis(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.clear();
		String canculateRule = protocol.getCanculateRule(); 	//计算规则 
		int binaryValue = protocol.getBinaryValue(); //转为 多少进制
		Integer beginByte = protocol.getBeginByte(); //开始位置
		Integer dataByte = protocol.getDataByte(); //结束位置
		//解析数据
		//下标值为位置数减去1   6   11-1=10
		Integer begin = beginByte-1;
		Integer end = dataByte -1;
		if(begin<backContent.length()) {
			//00 00 00 75  7-15
			String channelValue = backContent.substring(begin, end); 
			//1897
			String decimalConvert = StringUtil.decimalConvert(channelValue, 16, binaryValue, null); 
			//1897
			int value = new BigDecimal(decimalConvert).intValue(); 
			//9.26
			//Object ca = canculate(value,canculateRule); 
			BigDecimal canculate = new BigDecimal(value); 
			list.add(canculate);
		}
		return list;
	}
	
	private static List<BigDecimal> WaterHeigthAnalysis(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.clear();
		String canculateRule = protocol.getCanculateRule(); 	//计算规则 
		int binaryValue = protocol.getBinaryValue(); //转为 多少进制
		Integer beginByte = protocol.getBeginByte(); //开始位置
		Integer dataByte = protocol.getDataByte(); //结束位置
		//解析数据
		//下标值为位置数减去1   6   11-1=10
		Integer begin = beginByte-1;
		Integer end = dataByte -1;
		if(begin<backContent.length()) {
			//00 00 00 75  7-15
			String channelValue = backContent.substring(begin, end); 
			//1897
			String decimalConvert = StringUtil.decimalConvert(channelValue, 16, binaryValue, null); 
			//1897
			int value = new BigDecimal(decimalConvert).intValue(); 
			//9.26
			Object ca = canculate(value,canculateRule); 
			BigDecimal canculate = new BigDecimal(value); 
			list.add(canculate);
		}
		return list;
	}
	

	private static List<BigDecimal> firstAnalysis(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.clear();
		Integer neddCanculate =  protocol.getNeddCanculate();
		String canculateRule = protocol.getCanculateRule(); 	//计算规则 
		int binaryValue = protocol.getBinaryValue(); //转为 多少进制
		Integer beginByte = protocol.getBeginByte(); //开始位置
		Integer dataByte = protocol.getDataByte(); //结束位置
		//解析数据
		//下标值为位置数减去1   6   11-1=10
		Integer begin = beginByte-1;
		Integer end = dataByte -1;
		if(begin<backContent.length()) {
			//0769  7-11
			String channelValue = backContent.substring(begin, end); 
			//1897
			String decimalConvert = StringUtil.decimalConvert(channelValue, 16, binaryValue, null); 
			//1897
			int value = new BigDecimal(decimalConvert).intValue(); 
			//9.26
			if(neddCanculate == 1) {
				Object ca = canculate(value,canculateRule); 
				BigDecimal canculateOne = new BigDecimal(ca.toString());
				BigDecimal canculate =  canculateOne.setScale(2,BigDecimal.ROUND_HALF_DOWN);
				list.add(canculate);
			}else {
				BigDecimal canculateOne = new BigDecimal(value);
				BigDecimal canculate =  canculateOne.setScale(2,BigDecimal.ROUND_HALF_DOWN);
				list.add(canculate);
			}
		}
		return list;
	}
	
	private static List<BigDecimal> secondAnalysis(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.clear();
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
			//解析00000101后面四位
			String value = decimalConvert.substring(dataByte-1,dataByte-1+1);
			//9.26
			BigDecimal canculate = new BigDecimal(value); 
			list.add(canculate);
		}
		return list;
	}
	
	private static List<BigDecimal> temperature(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.clear();
//		String canculateRule = protocol.getCanculateRule(); 	//计算规则 
		int binaryValue = protocol.getBinaryValue(); //转为 多少进制
		Integer beginByte = protocol.getBeginByte(); //开始位置
		Integer dataByte = protocol.getDataByte(); //结束位置
		//解析数据
		//下标值为位置数减去1   6   
		Integer begin = beginByte-1;
		Integer end = dataByte -1;
		if(begin<backContent.length()) {
			//0769  6-10
			String channelValue = backContent.substring(begin, end); 
			//1897
			String decimalConvert = StringUtil.decimalConvert(channelValue, 16, binaryValue, null); 
			//1897
//			int value = new BigDecimal(decimalConvert).intValue(); 
			//9.26
//			Object ca = canculate(value,canculateRule); 
			BigDecimal canculate = new BigDecimal(decimalConvert); 
			list.add(canculate);
		}
		return list;
	}

	
	//菲博泰温度采集多个温度数据
	private static List<BigDecimal> getTemperature(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.clear();
		Integer neddCanculate =  protocol.getNeddCanculate();
		String canculateRule = protocol.getCanculateRule(); 	//计算规则 
		int binaryValue = protocol.getBinaryValue(); //转为 多少进制
		Integer beginByte = protocol.getBeginByte(); //开始位置
		Integer dataByte = protocol.getDataByte(); //结束位置
		//解析数据
		//下标值为位置数减去1   6   
		Integer begin = beginByte-1;
		Integer end = dataByte -1;
		if(begin<backContent.length()) {
			//0769  6-10
			String channelValue = backContent.substring(begin, end); 
			//1897
			String decimalConvert = StringUtil.decimalConvert(channelValue, 16, binaryValue, null); 
            
			int length = Integer.parseInt(decimalConvert)/2;
			
			for (int i =0 ; i< length; i++) {
				int first = end+4*i;
				String value = backContent.substring(first, first + 4);
				String temperatureData = StringUtil.decimalConvert(value, 16, binaryValue, null); 
				int canculate = new BigDecimal(temperatureData).intValue();
				if(neddCanculate==1) {
					Object ca = canculate(canculate,canculateRule); 
					BigDecimal canculateOne = new BigDecimal(ca.toString());
					System.out.println(canculateOne);
					BigDecimal canculateValue  =  canculateOne.setScale(2,BigDecimal.ROUND_HALF_DOWN);
					list.add(canculateValue);
				}else {
					BigDecimal canculateOne = new BigDecimal(canculate);
					BigDecimal canculateValue  =  canculateOne.setScale(2,BigDecimal.ROUND_HALF_DOWN);
					list.add(canculateValue);
				}
			}
			/*
			 * BigDecimal canculate = new BigDecimal(decimalConvert); list.add(canculate);
			 */
		}
		return list;
	}
    //获取频次，幅值,能量
	private static List<BigDecimal> partialDischarge(String backContent, TblCommandProtocolEntity protocol) {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.clear();
		int  k = backContent.length()-8;
		String frequency = backContent.substring(k, k+4);
		frequency = StringUtil.decimalConvert(frequency, 16, 10, null); 
		System.out.println("频次,幅值,能量"+frequency); 
		BigDecimal value = new BigDecimal(frequency);
		list.add(value);
		
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
