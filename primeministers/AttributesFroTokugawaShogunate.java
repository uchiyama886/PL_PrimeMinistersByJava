package primeministers;

/**
 * 属性リスト：徳川幕府の情報テーブルを入出力する際の属性情報を記憶。
 */
public class AttributesFroTokugawaShogunate extends Attributes {

	public AttributesFroTokugawaShogunate(String aString) {
		
	}

	/**
	 * 徳川幕府のページのためのディレクトリを応答する。
	 */
	public String baseDirectroy() {
		return this.baseDirectory("TokugawaShogunate");
	}

	/**
	 * 徳川幕府の情報の在処(URL)を文字列で応答する。
	 */
	public String baseUrl() {
		return "http://www.cc.kyoto-su.ac.jp/~atsushi/Programs/VisualWorks/CSV2HTML/TokugawaShogunate/";
	}

	/**
	 * 標題文字列を応答する。
	 */
	public String captionString() {
		return "徳川将軍";
	}

	/**
	 * 徳川幕府の情報を記したCSVファイルの在処(URL)を文字列で応答する。
	 */
	public String csvUrl() {
		return this.baseUrl() + "TokugawaShogunate.csv";
	}

	/**
	 * 墓所のインデックスを応答する。
	 */
	public int indexOfCemetery() {
		return this.indexOf("墓所");
	}

	/**
	 * 出身家のインデックスを応答する。
	 */
	public int indexOfFamily() {
		return this.indexOf("出身家");
	}

	/**
	 * 院号のインデックスを応答する。
	 */
	public int indexOfFormer() {
		return this.indexOf("院号");
	}

	/**
	 * 官位のインデックスを応答する。
	 */
	public int indexOfRank() {
		return this.indexOf("官位");
	}

	/**
	 * タイトル文字列を応答する。
	 */
	public String titleString() {
		return "TokugawaShogunate";
	}

}
