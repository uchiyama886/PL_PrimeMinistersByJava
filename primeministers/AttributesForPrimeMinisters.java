package primeministers;

/**
 * 属性リスト：総理大臣の情報テーブルを入出力する際の属性情報を記憶。
 */
public class AttributesForPrimeMinisters extends Attributes {

	public AttributesForPrimeMinisters(String aString) {
		super();
		if(aString.compareTo("input") == 0) {
			String[] aCollection = new String[] {"人目", "代", "氏名", "ふりがな", "在位期間", "出身校", "政党", "出身地", "画像",  "縮小画像"};
			for (String each : aCollection) {
				this.keys().add(each);
				this.names().add(new String());
			}
		}
		
		if(aString.compareTo("output") == 0) {
			String[] aCollection = new String[] {"人目", "代", "ふりがな", "在位期間", "在位日数", "出身校", "政党", "出身地", "画像"};
			for (String each : aCollection) {
				this.keys().add(each);
				this.names().add(new String());
			}
		}
		
	}

	/**
	 * 総理大臣のページのためのディレクトリを応答する。
	 */
	public String baseDirectory() {
		return this.baseDirectory("PrimeMinisters");
	}

	/**
	 * 総理大臣の情報の在処(URL)を文字列で応答する。
	 */
	public String baseUrl() {
		return "http://www.cc.kyoto-su.ac.jp/~atsushi/Programs/VisualWorks/CSV2HTML/PrimeMinisters/";
	}

	/**
	 * 標題文字列を応答する。
	 */
	public String captionString() {
		return "総理大臣";
	}

	/**
	 * 総理大臣の情報を記したCSVファイルの在処(URL)を文字列で応答する。
	 */
	public String csvUrl() {
		return this.baseUrl() + "PrimeMinisters.csv";
	}

	/**
	 * 代のインデックスを応答する。
	 */
	public int indexOfOrder() {
		return this.indexOf("代");
	}

	/**
	 * 政党のインデックスを応答する。
	 */
	public int indexOfParty() {
		return this.indexOf("政党");
	}

	/**
	 * 出身地のインデックスを応答する。
	 */
	public int indexOfPlace() {
		return this.indexOf("出身地");
	}

	/**
	 * 出身校のインデックスを応答する。
	 */
	public int indexOfSchool() {
		return this.indexOf("出身校");
	}

	/**
	 * タイトル文字列を応答する。
	 */
	public String titleString() {
		return "Prime Ministers";
	}

}
