package transactional.monad;

import static transactional.monad.Transactional.withTransaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import result.monad.Result;

import java.util.Random;

public class Example {
    static final Logger logger = LogManager.getLogger(Example.class.getName());

    public static void main(final String[] args) {
        final Result<Double> successfulTransaction = withTransaction(Example::calculateCosts);
    	System.out.println("First transacted value = " + successfulTransaction.getValue());
    	
        final Result<Double> transactionFailure = withTransaction(Example::calculateCosts, true);
        if (transactionFailure.isError()) {
        	System.out.println("Status of the next transaction = " + transactionFailure.getError());
        }
    }

    private static Double calculateCosts() {
        pretendToWorkHard();
        return 4567.3;
    }

    private static final Random r = new Random();
    private static final Integer MAX_WORK_TIME_MS = 2000;
    private static void pretendToWorkHard() {
        try {
            Thread.sleep(r.nextInt(MAX_WORK_TIME_MS));
        } catch (InterruptedException e) {
        }
    }
}
