package lotto.model;

import lotto.code.ErrorCode;
import lotto.exception.LottoException;

public class WinningLottoNumbers {
	private static final int COUNT_VALUE = 1;
	private static final int NOT_COUNT_VALUE = 0;

	private final LottoNumbers winningLottoNumbers;
	private final LottoNumber bonusNumber;

	private WinningLottoNumbers(LottoNumbers winningLottoNumbers, LottoNumber bonusNumber) {
		this.winningLottoNumbers = winningLottoNumbers;
		this.bonusNumber = bonusNumber;
	}

	public static WinningLottoNumbers of(String inputWinningLottoNumber, String inputBonusNumber) {
		duplicateBonusNumber(inputWinningLottoNumber, inputBonusNumber);
		return new WinningLottoNumbers(LottoNumbers.from(inputWinningLottoNumber), LottoNumber.from(inputBonusNumber));
	}

	private static void duplicateBonusNumber(String inputWinningLottoNumber, String bonusNumber) {
		if (validDuplicateBonusNumber(inputWinningLottoNumber, bonusNumber)) {
			throw new LottoException(ErrorCode.BONUS_NUMBER_DUPLICATE_ERROR);
		}
	}

	private static boolean validDuplicateBonusNumber(String inputWinningLottoNumber, String bonusNumber) {
		return inputWinningLottoNumber.contains(bonusNumber);
	}

	public int size() {
		return winningLottoNumbers.size();
	}

	public boolean containsLottoNumber(LottoNumber lottoNumber) {
		return winningLottoNumbers.containsLottoNumber(lottoNumber);
	}

	public int containsCountLottoNumber(LottoNumber lottoNumber) {
		if (containsLottoNumber(lottoNumber)) {
			return COUNT_VALUE;
		}
		return NOT_COUNT_VALUE;
	}

	public boolean containsBonusLottoNumber(LottoNumbers lottoNumbers) {
		return lottoNumbers.containsLottoNumber(bonusNumber);
	}

	public int containsBonusCountLottoNumber(LottoNumbers lottoNumbers) {
		if (containsBonusLottoNumber(lottoNumbers)) {
			return COUNT_VALUE;
		}
		return NOT_COUNT_VALUE;
	}
}
