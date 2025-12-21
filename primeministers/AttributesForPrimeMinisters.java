package primeministers;

import java.io.File;
import java.util.function.Consumer;

import condition.Condition;
import condition.Interval;
import java.util.function.Supplier;

/**
 * 属性リスト：総理大臣の情報テーブルを入出力する際の属性情報を記憶。
 */
public class AttributesForPrimeMinisters extends Attributes
{
	/**
	 * 入力用("input")または出力用("output")で総理大臣の属性リストを作成するコンストラクタ。
	 * @param aString 入力用("input")または出力用("output")
	 */
	public AttributesForPrimeMinisters(String aString)
	{
		super();
		Supplier<String[]> stringForInput = () -> new String[] {"人目", "代", "氏名", "ふりがな", "在位期間", "出身校", "政党", "出身地", "画像", "縮小画像"};
		Runnable truePassageForInput = () -> {
			String[] aCollection = stringForInput.get();
			Consumer<Integer> loopPassage = index -> {
				this.keys().add(aCollection[index]);
				this.names().add(new String());
			};
			Interval<Integer> anInterval = new Interval<Integer>(0, index -> index < aCollection.length, index -> index++);
			anInterval.forEach(loopPassage);		
		};
		Condition aConditionForInput = new Condition(() -> aString.compareTo("input") == 0);
		aConditionForInput.ifTrue(truePassageForInput);

		Supplier<String[]> stringForOutput = () -> new String[] { "人目", "代", "氏名", "ふりがな", "在位期間", "在位日数", "出身校", "政党", "出身地", "画像" };
		Runnable truePassageForOutput = () -> {
			String[] aCollection = stringForOutput.get();
			Consumer<Integer> loopPassage = index -> {
				this.keys().add(aCollection[index]);
				this.names().add(new String());
			};
			Interval<Integer> anInterval = new Interval<Integer>(0, index -> index < aCollection.length, index -> index++);
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
		return "総理大臣";
	}

	/**
	 * 総理大臣のページのためのディレクトリを応答する。
	 * @return 総理大臣のページのためのディレクトリ
	 */
	public String baseDirectory()
	{
		return this.baseDirectory("PrimeMinisters");
	}

	/**
	 * 総理大臣の情報の在処(URL)を文字列で応答する。
	 * @return 総理大臣の情報の在処の文字列
	 */
	public String baseUrl()
	{
		return "http://www.cc.kyoto-su.ac.jp/~atsushi/Programs/VisualWorks/CSV2HTML/PrimeMinisters/";
	}

	/**
	 * 総理大臣の情報を記したCSVファイルの在処(URL)を文字列で応答する。
	 * @return 情報を記したCSVファイル文字列
	 */
	public String csvUrl()
	{
		return this.baseUrl() + "PrimeMinisters.csv";
		// return this.baseUrl() + "PrimeMinisters2.csv";
	}

	/**
	 * 政党のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfParty()
	{
		return this.indexOf("政党");
	}

	/**
	 * 出身地のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfPlace()
	{
		return this.indexOf("出身地");
	}

	/**
	 * 代のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfOrder()
	{
		return this.indexOf("代");
	}

	/**
	 * 出身校のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfSchool()
	{
		return this.indexOf("出身校");
	}

	/**
	 * タイトル文字列を応答する。
	 * @return タイトル文字列
	 */
	public String titleString()
	{
		return "総理大臣";
	}
}
