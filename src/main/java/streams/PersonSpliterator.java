package streams;

import java.util.Spliterator;
import java.util.function.Consumer;

public class PersonSpliterator implements Spliterator<Person> {

	private Spliterator<String> fileSpliterator;
	private int age;
	private String name;
	private String city;

	public PersonSpliterator(Spliterator<String> fileSpliterator) {
		this.fileSpliterator = fileSpliterator;
	}

	@Override
	public boolean tryAdvance(Consumer<? super Person> action) {
		if (fileSpliterator.tryAdvance(line -> name = line) &&
		fileSpliterator.tryAdvance(line -> age = Integer.parseInt(line)) &&
		fileSpliterator.tryAdvance(line -> city = line)) {
			Person p = new Person(name,age,city);
			action.accept(p);
			return true;
		}else
			return false;
	}

	@Override
	public Spliterator<Person> trySplit() {
		return null;
	}

	@Override
	public long estimateSize() {
		return fileSpliterator.estimateSize() /3;
	}

	@Override
	public int characteristics() {
		return fileSpliterator.characteristics();
	}

}
