package primeministers;

import java.io.BufferedWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Consumer;

import utility.StringUtility;
import condition.Condition;
import condition.Interval;

/**
 * ライタ：情報のテーブルをHTMLページとして書き出す。
 */
public class Writer extends IO
{
	/**
	 * ライタのコンストラクタ。
	 * @param aTable テーブル
	 */
	public Writer(Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	 * HTMLページを基にするテーブルからインデックスファイル(index.html)に書き出す。
	 */
	public void perform()
	{
		try
		{
			Attributes attributes = this.attributes();
			String fileStringOfHTML = attributes.baseDirectory() + attributes.indexHTML();
			File aFile = new File(fileStringOfHTML);
			FileOutputStream outputStream = new FileOutputStream(aFile);
			OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, StringUtility.encodingSymbol());
			BufferedWriter aWriter = new BufferedWriter(outputWriter);

			this.writeHeaderOn(aWriter);
			this.writeTableBodyOn(aWriter);
			this.writeFooterOn(aWriter);

			aWriter.close();
		}
		catch (UnsupportedEncodingException | FileNotFoundException anException) { anException.printStackTrace(); }
		catch (IOException anException) { anException.printStackTrace(); }

		return;
	}

	/**
	 * 属性リストを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeAttributesOn(BufferedWriter aWriter)
	{
		try {
			AtomicInteger level = new AtomicInteger(0);
			writeLine(aWriter, level.get(), "<body>");
			writeLine(aWriter, level.get(), "<div class=\"belt\">");
			writeLine(aWriter, level.get(), "<h2>" + this.attributes().captionString() + "<h2>");
			writeLine(aWriter, level.get(), "</div>");
			writeLine(aWriter, level.get(), "<table class=\"belt\" summary=\"belt\">");
			level.incrementAndGet();
			writeLine(aWriter, level.get(), "<tbody>");
			level.incrementAndGet();
			writeLine(aWriter, level.get(),"<tr>");
			level.incrementAndGet();
			writeLine(aWriter, level.get(),"<td>");
			level.incrementAndGet();
			writeLine(aWriter, level.get(),"<table class=\"content\" summary=\"table\">");
			level.incrementAndGet();
			writeLine(aWriter, level.get(),"<tbody>");
			level.incrementAndGet();
			writeLine(aWriter, level.get(),"<tr>");
			Attributes anAttributes = this.table().attributes();
			List<String> names = anAttributes.names();
			names.forEach((String name) -> {
				try {
					writeLine(aWriter, level.get() + 1,"<td class=\"center-pink\"><strong>"+name+"</strong></td>\n");
				}catch (IOException anException) {anException.printStackTrace();}
			});
		}catch (IOException anException) {anException.printStackTrace();}
		return;
	}

	/**
	 * フッタを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeFooterOn(BufferedWriter aWriter)
	{
		try {
			Calendar now = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 'at' HH:mm:ss");
			String dataString = sdf.format(now.getTime());

			aWriter.write("<hr>\n");
			aWriter.write("<div class=\"right-small\">Created by csv2html.Translator on"+dataString+"</div>\n");
			aWriter.write("</body>\n");
			aWriter.write("</html>\n");
		} catch (IOException aException) {aException.printStackTrace();}
		return;
	}

	/**
	 * ヘッダを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeHeaderOn(BufferedWriter aWriter)
{
    try
    {
        aWriter.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" ");
        aWriter.write("\"http://www.w3.org/TR/html4/loose.dtd\">\n");
        aWriter.write("<html lang=\"ja\">\n<head>\n");
        aWriter.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
        aWriter.write("<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n");
        aWriter.write("<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">\n");
        aWriter.write("<meta name=\"keywords\" content=\"Smalltalk,Oriented,Programming\">\n");
        aWriter.write("<meta name=\"description\" content=\"" + this.attributes().titleString() + "\">\n");
        aWriter.write("<meta name=\"author\" content=\"AOKI Atsushi\">\n");
        aWriter.write("<link rev=\"made\" href=\"https://www.cc.kyoto-su.ac.jp/~atsushi/\">\n");
        aWriter.write("<link rel=\"index\" href=\"index.html\">\n");
        aWriter.write("<style type=\"text/css\">\n");
        aWriter.write("<!--\n");
        aWriter.write("body { background-color:#ffffff; margin:20px; padding:10px; font-family:serif; font-size:10pt; }\n");
        aWriter.write("a {\n\ttext-decoration:underline; color:#000000;\n}\n");
        aWriter.write("a:link {\n\tbackground-color:#ffddbb;\n}\n");
        aWriter.write("a:visited {\n\tbackground-color:#ccffcc;\n}\n");
        aWriter.write("a:hover {\n\tbackground-color:#dddddd;\n}\n");
        aWriter.write("a:active {\n\tbackground-color:#dddddd;\n}\n");
        aWriter.write("div.belt {\n\tbackground-color:#eeeeee; padding:0px 4px;\n}\n");
        aWriter.write("div.right-small {\n\ttext-align:right; font-size:8pt;\n}\n");
        aWriter.write("img.borderless {\n\tborder-width:0px; vertical-align:middle;\n}\n");
        aWriter.write("table.belt {\n\twidth:100%;\n}\n");
        aWriter.write("table.content {\n\tpadding:2px 2px;\n}\n");
        aWriter.write("td.center-blue {\n\ttext-align:center; background-color:#ddeeff;\n}\n");
        aWriter.write("td.center-pink {\n\ttext-align:center; background-color:#ffddee;\n}\n");
        aWriter.write("td.center-yellow {\n\ttext-align:center; background-color:#ffffcc;\n}\n");
        aWriter.write("-->\n</style>\n");
        aWriter.write("<title>" + this.attributes().titleString() + "</title>\n");
        aWriter.write("</head>\n");
    }
    catch (IOException anException) { anException.printStackTrace(); }
}


	/**
	 * ボディを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeTableBodyOn(BufferedWriter aWriter)
	{
		this.writeAttributesOn(aWriter);
		this.writeTuplesOn(aWriter);

		return;
	}

	/**
	 * タプル群を書き出す。
	 * @param aWriter ライタ
	 */
	public void writeTuplesOn(BufferedWriter aWriter)
	{
		List<Tuple> tuples = this.tuples();
		AtomicInteger level = new AtomicInteger(6);
		AtomicInteger line = new AtomicInteger(0);
		tuples.forEach((Tuple aTuple) -> {
			try { writeLine(aWriter, level.get(), "<tr>"); }
			catch (IOException anException) {anException.printStackTrace();}
			List<String> values = aTuple.values();
			String[] classColor = new String[] {"center-blue", "center-yellow"};
			values.forEach((String value) -> {
				try {
					String color = classColor[line.get() % 2];
					StringBuilder html = new StringBuilder();
					html.append("<td class=\"");
					html.append(color);
					html.append("\">");
					Runnable inTag = () -> {html.append(value);};
					Runnable outTag = () ->{html.append(IO.htmlCanonicalString(value));};
					new Condition(() -> value.contains("<") && value.contains(">")).ifThenElse(inTag, outTag);
					html.append("</td>");
					writeLine(aWriter, level.get()+ 1, html.toString());
				}catch (IOException anException) {anException.printStackTrace();}
			});
			line.incrementAndGet();
			try { writeLine(aWriter, level.get(), "</tr>");}
			catch (IOException anException) {anException.printStackTrace();}
		});
		return;
	}

	/**
	 * インデントを指定して行を書き出す。
	 * @param aWriter ライタ
	 * @param level インデント
	 * @param line 書き出す行内容
	 */
	private void writeLine(BufferedWriter aWriter, int level, String line) throws IOException
	{
		Consumer<Integer> loopPassage = index -> {
			try {	aWriter.write("    ");} 
			catch (IOException anException) {anException.printStackTrace();}
		};
		Interval<Integer> anInterval = new Interval<Integer>(0, index -> index < level, index -> index+1);
		anInterval.forEach(loopPassage);
		aWriter.write(line);
		aWriter.newLine();  
	}
}
