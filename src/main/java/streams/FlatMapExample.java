package streams;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FlatMapExample {
	
	List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    List<Integer> list2 = Arrays.asList(2, 4, 6);
    List<Integer> list3 = Arrays.asList(3, 5, 7);

    public static void main(String... args) {
    	
    	List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<Integer> list2 = Arrays.asList(2, 4, 6);
        List<Integer> list3 = Arrays.asList(3, 5, 7);
        
    	List<List<Integer>> list = Arrays.asList(list1, list2, list3);
    	
    	List<Integer> resultLst = new ArrayList<>();
    	list.stream().forEach(ls -> ls.stream().forEach(resultLst::add));
    	
    	//resultLst.stream().forEach(System.out::println);
        
        new FlatMapExample().flatMapUsingSpliterator();
    }

	private void flatMapExample() {
		List<List<Integer>> list = Arrays.asList(list1, list2, list3);
        
        System.out.println(list);
        
        Function<List<?>, Integer> size = List::size;
        Function<List<Integer>, Stream<Integer>> flatmapper = List::stream;
        
        list.stream()
                .flatMap(flatmapper)
                .forEach(System.out::println);
	}
	
	private void flatMapUsingSpliterator() {
		Stream<List<Integer>> streamOfList = Arrays.asList(list1, list2, list3).stream();
		Spliterator<List<Integer>> listSpliterator = streamOfList.spliterator();
		
		Spliterator<Integer> mySpliterator = new MyFlatMapSpliterator(listSpliterator);
		
		 Stream<Integer> myStream = StreamSupport.stream(mySpliterator, false);
		 myStream.forEach(System.out::println);
	}
	
	static class MyFlatMapSpliterator implements Spliterator<Integer>{
		
		private Spliterator<List<Integer>> listSpliterator;
		private List<Integer> lst;
		private int start=0;

		public MyFlatMapSpliterator(Spliterator<List<Integer>> listSpliterator) {
			this.listSpliterator = listSpliterator;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Integer> action) {
			if(lst==null) {
				if(listSpliterator.tryAdvance(list -> lst = list)) {
					action.accept(lst.get(start++));
					return true;
				}else {
					return false;
				}
			}else {
				if(start < lst.size()) {
					action.accept(lst.get(start++));
				}else {
					lst=null;
					start=0;
				}
				return true;
			}
		}

		@Override
		public Spliterator<Integer> trySplit() {
			return null;
		}

		@Override
		public long estimateSize() {
			return listSpliterator.estimateSize();
		}

		@Override
		public int characteristics() {
			return listSpliterator.characteristics();
		}
		
	}
	
}

