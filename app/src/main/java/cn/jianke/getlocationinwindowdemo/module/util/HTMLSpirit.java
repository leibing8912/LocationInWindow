package cn.jianke.getlocationinwindowdemo.module.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *     @author Wonghoman 
 */

public class HTMLSpirit {
	// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
	private static final String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
	// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
	private static final String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
	// 定义HTML标签的正则表达式
	private static final String regEx_html = "<[^>]+>";
	// 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	private static final String regEx_special = "\\&[a-zA-Z]{1,10};";
	
	/**
	 * 删除Html标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String removeHtmlTag(String inputString) {
		if (inputString == null)
			return null;
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;
		Pattern p_special;
		Matcher m_special;
		try {
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签
			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签
			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			p_special = Pattern
					.compile(regEx_special, Pattern.CASE_INSENSITIVE);
			m_special = p_special.matcher(htmlStr);
			htmlStr = m_special.replaceAll(""); // 过滤特殊标签
			textStr = htmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}
}
