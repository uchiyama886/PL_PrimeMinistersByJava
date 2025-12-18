package primeministers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 属性リスト：情報テーブルを入出力する際の属性情報を記憶。
 */
public class Attributes extends Object {

	/**
	 * ベースとなるディレクトリを記憶する（クラス変数）フィールド。
	 */
	private static String baseDirectory = null;

	/**
	 * 属性リストのキー群を記憶する（インスタンス変数）フィールド。
	 */
	private List<String> keys;

	/**
	 * 属性リストの名前群を記憶する（インスタンス変数）フィールド。
	 */
	private List<String> names;

	Attributes() {
		super();

		this.keys = new ArrayList<String>();
		this.names = new ArrayList<String>();

		return;
	}

	/**
	 * 指定されたインデックスに対応する名前を応答する。名前が無いときはキーを応答する。
	 */
	protected String at(int index) {
		String aString = this.names().get(index);
		if(aString.length() < 1) aString = this.keys().get(index);
		return aString;
	}

	/**
	 * ページのためのディレクトリを応答する。
	 */
	abstract String baseDirectory();

	/**
	 * ページのためのディレクトリ（存在しなければ作成して）を応答する。
	 */
	public String baseDirectory(String kindString) {
		// ベースとなるディレクトリ（ページを生成するためのフォルダ）の記憶が水に流されるまで
		// シングルトン（1回だけ）であることを保証する。
		if (Attributes.baseDirectory != null) { return Attributes.baseDirectory; }

		Date aDate = new Date();
		// SimpleDateFormat aFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat aFormat = new SimpleDateFormat("yyyyMMdd");
		String dateString = aFormat.format(aDate);

		String aString = System.getProperty("user.home");
		aString = aString + File.separator + "Desktop";
		aString = aString + File.separator + "CSV2HTML_" + kindString + "_" + dateString;
		File aDirectory = new File(aString);
		// ページのためのディレクトリが存在するならば消去しておく。
		if (aDirectory.exists()) { IO.deleteFileOrDirectory(aDirectory); }
		aDirectory.mkdirs();

		Attributes.baseDirectory = aDirectory.getPath() + File.separator;

		return Attributes.baseDirectory;
	}

	/**
	 * 情報の在処(URL)を文字列で応答する。
	 */
	abstract String baseUrl();

	/**
	 * 標題文字列を応答する。
	 */
	abstract String captionString();

	/**
	 * 情報を記したCSVファイルの在処(URL)を文字列で応答する。
	 */
	abstract String csvUrl();

	/**
	 * ベースとなるディレクトリの記憶を水に流す。
	 */
	public static void flushBaseDirectory() {
		Attributes.baseDirectory = null;
		return;
	}

	/**
	 * ページのためのローカルなHTMLのインデックスファイル(index.html)を文字列で応答する。
	 */
	public String indexHTML() {
		return "index.html";
	}

	/**
	 * 指定されたキー文字列のインデックスを応答する。
	 */
	protected int indexOf(String aString) {
		int index = 0;
		for(String aKey: keys) {
			if(aString.equals(aKey)) {return index;}
			index++;
		}
		return -1;
	}

	/**
	 * 在位日数のインデックスを応答する。
	 */
	public int indexOfDays() {
		return this.indexOf("在位期間");
	}

	/**
	 * 画像のインデックスを応答する。
	 */
	public int indexOfImage() {
		return this.indexOf("画像");
	}

	/**
	 * ふりがなのインデックスを応答する。
	 */
	public int indexOfKana() {
		return this.indexOf("ふりがな");
	}

	/**
	 * 氏名のインデックスを応答する。
	 */
	public int indexOfName() {
		return this.indexOf("氏名");
	}

	/**
	 * 番号のインデックスを応答する。
	 */
	public int indexOfNo() {
		return this.indexOf("代目");
	}

	/**
	 * 在位期間のインデックスを応答する。
	 */
	public int indexOfPeriod() {
		return this.indexOf("在位期間");
	}

	/**
	 * 縮小画像のインデックスを応答する。
	 */
	public int indexOfThumbnail() {
		return this.indexOf("縮小画像");
	}

	/**
	 * 指定されたインデックスに対応するキーを応答する。
	 */
	protected String keyAt(int index) {
		return this.keys().get(index);
	}

	/**
	 * キー群を応答する。
	 */
	public List<String> keys() {
		return keys;
	}

	/**
	 * 指定されたインデックスに対応する名前を応答する。
	 */
	protected String nameAt(int index) {
		return this.names().get(index);
	}

	/**
	 * 名前群を応答する。
	 */
	public List<String> names() {
		return names;
	}

	/**
	 * 名前群を設定する。
	 */
	public void names(List<String> aCollection) {
		List<String> aList = new ArrayList<String>();

		for(String name : aCollection) {
			aList.add(name);
		}

		this.names = aList;

		return;
	}

	/**
	 * 属性リストの長さを応答する。
	 */
	public int size() {
		return this.keys().size();
	}

	/**
	 * タイトル文字列を応答する。
	 */
	abstract String titleString();

	/**
	 * 自分自身を文字列にして、それを応答する。
	 */
	public String toString() {
		return null;
	}

}
