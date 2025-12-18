package primeministers;

import java.util.List;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 表：情報テーブル。
 */
public class Table extends Object {

	/**
	 * 属性リストを記憶するフィールド。
	 */
	private Attributes attributes;

	/**
	 * 画像群を記憶するフィールド。
	 */
	private List<BufferedImage> images;

	/**
	 * サムネイル画像群を記憶するフィールド。
	 */
	private List<BufferedImage> thumbnails;

	/**
	 * タプル群を記憶するフィールド。
	 */
	private List<Tuple> tuples;

	Table(Attributes instanceOfAttributes) {
		super();

		this.attributes = instanceOfAttributes;
		this.images = null;
		this.thumbnails = null;
		this.tuples = null;

		return;
	}

	/**
	 * タプルを追加する。
	 */
	public void add(Tuple aTuple) {
		tuples.add(aTuple);
	}

	/**
	 * 属性リストを応答する。
	 */
	public Attributes attributes() {
		return this.attributes;
	}

	/**
	 * 属性リストを設定する。
	 */
	public void attributes(Attributes instanceOfAttributes) {
		this.attributes = instanceOfAttributes;
		return;
	}

	/**
	 * 画像群を応答する。
	 */
	public List<BufferedImage> images() {
		if(this.images != null) {return images;}

		this.images = new ArrayList<BufferedImage>();
		for(Tuple aTuple: tuples) {
			Stinrg aString = aTuple.values().get(this.attributes.indexOfImage());
			BufferedImage anImage = this.picture(aString);
			images.add(anImage);
		}
		return this.images;
	}

	/**
	 * 画像またはサムネイル画像の文字列を受けとって当該画像を応答する。
	 */
	private BufferedImage picture(String aString) {
		String directory = "~/Desktop/PrimeMinisters/";
		StringBuilder catFilePath = new StringBuilder();
		catFilePath.append(directory);
		catFilePath.append(aString);
		String filePath = catFilePath.toString();
		File aFile = new File(filePath);
		if(!aFile.exists()) {
			return null;
		}
		BufferedImage image = ImageIO.read(aFile);
		return image;
	}

	/**
	 * サムネイル画像群を応答する。
	 */
	public List<BufferedImage> thumbnails() {
		return thumbnails;
	}

	/**
	 * 自分自身を文字列にして、それを応答する。
	 */
	public String toString() {
		return null;
	}

	/**
	 * タプル群を応答する。
	 */
	public List<Tuple> tuples() {
		return tuples;
	}

}
