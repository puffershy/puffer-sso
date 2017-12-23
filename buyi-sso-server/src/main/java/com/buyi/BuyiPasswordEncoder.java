package com.buyi;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 重载密码加密方式
 * <p>
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017上午10:34:24
 */
public class BuyiPasswordEncoder implements PasswordEncoder {
	private String encodingAlgorithm = "MD5";
	private String CharacterEncoding = "UTF-8";

	@Override
	public String encode(CharSequence rawPassword) {
		if (rawPassword == null) {
			return null;
		}

		try {
			byte[] bytes = rawPassword.toString().getBytes(CharacterEncoding);
			String encodeHexString = Hex.encodeHexString(DigestUtils.getDigest(encodingAlgorithm).digest(bytes));
			return encodeHexString;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String encodeedRawPassword = StringUtils.isNotBlank(rawPassword) ? this.encode(rawPassword.toString()) : null;
		boolean matched = StringUtils.equals(encodeedRawPassword, encodedPassword);
		return matched;
	}

}
