package org.quincy.rock.core.security;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.quincy.rock.core.exception.CalculatorException;

/**
 * <b>实现了AES算法的加密解密器。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2015年8月13日 上午10:08:39</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class AESCrypto implements Crypto {
	/**
	 * 算法。
	 */
	public static final String ALGORITHM = "AES";

	/**
	 * 加密器。
	 */
	private Cipher encryptCipher = null;
	/**
	 * 解密器。
	 */
	private Cipher decryptCipher = null;

	/**
	 * <b>初始化。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 密钥
	 * @throws CalculatorException
	 */
	public void init(Key key) throws CalculatorException {
		try {
			//加密器
			this.encryptCipher = Cipher.getInstance(ALGORITHM);
			this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			//解密器
			this.decryptCipher = Cipher.getInstance(ALGORITHM);
			this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			throw new CalculatorException(e.getMessage(), e);
		}
	}

	/**
	 * <b>初始化。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param key 密钥
	 * @throws CalculatorException
	 */
	public void init(byte[] key) throws CalculatorException {
		this.init(new SecretKeySpec(key, ALGORITHM));
	}

	/** 
	 * encrypt。
	 * @see org.quincy.rock.core.security.Crypto#encrypt(byte[])
	 */
	@Override
	public byte[] encrypt(byte[] data) throws CalculatorException {
		try {
			return encryptCipher == null ? data : encryptCipher.doFinal(data);
		} catch (Exception e) {
			throw new CalculatorException(e.getMessage(), e);
		}
	}

	/** 
	 * decrypt。
	 * @see org.quincy.rock.core.security.Crypto#decrypt(byte[])
	 */
	@Override
	public byte[] decrypt(byte[] data) throws CalculatorException {
		try {
			return decryptCipher == null ? data : decryptCipher.doFinal(data);
		} catch (Exception e) {
			throw new CalculatorException(e.getMessage(), e);
		}
	}

	/**
	 * <b>产生随机密钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param seed 随机种子
	 * @return 密钥
	 * @throws CalculatorException
	 */
	public static final Key generateKey32(String seed) throws CalculatorException {
		return genKey(256, seed);
	}

	/**
	 * <b>产生随机密钥。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param seed 随机种子
	 * @return 密钥
	 * @throws CalculatorException
	 */
	public static final Key generateKey16(String seed) throws CalculatorException {
		return genKey(128, seed);
	}

	//产生密钥
	private static Key genKey(int keySize, String seed) throws CalculatorException {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
			kgen.init(keySize, new SecureRandom(seed.getBytes()));
			Key secretKey = kgen.generateKey();
			return secretKey;
		} catch (Exception e) {
			throw new CalculatorException(e.getMessage(), e);
		}
	}
}
