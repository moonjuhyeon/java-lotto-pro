package lotto.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import lotto.code.ErrorCode;
import lotto.exception.LottoException;

class LottoGeneratorTest {

	@Test
	void 입력된_수동번호로_로또를_생성해주는_기능테스트() {
		// given
		List<String> inputList = Arrays.asList("12,3,4,5,6,7", "1,2,3,4,5,6", "1,2,3,4,5,6");
		LottoGenerator lottoGenerator = new LottoGenerator("3000", inputList);

		// when
		List<LottoNumbers> lottoNumbersList = lottoGenerator.generateLottoInputNumbers();

		// then
		assertAll(
			() -> assertThat(lottoNumbersList).hasSize(3),
			() -> assertThat(lottoNumbersList.get(0)).isEqualTo(new LottoNumbers("12,3,4,5,6,7"))
		);
	}

	@ParameterizedTest
	@CsvSource(value = {
		"6000 : 3", "10000 : 7", "3000 : 0"
	}, delimiter = ':')
	void 입력된_금액과_수동번호갯수로_랜덤생성갯수를_반환하는_기능테스트(String inputMoney, int resultSize) {
		// given
		List<String> inputList = Arrays.asList("12,3,4,5,6,7", "1,2,3,4,5,6", "1,2,3,4,5,6");
		LottoGenerator lottoGenerator = new LottoGenerator(inputMoney, inputList);

		// when
		int size = lottoGenerator.calculateLottoAmount();

		// then
		assertThat(size).isEqualTo(resultSize);
	}

	@ParameterizedTest
	@CsvSource(value = {
		"6000 : 3", "10000 : 7", "4000 : 1"
	}, delimiter = ':')
	void 입력된_금액과_수동번호갯수로_랜덤생성로또_리스트를_반환하는_기능테스트(String inputMoney, int resultSize) {
		// given
		List<String> inputList = Arrays.asList("12,3,4,5,6,7", "1,2,3,4,5,6", "1,2,3,4,5,6");
		LottoGenerator lottoGenerator = new LottoGenerator(inputMoney, inputList);

		// when
		List<LottoNumbers> lottoNumbersList = lottoGenerator.generateRandomLottoNumbers();

		// then
		assertAll(
			() -> assertThat(lottoNumbersList).hasSize(resultSize),
			() -> assertThat(lottoNumbersList.get(0)).isInstanceOf(LottoNumbers.class)
		);
	}

	@Test
	void 입력된_금액과_수동번호갯수가_다를떄_예외처리_기능테스트() {
		// given
		String inputMoney = "1000";
		List<String> inputList = Arrays.asList("12,3,4,5,6,7", "1,2,3,4,5,6", "1,2,3,4,5,6");
		LottoGenerator lottoGenerator = new LottoGenerator(inputMoney, inputList);

		// when // then
		assertThatThrownBy(() -> {
			lottoGenerator.generateLottoNumbers();
		}).isInstanceOf(LottoException.class)
			.hasMessageContaining(ErrorCode.NEGATIVE_AMOUNT_ERROR.getErrorMessage());
	}

	@ParameterizedTest
	@CsvSource(value = {
		"6000 : 6", "10000 : 10", "4000 : 4", "3000 : 3"
	}, delimiter = ':')
	void 입력된_금액과_수동번호갯수로_생성로또_리스트를_반환하는_기능테스트(String inputMoney, int resultSize) {
		// given
		List<String> inputList = Arrays.asList("12,3,4,5,6,7", "1,2,3,4,5,6", "1,2,3,4,5,6");
		LottoGenerator lottoGenerator = new LottoGenerator(inputMoney, inputList);

		// when
		List<LottoNumbers> lottoNumbersList = lottoGenerator.generateMixLottoNumbers();

		// then
		assertAll(
			() -> assertThat(lottoNumbersList).hasSize(resultSize),
			() -> assertThat(lottoNumbersList.get(0)).isInstanceOf(LottoNumbers.class),
			() -> assertThat(lottoNumbersList.get(0).equals(new LottoNumbers("12,3,4,5,6,7"))).isTrue()
		);
	}
}