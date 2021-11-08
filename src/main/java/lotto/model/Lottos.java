package lotto.model;

import java.util.Collections;
import java.util.List;

public class Lottos {
	private final List<LottoNumbers> lottoNumbersList;
	private final String inputMoney;
	private int randomSize;

	public Lottos(List<LottoNumbers> lottoNumbersList, String inputMoney) {
		this.lottoNumbersList = Collections.unmodifiableList(lottoNumbersList);
		this.inputMoney = inputMoney;
		this.randomSize = lottoNumbersList.size();
	}

	public Lottos(LottoGenerator lottoGenerator) {
		this.lottoNumbersList = Collections.unmodifiableList(lottoGenerator.generateLottoNumbers());
		this.inputMoney = lottoGenerator.getInputMoney();
		this.randomSize = lottoGenerator.calculateLottoAmount();
	}

	public boolean contains(LottoNumbers lottoNumbers) {
		return this.lottoNumbersList.contains(lottoNumbers);
	}

	public List<LottoNumbers> getLottoNumbersList() {
		return lottoNumbersList;
	}

	public int getInputMoney() {
		return Integer.parseInt(this.inputMoney);
	}

	public int size() {
		return lottoNumbersList.size();
	}

	public int randomSize() {
		return randomSize;
	}

	public int purchaseSize() {
		return size() - randomSize;
	}
}
