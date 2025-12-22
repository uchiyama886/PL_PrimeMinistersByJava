package primeministers;

import condition.Condition;
import condition.Interval;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * リーダ：情報を記したCSVファイルを読み込んでテーブルに仕立て上げる。
 */
public class Reader extends IO
{
	/**
	 * リーダのコンストラクタ。
	 * @param aTable テーブル
	 */
	public Reader(Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	 * ダウンロードしたCSVファイルを読み込む。
	 */
	public void perform()
	{
		String urlString = table().attributes().csvUrl();
		String fileString = urlString.substring(urlString.lastIndexOf("/") + 1);
		String filePath = attributes().baseDirectory() + fileString;
		List<String> lines = IO.readTextFromFile(filePath);
		if(lines == null || lines.isEmpty()) {
			System.err.println("Warnig: CSV file is empty or not found.");
			return;
		}

		String headerLine = lines.get(0);
		List<String> headers = IO.splitString(headerLine, ",");
		

		Map<String, Integer> csvIndex = new HashMap<>();
		Consumer<Integer> loopPassage = index -> {csvIndex.put(headers.get(index), index);};
		Interval<Integer> anInterval = new Interval<Integer>(0, index -> index < headers.size(), index -> index+1);
		anInterval.forEach(loopPassage);

		Consumer<Integer> dataProcess = lineNo -> {
			String line = lines.get(lineNo);

			String[] values = line.split(",", -1);

			List<String> tupleValues = new ArrayList<>();

			this.attributes().names().forEach((String attributeName) -> {
				Integer pos = csvIndex.get(attributeName);
				Runnable truePassage = () -> {tupleValues.add(values[pos]);};
				Runnable elsePassage = () -> {tupleValues.add("");};
				Boolean isTrue = pos != null && pos < values.length;
				Condition aCondition = new Condition(() -> isTrue);
				aCondition.ifThenElse(truePassage, elsePassage);
			});

			Tuple aTuple = new Tuple(table().attributes(), tupleValues);
			table().add(aTuple);
		};
		Interval<Integer> process = new Interval<Integer>(1, lineNo -> lineNo < lines.size(), lineNo -> lineNo+1);
		process.forEach(dataProcess);

		return;
	}
}
