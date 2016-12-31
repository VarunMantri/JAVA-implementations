import java.util.*;


//test program that compares java's inbuilt implementation of treeset and my implementation of treeset


public class Test {
    long milliSeconds = 0;
    long deltaMilliSeconds = 0;
    TreeSet aTreeSet;
    final int MAX_ELEMENTS = 300000;
    String allObjects[] = new String[MAX_ELEMENTS];
    String whichOne;
   
    public Test() {
    }

    public void initArray()	{
	List<String> aList;
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		allObjects[index] = new String("" + index  );
	}
	aList = Arrays.asList(allObjects);
	Collections.shuffle(aList);
	allObjects  =  (String[])aList.toArray();
    }
    public void initArraySorted()	{
	List<String> aList;
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		allObjects[index] = new String("" + index  );
	}
    }
    public void rip(String reason, int exitCode)	{
		System.out.println(whichOne + "!" + reason);
		System.exit(exitCode);
	}
    public void start()	{
	milliSeconds = System.currentTimeMillis();
	deltaMilliSeconds = milliSeconds;
    }

    public Test  init(TreeSet aTreeSet)	{
	this.aTreeSet = aTreeSet;
	return this;
    }
    public void end(String whatKind)	{
	System.err.println(whatKind + ": Time for all:	" + ( System.currentTimeMillis() - milliSeconds) );
    }
    public void delta(String whatKind)	{
	System.err.println(whatKind + ": Time for all:	" + ( System.currentTimeMillis() - deltaMilliSeconds) );
	deltaMilliSeconds = System.currentTimeMillis();
    }

    public void addTest()	{
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		if ( !  aTreeSet.add(allObjects[index] ) )
			rip("addTest failed " + index, 10);
		if ( aTreeSet.size() != index + 1  )	
			rip("add  size failed ", 1);
	}
    }
    public void contains()	{
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		if ( !  aTreeSet.contains(allObjects[index] ) )	
			rip("contains failed " + index , 3);
	}
    }
    public void doesNotcontain()	{
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		if (   aTreeSet.contains("-" + allObjects[index] ) )	
			rip("doesNotcontain failed ", 4);
	}
    }
    public void remove()	{
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		if ( !  aTreeSet.remove(allObjects[index] ) )
			rip("remove failed ", 5);
		if ( aTreeSet.size() != MAX_ELEMENTS - index  )	{
			System.out.println(aTreeSet.size());
			 rip("remove size failed ", 6);
		}
	}
    }
    public void removeMore()	{
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		if (   aTreeSet.remove("-" + allObjects[index] ) )
			rip("removeMore failed ", 7);
			
	}
    }
    public void clearAll()	{
	for ( int index = 0; index < MAX_ELEMENTS; index++ )	{
		 aTreeSet.clear();
	}
	if ( aTreeSet.size() != 0 )	
			rip("clear size failed ", 8);
    }
    public void iteratorTest()	{
	initArray();
	Object allObjectsToRemove[] = new Object[MAX_ELEMENTS];

	aTreeSet.clear();
	Iterator aIterator = aTreeSet.iterator();		// order is important
	while ( aIterator.hasNext() )	
		rip(" iterator test failed with empty hashset", 9);

	addTest();
	for ( int index = 0; index < MAX_ELEMENTS; index ++ )	{
		aTreeSet.add( allObjects[index] );
	}
	aIterator = aTreeSet.iterator();		// order is important
	int runner = 0;
	while ( aIterator.hasNext() )	{
		allObjectsToRemove[runner++] = aIterator.next();	// remove can not be done ...
	}
	if ( runner != MAX_ELEMENTS )
		rip("iterator test failed with not finding all objects", 10);
    }
    public void executeTest(String whichOne)	{
	this.whichOne = whichOne;
	iteratorTest();
	delta("	" + whichOne +":iteratorTest");
	clearAll();
	delta("	" + whichOne +":clearAll");
	addTest();
	delta("	" + whichOne +":addTest");
	contains();
	delta("	" + whichOne +":contains");
	clearAll();
	delta("	" + whichOne +":clearAll");
	doesNotcontain();
	delta("	" + whichOne +":Notcontains");
	addTest();
	delta("	" + whichOne +":addTest");
	clearAll();
	delta("	" + whichOne +":clearAll");
    }
    public void testIt()	{
	start();
		initArray();
		(init(new TreeSet())).executeTest("TreeSet");
		initArraySorted();
		(init(new TreeSet())).executeTest("TreeSet");
	end("TreeSet");
	start();
		initArray();
		(init(new TreeSetV())).executeTest("VTreeSet");
		initArraySorted();
		(init(new TreeSetV())).executeTest("VTreeSet");
	end("HpTreeSet");
   }

   public static void main(String args[] )	{
	new Test().testIt();
	System.exit(0);
   }
}
