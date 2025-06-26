package com.example.demo.service;

import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;

@Service
public class ChangeCharService {

	//ひらがなをカタカナに変更
	public String convertHiraganaToKatakana(String kanaWord) {
		if (StringUtils.isEmpty(kanaWord)) {
			return "";
		}

		StringBuilder katakana = new StringBuilder(kanaWord.length());

		for (int i = 0; i < kanaWord.length(); i++) {
			char c = kanaWord.charAt(i);

			// ひらがな範囲内の文字かをチェック
			if (c >= '\u3041' && c <= '\u3096') {
				// ひらがなをカタカナに変換
				katakana.append((char) (c + 0x60));
			} else {
				// ひらがなでない文字はそのまま追加
				katakana.append(c);
			}
		}

		return katakana.toString();
	}

	//	public String charKana(String kanaWord) {
	//
	//		int flags = 0;
	//		flags |= KanaConverter.OP_HIRA_TO_KATA; // ひらがな→全角カタカナ
	//		flags |= KanaConverter.OP_HAN_KATA_TO_ZEN_KATA; // 半角カタカナ→全角カタカナ
	//
	//		return "kanaWord";
	//	}

}
