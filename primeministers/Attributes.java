package primeministers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import condition.Condition;
import condition.Interval;

/**
 * 属性リスト：情報テーブルを入出力する際の属性情報を記憶。
 */
abstract class Attributes extends Object
{
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

	/**
	 * 属性リストを作成するコンストラクタ。
	 */
	public Attributes()
	{
		super();

		this.keys = new ArrayList<String>();
		this.names = new ArrayList<String>();

		return;
	}

	/**
	 * 指定されたインデックスに対応する名前を応答する。名前が無いときはキーを応答する。
	 * @param index インデックス
	 * @return 名前（キー）
	 */
	protected String at(int index)
	{
		String[] aString = {this.nameAt(index)};
		Runnable truePassage = () -> {aString[0] = this.keyAt(index);};
		Condition aCondition = new Condition(() -> aString[0].length() < 1);
		aCondition.ifTrue(truePassage);

		return aString[0];
	}

	/**
	 * 標題文字列を応答する。
	 * @return 標題文字列
	 */
	abstract String captionString();

	/**
	 * ページのためのディレクトリを応答する。
	 * @return ページのためのディレクトリ
	 */
	abstract String baseDirectory();

	/**
	 * ページのためのディレクトリ（存在しなければ作成して）を応答する。
	 * @param kindString 種別を表す文字列
	 * @return ページのためのディレクトリ
	 */
	public String baseDirectory(String kindString)
	{
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
		Runnable truePassage = () -> {IO.deleteFileOrDirectory(aDirectory);};
		Condition aCondition = new Condition(() -> aDirectory.exists());
		aCondition.ifTrue(truePassage);
		//if (aDirectory.exists()) { IO.deleteFileOrDirectory(aDirectory); }
		aDirectory.mkdirs();

		Attributes.baseDirectory = aDirectory.getPath() + File.separator;

		return Attributes.baseDirectory;
	}

	/**
	 * 情報の在処(URL)を文字列で応答する。
	 * @return 情報の在処の文字列
	 */
	abstract String baseUrl();

	/**
	 * 情報を記したCSVファイルの在処(URL)を文字列で応答する。
	 * @return 情報を記したCSVファイル文字列
	 */
	abstract String csvUrl();

	/**
	 * ページのためのローカルなHTMLのインデックスファイル(index.html)を文字列で応答する。
	 * @return ページのためのローカルなHTMLのインデックスファイル文字列
	 */
	public String indexHTML()
	{
		return "index.html";
	}

	/**
	 * ベースとなるディレクトリの記憶を水に流す。
	 */
	public static void flushBaseDirectory()
	{
		Attributes.baseDirectory = null;

		return;
	}

	/**
	 * 指定されたキー文字列のインデックスを応答する。
	 * @param aString キー
	 * @return インデックス
	 */
	protected int indexOf(String aString)
	{
		AtomicInteger index = new AtomicInteger(-1);
		AtomicInteger tmp = new AtomicInteger(0);
		this.keys.forEach((String aKey) -> 
		{
			Runnable truePassage = () -> {index.set(tmp.get());};
			Condition aCondition = new Condition(() -> aString.compareTo(aKey) == 0);
			aCondition.ifTrue(truePassage);
			tmp.incrementAndGet();
		});

		return index.get();
	}

	/**
	 * 在位日数のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfDays()
	{
		return this.indexOf("在位日数");
	}

	/**
	 * 画像のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfImage()
	{
		return this.indexOf("画像");
	}

	/**
	 * ふりがなのインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfKana()
	{
		return this.indexOf("ふりがな");
	}

	/**
	 * 氏名のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfName()
	{
		return this.indexOf("氏名");
	}

	/**
	 * 番号のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfNo()
	{
		return this.indexOf("人目");
	}

	/**
	 * 在位期間のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfPeriod()
	{
		return this.indexOf("在位期間");
	}

	/**
	 * 縮小画像のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfThumbnail()
	{
		return this.indexOf("縮小画像");
	}

	/**
	 * 指定されたインデックスに対応するキーを応答する。
	 * @param index インデックス
	 * @return キー
	 */
	protected String keyAt(int index)
	{
		return this.keys().get(index);
	}

	/**
	 * キー群を応答する。
	 * @return キー群
	 */
	public List<String> keys()
	{
		return this.keys;
	}

	/**
	 * 指定されたインデックスに対応する名前を応答する。
	 * @param index インデックス
	 * @return 名前
	 */
	protected String nameAt(int index)
	{
		return this.names().get(index);
	}

	/**
	 * 名前群を応答する。
	 * @return 名前群
	 */
	public List<String> names()
	{
		return this.names;
	}

	/**
	 * 名前群を設定する。
	 * @param aCollection 名前群
	 */
	public void names(List<String> aCollection)
	{
		List<String> aList = new ArrayList<String>();
		aCollection.forEach((String aString) -> {
			aList.add(aString);
		});
		this.names = aList;

		return;
	}

	/**
	 * 属性リストの長さを応答する。
	 * @return 属性リストの長さ
	 */
	public int size()
	{
		return this.keys().size();
	}

	/**
	 * タイトル文字列を応答する。
	 * @return タイトル文字列
	 */
	abstract String titleString();

	/**
	 * 自分自身を文字列にして、それを応答する。
	 * @return 自分自身の文字列
	 */
	public String toString()
	{
		StringBuffer aBuffer = new StringBuffer();
		Class<?> aClass = this.getClass();
		aBuffer.append(aClass.getName());
		aBuffer.append("[");
		
		Runnable truePassage = () -> {aBuffer.append(",");};
		
		Consumer<Integer> loopPassage = index -> {
			Condition aCondition = new Condition(() -> index != 0); 
			aCondition.ifTrue(truePassage);
			aBuffer.append(this.at(index));
		};
		Interval<Integer> anInterval = new Interval<>(0, index -> index < this.size(), index -> index++);
		anInterval.forEach(loopPassage);
		
		aBuffer.append("]");

		return aBuffer.toString();
	}
}
