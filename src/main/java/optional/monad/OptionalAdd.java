package optional.monad;

import java.util.Optional;

public class OptionalAdd {
	//Notice how we had to manually “unwrap the Integer from the Optional context” by checking whether the numbers are present (line 9), 
	//and doing something with the empty case (line12).
	public Optional<Integer> optionalAdd(Optional<Integer> val1, Optional<Integer> val2) {
	    if(val1.isPresent() && val2.isPresent()) {
	        return Optional.of(val1.get() + val2.get());
	    }
	    return Optional.empty();
	}
	
	// Notice how we didn’t need to check whether the values are present and deal with the empty case. 
	// The Optional monad via its flatMap method properly unwraps the parameter for us. 
	// It won’t invoke the second flatMap mapper function if the first Optional is empty. 
	// In fact, if any of the Optionals involved in the composition is empty, the result will be an empty Optional.
	
	public Optional<Integer> optionalAddWithFlatMap(Optional<Integer> val1, Optional<Integer> val2) {
	    return
	           val1.flatMap( first ->
	           val2.flatMap( second ->
	           Optional.of(first + second)
	    ));
	}
	
	
	
}
