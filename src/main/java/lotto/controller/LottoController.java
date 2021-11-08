package lotto.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lotto.exception.LottoException;
import lotto.model.LottoGenerator;
import lotto.model.LottoResult;
import lotto.model.Lottos;
import lotto.model.WinningLottoNumbers;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

	public void lotto() {
		generateLottoResult(generateLotto(), winningLottoNumberGenerator());
	}

	private Lottos generateLotto() {
		Lottos lottos = lottoGenerator();
		OutputView.printCompletePurchaseLotto(lottos);
		OutputView.printLottoNumbers(lottos);

		return lottos;
	}

	private Lottos lottoGenerator() {
		try {
			LottoGenerator lottoGenerator = new LottoGenerator(InputView.inputMoneyPurchaseLotto(),
				generatePurchaseLotto());
			return new Lottos(lottoGenerator);
		} catch (LottoException lottoException) {
			OutputView.printErrorMessage(lottoException);
			return lottoGenerator();
		}
	}

	private List<String> generatePurchaseLotto() {
		String count = InputView.inputPurchaseLottoCount();
		InputView.inputPurchaseLottoNumberMent();
		return Stream.generate(InputView::inputPurchaseLottoNumber)
			.limit(Integer.parseInt(count))
			.collect(Collectors.toList());
	}

	private WinningLottoNumbers winningLottoNumberGenerator() {
		try {
			return new WinningLottoNumbers(InputView.inputWinningLottoNumber(), InputView.inputBonusLottoNumber());
		} catch (LottoException lottoException) {
			OutputView.printErrorMessage(lottoException);
			return winningLottoNumberGenerator();
		}
	}

	private void generateLottoResult(Lottos lottos, WinningLottoNumbers winningLottoNumbers) {
		OutputView.printResultHead();
		LottoResult lottoResult = new LottoResult(winningLottoNumbers, lottos);
		OutputView.printResultLottoList(lottoResult);
		OutputView.printYieldResult(lottoResult);
	}
}
