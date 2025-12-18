package primeministers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 入出力：リーダ・ダウンローダ・ライタを抽象する。
 */
public class IO extends Object {

	/**
	 * テーブル（表：スプレッドシート）を記憶するフィールド。
	 */
	private Table table;

	public IO() {

	}

	/**
	 * 属性リストを応答する。
	 */
	public Attributes attributes() {
		return this.table.attributes();
	}

	/**
	 * ファイルやディレクトリを削除するクラスメソッド。
	 */
	public static void deleteFirlOrDirectory(File aFile) {

	}

	/**
	 * 指定された文字列をHTML内に記述できる正式な文字列に変換して応答する。
	 */
	public static String htmlCanonicalString(String aString) {
		return null;
	}

	/**
	 * 指定されたファイルからテキストを読み込んで、それを行リストにして応答するクラスメソッド。
	 */
	public static List<String> readTextFromFile(File aFile) {
		List<String> aList = new ArrayList<String>();
		try {
			String fileString = Files.readString(aFile.toPath());
			aList = readTextFromFile(fileString);
		} catch (IOException e){
			e.printStackTrack();
		}
		return aList;
	}

	/**
	 * 指定されたファイル文字列からテキストを読み込んで、それを行リストにして応答するクラスメソッド。
	 */
	public static List<String> readTextFromFile(String fileString) {
		return splitString(fileString, "\n");
	}

	/**
	 * 指定されたURL文字列からテキストを読み込んで、それを行リストにして応答するクラスメソッド。
	 */
	public static List<String> readTextFromURL(String urlString) {
		return null;
	}

	/**
	 * 指定されたURLからテキストを読み込んで、それを行リストにして応答するクラスメソッド。
	 */
	public static List<String> readTextFromURL(URL aURL) {
		return null;
	}

	/**
	 * 文字列をセパレータで分割したトークン列を応答するクラスメソッド。
	 */
	public static List<String> splitString(String string, String separators) {
		String[] strings = string.split(separators);
		List<String> aList = new ArrayList<String>();
		for(String s: strings) {
			aList.add(s);
		} 
		return aList;
	}

	/**
	 * テーブルを応答する。
	 */
	public Table table() {
		return this.table;
	}


	/**
	 * 指定された行リストを、指定されたファイルに書き出すクラスメソッド。
	 */
	public static void writeText(List<String> aCollection, File aFile) {

	}

	/**
	 * 指定された行リストを、指定されたファイル名のファイルに書き出すクラスメソッド。
	 */
	public static void writeText(List<String> aCollection, String fileString) {

	}

}
