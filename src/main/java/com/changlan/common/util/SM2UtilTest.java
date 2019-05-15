package com.changlan.common.util;

import java.util.Arrays;

import org.bouncycastle.util.encoders.Hex;

import com.changlan.common.pojo.SM2KeyPair;

public class SM2UtilTest {
	
	/** 元消息串 */
    private static String M = "哈哈哈，&*&…………&、、//\\!@#$%^&*()物品woyebuzhidaowozijiqiaodesha!@#$%^&*())))))ooooooooppppppppppppppppppplllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkffffffffffffffffffffffffffffffffffffff";
     
    public static void main(String[] args) {
        SM2Util sm2 = new SM2Util();
        //公钥
        SM2KeyPair keyPair = sm2.generateKeyPair();
        System.out.println(Hex.toHexString(keyPair.getPublicKey().getEncoded())); 
        
        System.out.println("==========================");
        //加密
        byte[] data = sm2.encrypt(M,keyPair.getPublicKey());
        System.out.println("data is:"+  Hex.toHexString(data));
//        System.out.println("data is:"+Arrays.toString(data));
        
        System.out.println("==========================");
        //解密
        sm2.decrypt(data, keyPair.getPrivateKey());//71017045908707391874054405929626258767106914144911649587813342322113806533034
    }
    
}
