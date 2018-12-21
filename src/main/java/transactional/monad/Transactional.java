package transactional.monad;

import java.util.function.Supplier;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import result.monad.Result;

public class Transactional {

	public static <A> Result<A> withTransaction(Supplier<A> code) {
		TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
		A result = null;
		try {
			transactionManager.begin(); // start tx
			result = code.get();
			transactionManager.commit(); // end tx
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			return Result.error(e.getMessage());
		}
		return Result.ok(result);
	}
	
	public static <A> Result<A> withTransaction(Supplier<A> code, boolean rollBackOnly) {
		TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
		A result = null;
		try {
			transactionManager.begin(); // start tx
			result = code.get();
			
			if (rollBackOnly) {
				throw new RollbackException("transaction rolled back");
			} 
			transactionManager.commit(); // end tx
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			return Result.error(e.getMessage());
		}
		return Result.ok(result);
	}

}
