package com.haolinbang.modules.test.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class GogoTcpClient2 {

	private static final ThreadLocal<Socket> threadConnect = new ThreadLocal<Socket>();

	private static final String HOST = "119.23.125.219";

	private static final int PORT = 60608;

	private static Socket client;

	private static OutputStream outStr = null;

	private static InputStream inStr = null;

	private static Thread tRecv = new Thread(new RecvThread());
	private static Thread tKeep = new Thread(new KeepThread());

	private static String mMachineNumber = "H1234567890";
	private static int clientNumber = 0;

	public static void connect() throws UnknownHostException, IOException {
		client = threadConnect.get();
		if (client == null) {
			client = new Socket(HOST, PORT);
			threadConnect.set(client);
			tKeep.start();
			System.out.println("========链接开始！========");
		}
		outStr = client.getOutputStream();
		inStr = client.getInputStream();
	}

	public static void disconnect() {
		try {
			outStr.close();
			inStr.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static class KeepThread implements Runnable {
		public void run() {
			try {
				System.out.println("=====================开始发送心跳包==============");
				// 一直读取数据
				while (true) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					register();

					System.out.println("=====================发送心跳包==============");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static class RecvThread implements Runnable {
		public void run() {
			try {
				System.out.println("==============开始接收数据===============");	
				
				while (true) {
					byte[] b = new byte[1024];
					int r = inStr.read(b);
					
					if (r > -1) {
						parserMsg(b);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private static void parserMsg(byte[] buffer) {
		System.out.println("------------解析数据-----------------");
		int cmdType = ArrayUtil.ReadInt32FromByteArray(buffer, 8);

		System.out.println("cmdType=" + cmdType);
		if (1001 == cmdType) {
			int clientNumber = ArrayUtil.ReadInt32FromByteArray(buffer, 12);
			if (GogoTcpClient2.clientNumber == 0) {
				GogoTcpClient2.clientNumber = clientNumber;
			}

			System.out.println("clientNumber=" + clientNumber);

			if (GogoTcpClient2.clientNumber != clientNumber) {
				System.out.println("客户端斑马出错");
			}
		} else if (1024 == cmdType) {
			// 指令起始码（4byte）：0x6dfc1bf6
			// 指令长度（4byte）：不含起始码
			// 指令（4byte）：1024
			// 服务器发送指令内容：
			// 用户ID（8byte）：如007(新增)
			// 用户名称(20byte)：微信用户名称
			// 用户头像（url）(20byte)：微信用户头
			int len = ArrayUtil.ReadInt32FromByteArray(buffer, 4);
			int length = buffer.length - 4;
//			if (len != length || len < 36) {
//				return;
//			}

			byte idb[] = new byte[8];
			byte nameb[] = new byte[20];
			byte urlb[] = new byte[len - 36];
			System.arraycopy(buffer, 12, idb, 0, 8);
			System.arraycopy(buffer, 20, nameb, 0, 20);
			System.arraycopy(buffer, 40, urlb, 0, urlb.length);
			String userId = ArrayUtil.byteReadString(buffer, 12, 8);
			String userName = ArrayUtil.byteReadString(buffer, 20, 20, "gbk");
			String userHead = ArrayUtil.byteReadString(buffer, 40, len - 36);

			System.out.println("######----------userId=" + userId + ";userName=" + userName + ";userHead=" + userHead);
		}
	}

	/**
	 * 注册到服务器
	 * 
	 * @throws IOException
	 */
	public static void register() throws IOException {
		byte[] cmdHead = new byte[12];
		byte[] cmdCMachineNumber = mMachineNumber.getBytes();
		byte[] c = new byte[12];
		System.arraycopy(cmdCMachineNumber, 0, c, 0, cmdCMachineNumber.length);
		ArrayUtil.WriteInt32ToByteArray(cmdHead, 0, 0x6dfc1bf6);
		ArrayUtil.WriteInt32ToByteArray(cmdHead, 4, 20);
		ArrayUtil.WriteInt32ToByteArray(cmdHead, 8, 1001);
		byte[] cmd = ArrayUtil.byteMerger(cmdHead, c);
		outStr.write(cmd, 0, cmd.length);
		outStr.flush();

		client.sendUrgentData(0XFF);
	}

	public static void main(String[] args) {
		try {
			GogoTcpClient2.connect();
			tRecv.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
