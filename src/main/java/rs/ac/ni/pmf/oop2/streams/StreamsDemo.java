package rs.ac.ni.pmf.oop2.streams;

import java.util.*;
import java.util.function.Supplier;
import lombok.Value;

public class StreamsDemo
{
	public static void main(String[] args)
	{
		List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<String> numberStrings = List.of("1", "2", "a3", "4", "5", "6", "7", "8", "b9", "10");
		List<String> empty = Collections.emptyList();

//		int sum = numbers.stream()
//			.filter(x -> x % 2 == 0)
//			.mapToInt(el -> el)
//			.sum();
//
//		System.out.println("Sum: " + sum);

//		int sum3 = 0;
//		for (final String el : numberStrings)
//		{
//			try
//			{
//				final int value = Integer.parseInt(el);
//				if (value % 2 == 0)
//				{
//					sum3 += value;
//				}
//			}
//			catch (final NumberFormatException e)
//			{
//				System.out.println("Error! Cannot transform string '" + el + "' to a number");
//			}
//		}
//		System.out.println(sum3);

		try
		{

			double average = numberStrings.stream()
				.map(StreamsDemo::parseStringValue)
				.filter(Optional::isPresent)
				.mapToInt(Optional::get)
				.filter(el -> el > 10000)
				//			.filter(x -> x % 2 == 0)
				.average()
				//			.orElse(0.0);
				.orElseThrow(() -> new Exception("No average value available"));

			System.out.println("Average (string array): " + average);
		}
		catch (final Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	private static Optional<Integer> parseStringValue(final String value)
	{
		try
		{
			return Optional.of(Integer.parseInt(value));
		}
		catch (final NumberFormatException e)
		{
			System.out.println("Error! Cannot transform string '" + value + "' to a number");
		}

		return Optional.empty();
	}

	public static void main2(String[] args)
	{
		int[] numbersArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		int sum = 0;

		/*
			1 -> 2 -> 3 -> 4 -> 5 -> 6...
	filter	|    |    |    |    |    |
			x    2    x    4    x    6
			     2   ->    4    ->   6   -> ...
	map		     |		   |         |
			     3*2+1 -> 3*4+1 ->  3*6+1
			     7   ->    13   ->   19  -> ...
	forEach
		 */

		//		numbers.stream()
		//			.filter(el -> el % 2 == 0)
		//			.map(el -> 3 * el + 1)
		//			.forEach(System.out::println);

		//		numbers.stream()
		//			.filter(el -> el % 2 == 1)
		//			.map(el -> el - 1)
		//			.forEach(System.out::println);

		numbers.stream()
			.filter(el -> el % 3 != 2)
			.map(StreamsDemo::transform)
			.forEach(pair -> System.out.println(pair.original + " -> " + pair.transformed));

//		for (int i = 0; i < numbers.size(); i++)
//		{
//			final int el = numbers.get(i);
//			if (el % 3 == 0)
//			{
//				int transformed = 3 * el + 1;
//				System.out.println(el + " -> " + transformed);
//			}
//			else if (el % 3 == 1)
//			{
//				int transformed = el - 1;
//				System.out.println(el + " -> " + transformed);
//			}
//		}

		//		for (final Integer el : numbers)
		//		{
		//			if (el % 2 == 0)
		//			{
		//				sum += el;
		//			}
		//		}

		//		System.out.println(sum);
	}

	private static TransformationPair transform(final Integer el)
	{
		if (el % 3 == 0)
		{
			return new TransformationPair(el, 3 * el + 1);
		}

		return new TransformationPair(el, el - 1);
	}


	@Value
	private static class TransformationPair
	{
		Integer original;
		Integer transformed;
	}
}
