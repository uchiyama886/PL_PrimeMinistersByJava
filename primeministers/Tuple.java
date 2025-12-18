package primeministers;

import java.util.List;

/**
 * タプル：情報テーブルの中の各々のレコード。
 */
public class Tuple extends Object {

	/**
	 * 属性リストを記憶するフィールド。
	 */
	private Attributes attributes;

	/**
	 * 値リストを記憶するフィールド。
	 */
	private List<String> values;

	public Tuple(Attributes instanceOfAttributes, List<String> valueCollection) {
		super();

		this.attributes = instanceOfAttributes;
		this.values = valueCollection;
		
		return;
	}

	/**
	 * 属性リストを応答する。
	 */
	public Attributes attributes() {
		return attributes;
	}

	/**
	 * 自分自身を文字列にして、それを応答する。
	 */
	public String toString() {
		return null;
	}

	/**
	 * 値リストを応答する。
	 */
	public List<String> values() {
		return values;
	}

}
