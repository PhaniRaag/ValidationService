package com.epam.mck.validation.application.service.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.mck.validation.application.ValidationApplication;
import com.epam.mck.validation.application.configuration.impl.PasswordValidationConfiguration;
import com.epam.mck.validation.application.service.impl.PasswordValidationService;

/**
 * Unit Test of {@link PasswordValidationService}. Uses
 *  application-test.properties for test configuration
 * 
 * @author Raghavendra_Phani
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PasswordValidationServiceTest {

	@Configuration
	@Import(ValidationApplication.class)
	@PropertySource("classpath:application-test.properties")
	public static class Config {
	}

	@Autowired
	private PasswordValidationConfiguration configuration;

	@Autowired
	private PasswordValidationService validationService;

	private Map<String, String> errorMessages;

	@Before
	public void setup() {
		errorMessages = configuration.getErrorMessages();
	}
	
	
	@Test
	public void testForValidPassword() throws Exception {
		List<String> result = validationService.validate("abcde12345");
		assertTrue(result.isEmpty());
	}
	
	// test for upper case 

	@Test
	public void testForUpperCaseLettersAtStart() throws Exception {
		List<String> result = validationService.validate("Abcde");
		assertTrue(result.contains(configuration.getErrorMessages().get("upperCase")));
	}


	@Test
	public void testForUpperCaseLettersAtEnd() throws Exception {
		List<String> result = validationService.validate("abcdE");
		assertTrue(result.contains(errorMessages.get("upperCase")));
	}
	
	@Test
	public void testLengthBelowAndUpper() throws Exception {
		List<String> result = validationService.validate("ABCD");
		assertTrue(result.contains(errorMessages.get("length")));
		assertTrue(result.contains(errorMessages.get("upperCase")));
	}

	@Test
	public void testForAboveAndUpper() throws Exception {
		List<String> result = validationService.validate("ABCDEFGHIJKLMNOPQRST");
		assertTrue(result.contains(errorMessages.get("length")));
		assertTrue(result.contains(errorMessages.get("upperCase")));
	}
	
	@Test
	public void testForUpperMixedWithLowerNumber() throws Exception {
		List<String> result = validationService.validate("123aB");
		assertTrue(result.contains(errorMessages.get("upperCase")));
	}

	// test for length

	@Test
	public void testForLengthBelowFive() throws Exception {
		List<String> result = validationService.validate("a0");
		assertTrue(result.contains(errorMessages.get("length")));
	}



	@Test
	public void testLengthAboveLower() throws Exception {
		List<String> result = validationService.validate("abcdefghijklmnopqrst");
		assertTrue(result.contains(errorMessages.get("length")));
		assertTrue(result.contains(errorMessages.get("numbers")));
	}
	
 
	// test for only small letters 

	@Test
	public void testLengthBelowLower() throws Exception {
		List<String> result = validationService.validate("abcd");
		assertTrue(result.contains(errorMessages.get("length")));
		assertTrue(result.contains(errorMessages.get("numbers")));
	}
	@Test
	public void testForOnlyLower() throws Exception {
		List<String> result = validationService.validate("abcdefdfgsdfgdsfgdfsg");
		assertTrue(result.contains(errorMessages.get("numbers")));
		assertTrue(result.contains(errorMessages.get("length")));
	}

 
	// test for only small numbers and sequence
	@Test
	public void testForOnlyNumbers() throws Exception {
		List<String> result = validationService.validate("1234");
		assertTrue(result.contains(errorMessages.get("letters")));
		assertTrue(result.contains(errorMessages.get("length")));
	}

	@Test
	public void testLengthBelowSequenceNumbers() throws Exception {
		List<String> result = validationService.validate("1212");
		assertTrue(result.contains(errorMessages.get("letters")));
		assertTrue(result.contains(errorMessages.get("length")));
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testLengthAboveSequenceNumbers() throws Exception {
		List<String> result = validationService.validate("121212121212121212");
		assertTrue(result.contains(errorMessages.get("letters")));
		assertTrue(result.contains(errorMessages.get("length")));
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testForNumbersAbove() throws Exception {
		List<String> result = validationService.validate("12345678910111213");
		assertTrue(result.contains(errorMessages.get("letters")));
		assertTrue(result.contains(errorMessages.get("length")));
	}



	@Test
	public void testForSequenceLetters() throws Exception {
		List<String> result = validationService.validate("abcabc");
		assertTrue(result.contains(errorMessages.get("numbers")));
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testSequenceRepeatSingleLetter() throws Exception {
		List<String> result = validationService.validate("aaaaaa");
		assertTrue(result.contains(errorMessages.get("numbers")));
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testSequenceLettersNumbers() throws Exception {
		List<String> result = validationService.validate("ab1ab1");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testForSeqLettersAtEnd() throws Exception {
		List<String> result = validationService.validate("prefixabab");
		assertTrue(result.contains(errorMessages.get("sequence")));
		assertTrue(result.contains(errorMessages.get("numbers")));
	}

	@Test
	public void testForSeqLettersAtStart() throws Exception {
		List<String> result = validationService.validate("ababpostfix");
		assertTrue(result.contains(errorMessages.get("sequence")));
		assertTrue(result.contains(errorMessages.get("numbers")));
	}

	@Test
	public void testForNull() throws Exception {
		List<String> result = validationService.validate(null);
		assertTrue(result.contains(configuration.getNullPwd()));
	}

	@Test
	public void testForEmpty() throws Exception {
		List<String> result = validationService.validate("");
		assertTrue(result.contains(configuration.getNullPwd()));
	}

	@Test
	public void testForWhiteSpace() throws Exception {
		List<String> result = validationService.validate("     ");
		assertTrue(result.contains(errorMessages.get("special")));
	}

	

 
	@Test
	public void testForNoLetters() throws Exception {
		List<String> result = validationService.validate("12345");
		assertTrue(result.contains(errorMessages.get("letters")));
	}

	// test for special chars
	@Test
	public void testForSpecialCharAtEnd() throws Exception {
		List<String> result = validationService.validate("12345a!");
		assertTrue(result.contains(errorMessages.get("special")));
	}

	@Test
	public void testForSpecialCharAtStart() throws Exception {
		List<String> result = validationService.validate("!12345a");
		assertTrue(result.contains(errorMessages.get("special")));
	}

	@Test
	public void testForSpecialCharAtMiddle() throws Exception {
		List<String> result = validationService.validate("dab#c1");
		assertTrue(result.contains(errorMessages.get("special")));
		assertFalse(result.contains(errorMessages.get("length")));
	}
	@Test
	public void testTwoWordsToBeInvalid() throws Exception {
		List<String> result = validationService.validate("epam mck1");
		assertTrue(result.contains(errorMessages.get("special")));
	}
	
	// test for 3 letter sequence
	@Test
	public void testFor3LetterSeqAtMiddle() throws Exception {
		List<String> result = validationService.validate("12aaa345");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor3LetterSeqAtStart() throws Exception {
		List<String> result = validationService.validate("aaa123");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor3LetterSeqAtEnd() throws Exception {
		List<String> result = validationService.validate("12345aaa");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	// test for 4 letter sequence
	@Test
	public void testFor4LetterSeqAtMiddle() throws Exception {
		List<String> result = validationService.validate("12aaaa345");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor4LetterSeqAtStart() throws Exception {
		List<String> result = validationService.validate("aaaa123");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor4LetterSeqAtEnd() throws Exception {
		List<String> result = validationService.validate("12345aaaa");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	// test for 2 letter sequence
	@Test
	public void testFor2LetterSeqAtMiddle() throws Exception {
		List<String> result = validationService.validate("12aa345");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor2LetterSeqAtStart() throws Exception {
		List<String> result = validationService.validate("aa123");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor2LetterSeqAtEnd() throws Exception {
		List<String> result = validationService.validate("12345aa");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	// Test for 2 digit sequence
	@Test
	public void testFor2NumberSeqAtMiddle() throws Exception {
		List<String> result = validationService.validate("12a445");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor2NumberSeqAtStart() throws Exception {
		List<String> result = validationService.validate("4412345");
		assertTrue(result.contains(errorMessages.get("sequence")));
		assertTrue(result.contains(errorMessages.get("letters")));
	}

	@Test
	public void testFor2NumberSeqAtEnd() throws Exception {
		List<String> result = validationService.validate("1234544");
		assertTrue(result.contains(errorMessages.get("sequence")));
		assertTrue(result.contains(errorMessages.get("letters")));
	}

	// Test for 4 digit sequence
	@Test
	public void testFor4NumberSeqAtMiddle() throws Exception {
		List<String> result = validationService.validate("12a444445");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor4NumberSeqAtStart() throws Exception {
		List<String> result = validationService.validate("444412345");
		assertTrue(result.contains(errorMessages.get("sequence")));
		assertTrue(result.contains(errorMessages.get("letters")));
	}

	@Test
	public void testFor4NumberSeqAtEnd() throws Exception {
		List<String> result = validationService.validate("123454444");
		assertTrue(result.contains(errorMessages.get("sequence")));
		assertTrue(result.contains(errorMessages.get("letters")));
	}

	// Test for 2 mixed sequence
	@Test
	public void testFor2MixedSequenceAtStart() throws Exception {
		List<String> result = validationService.validate("a2a21");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor2MixedSequenceAtMiddle() throws Exception {
		List<String> result = validationService.validate("1a2a21");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor2MixedSequenceAtEnd() throws Exception {
		List<String> result = validationService.validate("1a2a2");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	// Test for 4 mixed sequence

	@Test
	public void testFor4MixedSequenceAtStart() throws Exception {
		List<String> result = validationService.validate("ab12ab121");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor4MixedSequenceAtMiddle() throws Exception {
		List<String> result = validationService.validate("112cd12cd1");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testFor4MixedSequenceAtEnd() throws Exception {
		List<String> result = validationService.validate("1abcdabcd");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void toTestForNoErrorOtherThanUpperCase() throws Exception {
		List<String> result = validationService.validate("Test123");
		assertTrue(result.contains(errorMessages.get("upperCase")));
		assertFalse(result.contains(errorMessages.get("letters")));
		assertFalse(result.contains(errorMessages.get("numbers")));
		assertFalse(result.contains(errorMessages.get("special")));
		assertFalse(result.contains(errorMessages.get("sequence")));
		assertFalse(result.contains(errorMessages.get("length")));

	}

	@Test
	public void toTestForNoErrorOtherThanNumbers() throws Exception {
		List<String> result = validationService.validate("abcdefghi");
		assertFalse(result.contains(errorMessages.get("upperCase")));
		assertFalse(result.contains(errorMessages.get("letters")));
		assertTrue(result.contains(errorMessages.get("numbers")));
		assertFalse(result.contains(errorMessages.get("special")));
		assertFalse(result.contains(errorMessages.get("sequence")));
		assertFalse(result.contains(errorMessages.get("length")));

	}
	
	@Test
	public void toTestForNoErrorOtherThanNull() throws Exception {
		List<String> result = validationService.validate("");
		assertTrue(result.contains(configuration.getNullPwd() ));
		assertFalse(result.contains(errorMessages.get("upperCase")));
		assertFalse(result.contains(errorMessages.get("letters")));
		assertFalse(result.contains(errorMessages.get("numbers")));
		assertFalse(result.contains(errorMessages.get("special")));
		assertFalse(result.contains(errorMessages.get("sequence")));
		assertFalse(result.contains(errorMessages.get("length")));
		 

	}

	@Test
	public void toTestForNoErrorOtherThanLetters() throws Exception {
		List<String> result = validationService.validate("123456");
		assertFalse(result.contains(errorMessages.get("upperCase")));
		assertTrue(result.contains(errorMessages.get("letters")));
		assertFalse(result.contains(errorMessages.get("numbers")));
		assertFalse(result.contains(errorMessages.get("special")));
		assertFalse(result.contains(errorMessages.get("sequence")));
		assertFalse(result.contains(errorMessages.get("length")));

	}

	@Test
	public void toTestForNoErrorOtherThanSpecial() throws Exception {
		List<String> result = validationService.validate("@tes1t");
		assertFalse(result.contains(errorMessages.get("upperCase")));
		assertFalse(result.contains(errorMessages.get("letters")));
		assertFalse(result.contains(errorMessages.get("numbers")));
		assertTrue(result.contains(errorMessages.get("special")));
		assertFalse(result.contains(errorMessages.get("sequence")));
		assertFalse(result.contains(errorMessages.get("length")));

	}

	@Test
	public void toTestForNoErrorOtherThanSequence() throws Exception {
		List<String> result = validationService.validate("tes1tes1");
		assertFalse(result.contains(errorMessages.get("upperCase")));
		assertFalse(result.contains(errorMessages.get("letters")));
		assertFalse(result.contains(errorMessages.get("numbers")));
		assertFalse(result.contains(errorMessages.get("special")));
		assertTrue(result.contains(errorMessages.get("sequence")));
		assertFalse(result.contains(errorMessages.get("length")));

	}

	@Test
	public void toTestForNoErrorOtherThanLength() throws Exception {
		List<String> result = validationService.validate("te12");
		assertFalse(result.contains(errorMessages.get("upperCase")));
		assertFalse(result.contains(errorMessages.get("letters")));
		assertFalse(result.contains(errorMessages.get("numbers")));
		assertFalse(result.contains(errorMessages.get("special")));
		assertFalse(result.contains(errorMessages.get("sequence")));
		assertTrue(result.contains(errorMessages.get("length")));

	}

	// test for random mixed sequence

	@Test
	public void testForOnlyMixedSequence() throws Exception {
		List<String> result = validationService.validate("ab1ab1");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testForRandomMixedSequenceNumbers() throws Exception {
		List<String> result = validationService.validate("test11weetqq33");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testForRandomMixedSequenceLetters() throws Exception {
		List<String> result = validationService.validate("test11weeeeeetest");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testForRandomMixedSequenceMoreNumbers() throws Exception {
		List<String> result = validationService.validate("test11w333333333333333test");
		assertTrue(result.contains(errorMessages.get("sequence")));
	}

	@Test
	public void testWithHash() throws Exception {

		List<String> result = validationService.validate("########################");
		assertTrue(result.contains(errorMessages.get("special")));
	}

	@Test
	public void testWithQuotes() throws Exception {

		List<String> result = validationService.validate("',,,,,,,''';;;;;;;;;;;;;;;;;;");
		assertTrue(result.contains(errorMessages.get("special")));
	}
	
	
	@Test
	public void testWithEquals() throws Exception {

		List<String> result = validationService.validate("=====");
		assertTrue(result.contains(errorMessages.get("special")));
	}
	
	@Test
	public void testWithPlus() throws Exception {

		List<String> result = validationService.validate("test+123");
		assertTrue(result.contains(errorMessages.get("special")));
	}
	

	@Test
	public void testWithMinus() throws Exception {

		List<String> result = validationService.validate("test-123");
		assertTrue(result.contains(errorMessages.get("special")));
	}

}
