package primeministers;

import condition.Condition;
import condition.Interval;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 属性リスト：徳川幕府の情報テーブルを入出力する際の属性情報を記憶。
 */
public class AttributesForTokugawaShogunate extends Attributes
{
	/**
	 * 入力用("input")または出力用("output")で徳川幕府の属性リストを作成するコンストラクタ。
	 * @param aString 入力用("input")または出力用("output")
	 */
	@SuppressWarnings("this-escape")
	public AttributesForTokugawaShogunate(String aString)
	{
		super();
		Supplier<String[]> keyStringForInput = () -> new String[] {"no", "name", "kana", "period", "family", "rank", "image", "thumbnail", "former", "cemetery" };
		Supplier<String[]> nameStringForInput = () -> new String[] {"代", "氏名", "ふりがな", "在位期間", "出身家", "官位", "画像", "縮小画像", "院号", "墓所" };
		Runnable truePassageForInput = () -> {
			String[] aKeyCollection = keyStringForInput.get();
			String[] aNameCollection = nameStringForInput.get();
			Consumer<Integer> loopPassage = index -> {
				this.keys().add(aKeyCollection[index]);
				this.names().add(aNameCollection[index]);
			};
			Interval<Integer> anInterval = new Interval<Integer>(0, index -> index < aKeyCollection.length, index -> index+1);
			anInterval.forEach(loopPassage);		
		};
		Condition aConditionForInput = new Condition(() -> aString.compareTo("input") == 0);
		aConditionForInput.ifTrue(truePassageForInput);

		Supplier<String[]> keyStringForOutput = () -> new String[] {"no", "name", "kana", "period", "days", "family", "rank", "image", "former", "cemetery" };
		Supplier<String[]> nameStringForOutput = () -> new String[] {"代", "氏名", "ふりがな", "在位期間", "在位日数", "出身家", "官位", "画像", "院号", "墓所" };
		Runnable truePassageForOutput = () -> {
			String[] aKeyCollection = keyStringForOutput.get();
			String[] aNameCollection = nameStringForOutput.get();
			Consumer<Integer> loopPassage = index -> {
				this.keys().add(aKeyCollection[index]);
				this.names().add(aNameCollection[index]);
			};
			Interval<Integer> anInterval = new Interval<Integer>(0, index -> index < aKeyCollection.length, index -> index+1);
			anInterval.forEach(loopPassage);
		};
		Condition aConditionForOutput = new Condition(() -> aString.compareTo("output") == 0);
		aConditionForOutput.ifTrue(truePassageForOutput);
	
		return;
	}

	/**
	 * 標題文字列を応答する。
	 * @return 標題文字列
	 */
	public String captionString()
	{
		return "徳川幕府";
	}

	/**
	 * 徳川幕府のページのためのディレクトリを応答する。
	 * @return 徳川幕府のページのためのディレクトリ
	 */
	public String baseDirectory()
	{
		return this.baseDirectory("TokugawaShogunate");
	}

	/**
	 * 徳川幕府の情報の在処(URL)を文字列で応答する。
	 * @return 徳川幕府の情報の在処の文字列
	 */
	public String baseUrl()
	{
		return "https://www.cc.kyoto-su.ac.jp/~atsushi/Programs/VisualWorks/CSV2HTML/TokugawaShogunate/";
	}

	/**
	 * 徳川幕府の情報を記したCSVファイルの在処(URL)を文字列で応答する。
	 * @return 情報を記したCSVファイル文字列
	 */
	public String csvUrl()
	{
		return this.baseUrl() + "TokugawaShogunate.csv";
		// return this.baseUrl() + "TokugawaShogunate2.csv";
	}

	/**
	 * 墓所のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfCemetery()
	{
		return this.indexOf("cemetery");
	}

	/**
	 * 出身家のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfFamily()
	{
		return this.indexOf("family");
	}

	/**
	 * 院号のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfFormer()
	{
		return this.indexOf("former");
	}

	/**
	 * 官位のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfRank()
	{
		return this.indexOf("rank");
	}

	/**
	 * タイトル文字列を応答する。
	 * @return タイトル文字列
	 */
	public String titleString()
	{
		return "Tokugawa Shogunate";
	}
}
