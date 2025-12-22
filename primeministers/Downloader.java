package primeministers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.imageio.ImageIO;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 * ダウンローダ：CSVファイル・画像ファイル・サムネイル画像ファイルをダウンロードする。
 */
public class Downloader extends IO
{
	/**
	 * ダウンローダのコンストラクタ。
	 * @param aTable テーブル
	 */
	public Downloader(Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	 * 総理大臣の情報を記したCSVファイルをダウンロードする。
	 */
	public void downloadCSV()
	{
		String urlString = this.table().attributes().csvUrl();
		URL aURL = null;
		try { aURL = java.net.URI.create(urlString).toURL(); }
		catch (MalformedURLException anException) {anException.printStackTrace();}

		String filePath = this.attributes().baseDirectory();
        String fileString = urlString.substring(urlString.lastIndexOf("/") + 1);
		filePath = filePath + fileString;
		try(InputStream inputStream = aURL.openStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));)
		{
			String aString;
			while ((aString = bufferedReader.readLine()) != null)
			{
				bufferedWriter.write(aString);
				bufferedWriter.newLine();
			}
		}catch (FileNotFoundException anException) {resourceNotFound(urlString);}
		catch (UnsupportedEncodingException anExcepiton) {anExcepiton.printStackTrace();}
		catch (IOException anException){ anException.printStackTrace();}
		return;
	}

	/**
	 * 総理大臣の画像群をダウンロードする。
	 */
	public void downloadImages()
	{
		int indexOfImage = this.attributes().indexOfImage();
		this.downloadPictures(indexOfImage);

		return;
	}

	/**
	 * 総理大臣の画像群またはサムネイル画像群をダウンロードする。
	 * @param indexOfPicture 画像のインデックス
	 */
	private void downloadPictures(int indexOfPicture)
	{
		List<Tuple> tuples = this.table().tuples();
		List<String> pictureNames = new ArrayList<String>();
		tuples.forEach((Tuple aTuple) -> {
			List<String> values = aTuple.values();
			pictureNames.add(values.get(indexOfPicture));
		});
		
		pictureNames.forEach((String pictureString) -> {
			String urlString = this.attributes().baseUrl();
			urlString = urlString + "/" + pictureString;
	
			URL aURL = null;
			try { aURL = java.net.URI.create(urlString).toURL();}
			catch (MalformedURLException anException) {anException.printStackTrace();}
	
			BufferedImage anImage = null;
			try {anImage = ImageIO.read(aURL);}
			catch (IOException anException) {anException.printStackTrace();}
	
			if (anImage == null) {resourceNotFound(urlString);}
	
			String filePath = this.attributes().baseDirectory();
			//String fileString = urlString.substring(urlString.lastIndexOf("/") + 1);
			filePath = filePath + File.separator + pictureString;
	
			File aFile = new File(filePath);
			aFile.getParentFile().mkdirs();
			String kindString = urlString.substring(urlString.lastIndexOf(".") + 1);
			try { ImageIO.write(anImage, kindString, aFile); }
			catch (IOException anException) {anException.printStackTrace();}
		});
	
		return;
	}

	/**
	 * 総理大臣の画像群をダウンロードする。
	 */
	public void downloadThumbnails()
	{
		int indexOfThumbnail = this.attributes().indexOfThumbnail();
		this.downloadPictures(indexOfThumbnail);

		return;
	}

	/**
	 * 総理大臣の情報を記したCSVファイルをダウンロードして、画像群やサムネイル画像群もダウンロードする。
	 */
	public void perform()
	{
		this.downloadCSV();
		Reader aReader = new Reader(this.table());
		aReader.perform();
		this.downloadImages();
		this.downloadThumbnails();
		return;
	}

	/**
	 * リソース(たとえば、URL文字列で指定されたファイルなど)が無かった時にエラーを告げるダイアログを出現させます。
	 * @param aString リソースを表す文字列(たとえば、URL文字列やファイル名)
	 */
	private static void resourceNotFound(String aString)
	{
		String messageString = String.format("エラー: 「%s」が見つかりません。\n", aString);
		JOptionPane.showMessageDialog(null, messageString, "失敗", JOptionPane.PLAIN_MESSAGE);
		throw new RuntimeException(aString);
	}
}
