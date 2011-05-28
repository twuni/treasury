package org.twuni.money.treasury.service;

import java.math.BigInteger;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.twuni.money.treasury.model.Dollar;

public class DollarServiceTest {

	private DollarService dollarService;

	@Before
	public void setUp() {
		dollarService = new MapDollarService();
	}

	@Test
	public void shouldCreateTwoValidDollarsOfTheProperAmountsWhenAskedToSplitOneDollarGivenSplitValueBetweenZeroAndItsWorth() {

		Dollar dollar = dollarService.create( 100 );
		int amount = 30;

		List<Dollar> dollars = dollarService.split( dollar, amount );

		Assert.assertEquals( 2, dollars.size() );
		Assert.assertEquals( 30, dollars.get( 0 ).getWorth() );
		Assert.assertEquals( 70, dollars.get( 1 ).getWorth() );

	}

	@Test
	public void shouldCreateValidDollarOfTheProperAmountWhenAskedToMergeTwoValidDollars() {

		Dollar a = dollarService.create( 30 );
		Dollar b = dollarService.create( 70 );

		Dollar dollar = dollarService.merge( a, b );

		Assert.assertEquals( a.getWorth() + b.getWorth(), dollar.getWorth() );

	}

	@Test
	public void shouldVoidDollarWhenItHasBeenSplit() {

		Dollar hundred = dollarService.create( 100 );

		dollarService.split( hundred, 30 );

		Assert.assertEquals( 0, dollarService.evaluate( hundred ) );

	}

	@Test
	public void shouldVoidDollarsWhenTheyHaveBeenMergedIntoAnotherDollar() {

		Dollar a = dollarService.create( 30 );
		Dollar b = dollarService.create( 70 );

		dollarService.merge( a, b );

		Assert.assertEquals( 0, dollarService.evaluate( a ) );
		Assert.assertEquals( 0, dollarService.evaluate( b ) );

	}

	@Test
	public void shouldReturnTheProperAmountWhenAskedForDollarsWorthGivenValidDollarId() {

		String dollarId = dollarService.create( 30 ).getId();

		int worth = dollarService.evaluate( dollarId );

		Assert.assertEquals( 30, worth );

	}

	@Test
	public void shouldReturnZeroWhenAskedForDollarsWorthGivenAnUnknownDollarId() {

		String dollarId = BigInteger.ZERO.toString();

		int worth = dollarService.evaluate( dollarId );

		Assert.assertEquals( 0, worth );

	}

	@Test
	public void shouldReturnZeroWhenAskedForDollarsWorthGivenIdForVoidedDollar() {

		Dollar hundred = dollarService.create( 100 );
		dollarService.split( hundred, 50 );

		int worth = dollarService.evaluate( hundred.getId() );

		Assert.assertEquals( 0, worth );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToMergeDollarsWhenNotGivenTheSecretToEitherDollar() {

		Dollar a = dollarService.create( 30 );
		Dollar b = dollarService.create( 70 );

		a.setSecret( null );

		dollarService.merge( a, b );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToSplitDollarWhenNotGivenItsSecret() {

		Dollar dollar = dollarService.create( 100 );

		dollar.setSecret( null );

		dollarService.split( dollar, 30 );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToMergeDollarWithItself() {

		Dollar dollar = dollarService.create( 100 );

		dollarService.merge( dollar, dollar );

	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToCreateDollarWhenGivenZeroWorth() {
		dollarService.create( 0 );
	}

	@Test( expected = IllegalArgumentException.class )
	public void shouldRefuseToCreateDollarWhenGivenNegativeWorth() {
		dollarService.create( -1 );
	}

}
