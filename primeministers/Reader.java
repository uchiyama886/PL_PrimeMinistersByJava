package primeministers;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;
import utility.StringUtility;
import condition.Condition;
import condition.Interval;
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
		List<String> lines = IO.readTextFromURL(table().attributes().csvUrl());

		String headerLine = lines.get(0);
		List<String> headers = IO.splitString(headerLine, ",");

		Map<String, Integer> csvIndex = new HashMap<>();
		Consumer<Integer> loopPassage = index -> {csvIndex.put(headers.get(index), index);};
		Interval<Integer> anInterval = new Interval<Integer>(0, index -> index < headers.size(), index -> index++);
		anInterval.forEach(loopPassage);

		Consumer<Integer> dataProcess = lineNo -> {
			String line = lines.get(lineNo);

			List<String> values = IO.splitString(line, ",");

			List<String> tupleValues = new ArrayList<>();

			this.attributes().names().forEach((String attributeName) -> {
				Integer pos = csvIndex.get(attributeName);
				Runnable truePassage = () -> {tupleValues.add(values.get(pos));};
				Runnable elsePassage = () -> {tupleValues.add("");};
				Boolean isTrue = pos != null && pos < values.size();
				Condition aCondition = new Condition(() -> isTrue);
				aCondition.ifThenElse(truePassage, elsePassage);
			});

			Tuple aTuple = new Tuple(table().attributes(), tupleValues);
			table().add(aTuple);
		};
		Interval<Integer> process = new Interval<Integer>(1, lineNo -> lineNo < lines.size(), lineNo -> lineNo++);
		process.forEach(dataProcess);

		return;
	}
}
