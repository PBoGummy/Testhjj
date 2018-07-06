package com.xxx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRCode {
	static void createqrcode(int version, String content, String imgpath, String startRgb, String endRgb)
			throws IOException {
		
		Qrcode qrcode = new Qrcode();

		qrcode.setQrcodeVersion(version);
		// System.out.println("�汾�ţ�"+qrcode.getQrcodeVersion());
		// ���ö�ά����Ŵ��ʣ�������L��7%����M��15%����Q��%25����H��30��
		// ��ά����Ŵ���Խ�߿ɴ洢����Ϣ��ԽС����ά���������Ҫ���ԽС
		qrcode.setQrcodeErrorCorrect('M');
		System.out.println("�Ŵ��ʣ�" + qrcode.getQrcodeErrorCorrect());
		qrcode.setQrcodeEncodeMode('B');
		System.out.println("�ɴ洢���ͣ�" + qrcode.getQrcodeErrorCorrect());
		int imgSize = 67 + (version - 1)*12;
		BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = bufferedImage.createGraphics();
		qrcode.setQrcodeVersion(version);
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, imgSize, imgSize);

	

		// ͨ����ά��Ҫ�������ݻ�ȡһ���������͵Ķ�ά����
		boolean[][] calQrocde = qrcode.calQrcode(content.getBytes());
		
		// ����ƫ����
		int pixoff = 2;
		int startR = 0, startG = 0, startB = 0;
		if (null != startRgb) {
			String[] rgb = startRgb.split(",");
			startR = Integer.valueOf(rgb[0]);
			startG = Integer.valueOf(rgb[1]);
			startB = Integer.valueOf(rgb[2]);
		}
		int endR = 0, endG = 0, endB = 0;
		if (null != endRgb) {
			String[] rgb = endRgb.split(",");
			endR = Integer.valueOf(rgb[0]);
			endG = Integer.valueOf(rgb[1]);
			endB = Integer.valueOf(rgb[2]);
		}
		for (int i = 0; i < calQrocde.length; i++) {// ������

			for (int j = 0; j < calQrocde[i].length; j++) {// ������

				if (calQrocde[i][j]) {// true����ά����ɫ��false�����

					int r = startR + (endR - startR) * (i + j) / 2 / calQrocde.length;
					int g = startG + (endG - startG) * (i + j) / 2 / calQrocde.length;
					int b = startB + (endB - startB) * (i + j) / 2 / calQrocde.length;

					Color color = new Color(r, g, b);
					gs.setColor(color);
					gs.fillRect(i * 3 + pixoff, j * 3 + pixoff, 3, 3);
				}
			}

		}
		// ����ͷ��
		BufferedImage logo = ImageIO.read(new File("D:/logo.jpg"));
		// ͷ���С
		int logoSize = imgSize / 3;
		// ͷ�����ʼλ��
		int o = (imgSize - logoSize) / 2;
		// ����ά���ϻ�ͷ��
		gs.drawImage(logo, o, o, logoSize, logoSize, null);
		// �رջ滭����
		gs.dispose();
		// �ѻ�����ͼƬ������ڴ�
		bufferedImage.flush();
		try {
			ImageIO.write(bufferedImage, "png", new File(imgpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("�ɹ�");
	}

	public static void main(String[] args) throws IOException {
		int version = 15;
		String content = "BEGIN:VCARD\r\n" + 
						   "FN:����:hjj\r\n"+
						   "ORG:ѧУ:�ӱ��Ƽ�ʦ��ѧԺ 	ְλ:ѧ��\r\n"+
						   "TITLE:  \r\n" + 
						   "TEL;WORK;VOICE:12345677456\r\n"+
						   "TEL;HOME;VOICE:66973635435\r\n"+
						   "TEL;CELL;VOICE:45654544424\r\n"+
						   "ADR;WORK:�ӱ��ػʵ�\r\n"+
						   "ADR;HOME:�ӱ�ʯ��ׯ\r\n"+
						   "URL:http://www.hjj.com\r\n"+
						   "EMAIL;HOME:4546454645@qq.com\r\n" + 
						   "PHOTO;VALUE=uri:http://www.hjj.com/images/logo.jpg\r\n" + 
						   "END:VCARD";

		String imgpath = "D:/q.png";
		String startRgb = "255,20,255";
		String endRgb = "100,125,255";

		createqrcode(version, content, imgpath, startRgb, endRgb);

	}

}