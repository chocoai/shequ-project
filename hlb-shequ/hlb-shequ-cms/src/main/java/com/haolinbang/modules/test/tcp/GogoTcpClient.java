package com.haolinbang.modules.test.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

/**
 * 与服务器通讯的点歌机客户端
 * 
 * @author NLP
 * 
 */
public class GogoTcpClient {
	private Socket mClient = null;
	private OutputStream mOutputStream = null;
	private InputStream mInputStream = null;
	private int mPort = 60608;// 端口号

	private boolean isInitSocket = false;
	private boolean mClientCreate = false;
	String mMachineNumber = "H1234567890";
	int clientNumber = 0;

	public static void main(String[] args) throws IOException, InterruptedException {
		final GogoTcpClient client = new GogoTcpClient();
		GogoTcpClient.CreateSocketThread t = client.new CreateSocketThread();
		// 开启链接
		t.run();

		// 开启心跳注册
		Runnable run1 = new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.out.println("注册用户信息");
					// 注册用户信息
					client.register();

					System.out.println("读取数据");
					// 读取数据
					byte[] buffer = null;
					try {
						buffer = IOUtils.toByteArray(client.getmClient().getInputStream());

					} catch (IOException e) {
						e.printStackTrace();
					}
					if (buffer != null) {
						client.parserMsg(buffer);
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		// 读取发送过来的数据
		Runnable run2 = new Runnable() {

			@Override
			public void run() {
				while (true) {
					System.out.println("读取数据");
					// 读取数据
					byte[] buffer = null;
					try {
						buffer = IOUtils.toByteArray(client.getmClient().getInputStream());

					} catch (IOException e) {
						e.printStackTrace();
					}
					if (buffer != null) {
						client.parserMsg(buffer);
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		Thread t1 = new Thread(run1);
		Thread t2 = new Thread(run2);
		t1.start();
	//	t2.start();

	}

	/**
	 * 注册到服务器
	 */
	public void register() {
		byte[] cmdHead = new byte[12];
		byte[] cmdCMachineNumber = mMachineNumber.getBytes();
		byte[] c = new byte[12];
		System.arraycopy(cmdCMachineNumber, 0, c, 0, cmdCMachineNumber.length);
		ArrayUtil.WriteInt32ToByteArray(cmdHead, 0, 0x6dfc1bf6);
		ArrayUtil.WriteInt32ToByteArray(cmdHead, 4, 20);
		ArrayUtil.WriteInt32ToByteArray(cmdHead, 8, 1001);
		byte[] cmd = ArrayUtil.byteMerger(cmdHead, c);
		sendCommand(cmd);
		sendUrgentData();
	}

	/**
	 * 向服务器发送数据
	 * 
	 * @param cmd
	 */
	private void sendCommand(byte[] cmd) {
		try {
			mClient.getOutputStream().write(cmd, 0, cmd.length);
			mClient.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendUrgentData() {
		if (mClient != null) {
			try {
				mClient.sendUrgentData(0XFF);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void parserMsg(byte[] buffer) {
		System.out.println("解析数据");
		int cmdType = ArrayUtil.ReadInt32FromByteArray(buffer, 8);
		if (1001 == cmdType) {
			final int clientNumber = ArrayUtil.ReadInt32FromByteArray(buffer, 12);
			if (this.clientNumber != clientNumber) {
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
			if (len != (buffer.length - 4) || len < 36) {
				return;
			}

			byte idb[] = new byte[8];
			byte nameb[] = new byte[20];
			byte urlb[] = new byte[len - 36];
			System.arraycopy(buffer, 12, idb, 0, 8);
			System.arraycopy(buffer, 20, nameb, 0, 20);
			System.arraycopy(buffer, 40, urlb, 0, urlb.length);
			String userId = ArrayUtil.byteReadString(buffer, 12, 8);
			String userName = ArrayUtil.byteReadString(buffer, 20, 20, "gbk");
			String userHead = ArrayUtil.byteReadString(buffer, 40, len - 36);

			System.out.println("userId=" + userId + ";userName=" + userName + ";userHead=" + userHead);
		}
	}

	class CreateSocketThread extends Thread {
		@Override
		public void run() {
			if (isInitSocket)
				return;
			try {
				isInitSocket = true;
				mClient = new Socket("119.23.125.219", mPort);

				mInputStream = mClient.getInputStream();
				mOutputStream = mClient.getOutputStream();
				isInitSocket = false;

			} catch (IOException e) {
				e.printStackTrace();
				isInitSocket = false;
				return;
			}
			mClientCreate = true;

		}
	}

	public Socket getmClient() {
		return mClient;
	}

	public void setmClient(Socket mClient) {
		this.mClient = mClient;
	}

	public OutputStream getmOutputStream() {
		return mOutputStream;
	}

	public void setmOutputStream(OutputStream mOutputStream) {
		this.mOutputStream = mOutputStream;
	}

	public InputStream getmInputStream() {
		return mInputStream;
	}

	public void setmInputStream(InputStream mInputStream) {
		this.mInputStream = mInputStream;
	}

	public int getmPort() {
		return mPort;
	}

	public void setmPort(int mPort) {
		this.mPort = mPort;
	}

	public boolean isInitSocket() {
		return isInitSocket;
	}

	public void setInitSocket(boolean isInitSocket) {
		this.isInitSocket = isInitSocket;
	}

	public boolean ismClientCreate() {
		return mClientCreate;
	}

	public void setmClientCreate(boolean mClientCreate) {
		this.mClientCreate = mClientCreate;
	}

	public String getmMachineNumber() {
		return mMachineNumber;
	}

	public void setmMachineNumber(String mMachineNumber) {
		this.mMachineNumber = mMachineNumber;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

}
