package com.haolinbang.modules.test.tcp;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/12/28.
 */

public class ArrayUtil {

	public static void WriteInt16ToByteArray(byte[] array, int offset, short n) {
		for (int i = 0; i < 2; i++) {
			array[offset + i] = (byte) (n >> (i * 8));
		}
	}

	public static void WriteInt32ToByteArray(byte[] array, int offset, int n) {
		for (int i = 0; i < 4; i++) {
			array[offset + i] = (byte) (n >> (i * 8));
		}
	}

	public static void WriteInt64ToByteArray(byte[] array, int offset, long n) {
		for (int i = 0; i < 8; i++) {
			array[offset + i] = (byte) (n >> (long) (i * 8));
		}
	}

	public static short ReadInt16FromByteArray(byte[] array, int offset) {
		short test = 0;

		for (int i = 0; i < 2; i++) {
			test |= (array[offset + i] & 0xff) << (i * 8);
		}

		return test;
	}

	public static int ReadInt32FromByteArray(byte[] array, int offset) {
		int test = 0;

		for (int i = 0; i < 4; i++) {
			test |= (array[offset + i] & 0xff) << (i * 8);
		}

		return test;
	}

	public static int ReadInt256FromByteArray(byte[] array, int offset) {
		int test = 0;

		for (int i = 0; i < 256; i++) {
			test |= (array[offset + i] & 0xff) << (i * 8);
		}

		return test;
	}

	public static long ReadInt64FromByteArray(byte[] array, int offset) {
		long test = 0;

		for (int i = 0; i < 8; i++) {
			test |= (array[offset + i] & 0xffl) << (i * 8);
		}

		return test;
	}

	public static String readStringFromByteArray(byte[] array, int offset) {
		return new String(array, offset, array.length - offset);
	}

	public static byte[] int2byteArray(int num) {
		byte[] result = new byte[4];
		result[0] = (byte) (num >>> 24);// 取最高8位放到0下标
		result[1] = (byte) (num >>> 16);// 取次高8为放到1下标
		result[2] = (byte) (num >>> 8); // 取次低8位放到2下标
		result[3] = (byte) (num); // 取最低8位放到3下标
		return result;
	}

	public static byte[] intToByte(int number) {
		byte[] abyte = new byte[4];
		// "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
		abyte[3] = (byte) (0xff & number);
		// ">>"右移位，若为正数则高位补0，若为负数则高位补1
		abyte[2] = (byte) ((0xff00 & number) >> 8);
		abyte[1] = (byte) ((0xff0000 & number) >> 16);
		abyte[0] = (byte) ((0xff000000 & number) >> 24);
		return abyte;
	}

	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	public static String byteReadString(byte[] data, int start, int count) {
		int end = count + start;
		int e = end;
		for (int i = start; i < end; i++) {
			if (data[i] == 0) {
				e = i;
				break;
			}
		}
		byte[] res = new byte[e - start];
		System.arraycopy(data, start, res, 0, res.length);
		return new String(res);
	}

	public static String byteReadString(byte[] data, int start, int count, String charsetName) {
		int end = count + start;
		int e = end;
		for (int i = start; i < end; i++) {
			if (data[i] == 0) {
				e = i;
				break;
			}
		}
		byte[] res = new byte[e - start];
		System.arraycopy(data, start, res, 0, res.length);
		try {
			return new String(res, charsetName);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return "";
	}

	// common
	public synchronized static void CopyByteArrays(byte[] src, int srcOffset, byte[] dst, int dstOffset, int count) {
		for (int i = 0; i < count; i++) {
			dst[dstOffset + i] = src[srcOffset + i];
		}
	}

}
