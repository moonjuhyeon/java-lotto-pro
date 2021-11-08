package lotto.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lotto.code.ErrorCode;
import lotto.exception.LottoException;

public class LottoGenerator {
	public static final int LOTTO_PRICE = 1000;
	private static final int NEGATIVE_EXP = 0;
	private final Money money;
	private List<String> inputNumberList;

	public LottoGenerator(String inputMoney) {
		this.money = new Money(inputMoney);
	}

	public LottoGenerator(String inputMoney, List<String> inputNumberList) {
		this.money = new Money(inputMoney);
		this.inputNumberList = inputNumberList;
	}

	public int calculateLottoAmount() {
		if (isNullInputNumberList()) {
			return (Integer.parseInt(money.money()) / LOTTO_PRICE);
		}
		return positiveLottoAmount();
	}

	public int positiveLottoAmount() {
		if (validPositiveLottoAmount()) {
			throw new LottoException(ErrorCode.NEGATIVE_AMOUNT_ERROR);
		}
		return (Integer.parseInt(money.money()) / LOTTO_PRICE) - inputNumberList.size();
	}

	public boolean validPositiveLottoAmount() {
		return (Integer.parseInt(money.money()) / LOTTO_PRICE) - inputNumberList.size() < NEGATIVE_EXP;
	}

	public List<LottoNumbers> generateLottoNumbers() {
		if (isNullInputNumberList()) {
			return generateRandomLottoNumbers();
		}
		return generateMixLottoNumbers();
	}

	public List<LottoNumbers> generateRandomLottoNumbers() {
		return Stream
			.generate(LottoNumbers::new)
			.limit(calculateLottoAmount())
			.collect(Collectors.toList());
	}

	public List<LottoNumbers> generateLottoInputNumbers() {
		return inputNumberList
			.stream()
			.map(LottoNumbers::new)
			.limit(inputNumberList.size())
			.collect(Collectors.toList());
	}

	public List<LottoNumbers> generateMixLottoNumbers() {
		return Stream
			.of(generateLottoInputNumbers(), generateRandomLottoNumbers())
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
	}

	private boolean isNullInputNumberList() {
		return this.inputNumberList == null || this.inputNumberList.isEmpty();
	}

	public String getInputMoney() {
		return money.money();
	}
}
