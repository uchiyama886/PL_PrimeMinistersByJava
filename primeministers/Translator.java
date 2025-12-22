package primeministers;

import condition.Condition;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * トランスレータ：CSVファイルをHTMLページへと変換するプログラム。
 */
public class Translator extends Object
{
	/**
	 * CSVに由来するテーブルを記憶するフィールド。
	 */
	private Table inputTable;

	/**
	 * HTMLに由来するテーブルを記憶するフィールド。
	 */
	private Table outputTable;

	/**
	 * 属性リストのクラスを指定したトランスレータのコンストラクタ。
	 * @param classOfAttributes 属性リストのクラス
	 */
	public Translator(Class<? extends Attributes> classOfAttributes)
	{
		super();

		Attributes.flushBaseDirectory();

		try
		{
			Constructor<? extends Attributes> aConstructor = classOfAttributes.getConstructor(String.class);

			this.inputTable = new Table(aConstructor.newInstance("input"));
			this.outputTable = new Table(aConstructor.newInstance("output"));
		}
		catch (NoSuchMethodException |
			   InstantiationException |
			   IllegalAccessException |
			   InvocationTargetException anException) { anException.printStackTrace(); }

		return;
	}

	/**
	 * 在位日数を計算して、それを文字列にして応答する。
	 * @param periodString 在位期間の文字列
	 * @return 在位日数の文字列
	 */
	public String computeNumberOfDays(String periodString)
	{
		String[] dates = periodString.split("〜");
		String startString = dates[0].trim();
		String endString = (dates.length > 1) ? dates[1].trim() : "";

		try {
			SimpleDateFormat aFormat = new SimpleDateFormat("yyyy年MM月dd日");

			Date startDate = aFormat.parse(startString);

			Date endDate  = (endString.isEmpty()) ? new Date() : aFormat.parse(endString); 

			GregorianCalendar startCalendar = new GregorianCalendar();
			startCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
			startCalendar.setTime(startDate);

			GregorianCalendar endCalendar = new GregorianCalendar();
			endCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
			endCalendar.setTime(endDate);

			int[] timeFields = {Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND};
			Arrays.stream(timeFields).forEach((int field) -> {
				startCalendar.set(field, 0);
				endCalendar.set(field, 0);
			});

			long startMillis = startCalendar.getTimeInMillis();
			long endMillis = endCalendar.getTimeInMillis();
			long diffMillis = endMillis - startMillis;

			long dayMillis = 1000L * 60L * 60L * 24L;
			long numberOfDays = (diffMillis / dayMillis) + 1;

			return String.valueOf(numberOfDays);
		} catch (Exception anEnException) {return "";}
	}

	/**
	 * サムネイル画像から画像へ飛ぶためのHTML文字列を作成して、それを応答する。
	 * @param aString 画像の文字列
	 * @param aTuple タプル
	 * @param no 番号
	 * @return サムネイル画像から画像へ飛ぶためのHTML文字列
	 */
	public String computeStringOfImage(String aString, Tuple aTuple, int no)
	{
		int indexThumbnail = aTuple.attributes().indexOfThumbnail();

		String thumbnailString = aTuple.values().get(indexThumbnail);

		int width = 25;
		int height = 32;

		try {
			String baseDirectory = aTuple.attributes().baseDirectory();
			File thumbnailFile = new File(baseDirectory, thumbnailString);

			BufferedImage image = javax.imageio.ImageIO.read(thumbnailFile);
			width = image.getWidth();
			height = image.getHeight();
		} catch (Exception anException) {anException.printStackTrace();}

		StringBuilder aBuffer = new StringBuilder();
		aBuffer.append("<a name=\"").append(no).append("\" href=\"").append(aString).append("\">")
				.append("<img class=\"borderless\" src=\"").append(thumbnailString).append("\" ")
				.append("width=\"").append(width).append("\" height=\"").append(height).append("\" ")
				.append("alt=\"").append(aString.substring(aString.lastIndexOf("/")+1)).append("\">")
				.append("</a>");
		return aBuffer.toString();
	}

	/**
	 * CSVファイルをHTMLページへ変換する。
	 */
	public void execute()
	{
		// 必要な情報をダウンロードする。
		Downloader aDownloader = new Downloader(this.inputTable);
		aDownloader.perform();

		// CSVに由来するテーブルをHTMLに由来するテーブルへと変換する。
		//System.out.println(this.inputTable);
		this.translate();
		//System.out.println(this.outputTable);

		// HTMLに由来するテーブルから書き出す。
		Writer aWriter = new Writer(this.outputTable);
		aWriter.perform();

		// ブラウザを立ち上げて閲覧する。
		try
		{
			Attributes attributes = this.outputTable.attributes();
			String fileStringOfHTML = attributes.baseDirectory() + attributes.indexHTML();
			ProcessBuilder aProcessBuilder = new ProcessBuilder("open", "-a", "Safari", fileStringOfHTML);
			aProcessBuilder.start();
		}
		catch (Exception anException) { anException.printStackTrace(); }

		return;
	}

	/**
	 * 属性リストのクラスを受け取って、CSVファイルをHTMLページへと変換するクラスメソッド。
	 * @param classOfAttributes 属性リストのクラス
	 */
	public static void perform(Class<? extends Attributes> classOfAttributes)
	{
		// トランスレータのインスタンスを生成する。
		Translator aTranslator = new Translator(classOfAttributes);
		// トランスレータにCSVファイルをHTMLページへ変換するように依頼する。
		aTranslator.execute();

		return;
	}

	/**
	 * CSVファイルを基にしたテーブルから、HTMLページを基にするテーブルに変換する。
	 */
	public void translate()
	{
		//Reader aReader = new Reader(this.inputTable);
		//aReader.perform();

		List<Tuple> inputTuples = this.inputTable.tuples();
		Attributes inAttr = this.inputTable.attributes();
		Attributes outAttr = this.outputTable.attributes();
		
		inputTuples.forEach((Tuple aTuple) -> {
			List<String> outValues = new ArrayList<>();
			
			outAttr.keys().forEach((String key) -> {
				String[] value = new String[1];
				Runnable whenDaysOrImage = () -> {
					Runnable whenDays = () -> {
						String period = aTuple.values().get(aTuple.attributes().indexOfPeriod());
						value[0] = this.computeNumberOfDays(period);
					};
					Runnable whenImage = () -> {
						String image = aTuple.values().get(aTuple.attributes().indexOfImage());
						int indexNo = inAttr.indexOfNo();
						int no = Integer.parseInt(aTuple.values().get(indexNo));
						value[0] = this.computeStringOfImage(image, aTuple, no);
					};
					Condition isDays = new Condition(() -> key.equals("days"));
					isDays.ifThenElse(whenDays, whenImage);
				};
				Runnable elsePassage = () -> { 
					int index = inAttr.indexOf(key);
					value[0] = aTuple.values().get(index);};
				Condition isDaysOrImage = new Condition(() -> key.equals("days") || key.equals("image"));
				isDaysOrImage.ifThenElse(whenDaysOrImage, elsePassage);
				
				outValues.add(value[0]);
			});

			Tuple outTuple = new Tuple(outAttr, outValues);
			this.outputTable.add(outTuple);
		});
		return;
	}
}
