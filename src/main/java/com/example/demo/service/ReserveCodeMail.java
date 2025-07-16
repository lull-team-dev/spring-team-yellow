package com.example.demo.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class ReserveCodeMail {

	@Autowired
	JavaMailSender mailSender;

	//メール送信
	public void mailSend(Reservation reserve) {

		try {
			//HTML送信を可能にしたメール送信用
			MimeMessage message = mailSender.createMimeMessage();
			//MimeMessageを簡単に利用するためのもの
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(reserve.getGuest().getEmail());
			helper.setFrom("t92704896@gmail.com");
			helper.setSubject("<GranMarina Resort>予約完了");

			//テンプレテートテキスト受け取り
			String text = buildWelcomeMessage(reserve);
			helper.setText(text, true);

			//QRコードの画像添付
			String qrText = buildQrContent(reserve);
			byte[] qrImageBytes = generateQrCodeBytes(qrText);
			// 添付画像にファイル名を明示
			helper.addInline("qr", new ByteArrayResource(qrImageBytes), "image/png");

			//メール送信を実施する
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println("メール送信失敗: " + e.getMessage());
		}

	}

	//メール送信用テキストの作成
	private String buildWelcomeMessage(Reservation reserve) {

		try {
			return "<html><body>" +
					"<p>" + reserve.getGuest().getName() + " 様</p>" +
					"<p>この度は <strong>GranMarina Resort</strong> をご予約いただきありがとうございます。</p>" +
					"<p>ご予約を下記の内容で承りましたのでご確認ください。</p>" +

					"<h3>&lt;&lt;ご予約内容・注意事項&gt;&gt;</h3>" +
					"<ul>" +
					"<li><strong>受付日時：</strong>" + reserve.getFormatReserveOn() + "</li>" +
					"<li><strong>宿泊者名：</strong>" + reserve.getGuest().getName() + "</li>" +
					"<li><strong>ルーム名：</strong>" + reserve.getRoom().getRoomName() + "</li>" +
					"<li><strong>ご宿泊日：</strong>" + reserve.getStayDate() + " より " + reserve.getStayNights() + "泊</li>" +
					"<li><strong>プラン名：</strong>【" + reserve.getPlan().getName() + "】" + reserve.getPlan().getDetails()
					+ "</li>" +
					"<li><strong>宿泊料金：</strong>" + reserve.getTotalPrice() + " 円</li>" +
					"</ul>" +

					"<p>当日は下記QRコードを従業員へご提示ください。</p>" +
					"<img src='cid:qr' alt='QRコード'>" +

					"<p style='color: red; font-weight: bold;'>※キャンセルの際は下記電話番号にお問い合わせください</p>" +
					"<p>【お問い合わせ】03-××××-××××</p>" +

					"<br><p>またのご利用をお待ちしております。</p>" +
					"<p>------------------------------------<br>Gran Marina Resort 事務局</p>" +
					"</body></html>";

			//cid:qrは添付画像参照
		} catch (Exception e) {
			System.out.println("QRコード生成失敗: " + e.getMessage());
			return null;
		}

	}

	//QRコード用データの作成
	private String buildQrContent(Reservation reserve) {
		return "予約Id：" + reserve.getId() + "\n" +
				"受付日時：" + reserve.getFormatReserveOn() + "\n\n" +
				"宿泊者名：" + reserve.getGuest().getName() + "様\n" +
				"ご宿泊日：" + reserve.getStayDate() + "より" + reserve.getStayNights() + "泊\n";
	}

	//QRコードを作成
	private byte[] generateQrCodeBytes(String qrText) throws Exception {
		// QRコードのエンコード設定（UTF-8指定）
		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		//QRコードの生成
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = writer.encode(qrText, BarcodeFormat.QR_CODE, 250, 250, hints);

		//QRコードを画像に生成
		BufferedImage image = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < 250; x++) {
			for (int y = 0; y < 250; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFFE77611 : 0xFFFFFF);
			}
		}

		//画像用データに変換
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		return baos.toByteArray();
	}

}
