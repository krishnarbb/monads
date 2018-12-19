package result.monad;

public abstract class DomainProcess {
	public abstract Result<Integer> readIntFromFile(String file);

	// some business logic
	public Result<Integer> adjustValue(Integer value) {
		if (value > 5) {
			Result.error("Value " + value + " should no be greater than 5");
		}
		return Result.ok(5 - value);
	}

	public Double calculateAverage(Integer val1, Integer val2) {
		return (val1 + val2) / 2d;
	}

	public Result<Double> businessOperation(String fileName, String fileName2) {
		Result<Integer> val1 = readIntFromFile(fileName);

		if (val1.isError()) {
			return Result.error(val1.getError());
		}

		Result<Integer> adjusted1 = adjustValue(val1.getValue());

		if (adjusted1.isError()) {
			return Result.error(adjusted1.getError());
		}

		Result<Integer> val2 = readIntFromFile(fileName2);

		if (val2.isError()) {
			return Result.error(val2.getError());
		}

		Double average = calculateAverage(adjusted1.getValue(), val2.getValue());
		return Result.ok(average);
	}

	// rewrite the businessOperation method using monadic composition.
	public Result<Double> businessOperation2(String fileName, String fileName2) {

		Result<Integer> adjustedValue = readIntFromFile(fileName).flatMap(this::adjustValue);

		return adjustedValue
				.flatMap(val1 -> readIntFromFile(fileName2).flatMap(val2 -> Result.ok(calculateAverage(val1, val2))));
	}

}
