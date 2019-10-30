package cn.hmxhy.timecircle.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 *
 */
public class FileUtil {
	/**
	 * 将base64编码转换为图片
	 *
	 * @param base64str 编码内容
	 * @param savePath  保存地址
	 * @return 是否成功
	 */
	public static boolean GenerateImage(String base64str, String savePath) {   //对字节数组字符串进行Base64解码并生成图片
		System.out.println(savePath);
		if (base64str == null) //图像数据为空
			return false;
		// System.out.println("开始解码");
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//Base64解码
			byte[] b = decoder.decodeBuffer(base64str);
			//  System.out.println("解码完成");
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {//调整异常数据
					b[i] += 256;
				}
			}
			// System.out.println("开始生成图片");
			//生成jpeg图片
			OutputStream out = new FileOutputStream(savePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
